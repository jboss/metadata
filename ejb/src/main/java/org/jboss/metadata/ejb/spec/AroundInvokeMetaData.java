/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * AroundInvokeMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="around-invokeType", propOrder={"className", "methodName"})
public class AroundInvokeMetaData implements Serializable
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -4782968110296545024L;

   /** The class */
   private String className;
   
   /** The method name */
   private String methodName;
   
   /**
    * Create a new AroundInvokeMetaData.
    */
   public AroundInvokeMetaData()
   {
      // For serialization
   }

   /**
    * Get the className.
    * 
    * @return the className.
    */
   public String getClassName()
   {
      return className;
   }

   /**
    * Set the className.
    * 
    * @param className the className.
    * @throws IllegalArgumentException for a null className
    */
   @XmlElement(name="class")
   public void setClassName(String className)
   {
      if (className == null)
         throw new IllegalArgumentException("Null className");
      this.className = className;
   }

   /**
    * Get the methodName.
    * 
    * @return the methodName.
    */
   public String getMethodName()
   {
      return methodName;
   }

   /**
    * Set the methodName.
    * 
    * @param methodName the methodName.
    * @throws IllegalArgumentException for a null methodName
    */
   public void setMethodName(String methodName)
   {
      if (methodName == null)
         throw new IllegalArgumentException("Null methodName");
      this.methodName = methodName;
   }
}
