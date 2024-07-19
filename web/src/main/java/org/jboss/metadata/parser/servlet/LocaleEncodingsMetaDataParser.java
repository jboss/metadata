/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.ee.IdMetaDataParser;
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

        IdMetaDataParser.parseAttributes(reader, localeEncodings);

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
