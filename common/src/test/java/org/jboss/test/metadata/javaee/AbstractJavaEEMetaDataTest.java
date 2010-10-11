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
package org.jboss.test.metadata.javaee;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;
import org.jboss.test.AbstractTestCaseWithSetup;
import org.jboss.test.AbstractTestDelegate;
import org.jboss.test.metadata.common.MetaDataSchemaResolverFactory;
import org.jboss.xb.annotations.JBossXmlConstants;
import org.jboss.xb.binding.resolver.MutableSchemaResolver;
import org.jboss.xb.binding.sunday.unmarshalling.DefaultSchemaResolver;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBinding;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingInitializer;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingResolver;
import org.jboss.xb.binding.sunday.unmarshalling.TypeBinding;
import org.jboss.xb.builder.JBossXBBuilder;
import org.w3c.dom.ls.LSInput;


/**
 * A JavaEE metadata Test.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.2 $
 */
public abstract class AbstractJavaEEMetaDataTest extends AbstractTestCaseWithSetup
{
   /**
    * Create a new JavaEEMetaDataTest.
    * 
    * @param name the test name
    */
   public AbstractJavaEEMetaDataTest(String name)
   {
      super(name);
   }

   public static SchemaBindingResolver initResolver()
   {
      MutableSchemaResolver resolver = MetaDataSchemaResolverFactory.createSchemaResolver();
      mapLocationToClass(resolver);
      return resolver;
   }
   
   public static void mapLocationToClass(MutableSchemaResolver resolver)
   {
      String value = System.getProperty("jbmeta.mapLocationToClass");
      if(value != null)
      {
         Properties mappings = new Properties();
         ByteArrayInputStream bais = new ByteArrayInputStream(value.getBytes());
         
         try
         {
            mappings.load(bais);
         }
         catch (IOException e)
         {
            throw new IllegalStateException("Failed to load location to class mappings", e);
         }
         
         for(Map.Entry<Object,Object> mapping : mappings.entrySet())
         {
            String className = (String) mapping.getValue();
            Class<?> clazz;
            try
            {
               clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            }
            catch (ClassNotFoundException e)
            {
               throw new IllegalStateException("Failed to load class " + className, e);
            }
            
            resolver.mapLocationToClass((String) mapping.getKey(), clazz);
            
            System.out.println("mapLocationToClass: " + mapping.getKey() + "=" + className);
         }
      }
   }
   
   public static SchemaBindingResolver initResolverJavaEE(Class<?> clazz, String xsd)
   {
      DefaultSchemaResolver resolver = initResolverJavaEE(clazz);
      String xsdPath = findXSD(xsd);
      resolver.addSchemaLocation(JavaEEMetaDataConstants.JAVAEE_NS, xsdPath);
      return resolver;
   }

   public static DefaultSchemaResolver initResolverJavaEE(Class<?> clazz)
   {
      DefaultSchemaResolver resolver = new DefaultSchemaResolver();
      SchemaBindingInitializer initializer = JBossXBBuilder.newInitializer(clazz);
      resolver.addSchemaInitializer(JavaEEMetaDataConstants.JAVAEE_NS, initializer);
      resolver.addSchemaParseAnnotations(JavaEEMetaDataConstants.JAVAEE_NS, Boolean.FALSE);
      return resolver;
   }

   public static DefaultSchemaResolver initResolverJ2EE(Class<?> clazz)
   {
      DefaultSchemaResolver resolver = new DefaultSchemaResolver();
      SchemaBindingInitializer initializer = JBossXBBuilder.newInitializer(clazz);
      resolver.addSchemaInitializer(JavaEEMetaDataConstants.J2EE_NS, initializer);
      resolver.addSchemaParseAnnotations(JavaEEMetaDataConstants.J2EE_NS, Boolean.FALSE);
      return resolver;
   }
   
   /**
    * Setup the test delegate
    * 
    * @param clazz the class
    * @return the delegate
    * @throws Exception for any error
    */
   public static AbstractTestDelegate getDelegate(Class<?> clazz) throws Exception
   {
      return new JavaEEMetaDataTestDelegate(clazz);
   }

   public static boolean validateSchema()
   {
      return true;
   }
   
   protected JavaEEMetaDataTestDelegate getJavaEEMetaDataDelegate()
   {
      return (JavaEEMetaDataTestDelegate) getDelegate();
   }

   /**
    * Unmarshal some xml
    * 
    * @param <T> the expected type
    * @param expected the expected type
    * @return the unmarshalled object
    * @throws Exception for any error
    */
   protected <T> T unmarshal(Class<T> expected) throws Exception
   {
      String name = getClass().getSimpleName();
      int index = name.lastIndexOf("UnitTestCase");
      if (index != -1)
         name = name.substring(0, index);
      name = name + "_" + getName() + ".xml";
      return unmarshal(name, expected, null);
   }

