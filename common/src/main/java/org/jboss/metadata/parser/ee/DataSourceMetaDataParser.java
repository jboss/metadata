/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DataSourceMetaData;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.IsolationLevelType;
import org.jboss.metadata.javaee.spec.PropertiesMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author Remy Maucherat
 */
public class DataSourceMetaDataParser extends MetaDataElementParser {

    public static DataSourceMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        DataSourceMetaData dataSource = new DataSourceMetaData();

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
                    dataSource.setId(value);
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
                if (dataSource.getDescriptions() == null) {
                    dataSource.setDescriptions(descriptions);
                }
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case NAME:
                    dataSource.setName(getElementText(reader, propertyReplacer));
                    break;
                case CLASS_NAME:
                    dataSource.setClassName(getElementText(reader, propertyReplacer));
                    break;
                case SERVER_NAME:
                    dataSource.setServerName(getElementText(reader, propertyReplacer));
                    break;
                case PORT_NUMBER:
                    dataSource.setPortNumber(Integer.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case DATABASE_NAME:
                    dataSource.setDatabaseName(getElementText(reader, propertyReplacer));
                    break;
                case URL:
                    dataSource.setUrl(getElementText(reader, propertyReplacer));
                    break;
                case USER:
                    dataSource.setUser(getElementText(reader, propertyReplacer));
                    break;
                case PASSWORD:
                    dataSource.setPassword(getElementText(reader, propertyReplacer));
                    break;
                case PROPERTY:
                    PropertiesMetaData properties = dataSource.getProperties();
                    if (properties == null) {
                        properties = new PropertiesMetaData();
                        dataSource.setProperties(properties);
                    }
                    properties.add(PropertyMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case LOGIN_TIMEOUT:
                    dataSource.setLoginTimeout(Integer.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case TRANSACTIONAL:
                    dataSource.setTransactional(Boolean.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case ISOLATION_LEVEL:
                    dataSource.setIsolationLevel(IsolationLevelType.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case INITAL_POOL_SIZE:
                    dataSource.setInitialPoolSize(Integer.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case MAX_POOL_SIZE:
                    dataSource.setMaxPoolSize(Integer.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case MIN_POOL_SIZE:
                    dataSource.setMinPoolSize(Integer.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case MAX_IDLE_TIME:
                    dataSource.setMaxIdleTime(Integer.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case MAX_STATEMENTS:
                    dataSource.setMaxStatements(Integer.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return dataSource;
    }

}
