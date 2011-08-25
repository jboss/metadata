/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
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

import org.jboss.metadata.ejb.spec.MultiplicityType;
import org.jboss.metadata.ejb.spec.RelationRoleMetaData;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.parser.ee.DescriptionsMetaDataParser;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Parses and creates metadata out of the &lt;ejb-relation-role&gt; element in the ejb-jar.xml
 *
 * @author Stuart Douglas
 */
public class RelationRoleMetaDataParser extends AbstractMetaDataParser<RelationRoleMetaData>
{

   public static final RelationRoleMetaDataParser INSTANCE = new RelationRoleMetaDataParser();


   @Override
   public RelationRoleMetaData parse(XMLStreamReader reader) throws XMLStreamException
   {
      RelationRoleMetaData data = new RelationRoleMetaData();

      // Look at the id attribute
      final int count = reader.getAttributeCount();
      for (int i = 0; i < count; i++)
      {
         if (attributeHasNamespace(reader, i))
         {
            continue;
         }
         final EjbJarAttribute ejbJarVersionAttribute = EjbJarAttribute.forName(reader.getAttributeLocalName(i));
         if (ejbJarVersionAttribute == EjbJarAttribute.ID)
         {
            data.setId(reader.getAttributeValue(i));
         }
      }

      this.processElements(data, reader);
      // return the metadata created out of parsing
      return data;
   }

   @Override
   protected void processElement(RelationRoleMetaData relation, XMLStreamReader reader) throws XMLStreamException
   {

      // Handle the description group elements
      DescriptionsImpl descriptionGroup = new DescriptionsImpl();
      if (DescriptionsMetaDataParser.parse(reader, descriptionGroup))
      {
         if (relation.getDescriptions() == null)
         {
            relation.setDescriptions(descriptionGroup);
         }
         return;
      }

      // get the element to process
      final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
      switch (ejbJarElement)
      {

         case EJB_RELATIONSHIP_ROLE_NAME:
            relation.setEjbRelationshipRoleName(getElementText(reader));
            return;

         case MULTIPLICITY:
            final  String text = getElementText(reader);
            relation.setMultiplicity(MultiplicityType.valueOf(text));
            return;

         case RELATIONSHIP_ROLE_SOURCE:
            relation.setRoleSource(RelationRoleSourceMetaDataParser.INSTANCE.parse(reader));
            return;
         case CMR_FIELD:
            
            return;

         default:
            throw unexpectedElement(reader);
      }
   }


}
