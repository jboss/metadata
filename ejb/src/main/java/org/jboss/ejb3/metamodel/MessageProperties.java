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
import java.util.List;

/**
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @version <tt>$Revision: 61136 $</tt>
 */
public class MessageProperties
{
   private List methods = new ArrayList();
   private String delivery;
   private String priority;
   private String className;

   public List getMethods()
   {
      return methods;
   }
   
   public void addMethod(Method method)
   {
      methods.add(method);
   }
   
   public String getDelivery()
   {
      return delivery;
   }
   
   public void setDelivery(String delivery)
   {
      this.delivery = delivery;
   }
   
   public String getClassName()
   {
      return className;
   }
   
   public void setClassName(String className)
   {
      this.className = className;
   }
   
   public String getPriority()
   {
      return priority;
   }
   
   public void setPriority(String priority)
   {
      this.priority = priority;
   }

   public String toString()
   {
      StringBuffer sb = new StringBuffer(100);
      sb.append("[");
      sb.append("className=").append(className);
      sb.append(", priority=").append(priority);
      sb.append(", delivery=").append(delivery);
      sb.append(", methods=").append(methods);
      sb.append("]");
      return sb.toString();
   }

}
