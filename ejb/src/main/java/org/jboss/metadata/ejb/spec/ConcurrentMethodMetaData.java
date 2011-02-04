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
package org.jboss.metadata.ejb.spec;

import javax.ejb.Lock;
import javax.ejb.LockType;
import java.io.Serializable;

/**
 * Metadata for methods which specify {@link Lock} for concurrency management
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class ConcurrentMethodMetaData implements Serializable
{

   private static final long serialVersionUID = 1L;

   private NamedMethodMetaData method;

   private LockType lockType;

   private AccessTimeoutMetaData accessTimeout;

   public void setMethod(NamedMethodMetaData namedMethod)
   {
      this.method = namedMethod;
   }

   public NamedMethodMetaData getMethod()
   {
      return this.method;
   }

   public void setLockType(LockType lockType)
   {
      this.lockType = lockType;
   }

   public LockType getLockType()
   {
      return this.lockType;
   }

   public void setAccessTimeout(AccessTimeoutMetaData accessTimeout)
   {
      this.accessTimeout = accessTimeout;
   }

   public AccessTimeoutMetaData getAccessTimeout()
   {
      return this.accessTimeout;
   }
   
   /**
    * A {@link ConcurrentMethodMetaData} is equal to another {@link ConcurrentMethodMetaData} if
    * the {@link ConcurrentMethodMetaData#method} of both the {@link ConcurrentMethodMetaData} is equal
    * 
    * @see java.lang.Object#equals(java.lang.Object)
    */
   @Override
   public boolean equals(Object obj)
   {
      if(this == obj)
         return true;
      if(!(obj instanceof ConcurrentMethodMetaData))
         return false;
      
      ConcurrentMethodMetaData anotherConcurrentMethod = (ConcurrentMethodMetaData) obj;
      if (this.method == null)
      {
         return false;
      }
      return this.method.equals(anotherConcurrentMethod.method);
   }

   public boolean matches(String methodName, String params[])
   {
      return getMethod().matches(methodName, params);
   }
}
