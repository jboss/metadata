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

import org.jboss.metadata.ejb.jboss.ejb3.JBossAssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.parser.spec.AssemblyDescriptor30MetaDataParser;
import org.jboss.metadata.ejb.spec.AssemblyDescriptorMetaData;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.Map;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class JBossAssemblyDescriptorMetaDataParser extends AssemblyDescriptor30MetaDataParser
{
   private final Map<String, AbstractMetaDataParser<?>> parsers;

   public JBossAssemblyDescriptorMetaDataParser(Map<String, AbstractMetaDataParser<?>> parsers)
   {
      this.parsers = parsers;
   }

   protected AbstractMetaDataParser<?> getParser(String uri)
   {
      return mandatory(parsers.get(uri), "No parser found for " + uri);
   }

   private static <V> V mandatory(V value, String message)
   {
      if (value == null)
         throw new IllegalStateException(message);
      return value;
   }

   @Override
   public AssemblyDescriptorMetaData parse(XMLStreamReader reader) throws XMLStreamException
   {
      AssemblyDescriptorMetaData assemblyDescriptorMetaData = new JBossAssemblyDescriptorMetaData();
      this.processElements(assemblyDescriptorMetaData, reader);
      return assemblyDescriptorMetaData;
   }

   @Override
   protected void processElement(AssemblyDescriptorMetaData assemblyDescriptor, XMLStreamReader reader) throws XMLStreamException
   {
      final Namespace namespace = Namespace.forUri(reader.getNamespaceURI());
      switch (namespace)
      {
         case JBOSS:
            throw new RuntimeException("NYI");
         case SPEC:
            super.processElement(assemblyDescriptor, reader);
            break;
         case UNKNOWN:
//            ParseResult<?> result = new ParseResult<Object>();
//            ((XMLExtendedStreamReader) reader).handleAny(result);
//            ((JBossAssemblyDescriptorMetaData) assemblyDescriptor).addAny(result.getResult());
            AbstractMetaDataParser<?> parser = getParser(reader.getNamespaceURI());
            ((JBossAssemblyDescriptorMetaData) assemblyDescriptor).addAny(parser.parse(reader));
            break;
      }
   }
}
