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

package org.jboss.metadata.ejb.parser.spec;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.RelationMetaData;
import org.jboss.metadata.ejb.spec.RelationRoleMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * Parses and creates metadata out of the &lt;ejb-relation&gt; element in the ejb-jar.xml
 *
 * @author Stuart Douglas
 */
public class RelationMetaDataParser extends AbstractWithDescriptionsParser<RelationMetaData> {

    public static final RelationMetaDataParser INSTANCE = new RelationMetaDataParser();


    /**
     * Creates and returns {@link org.jboss.metadata.ejb.spec.EntityBeanMetaData} after parsing the entity element.
     *
     * @param reader
     * @return
     * @throws javax.xml.stream.XMLStreamException
     *
     */
    @Override
    public RelationMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        RelationMetaData data = new RelationMetaData();

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
    protected void processElement(RelationMetaData relation, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        // get the element to process
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {

            case EJB_RELATION_NAME:
                relation.setEjbRelationName(getElementText(reader, propertyReplacer));
                return;

            case EJB_RELATIONSHIP_ROLE:
                final RelationRoleMetaData role = RelationRoleMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                List<RelationRoleMetaData> roles = relation.getEjbRelationshipRoles();
                roles.add(role);
                return;
            default:
                super.processElement(relation, reader, propertyReplacer);
                break;
        }
    }


}
