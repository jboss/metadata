/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextSynchronizationType;
import org.jboss.metadata.javaee.spec.PersistenceContextTypeDescription;
import org.jboss.metadata.javaee.spec.PropertiesMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.property.PropertyReplacers;

/**
 * @author Remy Maucherat
 */
public class PersistenceContextReferenceMetaDataParser extends MetaDataElementParser {

    public static PersistenceContextReferenceMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        return parse(reader, PropertyReplacers.noop());
    }

    public static PersistenceContextReferenceMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        PersistenceContextReferenceMetaData pcReference = new PersistenceContextReferenceMetaData();

        IdMetaDataParser.parseAttributes(reader, pcReference);

        DescriptionsImpl descriptions = new DescriptionsImpl();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionsMetaDataParser.parse(reader, descriptions, propertyReplacer)) {
                if (pcReference.getDescriptions() == null) {
                    pcReference.setDescriptions(descriptions);
                }
                continue;
            }
            if (ResourceInjectionMetaDataParser.parse(reader, pcReference, propertyReplacer)) {
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case PERSISTENCE_CONTEXT_REF_NAME:
                    pcReference.setPersistenceContextRefName(getElementText(reader, propertyReplacer));
                    break;
                case PERSISTENCE_CONTEXT_TYPE:
                    pcReference.setPersistenceContextType(PersistenceContextTypeDescription.valueOf(propertyReplacer.replaceProperties(getElementText(reader, propertyReplacer).toUpperCase())));
                    break;
                case PERSISTENCE_CONTEXT_SYNCHRONIZATION:
                    pcReference.setPersistenceContextSynchronization(PersistenceContextSynchronizationType.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case PERSISTENCE_UNIT_NAME:
                    pcReference.setPersistenceUnitName(getElementText(reader, propertyReplacer));
                    break;
                case PERSISTENCE_PROPERTY:
                    PropertiesMetaData properties = pcReference.getProperties();
                    if (properties == null) {
                        properties = new PropertiesMetaData();
                        pcReference.setProperties(properties);
                    }
                    properties.add(PropertyMetaDataParser.parse(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return pcReference;
    }

}
