/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author Remy Maucherat
 */
public class EnvironmentEntryMetaDataParser extends MetaDataElementParser {

    public static EnvironmentEntryMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        EnvironmentEntryMetaData environmentEntry = new EnvironmentEntryMetaData();

        IdMetaDataParser.parseAttributes(reader, environmentEntry);

        DescriptionsImpl descriptions = new DescriptionsImpl();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionsMetaDataParser.parse(reader, descriptions, propertyReplacer)) {
                if (environmentEntry.getDescriptions() == null) {
                    environmentEntry.setDescriptions(descriptions);
                }
                continue;
            }
            if (ResourceInjectionMetaDataParser.parse(reader, environmentEntry, propertyReplacer)) {
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case ENV_ENTRY_NAME:
                    environmentEntry.setEnvEntryName(getElementText(reader, propertyReplacer));
                    break;
                case ENV_ENTRY_TYPE:
                    environmentEntry.setType(getElementText(reader, propertyReplacer));
                    break;
                case ENV_ENTRY_VALUE:
                    environmentEntry.setValue(propertyReplacer.replaceProperties(getElementText(reader, false, propertyReplacer)));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return environmentEntry;
    }

}
