/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.RelationRoleSourceMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * relationship-role-sourceType
 *
 * @author Stuart Douglas
 */
public class RelationRoleSourceMetaDataParser extends AbstractWithDescriptionsParser<RelationRoleSourceMetaData> {

    public static final RelationRoleSourceMetaDataParser INSTANCE = new RelationRoleSourceMetaDataParser();

    /**
     * Creates and returns {@link org.jboss.metadata.ejb.spec.EntityBeanMetaData} after parsing the entity element.
     *
     * @param reader
     * @return
     * @throws javax.xml.stream.XMLStreamException
     *
     */
    @Override
    public RelationRoleSourceMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        RelationRoleSourceMetaData data = new RelationRoleSourceMetaData();

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
    protected void processElement(RelationRoleSourceMetaData source, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        // get the element to process
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {

            case EJB_NAME:
                source.setEjbName(getElementText(reader, propertyReplacer));
                return;

            default:
                super.processElement(source, reader, propertyReplacer);
                break;
        }
    }


}
