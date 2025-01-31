/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author Remy Maucherat
 */
public class ResourceEnvironmentReferenceMetaDataParser extends MetaDataElementParser {

    public static ResourceEnvironmentReferenceMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        ResourceEnvironmentReferenceMetaData resourceReference = new ResourceEnvironmentReferenceMetaData();

        IdMetaDataParser.parseAttributes(reader, resourceReference);

        DescriptionsImpl descriptions = new DescriptionsImpl();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionsMetaDataParser.parse(reader, descriptions, propertyReplacer)) {
                if (resourceReference.getDescriptions() == null) {
                    resourceReference.setDescriptions(descriptions);
                }
                continue;
            }
            if (ResourceInjectionMetaDataParser.parse(reader, resourceReference, propertyReplacer)) {
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case RESOURCE_ENV_REF_NAME:
                    resourceReference.setResourceEnvRefName(getElementText(reader, propertyReplacer));
                    break;
                case RESOURCE_ENV_REF_TYPE:
                    resourceReference.setType(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return resourceReference;
    }

}
