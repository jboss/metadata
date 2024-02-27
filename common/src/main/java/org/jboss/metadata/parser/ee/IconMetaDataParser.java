/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.IconImpl;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.property.PropertyReplacers;


/**
 * @author Remy Maucherat
 */
public class IconMetaDataParser extends MetaDataElementParser {

    public static IconImpl parse(XMLStreamReader reader) throws XMLStreamException {
        return parse(reader, PropertyReplacers.noop());
    }

    public static IconImpl parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        IconImpl icon = new IconImpl();
        // Handle attributes
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            final String value = reader.getAttributeValue(i);
            if ("http://www.w3.org/XML/1998/namespace".equals(reader.getAttributeNamespace(i))
                    && Attribute.forName(reader.getAttributeLocalName(i)) == Attribute.LANG) {
                icon.setLanguage(value);
            }
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case ID: {
                    icon.setId(value);
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case SMALL_ICON:
                    icon.setSmallIcon(getElementText(reader, propertyReplacer));
                    break;
                case LARGE_ICON:
                    icon.setLargeIcon(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }
        return icon;
    }

}
