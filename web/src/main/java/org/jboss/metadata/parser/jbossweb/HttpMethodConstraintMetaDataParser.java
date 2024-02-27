/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.jbossweb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.spec.EmptyRoleSemanticType;
import org.jboss.metadata.web.spec.HttpMethodConstraintMetaData;
import org.jboss.metadata.web.spec.TransportGuaranteeType;

/**
 * @author Remy Maucherat
 */
public class HttpMethodConstraintMetaDataParser extends MetaDataElementParser {

    public static HttpMethodConstraintMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        HttpMethodConstraintMetaData httpMethodConstraint = new HttpMethodConstraintMetaData();

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case METHOD:
                    httpMethodConstraint.setMethod(getElementText(reader, propertyReplacer));
                    break;
                case EMPTY_ROLE_SEMANTIC:
                    httpMethodConstraint.setEmptyRoleSemantic(EmptyRoleSemanticType.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case TRANSPORT_GUARANTEE:
                    httpMethodConstraint.setTransportGuarantee(TransportGuaranteeType.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case ROLE_ALLOWED:
                    List<String> rolesAllowed = httpMethodConstraint.getRolesAllowed();
                    if (rolesAllowed == null) {
                        rolesAllowed = new ArrayList<String>();
                        httpMethodConstraint.setRolesAllowed(rolesAllowed);
                    }
                    rolesAllowed.add(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return httpMethodConstraint;
    }

}
