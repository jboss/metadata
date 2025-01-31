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
import org.jboss.metadata.web.spec.TaglibMetaData;

/**
 * @author Remy Maucherat
 */
public class TaglibMetaDataParser extends MetaDataElementParser {

    public static TaglibMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        TaglibMetaData taglib = new TaglibMetaData();

        IdMetaDataParser.parseAttributes(reader, taglib);

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case TAGLIB_URI:
                    taglib.setTaglibUri(getElementText(reader, propertyReplacer));
                    break;
                case TAGLIB_LOCATION:
                    taglib.setTaglibLocation(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return taglib;
    }

}
