/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors as indicated
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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Useful methods on classes.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class ClassHelper
{
   /**
    * Find that one method.
    * 
    * @param cls            the class to scan
    * @param methodName     the method to find
    * @return               the method
    * @throws NoSuchMethodException     method can't be found
    * @throws RuntimeException          multiple methods found
    */
   public static Method getMethodByName(Class<?> cls, String methodName) throws NoSuchMethodException
   {
      List<Method> methods = getMethodsByName(cls, methodName);
      if(methods.size() == 0)
         throw new NoSuchMethodException(methodName + " on " + cls);
      if(methods.size() > 1)
         throw new RuntimeException("Doh! Too many methods named " + methodName + " on " + cls);
      return methods.get(0);
   }

   /**
    * Find all methods with a specific name on a class regardless
    * of parameter signature.
    * 
    * @param cls            the class to scan
    * @param methodName     the name of the methods to find
    * @return               a list of methods found, or empty
    */
   public static List<Method> getMethodsByName(Class<?> cls, String methodName)
   {
      List<Method> methods = new ArrayList<Method>();
      for(Method method : cls.getDeclaredMethods())
      {
         if(method.getName().equals(methodName))
            methods.add(method);
      }
      return methods;
   }
}
