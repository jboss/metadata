/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.RunAsMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author Remy Maucherat
 */
public class RunAsMetaDataParser extends MetaDataElementParser {

    public static RunAsMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        RunAsMetaData runAs = new RunAsMetaData();

        IdMetaDataParser.parseAttributes(reader, runAs);

        DescriptionsImpl descriptions = new DescriptionsImpl();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionsMetaDataParser.parse(reader, descriptions, propertyReplacer)) {
                if (runAs.getDescriptions() == null) {
                    runAs.setDescriptions(descriptions);
                }
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case ROLE_NAME:
                    runAs.setRoleName(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return runAs;
    }

}
