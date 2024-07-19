/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.parser.ee.DescriptionsMetaDataParser;
import org.jboss.metadata.parser.ee.IdMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.spec.TransportGuaranteeType;
import org.jboss.metadata.web.spec.UserDataConstraintMetaData;

/**
 * @author Remy Maucherat
 */
public class UserDataConstraintMetaDataParser extends MetaDataElementParser {

    public static UserDataConstraintMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        UserDataConstraintMetaData userDataConstraint = new UserDataConstraintMetaData();

        IdMetaDataParser.parseAttributes(reader, userDataConstraint);

        DescriptionsImpl descriptions = new DescriptionsImpl();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionsMetaDataParser.parse(reader, descriptions, propertyReplacer)) {
                if (userDataConstraint.getDescriptions() == null) {
                    userDataConstraint.setDescriptions(descriptions);
                }
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case TRANSPORT_GUARANTEE:
                    try {
                        userDataConstraint.setTransportGuarantee(TransportGuaranteeType.valueOf(getElementText(reader, propertyReplacer)));
                    } catch (IllegalArgumentException e) {
                        throw unexpectedValue(reader, e);
                    }
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return userDataConstraint;
    }

}
