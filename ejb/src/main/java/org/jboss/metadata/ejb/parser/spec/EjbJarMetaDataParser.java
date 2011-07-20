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

import org.jboss.metadata.ejb.spec.EjbJar1xMetaData;
import org.jboss.metadata.ejb.spec.EjbJar20MetaData;
import org.jboss.metadata.ejb.spec.EjbJar21MetaData;
import org.jboss.metadata.ejb.spec.EjbJar30MetaData;
import org.jboss.metadata.ejb.spec.EjbJar31MetaData;
import org.jboss.metadata.ejb.spec.EjbJar3xMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EjbJarVersion;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Parses a ejb-jar.xml file and creates metadata out of it
 * <p/>
 * User: Jaikiran Pai
 */
public class EjbJarMetaDataParser extends AbstractEjbJarMetaDataParser<EjbJarMetaData>
{
   @Override
   public EjbJarMetaData parse(XMLStreamReader reader) throws XMLStreamException
   {
      throw new UnsupportedOperationException("org.jboss.metadata.ejb.parser.spec.EjbJarMetaDataParser.parse");
   }

   /**
    * Create and return {@link EjbJarMetaData} from the passed {@link XMLStreamReader reader}
    *
    * @param reader
    * @return
    * @throws XMLStreamException
    */
   public static EjbJarMetaData parse(XMLStreamReader reader, DTDInfo info) throws XMLStreamException
   {
      return new EjbJarMetaDataParser().parseDocument(reader, info);
   }

   public EjbJarMetaData parseDocument(XMLStreamReader reader, DTDInfo info) throws XMLStreamException
   {
      reader.require(START_DOCUMENT, null, null);
      // Read until the first start element
      EjbJarVersion ejbJarVersion = null;
      while (reader.hasNext() && reader.next() != START_ELEMENT)
      {
         // read the version from the dtd namespace
         if (reader.getEventType() == DTD)
         {
            // TODO: we should be depending on the public id, not the system id
            String dtdLocation = info.getSystemID();
            if (dtdLocation != null)
            {
               ejbJarVersion = EjbJarNamespaceMapping.getEjbJarVersion(dtdLocation);
            }
         }
      }
      // if it wasn't a DTD namespace, then check for the xsd schema location
      if (ejbJarVersion == null)
      {
         String schemaLocation = readSchemaLocation(reader);
         if (schemaLocation != null)
         {
            ejbJarVersion = EjbJarNamespaceMapping.getEjbJarVersion(schemaLocation);
         }
      }
      // If ejb-jar version was still not found, then check the "version" attribute value
      if (ejbJarVersion == null)
      {
         ejbJarVersion = readVersionAttribute(reader);
      }

      // If we still haven't got the explicit version value, then default
      // to latest version (==3.1)
      if (ejbJarVersion == null)
      {
         ejbJarVersion = EjbJarVersion.EJB_3_1;
      }
      // Now get the EjbJarMetaData corresponding to the version
      EjbJarMetaData ejbJarMetaData = getEjbJarMetaData(ejbJarVersion);

      // Handle attributes and set them in the EjbJarMetaData
      final int count = reader.getAttributeCount();
      for (int i = 0; i < count; i++)
      {
         final String value = reader.getAttributeValue(i);
         if (attributeHasNamespace(reader, i))
         {
            continue;
         }
         final EjbJarAttribute ejbJarAttribute = EjbJarAttribute.forName(reader.getAttributeLocalName(i));
         switch (ejbJarAttribute)
         {
            case ID:
            {
               ejbJarMetaData.setId(value);
               break;
            }
            case VERSION:
            {
               ejbJarMetaData.setVersion(value);
               break;
            }
            case METADATA_COMPLETE:
            {
               // metadata-complete applies only to EJB 3.x
               if (ejbJarMetaData instanceof EjbJar3xMetaData)
               {
                  if (Boolean.TRUE.equals(Boolean.valueOf(value)))
                  {
                     ((EjbJar3xMetaData) ejbJarMetaData).setMetadataComplete(true);
                  }
               }
               else
               {
                  throw unexpectedAttribute(reader, i);
               }
               break;
            }
            default:
               throw unexpectedAttribute(reader, i);
         }
      }

      // parse and create metadata out of the elements under the root ejb-jar element
      processElements(ejbJarMetaData, reader);

      return ejbJarMetaData;
   }

   /**
    *
    * @param version
    * @throws  IllegalArgumentException If the passed {@link EjbJarVersion version} is null
    *
    * @return Returns the {@link EjbJarMetaData} corresponding to the passed {@link EjbJarVersion version}
    */
   private static EjbJarMetaData getEjbJarMetaData(EjbJarVersion version)
   {
      if (version == null)
      {
         throw new IllegalArgumentException(EjbJarVersion.class.getSimpleName() + " is null. Cannot return " + EjbJarMetaData.class);
      }
      switch (version)
      {
         case EJB_1_1:
            return new EjbJar1xMetaData();
         case EJB_2_0:
            return new EjbJar20MetaData();
         case EJB_2_1:
            return new EjbJar21MetaData();
         case EJB_3_0:
            return new EjbJar30MetaData();
         case EJB_3_1:
            return new EjbJar31MetaData();
         default:
            throw new IllegalArgumentException("Unknown ejb-jar version: " + version.name());
      }
   }
}
