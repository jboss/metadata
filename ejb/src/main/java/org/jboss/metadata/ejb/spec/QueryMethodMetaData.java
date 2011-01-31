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
 * QueryMethodMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="query-methodType", propOrder={"methodName", "methodParams"})
public class QueryMethodMetaData extends NamedMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -2699157157608413256L;

   /** The method parameters */
   private MethodParametersMetaData methodParams;
   
   /**
    * Create a new QueryMethodMetaData.
    */
   public QueryMethodMetaData()
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
}
