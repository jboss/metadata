/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.jsp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.web.spec.TldExtensionMetaData;

/**
 * @author Remy Maucherat
 */
public class TldExtensionMetaDataParser extends MetaDataElementParser {

    public static TldExtensionMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        TldExtensionMetaData extension = new TldExtensionMetaData();

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
                    extension.setId(value);
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
                case NAMESPACE:
                    extension.setNamespace(getElementText(reader));
                    break;
                case EXTENSION_ELEMENT:
                    List<Object> extensionElements = extension.getExtensionElements();
                    if (extensionElements == null) {
                        extensionElements = new ArrayList<Object>();
                        extension.setExtensionElements(extensionElements);
                    }
                    extensionElements.add(getElementText(reader));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return extension;
    }

}
