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
package org.jboss.test.metadata.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Pattern;

import org.jboss.logging.Logger;
import org.jboss.xb.binding.resolver.MultiClassSchemaResolver;
import org.jboss.xb.binding.resolver.MutableSchemaResolver;


/**
 * A MetaDataSchemaResolverFactory.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class MetaDataSchemaResolverFactory
{
   private static final Logger log = Logger.getLogger(MetaDataSchemaResolverFactory.class);
   
   private static MultiClassSchemaResolver resolver;
   private static final Pattern classesPattern = Pattern.compile("(\\s)*,(\\s)*");
   
   public static MutableSchemaResolver createSchemaResolver()
   {      
      if (resolver == null)
      {
         resolver = new MultiClassSchemaResolver();
         
         java.net.URL url = Thread.currentThread().getContextClassLoader().getResource("schema2class.properties");
         if(url != null)
         {
            Properties props = new Properties();
            InputStream is = null;
            try
            {
               is = url.openStream();
               props.load(is);
            }
            catch (IOException e)
            {
               throw new IllegalStateException("Failed to load schema2class.properties", e);
            }
            finally
            {
               if(is != null)
               {
                  try
                  {
                     is.close();
                  }
                  catch (IOException e)
                  {
                  }
               }
            }

            final Enumeration<Object> keys = props.keys();
            while(keys.hasMoreElements())
            {
               final Object key = keys.nextElement();
               final String schema = (String)key;
               String classesStr = props.getProperty(schema);
               String[] classNames = classesPattern.split(classesStr);               
               try
               {
                  resolver.mapLocationToClasses(schema, classNames);
               }
               catch (ClassNotFoundException e)
               {
                  throw new IllegalStateException("Failed to load class: '" + classesStr + '\'');
               }

            }
         }
         else
            log.warn("schema2class.properties not found");
      }
      
      return resolver;
   }
}
