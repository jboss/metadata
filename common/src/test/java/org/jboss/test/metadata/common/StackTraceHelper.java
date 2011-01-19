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

/**
 * Allows better use of StackTrace.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class StackTraceHelper
{
   private static int indexOfMe(StackTraceElement stackTrace[])
   {
      for(int i = 0; i < stackTrace.length; i++)
      {
         if(stackTrace[i].getClassName().equals(StackTraceHelper.class.getName()))
            return i;
      }
      return -1;
   }
   
   /**
    * Finds the method who called upon the caller of this method.
    * <br/>
    * <b>TODO: </b>If the caller has multiple methods with the same name, this
    * method fails.
    * 
    * @return   the method who called the caller
    */
   public static Method whoCalledMe()
   {
      try
      {
         StackTraceElement stackTrace[] = Thread.currentThread().getStackTrace();
         // Make sure we work on other VM implementations
         int i = indexOfMe(stackTrace);
         assert i != -1;
         StackTraceElement element = stackTrace[i + 2];
         Class<?> cls = Class.forName(element.getClassName());
         // TODO: this is not a safe way to find out the right method
         Method method = ClassHelper.getMethodByName(cls, element.getMethodName());
         return method;
      }
      catch(ClassNotFoundException e)
      {
         // Should not happen
         throw new RuntimeException(e);
      }
      catch (NoSuchMethodException e)
      {
         // Should not happen
         throw new RuntimeException(e);
      }
   }
}
