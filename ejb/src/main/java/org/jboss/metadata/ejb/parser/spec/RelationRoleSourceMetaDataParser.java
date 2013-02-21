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
