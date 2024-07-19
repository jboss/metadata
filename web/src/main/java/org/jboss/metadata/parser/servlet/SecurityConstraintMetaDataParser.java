/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.ee.IdMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.spec.SecurityConstraintMetaData;
import org.jboss.metadata.web.spec.WebResourceCollectionsMetaData;

/**
 * @author Remy Maucherat
 */
public class SecurityConstraintMetaDataParser extends MetaDataElementParser {

    public static SecurityConstraintMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        SecurityConstraintMetaData securityConstraint = new SecurityConstraintMetaData();

        IdMetaDataParser.parseAttributes(reader, securityConstraint);

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case DISPLAY_NAME:
                    securityConstraint.setDisplayName(getElementText(reader, propertyReplacer));
                    break;
                case WEB_RESOURCE_COLLECTION:
                    WebResourceCollectionsMetaData resourceCollections = securityConstraint.getResourceCollections();
                    if (resourceCollections == null) {
                        resourceCollections = new WebResourceCollectionsMetaData();
                        securityConstraint.setResourceCollections(resourceCollections);
                    }
                    resourceCollections.add(WebResourceCollectionMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case AUTH_CONSTRAINT:
                    securityConstraint.setAuthConstraint(AuthConstraintMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case USER_DATA_CONSTRAINT:
                    securityConstraint.setUserDataConstraint(UserDataConstraintMetaDataParser.parse(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return securityConstraint;
    }

}
