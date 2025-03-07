/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.ConnectionFactoryMetaData;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.PropertiesMetaData;
import org.jboss.metadata.javaee.spec.TransactionSupportType;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author Eduardo Martins
 */
public class ConnectionFactoryMetaDataParser extends MetaDataElementParser {

    public static ConnectionFactoryMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer)
            throws XMLStreamException {
        ConnectionFactoryMetaData metaData = new ConnectionFactoryMetaData();

        IdMetaDataParser.parseAttributes(reader, metaData);

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
                case RESOURCE_ADAPTER:
                    metaData.setResourceAdapter(getElementText(reader, propertyReplacer));
                    break;
                case MAX_POOL_SIZE:
                    metaData.setMaxPoolSize(Integer.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case MIN_POOL_SIZE:
                    metaData.setMinPoolSize(Integer.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case TRANSACTION_SUPPORT:
                    metaData.setTransactionSupport(TransactionSupportType.valueOf(getElementText(reader, propertyReplacer)));
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
