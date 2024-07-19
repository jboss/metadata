/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.PropertyMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author Remy Maucherat
 */
public class PropertyMetaDataParser extends MetaDataElementParser {

    public static PropertyMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        PropertyMetaData property = new PropertyMetaData();

        IdMetaDataParser.parseAttributes(reader, property);

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case NAME:
                    property.setName(getElementText(reader, propertyReplacer));
                    break;
                case VALUE:
                    property.setValue(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return property;
    }

}
