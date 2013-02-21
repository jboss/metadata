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

import org.jboss.metadata.ejb.spec.CMRFieldMetaData;
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.property.PropertyReplacer;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * @author John Bailey
 */
public class RelationRoleCmrFieldMetaDataParser extends AbstractWithDescriptionsParser<CMRFieldMetaData> {

    public static final RelationRoleCmrFieldMetaDataParser INSTANCE = new RelationRoleCmrFieldMetaDataParser();
    private static final AttributeProcessor<IdMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<IdMetaData>(UnexpectedAttributeProcessor.instance());

    /**
     * Creates and returns {@link org.jboss.metadata.ejb.spec.CMRFieldMetaData} after parsing the cmr-field element.
     *
     * @param reader
     * @return
     * @throws javax.xml.stream.XMLStreamException
     *
     */
    @Override
    public CMRFieldMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {

        CMRFieldMetaData data = new CMRFieldMetaData();
        processAttributes(data, reader, ATTRIBUTE_PROCESSOR);
        this.processElements(data, reader, propertyReplacer);
        return data;
    }

    @Override
    protected void processElement(CMRFieldMetaData field, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case CMR_FIELD_NAME: {
                field.setCmrFieldName(getElementText(reader, propertyReplacer));
                return;
            }
            case CMR_FIELD_TYPE: {
                field.setCmrFieldType(getElementText(reader, propertyReplacer));
                return;
            }
            default:
                super.processElement(field, reader, propertyReplacer);
                break;
        }
    }
}
