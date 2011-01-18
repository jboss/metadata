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
package org.jboss.metadata.ejb.jboss;

import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * IORASContextMetaData.
 * 
 * TODO LAST enums
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="as-contextType", propOrder={"descriptions", "authMethod", "realm", "required"})
public class IORASContextMetaData extends IdMetaDataImplWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -4611413076087109396L;

   /** Username and password  */
   public static final String AUTH_METHOD_USERNAME_PASSWORD = "USERNAME_PASSWORD";
   
   /** None */
   public static final String AUTH_METHOD_NONE = "NONE";

   /** The authorization method */
   private String authMethod = AUTH_METHOD_USERNAME_PASSWORD;

   /** The realm */
   private String realm = "Default";
   
   /** Whether it is required */
   private boolean required = false;
   
   /**
    * Get the authMethod.
    * 
    * @return the authMethod.
    */
   public String getAuthMethod()
   {
      return authMethod;
   }

   /**
    * Set the authMethod.
    * 
    * @param authMethod the authMethod.
    * @throws IllegalArgumentException for a null authMethod
    */
   public void setAuthMethod(String authMethod)
   {
      if (authMethod == null)
         throw new IllegalArgumentException("Null authMethod");
      if (AUTH_METHOD_NONE.equalsIgnoreCase(authMethod))
         this.authMethod = AUTH_METHOD_NONE;
      else if (AUTH_METHOD_USERNAME_PASSWORD.equalsIgnoreCase(authMethod))
         this.authMethod = AUTH_METHOD_USERNAME_PASSWORD;
      else
         throw new IllegalArgumentException("Unknown ascontext authMethod: " + authMethod);
   }

   /**
    * Get the realm.
    * 
    * @return the realm.
    */
   public String getRealm()
   {
      return realm;
   }

   /**
    * Set the realm.
    * 
    * @param realm the realm.
    * @throws IllegalArgumentException for a null realm
    */
   public void setRealm(String realm)
   {
      if (realm == null)
         throw new IllegalArgumentException("Null realm");
      this.realm = realm;
   }

   /**
    * Get the required.
    * 
    * @return the required.
    */
   public boolean isRequired()
   {
      return required;
   }

   /**
    * Set the required.
    * 
    * @param required the required.
    */
   public void setRequired(boolean required)
   {
      this.required = required;
   }
}
