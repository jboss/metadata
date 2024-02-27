/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.MailSessionMetaData;
import org.jboss.metadata.javaee.spec.PropertiesMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author Eduardo Martins
 */
public class MailSessionMetaDataParser extends MetaDataElementParser {

    public static MailSessionMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer)
            throws XMLStreamException {
        MailSessionMetaData metaData = new MailSessionMetaData();

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
                    metaData.setId(value);
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }

        DescriptionsImpl descriptions = new DescriptionsImpl();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionsMetaDataParser.parse(reader, descriptions, propertyReplacer)) {
                if (metaData.getDescriptions() == null) {
                    metaData.setDescriptions(descriptions);
                }
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case NAME:
                    metaData.setName(getElementText(reader, propertyReplacer));
                    break;
                case STORE_PROTOCOL:
                    metaData.setStoreProtocol(getElementText(reader, propertyReplacer));
                    break;
                case STORE_PROTOCOL_CLASS:
                    metaData.setStoreProtocolClass(getElementText(reader, propertyReplacer));
                    break;
                case TRANSPORT_PROTOCOL:
                    metaData.setTransportProtocol(getElementText(reader, propertyReplacer));
                    break;
                case TRANSPORT_PROTOCOL_CLASS:
                    metaData.setTransportProtocolClass(getElementText(reader, propertyReplacer));
                    break;
                case HOST:
                    metaData.setHost(getElementText(reader, propertyReplacer));
                    break;
                case USER:
                    metaData.setUser(getElementText(reader, propertyReplacer));
                    break;
                case PASSWORD:
                    metaData.setPassword(getElementText(reader, propertyReplacer));
                    break;
                case FROM:
                    metaData.setFrom(getElementText(reader, propertyReplacer));
                    break;
                case PROPERTY:
                    PropertiesMetaData properties = metaData.getProperties();
                    if (properties == null) {
                        properties = new PropertiesMetaData();
                        metaData.setProperties(properties);
                    }
                    properties.add(PropertyMetaDataParser.parse(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return metaData;
    }

}
