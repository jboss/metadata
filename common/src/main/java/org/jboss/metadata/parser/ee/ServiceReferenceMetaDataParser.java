/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
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
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author Remy Maucherat
 */
public class ServiceReferenceMetaDataParser extends MetaDataElementParser {

    public static ServiceReferenceMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        JBossServiceReferenceMetaData serviceReference = new JBossServiceReferenceMetaData();

        IdMetaDataParser.parseAttributes(reader, serviceReference);

        DescriptionGroupMetaData descriptionGroup = new DescriptionGroupMetaData();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionGroupMetaDataParser.parse(reader, descriptionGroup)) {
                if (serviceReference.getDescriptionGroup() == null) {
                    serviceReference.setDescriptionGroup(descriptionGroup);
                }
                continue;
            }
            if (ResourceInjectionMetaDataParser.parse(reader, serviceReference, propertyReplacer)) {
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case SERVICE_REF_NAME:
                    serviceReference.setServiceRefName(getElementText(reader, propertyReplacer));
                    break;
                case SERVICE_INTERFACE:
                    serviceReference.setServiceInterface(getElementText(reader, propertyReplacer));
                    break;
                case SERVICE_IMPL_CLASS:
                    serviceReference.setServiceClass(getElementText(reader, propertyReplacer));
                    break;
                case SERVICE_REF_TYPE:
                    serviceReference.setServiceRefType(getElementText(reader, propertyReplacer));
                    break;
                case WSDL_FILE:
                    serviceReference.setWsdlFile(getElementText(reader, propertyReplacer));
                    break;
                case JAXRPC_MAPPING_FILE:
                    serviceReference.setJaxrpcMappingFile(getElementText(reader, propertyReplacer));
                    break;
                case SERVICE_QNAME:
                    serviceReference.setServiceQname(parseQName(reader, getElementText(reader, propertyReplacer)));
                    break;
                case PORT_COMPONENT_REF:
                    List<JBossPortComponentRef> portComponentRefs = (List<JBossPortComponentRef>) serviceReference.getPortComponentRef();
                    if (portComponentRefs == null) {
                        portComponentRefs = new ArrayList<JBossPortComponentRef>();
                        serviceReference.setJBossPortComponentRef(portComponentRefs);
                    }
                    portComponentRefs.add(PortComponentRefParser.parse(reader, propertyReplacer));
                    break;
                case HANDLER:
                    ServiceReferenceHandlersMetaData handlers = serviceReference.getHandlers();
                    if (handlers == null) {
                        handlers = new ServiceReferenceHandlersMetaData();
                        serviceReference.setHandlers(handlers);
                    }
                    handlers.add(ServiceReferenceHandlerMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case HANDLER_CHAIN:
                    ServiceReferenceHandlerChainsMetaData handlerChain = serviceReference.getHandlerChains();
                    if (handlerChain == null) {
                        handlerChain = new ServiceReferenceHandlerChainsMetaData();
                        handlerChain.setHandlers(new ArrayList<ServiceReferenceHandlerChainMetaData>());
                        serviceReference.setHandlerChains(handlerChain);
                    }
                    handlerChain.getHandlers().add(ServiceReferenceHandlerChainMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case HANDLER_CHAINS:
                    ServiceReferenceHandlerChainsMetaData handlerChains = serviceReference.getHandlerChains();
                    if (handlerChains == null) {
                        handlerChains = new ServiceReferenceHandlerChainsMetaData();
                        handlerChains.setHandlers(new ArrayList<ServiceReferenceHandlerChainMetaData>());
                        serviceReference.setHandlerChains(handlerChains);
                    }
                    handlerChains.getHandlers().addAll(ServiceReferenceHandlerChainsMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case CONFIG_NAME:
                    serviceReference.setConfigName(getElementText(reader, propertyReplacer));
                    break;
                case CONFIG_FILE:
                    serviceReference.setConfigFile(getElementText(reader, propertyReplacer));
                    break;
                case WSDL_OVERRIDE:
                    serviceReference.setWsdlOverride(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return serviceReference;
    }

}
