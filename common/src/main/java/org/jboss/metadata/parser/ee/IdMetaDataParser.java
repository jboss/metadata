/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;

/**
 * @author Paul Ferraro
 */
public class IdMetaDataParser extends MetaDataElementParser {

    public static void parseAttributes(XMLStreamReader reader, IdMetaData metaData) throws XMLStreamException {
        for (int i = 0; i < reader.getAttributeCount(); i++) {
            parseAttribute(reader, i, metaData);
        }
    }

    public static void parseAttribute(XMLStreamReader reader, int index, IdMetaData metaData) throws XMLStreamException {
        if (!attributeHasNamespace(reader, index)) {
            switch (Attribute.forName(reader.getAttributeLocalName(index))) {
                case ID: {
                    metaData.setId(reader.getAttributeValue(index));
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, index);
            }
        }
    }
}
