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

import java.lang.reflect.Method;
import java.util.List;

import org.jboss.metadata.javaee.spec.LifecycleCallbackMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;

import junit.framework.TestCase;

/**
 * A OrderCallbacksUnitTestCase.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class OrderCallbacksUnitTestCase extends TestCase
{
   public void testOrder() throws Exception
   {
      LifecycleCallbacksMetaData callbacks = new LifecycleCallbacksMetaData();      
      LifecycleCallbackMetaData callback;

      callback = new LifecycleCallbackMetaData();
      callback.setClassName(DefaultSuperSuperClass.class.getName());
      callback.setMethodName("superSuperClassCallback");
      callbacks.add(callback);

      callback = new LifecycleCallbackMetaData();
      callback.setClassName(DefaultSuperClass.class.getName());
      callback.setMethodName("superClassCallback");
      callbacks.add(callback);
      
      callback = new LifecycleCallbackMetaData();
      callback.setMethodName("callback");
      callbacks.add(callback);

      callback = new LifecycleCallbackMetaData();
      callback.setClassName(DefaultSubclass.class.getName());
      callback.setMethodName("subclassCallback");
      callbacks.add(callback);

      callback = new LifecycleCallbackMetaData();
      callback.setClassName(DefaultSubsubclass.class.getName());
      callback.setMethodName("subsubclassCallback");
      callbacks.add(callback);

      callback = new LifecycleCallbackMetaData();
      callback.setClassName(CallbackInterceptorSuperClass.class.getName());
      callback.setMethodName("superClassCallback");
      callbacks.add(callback);

      callback = new LifecycleCallbackMetaData();
      callback.setClassName(CallbackInterceptorClass1.class.getName());
      callback.setMethodName("callback");
      callbacks.add(callback);

      callback = new LifecycleCallbackMetaData();
      callback.setClassName(CallbackInterceptorClass2.class.getName());
      callback.setMethodName("callback");
      callbacks.add(callback);

      // test it a few times
      for(int i = 0; i < 10; ++i)
         shuffleAndOrder(callbacks);
   }

   private void shuffleAndOrder(LifecycleCallbacksMetaData callbacks)
   {
      // shuffle the list
      java.util.Collections.shuffle(callbacks);
      
      // make sure all the callbacks are added
      assertEquals(8, callbacks.size());

      // get the ordered list
      List<Method> ordered = callbacks.getOrderedCallbacks(DefaultClass.class);
      assertNotNull(ordered);
      assertEquals(8, ordered.size());
      
      int i = 0;
      Method method;
      
      // default class and its superclasses are first
      method = ordered.get(i++);
      assertNotNull(method);
      assertEquals(DefaultSuperSuperClass.class, method.getDeclaringClass());

      method = ordered.get(i++);
      assertNotNull(method);
      assertEquals(DefaultSuperClass.class, method.getDeclaringClass());

      method = ordered.get(i++);
      assertNotNull(method);
      assertEquals(DefaultClass.class, method.getDeclaringClass());

      // now check that superclasses don't appear before their subclasses
      boolean sawDefaultSubclass = false;
      boolean sawCallbackInterceptorSuperclass = false;
      for(;i < ordered.size(); ++i)
      {
         method = ordered.get(i);
         Class<?> callbackClass = method.getDeclaringClass();
         if(DefaultSubclass.class.equals(callbackClass))
            sawDefaultSubclass = true;
         else if(DefaultSubsubclass.class.equals(callbackClass))
         {
            if(!sawDefaultSubclass)
               fail(DefaultSubsubclass.class.getName() + " appeared before " + DefaultSubclass.class.getName());
         }
         else if(CallbackInterceptorSuperClass.class.equals(callbackClass))
            sawCallbackInterceptorSuperclass = true;
         else if(!sawCallbackInterceptorSuperclass)
            fail(method.getDeclaringClass().getName() + " appeared before " + CallbackInterceptorSuperClass.class.getName());
      }
   }

   private String listContents(LifecycleCallbacksMetaData callbacks, List<Method> methods)
   {
      StringBuffer sb = new StringBuffer();
      sb.append("lifecycle callbaacks:\n");
      for(LifecycleCallbackMetaData c : callbacks)
         sb.append(c.getClassName()).append('\n');
      sb.append("\nmethods:\n");
      for(Method m : methods)
         sb.append(m.getDeclaringClass().getName()).append('\n');
      return sb.toString();
   }
   
   public static class DefaultSuperSuperClass
   {
      public void superSuperClassCallback() {}
   }

   public static class DefaultSuperClass extends DefaultSuperSuperClass
   {
      public void superClassCallback() {}
   }
   
   public static class DefaultClass extends DefaultSuperClass
   {
      public void callback() {}
   }
   
   public static class DefaultSubclass extends DefaultClass
   {
      public void subclassCallback() {}
   }

   public static class DefaultSubsubclass extends DefaultSubclass
   {
      public void subsubclassCallback() {}
   }

   public static class CallbackInterceptorSuperClass
   {
      public void superClassCallback() {}
   }

   public static class CallbackInterceptorClass1 extends CallbackInterceptorSuperClass
   {
      public void callback() {}
   }

   public static class CallbackInterceptorClass2 extends CallbackInterceptorSuperClass
   {
      public void callback() {}
   }
}
