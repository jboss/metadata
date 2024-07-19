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
import org.jboss.metadata.web.spec.MimeMappingMetaData;

/**
 * @author Remy Maucherat
 */
public class MimeMappingMetaDataParser extends MetaDataElementParser {

    public static MimeMappingMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        MimeMappingMetaData mimeMapping = new MimeMappingMetaData();

        IdMetaDataParser.parseAttributes(reader, mimeMapping);

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case EXTENSION:
                    mimeMapping.setExtension(getElementText(reader, propertyReplacer));
                    break;
                case MIME_TYPE:
                    mimeMapping.setMimeType(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return mimeMapping;
    }

}
