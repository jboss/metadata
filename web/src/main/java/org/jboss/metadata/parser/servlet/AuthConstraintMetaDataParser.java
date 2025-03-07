/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.parser.ee.DescriptionsMetaDataParser;
import org.jboss.metadata.parser.ee.IdMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.spec.AuthConstraintMetaData;

/**
 * @author Remy Maucherat
 */
public class AuthConstraintMetaDataParser extends MetaDataElementParser {

    public static AuthConstraintMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        AuthConstraintMetaData authConstraint = new AuthConstraintMetaData();

        IdMetaDataParser.parseAttributes(reader, authConstraint);

        DescriptionsImpl descriptions = new DescriptionsImpl();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionsMetaDataParser.parse(reader, descriptions, propertyReplacer)) {
                if (authConstraint.getDescriptions() == null) {
                    authConstraint.setDescriptions(descriptions);
                }
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case ROLE_NAME:
                    List<String> roleNames = authConstraint.getRoleNames();
                    if (roleNames == null) {
                        roleNames = new ArrayList<String>();
                        authConstraint.setRoleNames(roleNames);
                    }
                    roleNames.add(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return authConstraint;
    }

}
