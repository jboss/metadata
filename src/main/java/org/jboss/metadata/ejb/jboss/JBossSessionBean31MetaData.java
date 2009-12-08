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

import org.jboss.metadata.common.ejb.ITimeoutTarget;
import org.jboss.metadata.ejb.spec.AsyncMethodsMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class JBossSessionBean31MetaData extends JBossSessionBeanMetaData
   implements ITimeoutTarget // FIXME: AbstractProcessor.processClass doesn't take super interfaces into account
{
   private static final long serialVersionUID = 1L;

   private AsyncMethodsMetaData asyncMethods;
   
   /**
    * Flag indicating whether this bean has a no-interface view
    */
   private boolean noInterfaceBean;

   public AsyncMethodsMetaData getAsyncMethods()
   {
      return asyncMethods;
   }
   
   public void setAsyncMethods(AsyncMethodsMetaData asyncMethods)
   {
      if(asyncMethods == null)
         throw new IllegalArgumentException("asyncMethods is null");
      
      this.asyncMethods = asyncMethods;
   }
   
   private void merge(AsyncMethodsMetaData override, AsyncMethodsMetaData original)
   {
      this.asyncMethods = new AsyncMethodsMetaData();
      if(override != null)
         asyncMethods.addAll(override);
      if(original != null)
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

   @Override
   public void merge(JBossEnterpriseBeanMetaData override, JBossEnterpriseBeanMetaData original)
   {
      super.merge(override, original);
      
      JBossSessionBean31MetaData joverride = override instanceof JBossGenericBeanMetaData ? null : (JBossSessionBean31MetaData) override;
      JBossSessionBean31MetaData soriginal = original instanceof JBossGenericBeanMetaData ? null : (JBossSessionBean31MetaData) original;
      
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

      
   }
   
   @Override
   public void merge(JBossEnterpriseBeanMetaData override, EnterpriseBeanMetaData original, String overridenFile,
         String overrideFile, boolean mustOverride)
   {
      super.merge(override, original, overridenFile, overrideFile, mustOverride);
      
      JBossSessionBean31MetaData joverride = (JBossSessionBean31MetaData) override;
      SessionBean31MetaData soriginal = (SessionBean31MetaData) original;
      
      merge(joverride != null ? joverride.asyncMethods : null, soriginal != null ? soriginal.getAsyncMethods() : null);
      
      // merge the no-interface information
      if (joverride != null)
      {
         this.noInterfaceBean = joverride.isNoInterfaceBean();
      }
      else if (soriginal != null)
      {
         this.noInterfaceBean = soriginal.isNoInterfaceBean();
      }
   }
}
