/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
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

        IdMetaDataParser.parseAttributes(reader, portComponentRef);

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
