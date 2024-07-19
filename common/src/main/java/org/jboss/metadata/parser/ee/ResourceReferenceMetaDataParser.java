/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.ResourceAuthorityType;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceSharingScopeType;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author Remy Maucherat
 */
public class ResourceReferenceMetaDataParser extends MetaDataElementParser {

    public static ResourceReferenceMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        ResourceReferenceMetaData resourceReference = new ResourceReferenceMetaData();

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
                case RES_REF_NAME:
                    resourceReference.setResourceRefName(getElementText(reader, propertyReplacer));
                    break;
                case RES_TYPE:
                    resourceReference.setType(getElementText(reader, propertyReplacer));
                    break;
                case RES_AUTH:
                    resourceReference.setResAuth(ResourceAuthorityType.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case RES_SHARING_SCOPE:
                    resourceReference.setResSharingScope(ResourceSharingScopeType.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case RESOURCE_NAME:
                    resourceReference.setResourceName(getElementText(reader, propertyReplacer));
                    break;
                case RES_URL:
                    resourceReference.setResUrl(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return resourceReference;
    }

}
