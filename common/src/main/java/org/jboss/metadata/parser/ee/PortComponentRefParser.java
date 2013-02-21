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

package org.jboss.metadata.parser.ee;

import java.util.LinkedList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.jboss.JBossPortComponentRef;
import org.jboss.metadata.javaee.jboss.StubPropertyMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author Remy Maucherat
 */
public class PortComponentRefParser extends MetaDataElementParser {

    public static JBossPortComponentRef parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        JBossPortComponentRef portComponentRef = new JBossPortComponentRef();

        // Handle attributes
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            final String value = reader.getAttributeValue(i);
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case ID: {
                    portComponentRef.setId(value);
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }

        // Handle elements
        List<StubPropertyMetaData> stubProperties = new LinkedList<StubPropertyMetaData>();
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case SERVICE_ENDPOINT_INTERFACE:
                    portComponentRef.setServiceEndpointInterface(getElementText(reader, propertyReplacer));
                    break;
                case ENABLE_MTOM:
                    portComponentRef.setEnableMtom(Boolean.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case MTOM_THRESHOLD:
                    portComponentRef.setMtomThreshold(Integer.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case ADDRESSING:
                    portComponentRef.setAddressing(AddressingParser.parse(reader, propertyReplacer));
                    break;
                case RESPECT_BINDING:
                    portComponentRef.setRespectBinding(RespectBindingParser.parse(reader, propertyReplacer));
                    break;
                case PORT_COMPONENT_LINK:
                    portComponentRef.setPortComponentLink(getElementText(reader, propertyReplacer));
                    break;
                case CONFIG_NAME:
                    portComponentRef.setConfigName(getElementText(reader, propertyReplacer));
                    break;
                case CONFIG_FILE:
                    portComponentRef.setConfigFile(getElementText(reader, propertyReplacer));
                    break;
                case PORT_QNAME:
                    portComponentRef.setPortQname(parseQName(reader, getElementText(reader, propertyReplacer)));
                    break;
                case STUB_PROPERTY:
                    stubProperties.add(StubPropertyParser.parse(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }
        if (stubProperties.size() > 0) {
            portComponentRef.setStubProperties(stubProperties);
        }

        return portComponentRef;
    }

}
