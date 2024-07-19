/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DisplayNameImpl;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.property.PropertyReplacers;


/**
 * @author Remy Maucherat
 */
public class DisplayNameMetaDataParser extends MetaDataElementParser {

    public static DisplayNameImpl parse(XMLStreamReader reader) throws XMLStreamException {
        return parse(reader, PropertyReplacers.noop());
    }

    public static DisplayNameImpl parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        DisplayNameImpl displayName = new DisplayNameImpl();
        LanguageMetaDataParser.parseAttributes(reader, displayName);
        displayName.setDisplayName(getElementText(reader, propertyReplacer));
        return displayName;
    }

}
