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

import java.util.ArrayList;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 61136 $
 */
public class InterceptorBinding extends Method
{
   Boolean ordered = null;
   private ArrayList<String> interceptorClasses = new ArrayList<String>();
   private boolean excludeDefaultInterceptors;
   private boolean excludeClassInterceptors;
   
   public InterceptorBinding()
   {
      super();
   }

   public boolean getExcludeClassInterceptors()
   {
      return excludeClassInterceptors;
   }

   public void setExcludeClassInterceptors(boolean excludeClassInterceptors)
   {
      this.excludeClassInterceptors = excludeClassInterceptors;
   }

   public boolean getExcludeDefaultInterceptors()
   {
      return excludeDefaultInterceptors;
   }

   public void setExcludeDefaultInterceptors(boolean excludeDefaultInterceptors)
   {
      this.excludeDefaultInterceptors = excludeDefaultInterceptors;
   }

   public ArrayList<String> getInterceptorClasses()
   {
      return interceptorClasses;
   }

   public void addInterceptorClass(String interceptorClass)
   {
      if (ordered == null)
      {
         ordered = false;
      }
      if (ordered) throw new RuntimeException("Cannot have both interceptor-class and interceptor-order in interceptor-binding");
      this.interceptorClasses.add(interceptorClass);
   }

   public void setOrderedInterceptorClasses(InterceptorOrder order)
   {
      if (ordered == null)
      {
         ordered = true;
      }
      if (!ordered) throw new RuntimeException("Cannot have both interceptor-class and interceptor-order in interceptor-binding");
      this.interceptorClasses.addAll(order.getInterceptorClasses());
   }

   public boolean isOrdered()
   {
      if (ordered == null) return false;
      return ordered;
   }

   public String toString()
   {
      return InterceptorBinding.class.getName() + "[ejbName=" + getEjbName() +
         ",methodName=" + getMethodName() +
         ",ordered=" + isOrdered() + 
         ",excludeClassInterceptors=" + excludeClassInterceptors + 
         ",excludeDefaultInterceptors=" + excludeDefaultInterceptors +
         ",interceptorClasses=" + interceptorClasses +
         "]";
   }
}
