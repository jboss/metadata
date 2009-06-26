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
 * Represents an <pool-config> element of the jboss.xml deployment descriptor
 *
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @version <tt>$Revision: 61136 $</tt>
 */
public class PoolConfig
{
   private String poolClass = null;
   private String maxSize = null;
   private String timeout = null;
   
   public String getPoolClass()
   {
      return poolClass;
   }

   public void setPoolClass(String poolClass)
   {
      this.poolClass = poolClass;
   }

   public String getMaxSize()
   {
      return maxSize;
   }

   public void setMaxSize(String maxSize)
   {
      this.maxSize = maxSize;
   }
   
   public String getTimeout()
   {
      return timeout;
   }

   public void setTimeout(String timeout)
   {
      this.timeout = timeout;
   }

   public String toString()
   {
      StringBuffer sb = new StringBuffer(100);
      sb.append("[");
      sb.append("poolClass=").append(poolClass);
      sb.append(", maxSize=").append(maxSize);
      sb.append(", timeout=").append(timeout);
      sb.append("]");
      return sb.toString();
   }

}
