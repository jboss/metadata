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

import org.jboss.metadata.ejb.spec.QueryMetaData;
import org.jboss.metadata.ejb.spec.ResultTypeMapping;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.parser.ee.DescriptionsMetaDataParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * Parses and creates metadata out of &lt;query&gt; element for entity beans
 *
 * @author Stuart Douglas
 */
public class QueryMetaDataParser extends AbstractWithDescriptionsParser<QueryMetaData> {

    /**
     * Instance of this parser
     */
    public static final QueryMetaDataParser INSTANCE = new QueryMetaDataParser();

    @Override
    public QueryMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        QueryMetaData queryMetaData = new QueryMetaData();
        // Look at the id attribute
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final EjbJarAttribute ejbJarVersionAttribute = EjbJarAttribute.forName(reader.getAttributeLocalName(i));
            if (ejbJarVersionAttribute == EjbJarAttribute.ID) {
                queryMetaData.setId(reader.getAttributeValue(i));
            }
        }
        this.processElements(queryMetaData, reader, propertyReplacer);
        return queryMetaData;
    }

    @Override
    protected void processElement(QueryMetaData methodMetaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        DescriptionsImpl descriptionGroup = new DescriptionsImpl();
        if (DescriptionsMetaDataParser.parse(reader, descriptionGroup, propertyReplacer)) {
            if (methodMetaData.getDescriptions() == null) {
                methodMetaData.setDescriptions(descriptionGroup);
            }
            return;
        }
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case QUERY_METHOD:
                methodMetaData.setQueryMethod(NamedMethodMetaDataParser.INSTANCE.parse(reader, propertyReplacer));
                return;
            case RESULT_TYPE_MAPPING:
                String type = getElementText(reader, propertyReplacer);
                methodMetaData.setResultTypeMapping(ResultTypeMapping.valueOf(type));
                return;
            case EJB_QL:
                methodMetaData.setEjbQL(getElementText(reader, propertyReplacer));
                return;

            default:
                super.processElement(methodMetaData, reader, propertyReplacer);
        }
    }
}
