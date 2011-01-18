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
 * CacheInvalidationConfigMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="cache-invalidation-configType", propOrder={"invalidationGroupName", "invalidationManagerName"})
public class CacheInvalidationConfigMetaData extends IdMetaDataImplWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 5237940805332926376L;

   /** The default invalidation manager */
   public static final String DEFAULT_INVALIDATION_MANAGER_NAME = "jboss.cache:service=InvalidationManager";

   /** The invalidation group name */
   private String invalidationGroupName;

   /** The invalidation manager name */
   private String invalidationManagerName;

   /** The enterprise bean */
   private JBossEntityBeanMetaData entityBean;
   
   /**
    * Set the entityBean.
    * 
    * @param entityBean the entityBean.
    * @throws IllegalArgumentException for a null entityBean
    */
   void setEntityBean(JBossEntityBeanMetaData entityBean)
   {
      if (entityBean == null)
         throw new IllegalArgumentException("Null entityBean");
      this.entityBean = entityBean;
   }

   /**
    * Get the invalidationGroupName.
    * 
    * @return the invalidationGroupName.
    */
   public String getInvalidationGroupName()
   {
      return invalidationGroupName;
   }

   /**
    * Determine the invalidationGroupName.
    * 
    * @return the invalidationGroupName.
    */
   public String determineInvalidationGroupName()
   {
      if (invalidationGroupName == null && entityBean != null)
         return entityBean.getEjbName();
      return invalidationGroupName;
   }

   /**
    * Set the invalidationGroupName.
    * 
    * @param invalidationGroupName the invalidationGroupName.
    * @throws IllegalArgumentException for a null invalidationGroupName
    */
   public void setInvalidationGroupName(String invalidationGroupName)
   {
      if (invalidationGroupName == null)
         throw new IllegalArgumentException("Null invalidationGroupName");
      this.invalidationGroupName = invalidationGroupName;
   }

   /**
    * Get the invalidationManagerName.
    * 
    * @return the invalidationManagerName.
    */
   public String getInvalidationManagerName()
   {
      return invalidationManagerName;
   }

   /**
    * Determine the invalidationManagerName.
    * 
    * @return the invalidationManagerName.
    */
   public String determineInvalidationManagerName()
   {
      if (invalidationManagerName == null)
         return DEFAULT_INVALIDATION_MANAGER_NAME;
      return invalidationManagerName;
   }

   /**
    * Set the invalidationManagerName.
    * 
    * @param invalidationManagerName the invalidationManagerName.
    * @throws IllegalArgumentException for a null invalidationManagerName
    */
   public void setInvalidationManagerName(String invalidationManagerName)
   {
      if (invalidationManagerName == null)
         throw new IllegalArgumentException("Null invalidationManagerName");
      this.invalidationManagerName = invalidationManagerName;
   }
   
   public void merge(CacheInvalidationConfigMetaData override, CacheInvalidationConfigMetaData original)
   {
      String originalEjb = null;
      if(original != null)
      {
         originalEjb = original.entityBean != null ? original.entityBean.getEjbName() : null;
         if(original.invalidationGroupName != null)
            invalidationGroupName = original.invalidationGroupName;
         if(original.invalidationManagerName != null)
            invalidationManagerName = original.invalidationManagerName;
      }
      
      if(override != null)
      {
         if(override.entityBean != null && originalEjb != null)
         {
            if(!originalEjb.equals(override.entityBean.getEjbName()))
               throw new IllegalArgumentException(
                  "Attempt to merge cache invalidation config for different " +
                  "entity beans: " + originalEjb + " and " + 
                  override.entityBean.getEjbName());
         }
         
         if(override.invalidationGroupName != null)
            invalidationGroupName = override.invalidationGroupName;
         if(override.invalidationManagerName != null)
            invalidationManagerName = override.invalidationManagerName;
      }
   }
}
