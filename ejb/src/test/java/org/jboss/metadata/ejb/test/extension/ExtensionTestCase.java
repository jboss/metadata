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

import org.jboss.metadata.ejb.jboss.ejb3.JBossEjb31MetaData;
import org.jboss.metadata.ejb.parser.spec.AbstractMetaDataParser;
import org.jboss.metadata.ejb.spec.MethodInterfaceType;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.metadata.ejb.test.common.ValidationHelper;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class ExtensionTestCase
{
   private InputSource inputSource(final String systemId, final String resource)
   {
      final InputStream in = getClass().getResourceAsStream(resource);
      final InputSource inputSource = new InputSource(in);
      inputSource.setSystemId(systemId);
      return inputSource;
   }

   @Test
   public void testBean() throws Exception
   {
      JBossEjb31MetaData metaData = unmarshal(JBossEjb31MetaData.class, "/org/jboss/metadata/ejb/test/extension/jboss-ejb3-with-bean.xml");
      SessionBean31MetaData bean = (SessionBean31MetaData) metaData.getEnterpriseBean("Test");
      assertNotNull(bean);
   }

   @Test
   public void testCacheExtension() throws Exception
   {
      Map<String, AbstractMetaDataParser<?>> parsers = new HashMap<String, AbstractMetaDataParser<?>>();
      parsers.put("urn:cache-test", new CacheTestParser());
      parsers.put("urn:tx-timeout-test", new TxTestParser());
      JBossEjb31MetaData metaData = unmarshal(JBossEjb31MetaData.class, "/org/jboss/metadata/ejb/test/extension/jboss-ejb3.xml", parsers);
      assertNotNull(metaData.getAssemblyDescriptor());
      assertEquals(20, metaData.getAssemblyDescriptor().getAny(CacheTest.class).get(0).getSize());
      final TxTest txTest = metaData.getAssemblyDescriptor().getAny(TxTest.class).get(0);
      assertEquals("A", txTest.getMethods().get(0).getEjbName());
      assertEquals(MethodInterfaceType.Local, txTest.getMethods().get(0).getMethodIntf());
      assertEquals("*", txTest.getMethods().get(0).getMethodName());
      assertEquals(10, txTest.getTimeout());
      assertEquals(TimeUnit.MINUTES, txTest.getUnit());
   }

   @Test
   public void testMetadataComplete() throws Exception
   {
      JBossEjb31MetaData metaData = unmarshal(JBossEjb31MetaData.class, "/org/jboss/metadata/ejb/test/extension/jboss-ejb3-metadata-complete.xml");
      assertTrue(metaData.isMetadataComplete());
   }

   @Test
   public void testValidity() throws IOException, SAXException, ParserConfigurationException, URISyntaxException
   {
      InputStream in = getClass().getResourceAsStream("/org/jboss/metadata/ejb/test/extension/jboss-ejb3.xml");
      Document document = ValidationHelper.parse(new InputSource(in), getClass());
      assertNotNull(document);
   }

   @Test
   public void testValidity2() throws IOException, SAXException, ParserConfigurationException, URISyntaxException
   {
      InputStream in = getClass().getResourceAsStream("/org/jboss/metadata/ejb/test/extension/jboss-ejb3-with-bean.xml");
      Document document = ValidationHelper.parse(new InputSource(in), getClass());
      assertNotNull(document);
   }

   @Test
   public void testValidityXSD() throws IOException, SAXException, ParserConfigurationException
   {
      // parse an XML document into a DOM tree
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      factory.setValidating(true);
      factory.setAttribute(
              "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
              "http://www.w3.org/2001/XMLSchema");
      factory.setAttribute(
              "http://java.sun.com/xml/jaxp/properties/schemaSource",
              "http://www.w3.org/2001/XMLSchema.xsd");
      DocumentBuilder parser = factory.newDocumentBuilder();
      final Map<String, String> system = new HashMap<String, String>();
      system.put("http://www.w3.org/2001/XMLSchema.xsd", "/schema/XMLSchema.xsd");
      system.put("http://www.w3.org/2001/XMLSchema.dtd", "/schema/XMLSchema.dtd");
      system.put("http://www.w3.org/2001/datatypes.dtd", "/schema/datatypes.dtd");
      system.put("http://www.w3.org/2001/xml.xsd", "/schema/xml.xsd");
      parser.setEntityResolver(new EntityResolver()
      {
         @Override
         public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException
         {
//            System.out.println("resolve " + publicId + ", " + systemId);
            final String resource = system.get(systemId);
            if(resource == null)
               throw new SAXException("Can't resolve systemId " + systemId);
            return inputSource(systemId, resource);
         }
      });
      parser.setErrorHandler(new ErrorHandler()
      {
         @Override
         public void warning(SAXParseException exception) throws SAXException
         {
            throw exception;
         }

         @Override
         public void error(SAXParseException exception) throws SAXException
         {
            throw exception;
         }

         @Override
         public void fatalError(SAXParseException exception) throws SAXException
         {
            throw exception;
         }
      });
      InputStream in = getClass().getResourceAsStream("/schema/jboss-ejb3-2_0.xsd");
      Document document = parser.parse(new InputSource(in));
      assertNotNull(document);
   }
}
