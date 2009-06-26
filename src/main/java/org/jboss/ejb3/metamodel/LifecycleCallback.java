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
package org.jboss.ejb3.metamodel;

/**
 * The lifecycle-callback type specifies a method on a
 * class to be called when a lifecycle event occurs.
 * Note that each class may have only one lifecycle callback
 * method for any given event and that the method may not
 * be overloaded.
 * 
 * If the lifefycle-callback-class element is missing then
 * the class defining the callback is assumed to be the
 * component class in scope at the place in the descriptor
 * in which the callback definition appears.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class LifecycleCallback
{
   private String lifecycleCallbackClass;
   private String lifecycleCallbackMethod;
   
   public String getLifecycleCallbackClass()
   {
      return lifecycleCallbackClass;
   }
   
   public void setLifecycleCallbackClass(String lifecycleCallbackClass)
   {
      this.lifecycleCallbackClass = lifecycleCallbackClass;
   }
   
   public String getLifecycleCallbackMethod()
   {
      return lifecycleCallbackMethod;
   }
   
   public void setLifecycleCallbackMethod(String lifecycleCallbackMethod)
   {
      this.lifecycleCallbackMethod = lifecycleCallbackMethod;
   }
}
