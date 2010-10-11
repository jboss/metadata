/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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

import java.lang.reflect.Method;
import java.net.URL;

import org.jboss.net.protocol.URLStreamHandlerFactory;
import org.jboss.test.AbstractTestDelegate;
import org.jboss.xb.binding.Unmarshaller;
import org.jboss.xb.binding.UnmarshallerFactory;
import org.jboss.xb.binding.sunday.unmarshalling.DefaultSchemaResolver;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBinding;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingResolver;
import org.jboss.xb.binding.sunday.unmarshalling.XsdBinder;

/**
 * JBossXBTestDelegate.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 40492 $
 */
public class JBossXBTestDelegate extends AbstractTestDelegate
{
   /** Whether initialization has been done */
   private static boolean done = false;

   /** The unmarshaller factory */
   protected UnmarshallerFactory unmarshallerFactory;

   /** The resolver */
   protected SchemaBindingResolver defaultResolver;
   
   /** validate the schema? */
   protected boolean validateSchema = false;
   
   /**
    * Initialize
    */
   public synchronized static void init()
   {
      if (done)
         return;
      done = true;
      URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory());
      URLStreamHandlerFactory.preload();
      String handlerPkgs = System.getProperty("java.protocol.handler.pkgs");
      if (handlerPkgs != null)
         handlerPkgs += "|org.jboss.net.protocol";
      else
         handlerPkgs = "org.jboss.net.protocol";
      System.setProperty("java.protocol.handler.pkgs", handlerPkgs);
   }

   /**
    * Create a new JBossXBTestDelegate.
    * 
    * @param clazz the test class
    */
   public JBossXBTestDelegate(Class<?> clazz)
   {
      super(clazz);
   }

   @Override
   public void setUp() throws Exception
   {
      super.setUp();
      init();
      unmarshallerFactory = UnmarshallerFactory.newInstance();
      initResolver();
      initValidateSchema();
   }

   
   public boolean isValidateSchema()
   {
      return validateSchema;
   }

   public void setValidateSchema(boolean validateSchema)
   {
      this.validateSchema = validateSchema;
   }

   protected void initResolver() throws Exception
   {
      try
      {
         Method method = clazz.getMethod("initResolver");
         defaultResolver = (SchemaBindingResolver) method.invoke(null);
      }
      catch (NoSuchMethodException ignored)
      {
         defaultResolver = new DefaultSchemaResolver();
      }
   }
   
   protected void initValidateSchema() throws Exception
   {
      try
      {
         Method method = clazz.getMethod("validateSchema");
         validateSchema = (Boolean) method.invoke(null);
      }
      catch (NoSuchMethodException ignored)
      {
         validateSchema = false;
      }
   }
   
   /**
    * Unmarshal an object
    * 
    * @param url the url
    * @param resolver the resolver
    * @return the object
    * @throws Exception for any error
    */
   public Object unmarshal(String url, SchemaBindingResolver resolver) throws Exception
   {
      if (resolver == null)
         resolver = defaultResolver;
      
      long start = System.currentTimeMillis();
      Unmarshaller unmarshaller = unmarshallerFactory.newUnmarshaller();
      unmarshaller.setSchemaValidation(validateSchema);
      log.debug("Initialized parsing in " + (System.currentTimeMillis() - start) + "ms");
      try
      {
         Object result = unmarshaller.unmarshal(url, resolver);
         log.debug("Total parse for " + url + " took " + (System.currentTimeMillis() - start) + "ms");
         return result;
      }
      catch (Exception e)
      {
         log.debug("Error during parsing: " + url, e);
         throw e;
      }
   }
   
   /**
    * Unmarshal an object
    * 
    * @param url the url
    * @param schema the schema
    * @return the object
    * @throws Exception for any error
    */
   public Object unmarshal(String url, SchemaBinding schema) throws Exception
   {
      long start = System.currentTimeMillis();
      Unmarshaller unmarshaller = unmarshallerFactory.newUnmarshaller();
      unmarshaller.setSchemaValidation(isValidateSchema());
      log.debug("Initialized parsing in " + (System.currentTimeMillis() - start) + "ms");
      try
      {
         Object result = unmarshaller.unmarshal(url, schema);
         log.debug("Total parse for " + url + " took " + (System.currentTimeMillis() - start) + "ms");
         return result;
      }
      catch (Exception e)
      {
         log.debug("Error during parsing: " + url, e);
         throw e;
      }
   }
   
   /**
    * Bind a schema
    * 
    * @param url the url
    * @param resolver the resolver
    * @return the object
    * @throws Exception for any error
    */
   public SchemaBinding bind(String url, SchemaBindingResolver resolver) throws Exception
   {
      return XsdBinder.bind(url, resolver);
   }
}
