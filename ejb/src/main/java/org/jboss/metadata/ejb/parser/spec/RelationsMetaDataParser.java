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

import org.jboss.metadata.ejb.spec.RelationMetaData;
import org.jboss.metadata.ejb.spec.RelationsMetaData;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.parser.ee.DescriptionsMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Parses the relations elements of ejb-jar.xml
 */
public class RelationsMetaDataParser extends MetaDataElementParser
{

   /**
    * Creates and returns {@link org.jboss.metadata.ejb.spec.RelationsMetaData} after parsing the enterprise-beans
    * element
    *
    * @param reader
    * @return
    * @throws javax.xml.stream.XMLStreamException
    *
    */
   public static RelationsMetaData parse(XMLStreamReader reader) throws XMLStreamException
   {
      RelationsMetaData relations = new RelationsMetaData();
      DescriptionsImpl descriptions = new DescriptionsImpl();
      // Handle elements
      while (reader.hasNext() && reader.nextTag() != END_ELEMENT)
      {
         if (DescriptionsMetaDataParser.parse(reader, descriptions))
         {
            if (relations.getDescriptions() == null)
            {
               relations.setDescriptions(descriptions);
            }
            continue;
         }
         final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
         switch (ejbJarElement)
         {
            case EJB_RELATION:
               
               RelationMetaData relation = RelationMetaDataParser.INSTANCE.parse(reader);
               relations.add(relation);
               break;

            default:
               throw unexpectedElement(reader);
         }
      }
      return relations;
   }
}
