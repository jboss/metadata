/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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

import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Startup;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jboss.metadata.common.ejb.ITimeoutTarget;
import org.jboss.metadata.ejb.spec.AsyncMethodsMetaData;
import org.jboss.metadata.ejb.spec.ConcurrencyManagementTypeAdapter;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.metadata.ejb.spec.SessionType;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class JBossSessionBean31MetaData extends JBossSessionBeanMetaData implements ITimeoutTarget // FIXME: AbstractProcessor.processClass doesn't take super interfaces into account
{
   private static final long serialVersionUID = 1L;

   private AsyncMethodsMetaData asyncMethods;

   /**
    * Flag indicating whether this bean has a no-interface view
    */
   private boolean noInterfaceBean;

   /**
    * Flag indicating if a singleton bean is set for init-on-startup
    */
   private Boolean initOnStartup;

   /**
    * Concurrency management type of the bean
    */
   private ConcurrencyManagementType concurrencyManagementType;

   public AsyncMethodsMetaData getAsyncMethods()
   {
      return asyncMethods;
   }

   public void setAsyncMethods(AsyncMethodsMetaData asyncMethods)
   {
      if (asyncMethods == null)
         throw new IllegalArgumentException("asyncMethods is null");

      this.asyncMethods = asyncMethods;
   }

   private void merge(AsyncMethodsMetaData override, AsyncMethodsMetaData original)
   {
      this.asyncMethods = new AsyncMethodsMetaData();
      if (override != null)
         asyncMethods.addAll(override);
      if (original != null)
         asyncMethods.addAll(original);
   }

   /**
    * Returns true if this bean exposes a no-interface view
    * @return
    */
   public boolean isNoInterfaceBean()
   {
      return this.noInterfaceBean;
   }

   /**
    * Set the metadata to represent whether this bean
    * exposes an no-interface view
    * @param isNoInterfaceBean True if the bean exposes a no-interface
    *                           view. Else set to false. 
    */
   public void setNoInterfaceBean(boolean isNoInterfaceBean)
   {
      this.noInterfaceBean = isNoInterfaceBean;
   }

   /**
    * 
    * @return Returns true if this is a singleton session bean.
    * Else returns false.
    */
   public boolean isSingleton()
   {
      SessionType type = this.getSessionType();
      if (type == null)
      {
         return false;
      }
      return SessionType.Singleton == type;
   }

   /**
    * @return Returns true if a singleton bean is marked for init-on-startup ({@link Startup})
    * 
    */
   public boolean isInitOnStartup()
   {
      return this.initOnStartup == null ? Boolean.FALSE : this.initOnStartup;
   }

   /**
    * Set the init-on-startup property of a singleton bean
    * 
    * @param initOnStartup True if the singleton bean has to be inited on startup. False otherwise
    * 
    */
   public void setInitOnStartup(boolean initOnStartup)
   {

      this.initOnStartup = initOnStartup;
   }

   /**
    * Sets the concurrency management type of this bean
    * @param concurrencyManagementType The concurrency management type
    * @throws If the passed <code>concurrencyManagementType</code> is null
    */
   @XmlJavaTypeAdapter(ConcurrencyManagementTypeAdapter.class)
   public void setConcurrencyManagementType(ConcurrencyManagementType concurrencyManagementType)
   {
      if (concurrencyManagementType == null)
      {
         throw new IllegalArgumentException("Concurrency management type cannot be null");
      }
      this.concurrencyManagementType = concurrencyManagementType;
   }

   /**
    * Returns the concurrency management type of this bean
    * @return
    */
   public ConcurrencyManagementType getConcurrencyManagementType()
   {
      return this.concurrencyManagementType;
   }

   @Override
   public void merge(JBossEnterpriseBeanMetaData override, JBossEnterpriseBeanMetaData original)
   {
      super.merge(override, original);

      JBossSessionBean31MetaData joverride = override instanceof JBossGenericBeanMetaData
            ? null
            : (JBossSessionBean31MetaData) override;
      JBossSessionBean31MetaData soriginal = original instanceof JBossGenericBeanMetaData
            ? null
            : (JBossSessionBean31MetaData) original;

      merge(joverride != null ? joverride.asyncMethods : null, soriginal != null ? soriginal.asyncMethods : null);

      // merge the no-interface information
      if (joverride != null)
      {
         this.noInterfaceBean = joverride.isNoInterfaceBean();
      }
      else if (soriginal != null)
      {
         this.noInterfaceBean = soriginal.isNoInterfaceBean();
      }

      // merge the init-on-startup information
      if (joverride != null)
      {
         this.initOnStartup = joverride.isInitOnStartup();
      }
      else if (soriginal != null)
      {
         this.initOnStartup = soriginal.isInitOnStartup();
      }

   }

   @Override
   public void merge(JBossEnterpriseBeanMetaData override, EnterpriseBeanMetaData original, String overridenFile,
         String overrideFile, boolean mustOverride)
   {
      super.merge(override, original, overridenFile, overrideFile, mustOverride);

      JBossSessionBean31MetaData joverride = (JBossSessionBean31MetaData) override;
      SessionBean31MetaData soriginal = (SessionBean31MetaData) original;

      merge(joverride != null ? joverride.asyncMethods : null, soriginal != null ? soriginal.getAsyncMethods() : null);

      // merge rest of the metadata
      if (joverride != null)
      {
         this.noInterfaceBean = joverride.isNoInterfaceBean();
         this.initOnStartup = joverride.isInitOnStartup();
         if (joverride.concurrencyManagementType != null)
         {
            this.concurrencyManagementType = joverride.concurrencyManagementType;
         }
      }
      else if (soriginal != null)
      {
         this.noInterfaceBean = soriginal.isNoInterfaceBean();
         this.initOnStartup = soriginal.isInitOnStartup();
         if (soriginal.getConcurrencyManagementType() != null)
         {
            this.concurrencyManagementType = soriginal.getConcurrencyManagementType();
         }
      }

   }
}
