/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.MultiplicityType;
import org.jboss.metadata.ejb.spec.RelationRoleMetaData;
import org.jboss.metadata.javaee.spec.EmptyMetaData;
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.property.PropertyReplacer;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * Parses and creates metadata out of the &lt;ejb-relation-role&gt; element in the ejb-jar.xml
 *
 * @author Stuart Douglas
 */
public class RelationRoleMetaDataParser extends AbstractWithDescriptionsParser<RelationRoleMetaData> {
    private static final AttributeProcessor<IdMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<IdMetaData>(UnexpectedAttributeProcessor.instance());
    public static final RelationRoleMetaDataParser INSTANCE = new RelationRoleMetaDataParser();

    @Override
    public RelationRoleMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        RelationRoleMetaData data = new RelationRoleMetaData();

        // Look at the id attribute
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final EjbJarAttribute ejbJarVersionAttribute = EjbJarAttribute.forName(reader.getAttributeLocalName(i));
            if (ejbJarVersionAttribute == EjbJarAttribute.ID) {
                data.setId(reader.getAttributeValue(i));
            }
        }

        this.processElements(data, reader, propertyReplacer);
        // return the metadata created out of parsing
        return data;
    }

    @Override
    protected void processElement(RelationRoleMetaData relation, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        // get the element to process
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {

            case EJB_RELATIONSHIP_ROLE_NAME:
                relation.setEjbRelationshipRoleName(getElementText(reader, propertyReplacer));
                return;

            case MULTIPLICITY:
                final String text = getElementText(reader, propertyReplacer);
                relation.setMultiplicity(MultiplicityType.valueOf(text));
                return;
            case RELATIONSHIP_ROLE_SOURCE:
                relation.setRoleSource(RelationRoleSourceMetaDataParser.INSTANCE.parse(reader, propertyReplacer));
                return;
            case CMR_FIELD:
                relation.setCmrField(RelationRoleCmrFieldMetaDataParser.INSTANCE.parse(reader, propertyReplacer));
                return;
            case CASCADE_DELETE:
                final EmptyMetaData cascade = new EmptyMetaData();
                processAttributes(cascade, reader, ATTRIBUTE_PROCESSOR);
                relation.setCascadeDelete(cascade);
                reader.getElementText();
                return;
            default:
                super.processElement(relation, reader, propertyReplacer);
                break;
        }
    }


}
