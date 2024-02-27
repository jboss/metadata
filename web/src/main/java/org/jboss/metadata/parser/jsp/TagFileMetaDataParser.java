/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.jsp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.parser.ee.DescriptionGroupMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.web.spec.TagFileMetaData;
import org.jboss.metadata.web.spec.TldExtensionMetaData;

/**
 * @author Remy Maucherat
 */
public class TagFileMetaDataParser extends MetaDataElementParser {

    public static TagFileMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        TagFileMetaData tagFile = new TagFileMetaData();

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
                    tagFile.setId(value);
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }

        DescriptionGroupMetaData descriptionGroup = new DescriptionGroupMetaData();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionGroupMetaDataParser.parse(reader, descriptionGroup)) {
                if (tagFile.getDescriptionGroup() == null) {
                    tagFile.setDescriptionGroup(descriptionGroup);
                }
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case NAME:
                    tagFile.setName(getElementText(reader));
                    break;
                case PATH:
                    tagFile.setPath(getElementText(reader));
                    break;
                case EXAMPLE:
                    List<String> examples = tagFile.getExamples();
                    if (examples == null) {
                        examples = new ArrayList<String>();
                        tagFile.setExamples(examples);
                    }
                    examples.add(getElementText(reader));
                    break;
                case TAG_EXTENSION:
                    List<TldExtensionMetaData> extensions = tagFile.getTagExtensions();
                    if (extensions == null) {
                        extensions = new ArrayList<TldExtensionMetaData>();
                        tagFile.setTagExtensions(extensions);
                    }
                    extensions.add(TldExtensionMetaDataParser.parse(reader));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return tagFile;
    }

}
