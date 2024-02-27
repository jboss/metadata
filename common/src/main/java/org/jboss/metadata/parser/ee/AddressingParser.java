/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.Addressing;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author Remy Maucherat
 */
public class AddressingParser extends MetaDataElementParser {

    public static Addressing parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        Addressing addressing = new Addressing();

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case ENABLED:
                    addressing.setEnabled(Boolean.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case REQUIRED:
                    addressing.setRequired(Boolean.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case RESPONSES:
                    addressing.setResponses(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return addressing;
    }

}
