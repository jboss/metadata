/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.parser.ee;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.support.LanguageMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;

/**
 * @author Paul Ferraro
 */
public class LanguageMetaDataParser extends MetaDataElementParser {

    public static void parseAttributes(XMLStreamReader reader, LanguageMetaData metaData) throws XMLStreamException {
        for (int i = 0; i < reader.getAttributeCount(); i++) {
            parseAttribute(reader, i, metaData);
        }
    }

    public static void parseAttribute(XMLStreamReader reader, int index, LanguageMetaData metaData) throws XMLStreamException {
        switch (Attribute.forName(reader.getAttributeLocalName(index))) {
            case LANG: {
                if (XMLConstants.XML_NS_URI.equals(reader.getAttributeNamespace(index))) {
                    metaData.setLanguage(reader.getAttributeValue(index));
                    break;
                } // Otherwise, fall through
            }
            default:
                IdMetaDataParser.parseAttribute(reader, index, metaData);
        }
    }
}
