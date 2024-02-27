/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.spec.OrderingMetaData;

/**
 * @author Remy Maucherat
 */
public class OrderingMetaDataParser extends MetaDataElementParser {

    public static OrderingMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        OrderingMetaData ordering = new OrderingMetaData();

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case BEFORE:
                    ordering.setBefore(RelativeOrderingMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case AFTER:
                    ordering.setAfter(RelativeOrderingMetaDataParser.parse(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return ordering;
    }

}
