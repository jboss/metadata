/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DisplayNameImpl;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.property.PropertyReplacers;


/**
 * @author Remy Maucherat
 */
public class DisplayNameMetaDataParser extends MetaDataElementParser {

    public static DisplayNameImpl parse(XMLStreamReader reader) throws XMLStreamException {
        return parse(reader, PropertyReplacers.noop());
    }

    public static DisplayNameImpl parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        DisplayNameImpl displayName = new DisplayNameImpl();
        // Handle attributes
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            final String value = reader.getAttributeValue(i);
            if ("http://www.w3.org/XML/1998/namespace".equals(reader.getAttributeNamespace(i))
                    && Attribute.forName(reader.getAttributeLocalName(i)) == Attribute.LANG) {
                displayName.setLanguage(value);
            }
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case ID: {
                    displayName.setId(value);
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }
        displayName.setDisplayName(getElementText(reader, propertyReplacer));
        return displayName;
    }

}
