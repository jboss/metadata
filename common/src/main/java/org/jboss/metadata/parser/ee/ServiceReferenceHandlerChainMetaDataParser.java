/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.ServiceReferenceHandlerChainMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceHandlerMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author Remy Maucherat
 */
public class ServiceReferenceHandlerChainMetaDataParser extends MetaDataElementParser {

    public static ServiceReferenceHandlerChainMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        ServiceReferenceHandlerChainMetaData handlerChain = new ServiceReferenceHandlerChainMetaData();

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
                    handlerChain.setId(value);
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case SERVICE_NAME_PATTERN:
                    handlerChain.setServiceNamePattern(parseQName(reader, getElementText(reader, propertyReplacer)));
                    break;
                case PORT_NAME_PATTERN:
                    handlerChain.setPortNamePattern(parseQName(reader, getElementText(reader, propertyReplacer)));
                    break;
                case PROTOCOL_BINDINGS:
                    handlerChain.setProtocolBindings(getElementText(reader, propertyReplacer));
                    break;
                case HANDLER:
                    List<ServiceReferenceHandlerMetaData> handlers = handlerChain.getHandler();
                    if (handlers == null) {
                        handlers = new ArrayList<ServiceReferenceHandlerMetaData>();
                        handlerChain.setHandler(handlers);
                    }
                    handlers.add(ServiceReferenceHandlerMetaDataParser.parse(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return handlerChain;
    }

}
