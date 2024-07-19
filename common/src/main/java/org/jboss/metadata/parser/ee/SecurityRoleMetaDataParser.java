/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import java.util.HashSet;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.property.PropertyReplacers;

/**
 * @author Remy Maucherat
 */
public class SecurityRoleMetaDataParser extends MetaDataElementParser {

    public static SecurityRoleMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        return parse(reader, PropertyReplacers.noop());
    }

    public static SecurityRoleMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        SecurityRoleMetaData securityRole = new SecurityRoleMetaData();

        IdMetaDataParser.parseAttributes(reader, securityRole);

        DescriptionsImpl descriptions = new DescriptionsImpl();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionsMetaDataParser.parse(reader, descriptions, propertyReplacer)) {
                if (securityRole.getDescriptions() == null) {
                    securityRole.setDescriptions(descriptions);
                }
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case ROLE_NAME:
                    securityRole.setRoleName(getElementText(reader, propertyReplacer));
                    break;
                case PRINCIPAL_NAME:
                    Set<String> principalNames = securityRole.getPrincipals();
                    if (principalNames == null) {
                        principalNames = new HashSet<String>();
                        securityRole.setPrincipals(principalNames);
                    }
                    principalNames.add(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return securityRole;
    }

}
