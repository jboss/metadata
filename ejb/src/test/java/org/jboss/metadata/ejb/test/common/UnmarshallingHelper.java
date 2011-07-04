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
package org.jboss.metadata.ejb.test.common;

import org.jboss.metadata.ejb.jboss.ejb3.JBossEjb31MetaData;
import org.jboss.metadata.ejb.parser.jboss.ejb3.JBossEjb3MetaDataParser;
import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.parser.spec.EjbJarMetaDataParser;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.parser.util.MetaDataElementParser;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class UnmarshallingHelper
{
   // TODO: merge with AbstractEJBEverythingTest#merge
   public static <T> T unmarshal(Class<T> expected, String resource) throws Exception
   {
      return unmarshal(expected, resource, new HashMap<String, AbstractMetaDataParser<?>>());
   }

   public static <T> T unmarshal(Class<T> expected, String resource, Map<String, AbstractMetaDataParser<?>> parsers) throws Exception
   {
      MetaDataElementParser.DTDInfo info = new MetaDataElementParser.DTDInfo();
      final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
      inputFactory.setXMLResolver(info);
      XMLStreamReader reader = inputFactory.createXMLStreamReader(expected.getResourceAsStream(resource));

      final EjbJarMetaDataParser parser;
      if (JBossEjb31MetaData.class.isAssignableFrom(expected))
      {
         parser = new JBossEjb3MetaDataParser(parsers);
      }
      else if(EjbJarMetaData.class.isAssignableFrom(expected))
      {
         parser = new EjbJarMetaDataParser();
      }
      else
      {
         fail("NYI: parsing for " + expected);
         return null;
      }
      return expected.cast(parser.parse(reader, info));
   }
}
