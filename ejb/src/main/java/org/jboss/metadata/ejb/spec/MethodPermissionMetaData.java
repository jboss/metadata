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

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.spec.EmptyMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * MethodPermissionMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="method-permissionType", propOrder={"descriptions", "roles", "unchecked", "methods"})
public class MethodPermissionMetaData extends IdMetaDataImplWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -985973586611090108L;
   
   /** Whether it is unchecked */
   private EmptyMetaData unchecked;
   
   /** The roles */
   private Set<String> roles;
   
   /** The methods */
   private MethodsMetaData methods;
   
   /**
    * Create a new MethodPermissionMetaData.
    */
   public MethodPermissionMetaData()
   {
      // For serialization
   }

   /**
    * Get the unchecked.
    * 
    * @return the unchecked.
    */
   public boolean isNotChecked()
   {
      return unchecked != null;
   }

   /**
    * Get the unchecked.
    * 
    * @return the unchecked.
    */
   public EmptyMetaData getUnchecked()
   {
      return unchecked;
   }

   /**
    * Set the unchecked.
    * 
    * @param unchecked the unchecked.
    */
   public void setUnchecked(EmptyMetaData unchecked)
   {
      this.unchecked = unchecked;
   }

   /**
    * Get the roles.
    * 
    * @return the roles.
    */
   public Set<String> getRoles()
   {
      return roles;
   }

   /**
    * Set the roles.
    * 
    * @param roles the roles.
    */
   @XmlElement(name="role-name"/*, type=NonNullLinkedHashSet.class*/)
   public void setRoles(Set<String> roles)
   {
      this.roles = roles;
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
    * Get the method permissions for an ejb
    * 
    * @param ejbName the ejb name
    * @return the method permission or null for no result
    * @throws IllegalArgumentException for a null ejb name
    */
   public MethodPermissionMetaData getMethodPermissionByEjbName(String ejbName)
   {
      if (ejbName == null)
         throw new IllegalArgumentException("Null ejbName");

      if (methods == null)
         return null;
      
      MethodsMetaData ejbMethods = methods.getMethodsByEjbName(ejbName);
      if (ejbMethods == null)
         return null;
      
      MethodPermissionMetaData result = clone();
      result.setMethods(ejbMethods);
      return result;
   }

   @Override
   public MethodPermissionMetaData clone()
   {
      return (MethodPermissionMetaData) super.clone();
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
    * Whether this is not checked
    * 
    * @param methodName the method name
    * @param params the parameters
    * @param interfaceType the interface type
    * @return true when it is not checked and it matches matches
    */
   public boolean isNotChecked(String methodName, Class[] params, MethodInterfaceType interfaceType)
   {
      if (isNotChecked() == false)
         return false;
      return matches(methodName, params, interfaceType);
   }

   @Override
   public String toString()
   {
      StringBuilder tmp = new StringBuilder("MethodPermissionMetaData(id=");
      tmp.append(getId());
      if (isNotChecked())
      {
         tmp.append(",unchecked=true");
      }
      else
      {
         tmp.append(",roles=");
         tmp.append(this.roles);
      }
      tmp.append(",methods=");
      tmp.append(this.methods);
      tmp.append(')');
      return tmp.toString();
   }
}
