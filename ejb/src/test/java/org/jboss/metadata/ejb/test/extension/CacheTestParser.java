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
package org.jboss.metadata.ejb.test.extension;

import org.jboss.metadata.ejb.parser.jboss.ejb3.AbstractEJBBoundMetaDataParser;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class CacheTestParser extends AbstractEJBBoundMetaDataParser<CacheTest>
{
   @Override
   public CacheTest parse(XMLStreamReader reader) throws XMLStreamException
   {
      CacheTest cacheTest = new CacheTest();
      processElements(cacheTest, reader);
      return cacheTest;
   }

   @Override
   protected void processElement(CacheTest metaData, XMLStreamReader reader) throws XMLStreamException
   {
      if (reader.getNamespaceURI().equals("urn:cache-test"))
      {
         if (reader.getLocalName().equals("size"))
            metaData.setSize(Integer.valueOf(reader.getElementText()));
         else
            throw unexpectedElement(reader);
      }
      else
         super.processElement(metaData, reader);
   }
}
