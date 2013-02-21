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

import org.jboss.metadata.ejb.spec.MethodInterfaceType;
import org.jboss.metadata.ejb.spec.MethodMetaData;
import org.jboss.metadata.ejb.spec.MethodParametersMetaData;
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.property.PropertyReplacer;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * Parses and creates metadata out of &lt;method&gt; element belonging to the &lt;container-transaction&gt;
 * element in a ejb-jar.xml
 * <p/>
 * Author : Jaikiran Pai
 */
public class MethodMetaDataParser extends AbstractWithDescriptionsParser<MethodMetaData> {
    private static final AttributeProcessor<IdMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<IdMetaData>(UnexpectedAttributeProcessor.instance());

    /**
     * Instance of this parser
     */
    public static final MethodMetaDataParser INSTANCE = new MethodMetaDataParser();

    @Override
    public MethodMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        MethodMetaData methodMetaData = new MethodMetaData();
        processAttributes(methodMetaData, reader, ATTRIBUTE_PROCESSOR);
        this.processElements(methodMetaData, reader, propertyReplacer);
        return methodMetaData;
    }

    @Override
    protected void processElement(MethodMetaData methodMetaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case EJB_NAME:
                String ejbName = getElementText(reader, propertyReplacer);
                methodMetaData.setEjbName(ejbName);
                return;

            case METHOD_NAME:
                String methodName = getElementText(reader, propertyReplacer);
                methodMetaData.setMethodName(methodName);
                return;

            case METHOD_PARAMS:
                MethodParametersMetaData methodParams = MethodParametersMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                methodMetaData.setMethodParams(methodParams);
                return;

            case METHOD_INTF:
                String methodIntfValue = getElementText(reader, propertyReplacer);
                if (methodIntfValue == null || methodIntfValue.isEmpty()) {
                    throw unexpectedValue(reader, new Exception("Unexpected null or empty value for method-intf element"));
                }
                MethodInterfaceType methodIntf = MethodInterfaceType.valueOf(methodIntfValue);
                methodMetaData.setMethodIntf(methodIntf);
                return;

            default:
                super.processElement(methodMetaData, reader, propertyReplacer);
        }
    }
}
