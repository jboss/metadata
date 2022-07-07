/*
 * JBoss, Home of Professional Open Source
 * Copyright 2022, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.metadata.parser.ee;

import org.jboss.metadata.javaee.spec.ContextServiceMetaData;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.PropertiesMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.HashSet;

/**
 * @author emmartins
 */
public class ContextServiceMetaDataParser extends MetaDataElementParser {

    public static ContextServiceMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer)
            throws XMLStreamException {
        ContextServiceMetaData metaData = new ContextServiceMetaData();

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
                case CLEARED:
                    String cleared = getElementText(reader, propertyReplacer);
                    if (metaData.getCleared() == null) {
                        metaData.setCleared(new HashSet<>());
                    }
                    metaData.getCleared().add(cleared);
                    break;
                case PROPAGATED:
                    String propagated = getElementText(reader, propertyReplacer);
                    if (metaData.getPropagated() == null) {
                        metaData.setPropagated(new HashSet<>());
                    }
                    metaData.getPropagated().add(propagated);
                    break;
                case UNCHANGED:
                    String unchanged = getElementText(reader, propertyReplacer);
                    if (metaData.getUnchanged() == null) {
                        metaData.setUnchanged(new HashSet<>());
                    }
                    metaData.getUnchanged().add(unchanged);
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
