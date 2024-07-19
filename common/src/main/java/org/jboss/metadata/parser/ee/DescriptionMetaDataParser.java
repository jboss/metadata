/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionImpl;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;


/**
 * @author Remy Maucherat
 */
public class DescriptionMetaDataParser extends MetaDataElementParser {

    public static DescriptionImpl parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        DescriptionImpl description = new DescriptionImpl();
        LanguageMetaDataParser.parseAttributes(reader, description);
        description.setDescription(getElementText(reader, propertyReplacer));
        return description;
    }

}
