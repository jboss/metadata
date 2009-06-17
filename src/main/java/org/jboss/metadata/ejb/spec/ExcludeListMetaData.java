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
 * ExcludeListMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="exclude-listType", propOrder={"descriptions", "methods"})
public class ExcludeListMetaData extends IdMetaDataImplWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -7505132782235878508L;
   
   /** The methods */
   private MethodsMetaData methods;
   
   /**
    * Create a new ExcludeListMetaData.
    */
   public ExcludeListMetaData()
   {
      // For serialization
   }

   /**
    * Get the methods.
    * 
    * @return the methods.
    */
   public MethodsMetaData getMethods()
   {
      return methods;
   }

   /**
    * Set the methods.
    * 
    * @param methods the methods.
    * @throws IllegalArgumentException for a null methods
    */
   @XmlElement(name="method")
   public void setMethods(MethodsMetaData methods)
   {
      if (methods == null)
         throw new IllegalArgumentException("Null methods");
      this.methods = methods;
   }
   
   /**
    * Whether this matches
    * 
    * @param methodName the method name
    * @param params the parameters
    * @param interfaceType the interface type
    * @return true when it matches
    */
   public boolean matches(String methodName, Class[] params, MethodInterfaceType interfaceType)
   {
      if (methods == null)
         return false;
      return methods.matches(methodName, params, interfaceType);
   }

   /**
    * Get the exclude list for an ejb
    * 
    * @param ejbName the ejb name
    * @return the exclude list or null for no result
    * @throws IllegalArgumentException for a null ejb name
    */
   public ExcludeListMetaData getExcludeListByEjbName(String ejbName)
   {
      if (ejbName == null)
         throw new IllegalArgumentException("Null ejbName");

      if (methods == null)
         return null;
      
      MethodsMetaData ejbMethods = methods.getMethodsByEjbName(ejbName);
      if (ejbMethods == null)
         return null;
      
      ExcludeListMetaData result = clone();
      result.setMethods(ejbMethods);
      return result;
   }
   
   @Override
   public ExcludeListMetaData clone()
   {
      return (ExcludeListMetaData) super.clone();
   }
   
   public void merge(ExcludeListMetaData override, ExcludeListMetaData original)
   {
      super.merge(override, original);
      
      // TODO: can't merge myself
      
      methods = new MethodsMetaData();
      methods.merge(override != null ? override.methods : null, original != null ? original.methods : null);
   }
}
