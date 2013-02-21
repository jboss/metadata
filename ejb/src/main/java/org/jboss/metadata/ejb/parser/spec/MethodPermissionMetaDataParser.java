/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.metadata.ejb.parser.spec;

import java.util.HashSet;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.MethodMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionMetaData;
import org.jboss.metadata.ejb.spec.MethodsMetaData;
import org.jboss.metadata.javaee.spec.EmptyMetaData;
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.property.PropertyReplacer;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * Processes &lt;method-permission&gt; element in ejb-jar.xml
 * <p/>
 * User: Jaikiran Pai
 */
public class MethodPermissionMetaDataParser extends AbstractWithDescriptionsParser<MethodPermissionMetaData> {
    private static final AttributeProcessor<IdMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<IdMetaData>(UnexpectedAttributeProcessor.instance());
    public static final MethodPermissionMetaDataParser INSTANCE = new MethodPermissionMetaDataParser();

    @Override
    public MethodPermissionMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        MethodPermissionMetaData methodPermission = new MethodPermissionMetaData();
        processAttributes(methodPermission, reader, ATTRIBUTE_PROCESSOR);
        this.processElements(methodPermission, reader, propertyReplacer);
        return methodPermission;

    }

    @Override
    protected void processElement(MethodPermissionMetaData methodPermission, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case ROLE_NAME:
                Set<String> roles = methodPermission.getRoles();
                if (roles == null) {
                    roles = new HashSet<String>();
                    methodPermission.setRoles(roles);
                }
                roles.add(getElementText(reader, propertyReplacer));
                return;

            case UNCHECKED:
                final EmptyMetaData unchecked = new EmptyMetaData();
                processAttributes(unchecked, reader, ATTRIBUTE_PROCESSOR);
                // read away the emptiness
                reader.getElementText();
                methodPermission.setUnchecked(unchecked);
                return;

            case METHOD:
                MethodsMetaData methods = methodPermission.getMethods();
                if (methods == null) {
                    methods = new MethodsMetaData();
                    methodPermission.setMethods(methods);
                }
                MethodMetaData method = MethodMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                methods.add(method);
                return;

            default:
                super.processElement(methodPermission, reader, propertyReplacer);

        }
    }
}
