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

import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.NamedMetaData;

/**
 * NamedMethodMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="named-methodType", propOrder={"methodName", "methodParams"})
public class NamedMethodMetaData extends NamedMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -2439949201898491207L;

   /** The method parameters */
   private MethodParametersMetaData methodParams;
   
   /**
    * Create a new NamedMethodMetaData.
    */
   public NamedMethodMetaData()
   {
      // For serialization
   }

   /**
    * Get the methodName.
    * 
    * @return the methodName.
    */
   public String getMethodName()
   {
      return getName();
   }

   /**
    * Set the methodName.
    * 
    * @param methodName the methodName.
    * @throws IllegalArgumentException for a null methodName
    */
   public void setMethodName(String methodName)
   {
      setName(methodName);
   }

   /**
    * Get the methodParams.
    * 
    * @return the methodParams.
    */
   public MethodParametersMetaData getMethodParams()
   {
      return methodParams;
   }

   /**
    * Set the methodParams.
    * 
    * @param methodParams the methodParams.
    * @throws IllegalArgumentException for a null methodParams
    */
   public void setMethodParams(MethodParametersMetaData methodParams)
   {
      if (methodParams == null)
         throw new IllegalArgumentException("Null methodParams");
      this.methodParams = methodParams;
   }
   
   public boolean equals(Object o)
   {
      if(this == o)
         return true;
      if(!(o instanceof NamedMethodMetaData))
         return false;
      
      NamedMethodMetaData other = (NamedMethodMetaData) o;
      if(this.getMethodName() == null)
      {
         if(other.getMethodName() != null)
            return false;
      }
      else if(! this.getMethodName().equals(other.getMethodName()))
         return false;

      if(this.methodParams == null
            && other.methodParams == null)
         return true;
      
      if(this.methodParams == null
            && other.methodParams != null
            && other.methodParams.size() == 0)
         return true;
      
      if(other.methodParams == null
            && this.methodParams != null
            && this.methodParams.size() == 0)
         return true;
      
      if(this.methodParams != null
            && other.methodParams != null
            && this.methodParams.size() == other.methodParams.size()
            && this.methodParams.equals(other.methodParams))
         return true;
      
      return false;
   }
}
