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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.JMSConnectionFactoryMetaData;
import org.jboss.metadata.javaee.spec.PropertiesMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author Eduardo Martins
 */
public class JMSConnectionFactoryMetaDataParser extends MetaDataElementParser {

    public static JMSConnectionFactoryMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer)
            throws XMLStreamException {
        JMSConnectionFactoryMetaData metaData = new JMSConnectionFactoryMetaData();

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
                case INTERFACE_NAME:
                    metaData.setInterfaceName(getElementText(reader, propertyReplacer));
                    break;
                case CLASS_NAME:
                    metaData.setClassName(getElementText(reader, propertyReplacer));
                    break;
                case RESOURCE_ADAPTER:
                    metaData.setResourceAdapter(getElementText(reader, propertyReplacer));
                    break;
                case USER:
                    metaData.setUser(getElementText(reader, propertyReplacer));
                    break;
                case PASSWORD:
                    metaData.setPassword(getElementText(reader, propertyReplacer));
                    break;
                case CLIENT_ID:
                    metaData.setClientId(getElementText(reader, propertyReplacer));
                    break;
                case PROPERTY:
                    PropertiesMetaData properties = metaData.getProperties();
                    if (properties == null) {
                        properties = new PropertiesMetaData();
                        metaData.setProperties(properties);
                    }
                    properties.add(PropertyMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case TRANSACTIONAL:
                    metaData.setTransactional(Boolean.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case MAX_POOL_SIZE:
                    metaData.setMaxPoolSize(Integer.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case MIN_POOL_SIZE:
                    metaData.setMinPoolSize(Integer.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return metaData;
    }

}
