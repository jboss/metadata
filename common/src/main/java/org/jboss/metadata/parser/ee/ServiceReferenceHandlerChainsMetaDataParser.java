/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import java.util.LinkedList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.ServiceReferenceHandlerChainMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author Richard Opalka
 */
public final class ServiceReferenceHandlerChainsMetaDataParser extends MetaDataElementParser {

    public static List<ServiceReferenceHandlerChainMetaData> parse(final XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        final List<ServiceReferenceHandlerChainMetaData> handlerChain = new LinkedList<ServiceReferenceHandlerChainMetaData>();

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case HANDLER_CHAIN:
                    handlerChain.add(ServiceReferenceHandlerChainMetaDataParser.parse(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return handlerChain;
    }

}
