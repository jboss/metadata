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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.ExcludeListMetaData;
import org.jboss.metadata.ejb.spec.MethodMetaData;
import org.jboss.metadata.ejb.spec.MethodsMetaData;
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.property.PropertyReplacer;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * Processes &lt;exclude-list&gt; element of ejb-jar.xml
 * <p/>
 * User: Jaikiran Pai
 */
public class ExcludeListMetaDataParser extends AbstractWithDescriptionsParser<ExcludeListMetaData> {
    private static final AttributeProcessor<IdMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<IdMetaData>(UnexpectedAttributeProcessor.instance());
    public static final ExcludeListMetaDataParser INSTANCE = new ExcludeListMetaDataParser();

    @Override
    public ExcludeListMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        ExcludeListMetaData excludeListMetaData = new ExcludeListMetaData();
        processAttributes(excludeListMetaData, reader, ATTRIBUTE_PROCESSOR);
        this.processElements(excludeListMetaData, reader, propertyReplacer);
        return excludeListMetaData;
    }

    @Override
    protected void processElement(ExcludeListMetaData excludeList, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case METHOD:
                MethodsMetaData methods = excludeList.getMethods();
                if (methods == null) {
                    methods = new MethodsMetaData();
                    excludeList.setMethods(methods);
                }
                MethodMetaData method = MethodMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                methods.add(method);
                return;

            default:
                super.processElement(excludeList, reader, propertyReplacer);

        }
    }
}
