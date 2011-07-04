/*
 * JBoss, Home of Professional Open Source.
 * Copyright (c) 2011, Red Hat, Inc., and individual contributors
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
package org.jboss.metadata.ejb.parser.jboss.ejb3;

import org.jboss.metadata.ejb.jboss.ejb3.JBossEjb31MetaData;
import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.parser.spec.EjbJarMetaDataParser;
import org.jboss.metadata.ejb.parser.spec.EjbJarNamespaceMapping;
import org.jboss.metadata.ejb.parser.spec.EjbJarVersion;
import org.jboss.metadata.ejb.spec.AssemblyDescriptorMetaData;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.Map;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class JBossEjb3MetaDataParser extends EjbJarMetaDataParser<JBossEjb31MetaData>
{
   private final Map<String, AbstractMetaDataParser<?>> parsers;

   public JBossEjb3MetaDataParser(Map<String, AbstractMetaDataParser<?>> parsers)
   {
      this.parsers = parsers;
   }

   @Override
   public JBossEjb31MetaData parse(final XMLStreamReader reader, final DTDInfo info) throws XMLStreamException
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

      if (ejbJarVersion != EjbJarVersion.EJB_3_1)
         throw new UnsupportedOperationException("Only EJB 3.1 descriptor is supported, found " + ejbJarVersion);

      final JBossEjb31MetaData metaData = new JBossEjb31MetaData();
      processElements(metaData, reader);
      return metaData;
   }

   private AssemblyDescriptorMetaData parseAssemblyDescriptor(XMLStreamReader reader) throws XMLStreamException
   {
      return new JBossAssemblyDescriptorMetaDataParser(parsers).parse(reader);
   }

   @Override
   protected void processElement(JBossEjb31MetaData metaData, XMLStreamReader reader) throws XMLStreamException
   {
      final Namespace namespace = Namespace.forUri(reader.getNamespaceURI());
      if (namespace == Namespace.JBOSS)
      {
         final Element element = Element.forName(reader.getLocalName());
         switch (element)
         {
            case ASSEMBLY_DESCRIPTOR:
               metaData.setAssemblyDescriptor(parseAssemblyDescriptor(reader));
               break;
            default:
               throw unexpectedElement(reader);
         }
      }
      else
         super.processElement(metaData, reader);
   }
}