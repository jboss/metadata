/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.ee.IdMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.spec.LocaleEncodingMetaData;

/**
 * @author Remy Maucherat
 */
public class LocaleEncodingMetaDataParser extends MetaDataElementParser {

    public static LocaleEncodingMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        LocaleEncodingMetaData localeEncoding = new LocaleEncodingMetaData();

        IdMetaDataParser.parseAttributes(reader, localeEncoding);

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case LOCALE:
                    localeEncoding.setLocale(getElementText(reader, propertyReplacer));
                    break;
                case ENCODING:
                    localeEncoding.setEncoding(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return localeEncoding;
    }

}
