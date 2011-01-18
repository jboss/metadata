/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.test.metadata.ejb;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;

import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.util.Classes;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractJBossEnterpriseBeanOverrideTest extends TestCase
{
   private static final Object[] EMPTY_ARR = new Object[]{};

   /**
    * Test the simpleProperties method for consistency of the values being set and compared
    */
   public void testSimplePropertiesMethod() throws Exception
   {
      BeanInfo beanInfo = Introspector.getBeanInfo(JBossSessionBeanMetaData.class);
      PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
      Set<String> ignoreNames = Collections.singleton("name");
   
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      processSimpleProperties(props, original, true, true, ignoreNames);
      processSimpleProperties(props, original, true, false, ignoreNames);
   
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      processSimpleProperties(props, override, false, true, ignoreNames);
      processSimpleProperties(props, override, false, false, ignoreNames);
      
      try
      {
         processSimpleProperties(props, override, true, false, ignoreNames);
         fail("override passed for original");
      }
      catch(AssertionFailedError e)
      {
         // expected
      }
   }

   protected void simplePropertiesTest(Class type, Class stopType, Class typeImpl) throws Exception
   {
      simplePropertiesTest(type, stopType, typeImpl, Collections.singleton("name"));
   }
   
   /**
    * Tests merge/override of properties (except those in the ignoreNames set)
    * of primitive, wrapper and java.lang.String types
    * found in the class specified by the argument 'type'.
    * Since 'type' maybe abstract, 'typeImpl' argument is the non-abstract
    * subclass of the 'type'. If 'type' is not abstract then 'typeImpl' should
    * be the same as 'type' or null.
    * 
    * @param type  the type tested for property merges/overrides
    * @param stopType  the superclass of the type whose properties should be
    *  excluded from the test (can be null)
    * @param typeImpl  the type which is a non-abstract subclass
    *  of the type argument (or the same as the type argument, or null)
    * @param ignoreNames  property names that should be excluded from the test
    */
   protected void simplePropertiesTest(Class type, Class stopType, Class typeImpl, Set<String> ignoreNames) throws Exception
   {
      BeanInfo beanInfo = stopType == null ? Introspector.getBeanInfo(type) : Introspector.getBeanInfo(type, stopType);
      PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();

      if(typeImpl == null)
         typeImpl = type;
      
      JBossEnterpriseBeanMetaData original = (JBossEnterpriseBeanMetaData) typeImpl.newInstance();
      original.setEjbName(getName() + "EjbName");
      processSimpleProperties(props, original, true, true, ignoreNames);
   
      // merge only original
      JBossEnterpriseBeanMetaData merged = (JBossEnterpriseBeanMetaData) typeImpl.newInstance();
      merged.merge(null, original);
      assertEquals("missing super.merge(..)?", original.getEjbName(), merged.getEjbName());
      processSimpleProperties(props, merged, true, false, ignoreNames);
   
      // merge with override
      JBossEnterpriseBeanMetaData override = (JBossEnterpriseBeanMetaData) typeImpl.newInstance();
      override.setEjbName(getName() + "EjbName");
      processSimpleProperties(props, override, false, true, ignoreNames);      
      merged = (JBossEnterpriseBeanMetaData) typeImpl.newInstance();
      merged.merge(override, original);
      processSimpleProperties(props, merged, false, false, ignoreNames);
   }

   /**
    * Initializes or asserts properties of java.lang.String, primitive and wrapper types.
    * If original is true then the generated property values are for the original object,
    * otherwise - for the override.
    * If init is true the object is initialized,
    * otherwise the property values are compared to the expected values.
    */
   private void processSimpleProperties(PropertyDescriptor[] props, Object o, boolean original, boolean init, Set<String> ignoreNames) throws Exception
   {
      for (PropertyDescriptor prop : props)
      {
         if(ignoreNames.contains(prop.getName()))
            continue;
         
         Class<?> propertyType = prop.getPropertyType();
         boolean isString = java.lang.String.class.equals(propertyType);
         boolean isPrimitive = propertyType.isPrimitive();
         Method writeMethod = prop.getWriteMethod();
         if (writeMethod == null /*&& prop.getReadMethod() != null*/
               || !(isPrimitive || Classes.isPrimitiveWrapper(propertyType) || isString))
            continue;
      
         Object propValue;
         if(isString)
         {
            propValue = (original ? "original_" : "override_") + prop.getName();
         }
         else
         {
            Class wrapper = propertyType;
            if(isPrimitive)
               wrapper = Classes.getPrimitiveWrapper(propertyType);
            if(wrapper.equals(Boolean.class))
               propValue = original ? "false" : "true";
            else
               propValue = original ? "0" : "1";
            
            Method method = wrapper.getMethod("valueOf", new Class[]{String.class});
            propValue = method.invoke(null, new Object[]{propValue});
         }
   
         if(init)
         {
            writeMethod.invoke(o, new Object[]{propValue});
         }
         else
         {
            Method readMethod = prop.getReadMethod();
            if(readMethod == null)
            {
               if(propertyType.equals(Boolean.class))
                  readMethod = Classes.getAttributeGetter(o.getClass(), prop.getName());
               if(readMethod == null)
                  fail("Read-method not found for " + prop.getName() + " in " + o.getClass());
            }
            assertEquals(prop.getName(), propValue, readMethod.invoke(o, EMPTY_ARR));
         }
      }
   }
}
