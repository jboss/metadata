/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionImpl;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;


/**
 * @author Remy Maucherat
 */
public class DescriptionMetaDataParser extends MetaDataElementParser {

    public static DescriptionImpl parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        DescriptionImpl description = new DescriptionImpl();
        // Handle attributes
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            final String value = reader.getAttributeValue(i);
            if ("http://www.w3.org/XML/1998/namespace".equals(reader.getAttributeNamespace(i))
                    && Attribute.forName(reader.getAttributeLocalName(i)) == Attribute.LANG) {
                description.setLanguage(value);
            }
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case ID: {
                    description.setId(value);
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }
        description.setDescription(getElementText(reader, propertyReplacer));
        return description;
    }

}
