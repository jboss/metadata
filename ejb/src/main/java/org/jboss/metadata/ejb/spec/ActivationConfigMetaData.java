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
 * ActivationConfigMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="activation-configType", propOrder={"descriptions", "activationConfigProperties"})
public class ActivationConfigMetaData extends IdMetaDataImplWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -9138498601978922673L;

   /** The activation config properties */
   private ActivationConfigPropertiesMetaData activationConfigProperties;
   
   /**
    * Create a new CMPFieldMetaData.
    */
   public ActivationConfigMetaData()
   {
      // For serialization
   }

   /**
    * Get the activationConfigProperties.
    * 
    * @return the activationConfigProperties.
    */
   public ActivationConfigPropertiesMetaData getActivationConfigProperties()
   {
      return activationConfigProperties;
   }

   /**
    * Set the activationConfigProperties.
    * 
    * @param activationConfigProperties the activationConfigProperties.
    * @throws IllegalArgumentException for a null activationConfigProperties
    */
   @XmlElement(name="activation-config-property")
   public void setActivationConfigProperties(ActivationConfigPropertiesMetaData activationConfigProperties)
   {
      if (activationConfigProperties == null)
         throw new IllegalArgumentException("Null activationConfigProperties");
      this.activationConfigProperties = activationConfigProperties;
   }

   public void merge(ActivationConfigMetaData override, ActivationConfigMetaData original)
   {
      super.merge(override, original);
      ActivationConfigPropertiesMetaData propertyOverride = null;
      if (override != null)
         propertyOverride = override.getActivationConfigProperties();
      ActivationConfigPropertiesMetaData propertyOriginal = null;
      if (original != null)
         propertyOriginal = original.getActivationConfigProperties();
      if (propertyOverride == null || propertyOverride.isEmpty())
      {
         if (propertyOriginal != null)
            activationConfigProperties = propertyOriginal;
         return;
      }
      if (propertyOriginal == null || propertyOriginal.isEmpty())
      {
         if (propertyOverride != null)
            activationConfigProperties = propertyOverride;
         return;
      }
      activationConfigProperties = new ActivationConfigPropertiesMetaData();
      activationConfigProperties.merge(propertyOverride, propertyOriginal);
   }
}
