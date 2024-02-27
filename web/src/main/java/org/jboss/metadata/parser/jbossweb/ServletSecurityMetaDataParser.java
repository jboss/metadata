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
import org.jboss.metadata.web.spec.ServletSecurityMetaData;
import org.jboss.metadata.web.spec.TransportGuaranteeType;

/**
 * @author Remy Maucherat
 */
public class ServletSecurityMetaDataParser extends MetaDataElementParser {

    public static ServletSecurityMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        ServletSecurityMetaData servletSecurity = new ServletSecurityMetaData();

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case HTTP_METHOD_CONSTRAINT:
                    List<HttpMethodConstraintMetaData> httpMethodConstraints = servletSecurity.getHttpMethodConstraints();
                    if (httpMethodConstraints == null) {
                        httpMethodConstraints = new ArrayList<HttpMethodConstraintMetaData>();
                        servletSecurity.setHttpMethodConstraints(httpMethodConstraints);
                    }
                    httpMethodConstraints.add(HttpMethodConstraintMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case EMPTY_ROLE_SEMANTIC:
                    try {
                        servletSecurity.setEmptyRoleSemantic(EmptyRoleSemanticType.valueOf(getElementText(reader, propertyReplacer)));
                    } catch (IllegalArgumentException e) {
                        throw unexpectedValue(reader, e);
                    }
                    break;
                case TRANSPORT_GUARANTEE:
                    try {
                        servletSecurity.setTransportGuarantee(TransportGuaranteeType.valueOf(getElementText(reader, propertyReplacer)));
                    } catch (IllegalArgumentException e) {
                        throw unexpectedValue(reader, e);
                    }
                    break;
                case ROLE_ALLOWED:
                    List<String> rolesAllowed = servletSecurity.getRolesAllowed();
                    if (rolesAllowed == null) {
                        rolesAllowed = new ArrayList<String>();
                        servletSecurity.setRolesAllowed(rolesAllowed);
                    }
                    rolesAllowed.add(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return servletSecurity;
    }

}
