/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.RelationMetaData;
import org.jboss.metadata.ejb.spec.RelationsMetaData;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.parser.ee.DescriptionsMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.property.PropertyReplacers;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * Parses the relations elements of ejb-jar.xml
 */
public class RelationsMetaDataParser extends MetaDataElementParser {
    private static final AttributeProcessor<IdMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<IdMetaData>(UnexpectedAttributeProcessor.instance());

    public static RelationsMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        return parse(reader, PropertyReplacers.noop());
    }

    /**
     * Creates and returns {@link org.jboss.metadata.ejb.spec.RelationsMetaData} after parsing the enterprise-beans
     * element
     *
     * @param reader
     * @return
     * @throws javax.xml.stream.XMLStreamException
     *
     */
    public static RelationsMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        RelationsMetaData relations = new RelationsMetaData();
        processAttributes(relations, reader, ATTRIBUTE_PROCESSOR);
        DescriptionsImpl descriptions = new DescriptionsImpl();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionsMetaDataParser.parse(reader, descriptions, propertyReplacer)) {
                if (relations.getDescriptions() == null) {
                    relations.setDescriptions(descriptions);
                }
                continue;
            }
            final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
            switch (ejbJarElement) {
                case EJB_RELATION:

                    RelationMetaData relation = RelationMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                    relations.add(relation);
                    break;

                default:
                    throw unexpectedElement(reader);
            }
        }
        return relations;
    }
}
