/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
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

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.jboss.JBossPortComponentRef;
import org.jboss.metadata.javaee.jboss.JBossServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceHandlerChainMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceHandlerChainsMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceHandlersMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;

/**
 * @author Remy Maucherat
 */
public class ServiceReferenceMetaDataParser extends MetaDataElementParser {

    public static ServiceReferenceMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        JBossServiceReferenceMetaData serviceReference = new JBossServiceReferenceMetaData();

        // Handle attributes
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i ++) {
            final String value = reader.getAttributeValue(i);
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case ID: {
                    serviceReference.setId(value);
                    break;
                }
                default: throw unexpectedAttribute(reader, i);
            }
        }

        DescriptionGroupMetaData descriptionGroup = new DescriptionGroupMetaData();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionGroupMetaDataParser.parse(reader, descriptionGroup)) {
                if (serviceReference.getDescriptionGroup() == null) {
                    serviceReference.setDescriptionGroup(descriptionGroup);
                }
                continue;
            }
            if (ResourceInjectionMetaDataParser.parse(reader, serviceReference)) {
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case SERVICE_REF_NAME:
                    serviceReference.setServiceRefName(getElementText(reader));
                    break;
                case SERVICE_INTERFACE:
                    serviceReference.setServiceInterface(getElementText(reader));
                    break;
                case SERVICE_IMPL_CLASS:
                   serviceReference.setServiceClass(getElementText(reader));
                    break;
                case SERVICE_REF_TYPE:
                    serviceReference.setServiceRefType(getElementText(reader));
                    break;
                case WSDL_FILE:
                    serviceReference.setWsdlFile(getElementText(reader));
                    break;
                case JAXRPC_MAPPING_FILE:
                    serviceReference.setJaxrpcMappingFile(getElementText(reader));
                    break;
                case SERVICE_QNAME:
                    serviceReference.setServiceQname(parseQName(reader, getElementText(reader)));
                    break;
                case PORT_COMPONENT_REF:
                    List<JBossPortComponentRef> portComponentRefs = (List<JBossPortComponentRef>) serviceReference.getPortComponentRef();
                    if (portComponentRefs == null) {
                        portComponentRefs = new ArrayList<JBossPortComponentRef>();
                        serviceReference.setJBossPortComponentRef(portComponentRefs);
                    }
                    portComponentRefs.add(PortComponentRefParser.parse(reader));
                    break;
                case HANDLER:
                    ServiceReferenceHandlersMetaData handlers = serviceReference.getHandlers();
                    if (handlers == null) {
                        handlers = new ServiceReferenceHandlersMetaData();
                        serviceReference.setHandlers(handlers);
                    }
                    handlers.add(ServiceReferenceHandlerMetaDataParser.parse(reader));
                    break;
                case HANDLER_CHAIN:
                    ServiceReferenceHandlerChainsMetaData handlerChain = serviceReference.getHandlerChains();
                    if (handlerChain == null) {
                        handlerChain = new ServiceReferenceHandlerChainsMetaData();
                        handlerChain.setHandlers(new ArrayList<ServiceReferenceHandlerChainMetaData>());
                        serviceReference.setHandlerChains(handlerChain);
                    }
                    handlerChain.getHandlers().add(ServiceReferenceHandlerChainMetaDataParser.parse(reader));
                    break;
                case HANDLER_CHAINS:
                    ServiceReferenceHandlerChainsMetaData handlerChains = serviceReference.getHandlerChains();
                    if (handlerChains == null) {
                        handlerChains = new ServiceReferenceHandlerChainsMetaData();
                        handlerChains.setHandlers(new ArrayList<ServiceReferenceHandlerChainMetaData>());
                        serviceReference.setHandlerChains(handlerChains);
                    }
                    handlerChains.getHandlers().addAll(ServiceReferenceHandlerChainsMetaDataParser.parse(reader));
                    break;
                case CONFIG_NAME:
                   serviceReference.setConfigName(getElementText(reader));
                   break;
                case CONFIG_FILE:
                   serviceReference.setConfigFile(getElementText(reader));
                   break;
                case WSDL_OVERRIDE:
                    serviceReference.setWsdlOverride(getElementText(reader));
                    break;
                default: throw unexpectedElement(reader);
            }
        }

        return serviceReference;
    }

}
