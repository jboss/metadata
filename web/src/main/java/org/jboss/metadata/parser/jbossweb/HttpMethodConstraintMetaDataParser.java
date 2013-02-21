/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
