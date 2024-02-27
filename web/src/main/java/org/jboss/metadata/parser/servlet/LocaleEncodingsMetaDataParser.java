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
import org.jboss.metadata.web.spec.LocaleEncodingMetaData;
import org.jboss.metadata.web.spec.LocaleEncodingsMetaData;

/**
 * @author Remy Maucherat
 */
public class LocaleEncodingsMetaDataParser extends MetaDataElementParser {

    public static LocaleEncodingsMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        LocaleEncodingsMetaData localeEncodings = new LocaleEncodingsMetaData();

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
                    localeEncodings.setId(value);
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
                case LOCALE_ENCODING_MAPPING:
                    List<LocaleEncodingMetaData> localeEncodingsList = localeEncodings.getMappings();
                    if (localeEncodingsList == null) {
                        localeEncodingsList = new ArrayList<LocaleEncodingMetaData>();
                        localeEncodings.setMappings(localeEncodingsList);
                    }
                    localeEncodingsList.add(LocaleEncodingMetaDataParser.parse(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return localeEncodings;
    }

}
