/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DisplayNamesImpl;


/**
 * @author Remy Maucherat
 */
public class DisplayNamesMetaDataParser {

    public static boolean parse(XMLStreamReader reader, DisplayNamesImpl displayNames) throws XMLStreamException {
        // Only look at the current element, no iteration
        final Element element = Element.forName(reader.getLocalName());
        switch (element) {
            case DISPLAY_NAME:
                displayNames.add(DisplayNameMetaDataParser.parse(reader));
                break;
            default:
                return false;
        }
        return true;
    }

}
