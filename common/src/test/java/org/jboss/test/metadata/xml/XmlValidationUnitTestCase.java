/*
  * JBoss, Home of Professional Open Source
  * Copyright 2005, JBoss Inc., and individual contributors as indicated
  * by the @authors tag. See the copyright.txt in the distribution for a
  * full listing of individual contributors.
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
package org.jboss.test.metadata.xml;

import junit.framework.TestCase;
import org.jboss.logging.Logger;
import org.jboss.util.xml.JBossEntityResolver;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * A XmlValidationUnitTestCase.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class XmlValidationUnitTestCase extends TestCase
{
   private static final Logger log = Logger.getLogger(XmlValidationUnitTestCase.class);
   
   private static final SAXParserFactory FACTORY;
   private static final Set<String> IGNORE = new HashSet<String>();
   
   static
   {
      FACTORY = SAXParserFactory.newInstance();
      FACTORY.setNamespaceAware(true);
      FACTORY.setValidating(true);
      
      IGNORE.add("log4j.xml");
      // these test only fragments of descriptors
      IGNORE.add("Icon_testNonDefaultLanguage.xml");
      IGNORE.add("Icon_testDefaultLanguage.xml");
      IGNORE.add("DisplayName_testNonDefaultLanguage.xml");
      IGNORE.add("DisplayName_testDefaultLanguage.xml");
      IGNORE.add("Description_testNonDefaultLanguage.xml");
      IGNORE.add("Description_testDefaultLanguage.xml");
      IGNORE.add("DescriptionGroup_testIconDefaultLanguage.xml");
      IGNORE.add("DescriptionGroup_testDisplayNameDefaultLanguage.xml");
      IGNORE.add("DescriptionGroup_testDescriptionDefaultLanguage.xml");
      IGNORE.add("LookupName_testEjbLocalRef.xml");
   }
   
   private static final EntityResolver RESOLVER = new JBossEntityResolver()
   {
      @Override
      public InputSource resolveEntity (String publicId, String systemId) throws SAXException, IOException
      {
         InputSource is = super.resolveEntity(publicId, systemId);
         if(is == null)
         {
            log.warn("Failed to resolve " + publicId + ", " + systemId);
         }
         return is;
      }
   };
   
   private Set<String> excluded;
   private int total;
   private int invalid;

   /**
    * Excludes xml file from validation
    * 
    * @param xmlName  xml file name w/o the path part
    */
   protected void exclude(String xmlName)
   {
      if(excluded == null)
         throw new IllegalStateException("The excluded set has not been initialized (it's done in setUp())");
      excluded.add(xmlName);
   }
   
   /**
    * Includes previously excluded xml file into the set of files to be validated
    * 
    * @param xmlName  xml file name w/o the path part
    */
   protected void include(String xmlName)
   {
      if(excluded == null)
         throw new IllegalStateException("The excluded set has not been initialized (it's done in setUp())");
      excluded.remove(xmlName);
   }

   /**
    * Checks whether the specified file excluded from validation
    * 
    * @param xmlName  xml file name w/o the path part
    * @return true if the file is excluded
    */
   protected boolean isExcluded(String xmlName)
   {
      if(excluded == null)
         throw new IllegalStateException("The excluded set has not been initialized (it's done in setUp())");
      return excluded.contains(xmlName);      
   }
   
   public void setUp() throws Exception
   {
      super.setUp();
      excluded = new HashSet<String>(IGNORE);
   }
   
   public void testValidation() throws Exception
   {
      String resourcesPath = System.getProperty("user.dir") + File.separatorChar + "src" + File.separatorChar + "test" + File.separatorChar + "resources";
      File resourcesDir = new File(resourcesPath);
      
      final Set<String> names = new HashSet<String>();
      
      resourcesDir.listFiles(new FileFilter()
      {
         public boolean accept(File pathname)
         {
            if(".svn".equals(pathname.getName()))
               return false;

            if(pathname.isDirectory())
               pathname.listFiles(this);
            
            if(pathname.getName().endsWith(".xml") && 
               !excluded.contains(pathname.getName()) &&
               pathname.getName().indexOf("Negative_testParser") <=0) //*Negative_testParser*.xml should not be well validated
            {
               names.add(pathname.getName());
               if(!isValid(pathname))
                  ++invalid;
               ++total;
            }

            return false;
         }
      });
   
      assertEquals("Zero invalid files among total of " + total, 0, invalid);
   }
   
   private static boolean isValid(final File xmlFile)
   {
      SAXParser parser;      
      try
      {
         parser = FACTORY.newSAXParser();
      }
      catch (Exception e)
      {
         throw new IllegalStateException("Failed to instantiate a SAX parser: " + e.getMessage());
      }

      try
      {
         parser.getXMLReader().setFeature("http://apache.org/xml/features/validation/schema", true);
      }
      catch (SAXException e)
      {
         throw new IllegalStateException("Schema validation feature is not supported by the parser: " + e.getMessage());
      }

      
      InputStream is;
      try
      {
         is = xmlFile.toURL().openStream();
      }
      catch (Exception e)
      {
         throw new IllegalStateException("Failed to open file: " + xmlFile.getAbsolutePath(), e);
      }

      final boolean[] failed = new boolean[1];
      try
      {
         parser.parse(is, new DefaultHandler()
         {
            public void warning(SAXParseException e)
            {
            }

            public void error(SAXParseException e) throws SAXException
            {
               log.error(xmlFile.getPath() + "[" + e.getLineNumber() + ","  + e.getColumnNumber() + "]: " + e.getMessage());
               failed[0] = true;
               throw e;
            }

            public void fatalError(SAXParseException e) throws SAXException
            {
               log.fatal(xmlFile.getPath() + "[" + e.getLineNumber() + ","  + e.getColumnNumber() + "]: " + e.getMessage());
               failed[0] = true;
               throw e;
            }

            public InputSource resolveEntity(String publicId, String systemId) throws SAXException
            {
               InputSource is = null;
               if (RESOLVER != null)
               {
                  try
                  {
                     is = RESOLVER.resolveEntity(publicId, systemId);
                  }
                  catch (Exception e)
                  {
                     throw new IllegalStateException("Failed to resolveEntity " + systemId + ": " + systemId);
                  }
               }

               if (is == null)
               {
                  throw new SAXException("Failed to resolve entity: publicId=" + publicId + " systemId=" + systemId);
               }

               return is;
            }
         });
      }
      catch(SAXException e)
      {
      }
      catch (IOException e)
      {
         throw new IllegalStateException("Failed to read file: " + xmlFile.getAbsolutePath(), e);
      }

      return !failed[0];
   }
}
