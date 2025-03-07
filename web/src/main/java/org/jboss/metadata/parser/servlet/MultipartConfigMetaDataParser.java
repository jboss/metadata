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
import org.jboss.metadata.web.spec.MultipartConfigMetaData;

/**
 * @author Remy Maucherat
 */
public class MultipartConfigMetaDataParser extends MetaDataElementParser {

    public static MultipartConfigMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        MultipartConfigMetaData multipartConfig = new MultipartConfigMetaData();

        IdMetaDataParser.parseAttributes(reader, multipartConfig);

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case LOCATION:
                    multipartConfig.setLocation(getElementText(reader, propertyReplacer));
                    break;
                case MAX_FILE_SIZE:
                    try {
                        multipartConfig.setMaxFileSize(Long.valueOf(getElementText(reader, propertyReplacer)));
                    } catch (NumberFormatException e) {
                        throw unexpectedValue(reader, e);
                    }
                    break;
                case MAX_REQUEST_SIZE:
                    try {
                        multipartConfig.setMaxRequestSize(Long.valueOf(getElementText(reader, propertyReplacer)));
                    } catch (NumberFormatException e) {
                        throw unexpectedValue(reader, e);
                    }
                    break;
                case FILE_SIZE_THRESHOLD:
                    try {
                        multipartConfig.setFileSizeThreshold(Integer.valueOf(getElementText(reader, propertyReplacer)));
                    } catch (NumberFormatException e) {
                        throw unexpectedValue(reader, e);
                    }
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return multipartConfig;
    }

}
