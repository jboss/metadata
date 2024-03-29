/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.spec.CookieConfigMetaData;

/**
 * @author Remy Maucherat
 */
public class CookieConfigMetaDataParser extends MetaDataElementParser {

    public static CookieConfigMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        CookieConfigMetaData cookieConfig = new CookieConfigMetaData();

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
                    cookieConfig.setId(value);
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
                case NAME:
                    cookieConfig.setName(getElementText(reader, propertyReplacer));
                    break;
                case DOMAIN:
                    cookieConfig.setDomain(getElementText(reader, propertyReplacer));
                    break;
                case PATH:
                    cookieConfig.setPath(getElementText(reader, propertyReplacer));
                    break;
                case COMMENT:
                    cookieConfig.setComment(getElementText(reader, propertyReplacer));
                    break;
                case HTTP_ONLY:
                    if (Boolean.TRUE.equals(Boolean.valueOf(getElementText(reader, propertyReplacer)))) {
                        cookieConfig.setHttpOnly(true);
                    } else {
                        cookieConfig.setHttpOnly(false);
                    }
                    break;
                case SECURE:
                    if (Boolean.TRUE.equals(Boolean.valueOf(getElementText(reader, propertyReplacer)))) {
                        cookieConfig.setSecure(true);
                    } else {
                        cookieConfig.setSecure(false);
                    }
                    break;
                case MAX_AGE:
                    try {
                        cookieConfig.setMaxAge(Integer.valueOf(getElementText(reader, propertyReplacer)));
                    } catch (NumberFormatException e) {
                        throw unexpectedValue(reader, e);
                    }
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return cookieConfig;
    }

}
