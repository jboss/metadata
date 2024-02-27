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
import org.jboss.metadata.web.spec.WelcomeFileListMetaData;

/**
 * @author Remy Maucherat
 */
public class WelcomeFileListMetaDataParser extends MetaDataElementParser {

    public static WelcomeFileListMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        WelcomeFileListMetaData welcomeFileList = new WelcomeFileListMetaData();

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
                    welcomeFileList.setId(value);
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
                case WELCOME_FILE:
                    List<String> welcomeFiles = welcomeFileList.getWelcomeFiles();
                    if (welcomeFiles == null) {
                        welcomeFiles = new ArrayList<String>();
                        welcomeFileList.setWelcomeFiles(welcomeFiles);
                    }
                    welcomeFiles.add(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return welcomeFileList;
    }

}
