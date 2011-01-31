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

import org.jboss.metadata.javaee.support.NamedMetaData;


/**
 * ActivationConfigPropertyMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="activation-config-propertyType", propOrder={"activationConfigPropertyName", "value"})
public class ActivationConfigPropertyMetaData extends NamedMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -2551310342222240351L;

   /** The value */
   private String value;
   
   /**
    * Create a new ActivationConfigMetaData.
    */
   public ActivationConfigPropertyMetaData()
   {
      // For serialization
   }

   /**
    * Get the activationConfigPropertyName.
    * 
    * @return the activationConfigPropertyName.
    */
   public String getActivationConfigPropertyName()
   {
      return getName();
   }

   /**
    * Set the activationConfigPropertyName.
    * 
    * @param activationConfigPropertyName the activationConfigPropertyName.
    * @throws IllegalArgumentException for a null activationConfigPropertyName
    */
   public void setActivationConfigPropertyName(String activationConfigPropertyName)
   {
      setName(activationConfigPropertyName);
   }

   /**
    * Get the value.
    * 
    * @return the value.
    */
   public String getValue()
   {
      return value;
   }

   /**
    * Set the value.
    * 
    * @param value the value.
    * @throws IllegalArgumentException for a null value
    */
   @XmlElement(name="activation-config-property-value")
   public void setValue(String value)
   {
      if (value == null)
         throw new IllegalArgumentException("Null value");
      this.value = value;
   }
}