   /**
    * Unmarshal some xml
    *
    * @param <T> the expected type
    * @param expected the expected type
    * @param resolver the resolver 
    * @return the unmarshalled object
    * @throws Exception for any error
    */
   protected <T> T unmarshal(Class<T> expected, SchemaBindingResolver resolver) throws Exception
   {
      String name = getClass().getSimpleName();
      int index = name.lastIndexOf("UnitTestCase");
      if (index != -1)
         name = name.substring(0, index);
      name = name + "_" + getName() + ".xml";
      return unmarshal(name, expected, resolver);
   }

   /**
    * Unmarshal some xml
    * 
    * @param <T> the expected type
    * @param name the name
    * @param expected the expected type
    * @param resolver the resolver
    * @return the unmarshalled object
    * @throws Exception for any error
    */
   protected <T> T unmarshal(String name, Class<T> expected, SchemaBindingResolver resolver) throws Exception
   {
      Object object = unmarshal(name, resolver);
      if (object == null)
         fail("No object from " + name);
      assertTrue("Object '" + object + "' cannot be assigned to " + expected.getName(), expected.isAssignableFrom(object.getClass()));
      return expected.cast(object);
   }
   
   /**
    * Unmarshal some xml
    * 
    * @param name the name
    * @return the unmarshalled object
    * @throws Exception for any error
    */
   protected Object unmarshal(String name) throws Exception
   {
      return unmarshal(name, (SchemaBindingResolver) null);
   }
   
   /**
    * Unmarshall some xml.
    * 
    * @param <T>        the expected type
    * @param name       the file name
    * @param expected   the expected type
    * @return           the unmarshalled object
    * @throws Exception for any error
    */
   protected <T> T unmarshal(String name, Class<T> expected) throws Exception
   {
      return unmarshal(name, expected, null);
   }
   
   /**
    * Unmarshal some xml
    * 
    * @param name the name
    * @param resolver the resolver
    * @return the unmarshalled object
    * @throws Exception for any error
    */
   protected Object unmarshal(String name, SchemaBindingResolver resolver) throws Exception
   {
      String url = findXML(name);
      JavaEEMetaDataTestDelegate delegate = getJavaEEMetaDataDelegate();
      //delegate.setValidateSchema(false);
      return delegate.unmarshal(url, resolver);
   }

   /**
    * Find the xml
    * 
    * @param name the name
    * @return the url of the xml
    */
   protected String findXML(String name)
   {
      URL url = getResource(name);
      if (url == null)
         fail(name + " not found");
      return url.toString();
   }

   /**
    * Find the xml
    * 
    * @param name the name
    * @return the url of the xml
    */
   protected static String findXSD(String name)
   {
      URL url = findResource(AbstractJavaEEMetaDataTest.class, "test" +"/" + name);
      if (url == null)
         fail(name + " not found");
      return url.toString();
   }
   
   protected static SchemaBindingResolver schemaResolverForClass(final Class<?> root)
   {
      return new SchemaBindingResolver()
      {
         public String getBaseURI()
         {
            return null;
         }

         public SchemaBinding resolve(String nsUri, String baseURI, String schemaLocation)
         {
            return JBossXBBuilder.build(root);
         }

         public LSInput resolveAsLSInput(String nsUri, String baseUri, String schemaLocation)
         {
            return null;
         }

         public void setBaseURI(String baseURI)
         {
         }
      };
   }

   protected static SchemaBindingResolver schemaResolverForElement(final QName rootQName, final Class<?> rootType)
   {
      return new SchemaBindingResolver()
      {
         public String getBaseURI()
         {
            return null;
         }

         public SchemaBinding resolve(String nsUri, String baseURI, String schemaLocation)
         {
            XmlType xmlType = rootType.getAnnotation(XmlType.class);
            if(xmlType == null)
            {
               fail("The root type is expected to be annotated with XmlType: " + rootType);
            }
            
            SchemaBinding schema = JBossXBBuilder.build(rootType);
            String typeNs = xmlType.namespace();
            if(typeNs.equals(JBossXmlConstants.DEFAULT))
            {
               typeNs = (String) schema.getNamespaces().iterator().next();
            }
            QName typeName = new QName(typeNs, xmlType.name());
            TypeBinding type = schema.getType(typeName);
            if(type == null)
            {
               fail("Type " + typeName + " not found in the schema!");
            }
            
            schema.addElement(rootQName, type);
            
            return schema;
         }

         public LSInput resolveAsLSInput(String nsUri, String baseUri, String schemaLocation)
         {
            return null;
         }

         public void setBaseURI(String baseURI)
         {
         }
      };
   }
}
