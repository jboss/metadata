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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * MethodMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="methodType", propOrder={"descriptions", "ejbName", "methodIntf", "methodName", "methodParams"})
public class MethodMetaData extends IdMetaDataImplWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -985973586611090108L;

   /** The ejb name */
   private String ejbName;
   
   /** The method interface */
   private MethodInterfaceType methodInterface;
   
   /** The method name */
   private String methodName;
   
   /** The method parameters */
   private MethodParametersMetaData methodParams;
   
   /**
    * Create a new MethodMetaData.
    */
   public MethodMetaData()
   {
      // For serialization
   }

   /**
    * Get the ejbName.
    * 
    * @return the ejbName.
    */
   public String getEjbName()
   {
      return ejbName;
   }

   /**
    * Set the ejbName.
    * 
    * @param ejbName the ejbName.
    * @throws IllegalArgumentException for a null ejbName
    */
   public void setEjbName(String ejbName)
   {
      if (ejbName == null)
         throw new IllegalArgumentException("Null ejbName");
      this.ejbName = ejbName;
   }

   /**
    * Get the methodInterface.
    * 
    * @return the methodInterface.
    */
   public MethodInterfaceType getMethodIntf()
   {
      return methodInterface;
   }

   /**
    * Set the methodInterface.
    * 
    * @param methodInterface the methodInterface.
    * @throws IllegalArgumentException for a null methodInterface
    */
   @XmlElement(name="method-intf")
   public void setMethodIntf(MethodInterfaceType methodInterface)
   {
      if (methodInterface == null)
         throw new IllegalArgumentException("Null methodInterface");
      this.methodInterface = methodInterface;
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
    * TODO this should probably be done with XmlElementWrapper annotation
    * instead of the JBossXmlChild...
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

   /**
    * Whether this matches
    * 
    * @param methodName the method name
    * @param params the parameters
    * @param interfaceType the interface type
    * @return true when it matches
    */
   public boolean matches(String methodName, String[] params, MethodInterfaceType interfaceType)
   {
      if (methodName == null)
         throw new IllegalArgumentException("Null methodName");

      // Check the type
      if (methodInterface != null && methodInterface != interfaceType)
         return false;
      
      // the wildcard matches everything
      if (getMethodName().equals("*"))
         return true;

      // Method name does not match
      if (getMethodName().equals(methodName) == false)
         return false;
      
      // No parameters to match
      if (methodParams == null)
         return true;

      // Wrong number of paremters
      if(params == null && methodParams.size() > 0)
         return false;
      else if(params != null && params.length != methodParams.size())
         return false;

      // Check each parameter
      int i = 0;
      for (String param : methodParams)
      {
         if (param.equals(params[i++]) == false)
            return false;
      }
      
      // We match
      return true;
   }

   /**
    * Whether this is a home method
    * 
    * @return true when a home method
    */
   public boolean isHomeMethod()
   {
      return MethodInterfaceType.Home == methodInterface;
   }

   /**
    * Whether this is a remote method
    * 
    * @return true when a remote method
    */
   public boolean isRemoteMethod()
   {
      return MethodInterfaceType.Remote == methodInterface;
   }

   /**
    * Whether this is a local home method
    * 
    * @return true when a local home method
    */
   public boolean isLocalHomeMethod()
   {
      return MethodInterfaceType.LocalHome == methodInterface;
   }

   /**
    * Whether this is a local method
    * 
    * @return true when a local method
    */
   public boolean isLocalMethod()
   {
      return MethodInterfaceType.Local == methodInterface;
   }

   /**
    * Whether this is a service endpoint method
    * 
    * @return true when a service endpoint method
    */
   public boolean isServiceEndpointMethod()
   {
      return MethodInterfaceType.ServiceEndpoint == methodInterface;
   }

   @Override
   public String toString()
   {
      StringBuilder tmp = new StringBuilder("MethodMetaData(");
      tmp.append("ejbName=");
      tmp.append(ejbName);
      tmp.append(",interface=");
      tmp.append(methodInterface);
      tmp.append(",method=");
      tmp.append(methodName);
      tmp.append(",params=");
      tmp.append(methodParams);
      tmp.append(')');
      return tmp.toString();
   }
}
