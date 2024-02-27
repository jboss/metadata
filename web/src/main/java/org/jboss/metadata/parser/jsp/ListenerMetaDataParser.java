/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.jsp;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.parser.ee.DescriptionGroupMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.web.spec.ListenerMetaData;

/**
 * @author Remy Maucherat
 */
public class ListenerMetaDataParser extends MetaDataElementParser {

    public static ListenerMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        ListenerMetaData listener = new ListenerMetaData();

        // Handle attributes
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            final String value = reader.getAttributeValue(i);
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case ID: {
                    listener.setId(value);
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }

        DescriptionGroupMetaData descriptionGroup = new DescriptionGroupMetaData();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionGroupMetaDataParser.parse(reader, descriptionGroup)) {
                if (listener.getDescriptionGroup() == null) {
                    listener.setDescriptionGroup(descriptionGroup);
                }
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case LISTENER_CLASS:
                    listener.setListenerClass(getElementText(reader));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return listener;
    }

}
