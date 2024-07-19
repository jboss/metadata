/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationUsageType;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author Remy Maucherat
 */
public class MessageDestinationReferenceMetaDataParser extends MetaDataElementParser {

    public static MessageDestinationReferenceMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        MessageDestinationReferenceMetaData mdReference = new MessageDestinationReferenceMetaData();

        IdMetaDataParser.parseAttributes(reader, mdReference);

        DescriptionsImpl descriptions = new DescriptionsImpl();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionsMetaDataParser.parse(reader, descriptions, propertyReplacer)) {
                if (mdReference.getDescriptions() == null) {
                    mdReference.setDescriptions(descriptions);
                }
                continue;
            }
            if (ResourceInjectionMetaDataParser.parse(reader, mdReference, propertyReplacer)) {
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case MESSAGE_DESTINATION_REF_NAME:
                    mdReference.setMessageDestinationRefName(getElementText(reader, propertyReplacer));
                    break;
                case MESSAGE_DESTINATION_TYPE:
                    mdReference.setType(getElementText(reader, propertyReplacer));
                    break;
                case MESSAGE_DESTINATION_USAGE:
                    mdReference.setMessageDestinationUsage(MessageDestinationUsageType.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case MESSAGE_DESTINATION_LINK:
                    mdReference.setLink(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return mdReference;
    }

}
