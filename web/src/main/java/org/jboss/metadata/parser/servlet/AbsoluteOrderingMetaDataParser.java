/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.spec.AbsoluteOrderingMetaData;
import org.jboss.metadata.web.spec.NameMetaData;
import org.jboss.metadata.web.spec.OrderingElementMetaData;
import org.jboss.metadata.web.spec.OthersMetaData;

/**
 * @author Remy Maucherat
 */
public class AbsoluteOrderingMetaDataParser extends MetaDataElementParser {

    public static AbsoluteOrderingMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        AbsoluteOrderingMetaData ordering = new AbsoluteOrderingMetaData();

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case NAME:
                    List<OrderingElementMetaData> order = ordering.getOrdering();
                    if (order == null) {
                        order = new ArrayList<OrderingElementMetaData>();
                        ordering.setOrdering(order);
                    }
                    NameMetaData name = new NameMetaData();
                    name.setName(getElementText(reader, propertyReplacer));
                    order.add(name);
                    break;
                case OTHERS:
                    List<OrderingElementMetaData> order2 = ordering.getOrdering();
                    if (order2 == null) {
                        order2 = new ArrayList<OrderingElementMetaData>();
                        ordering.setOrdering(order2);
                    }
                    OthersMetaData others = new OthersMetaData();
                    order2.add(others);
                    requireNoContent(reader);
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return ordering;
    }

}
