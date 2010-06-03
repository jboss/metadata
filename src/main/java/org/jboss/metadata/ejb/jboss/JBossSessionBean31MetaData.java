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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Startup;

import org.jboss.metadata.common.ejb.IScheduleTarget;
import org.jboss.metadata.common.ejb.ITimeoutTarget;
import org.jboss.metadata.ejb.spec.AccessTimeoutMetaData;
import org.jboss.metadata.ejb.spec.AsyncMethodsMetaData;
import org.jboss.metadata.ejb.spec.ConcurrentMethodMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.metadata.ejb.spec.SessionType;
import org.jboss.metadata.ejb.spec.TimerMetaData;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class JBossSessionBean31MetaData extends JBossSessionBeanMetaData implements ITimeoutTarget, IScheduleTarget // FIXME: AbstractProcessor.processClass doesn't take super interfaces into account
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
    * Concurrent methods of this bean
    */
   private Map<NamedMethodMetaData, ConcurrentMethodMetaData> concurrentMethods;

   /**
    * Bean level access timeout
    */
   private AccessTimeoutMetaData beanLevelAccessTimeout;

   /**
    * Concurrency management type of the bean
    */
   private ConcurrencyManagementType concurrencyManagementType;

   /**
    * The lock type that is set at the bean level
    */
   private LockType beanLevelLockType;
   
   /**
    * DependsOn for a singleton bean
    */
   private String[] dependsOn;
   
   /**
    * For beans which have auto-timers (ex: through use of {@link Schedule})
    */
   private TimerMetaData timer;

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

   /**
    * Sets the concurrent methods of this bean
    * @param concurrentMethods
    * @throws IllegalArgumentException If the passed <code>concurrentMethods</code> is null
    */
   public void setConcurrentMethods(Set<ConcurrentMethodMetaData> concurrentMethods)
   {
      if (concurrentMethods == null)
      {
         throw new IllegalArgumentException("Concurrent methods cannot be set to null");
      }
      this.concurrentMethods = new HashMap<NamedMethodMetaData, ConcurrentMethodMetaData>();
      for (ConcurrentMethodMetaData concurrentMethod : concurrentMethods)
      {
         this.concurrentMethods.put(concurrentMethod.getMethod(), concurrentMethod);
      }
   }

   /**
    * Returns a {@link Map} whose key represents a {@link NamedMethodMetaData} and whose value
    * represents {@link ConcurrentMethodMetaData} of this bean. Returns an empty {@link Map} if
    * there are no concurrent methods for this bean
    * @return
    */
   public Map<NamedMethodMetaData, ConcurrentMethodMetaData> getConcurrentMethods()
   {
      if (this.concurrentMethods == null)
      {
         this.concurrentMethods = new HashMap<NamedMethodMetaData, ConcurrentMethodMetaData>();
      }
      return this.concurrentMethods;
   }

   /**
    * Sets the lock type applicable at the bean level
    * @param lockType {@link LockType}
    */
   public void setLockType(LockType lockType)
   {
      this.beanLevelLockType = lockType;
   }

   /**
    * Returns the lock type applicable at the bean level
    * @return
    */
   public LockType getLockType()
   {
      return this.beanLevelLockType;
   }

   /**
    * Sets the bean level access timeout metadata
    * @param accessTimeout {@link AccessTimeoutMetaData}
    */
   public void setAccessTimeout(AccessTimeoutMetaData accessTimeout)
   {
      this.beanLevelAccessTimeout = accessTimeout;
   }

   /**
    * Returns the access timeout metadata applicable at bean level
    * 
    * @return
    */
   public AccessTimeoutMetaData getAccessTimeout()
   {
      return this.beanLevelAccessTimeout;
   }
   
   /**
    * Returns the names of one or more Singleton beans in the same application 
    * as the referring Singleton. 
    * @return
    */
   public String[] getDependsOn()
   {
      return this.dependsOn;
   }
   
   /**
    * Sets the names of one or more singleton beans, each of which must be initialized before
    * the referring bean. Each dependent bean is expressed using ejb-link syntax.
    * 
    * @param dependsOn The singleton bean dependencies 
    */
   public void setDependsOn(String[] dependsOn)
   {
      this.dependsOn = dependsOn;
   }
   
   /**
    * Sets the names of one or more singleton beans, each of which must be initialized before
    * the referring bean. Each dependent bean is expressed using ejb-link syntax.
    * 
    * @param dependsOn The singleton bean dependencies
    */
   public void setDependsOn(Collection<String> dependsOn)
   {
      if (dependsOn == null)
      {
         return;
      }
      this.setDependsOn(dependsOn.toArray(new String[dependsOn.size()]));
   }


   /**
    * Returns the {@link TimerMetaData} associated (if any) with this bean
    */
   public TimerMetaData getTimer()
   {
      return this.timer;
   }
   
   /**
    * Sets the {@link TimerMetaData} associated with this bean
    */
   public void setTimer(TimerMetaData timer)
   {
      this.timer = timer;
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

      // merge the rest
      if (joverride != null)
      {
         this.noInterfaceBean = joverride.isNoInterfaceBean();
         this.initOnStartup = joverride.isInitOnStartup();
         if (joverride.concurrencyManagementType != null)
         {
            this.concurrencyManagementType = joverride.concurrencyManagementType;
         }
         if (joverride.concurrentMethods != null)
         {
            if (this.concurrentMethods == null)
            {
               this.concurrentMethods = new HashMap<NamedMethodMetaData, ConcurrentMethodMetaData>();
            }
            this.concurrentMethods.putAll(joverride.concurrentMethods);
         }
         if (joverride.beanLevelLockType != null)
         {
            this.beanLevelLockType = joverride.beanLevelLockType;
         }
         if (joverride.beanLevelAccessTimeout != null)
         {
            this.beanLevelAccessTimeout = joverride.beanLevelAccessTimeout;
         }
         if (joverride.dependsOn != null)
         {
            this.dependsOn = joverride.dependsOn;
         }
         if (joverride.timer != null)
         {
            this.timer = joverride.timer;
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
         if (soriginal.concurrentMethods != null)
         {
            if (this.concurrentMethods == null)
            {
               this.concurrentMethods = new HashMap<NamedMethodMetaData, ConcurrentMethodMetaData>();
            }
            this.concurrentMethods.putAll(soriginal.concurrentMethods);
         }
         if (soriginal.beanLevelLockType != null)
         {
            this.beanLevelLockType = soriginal.beanLevelLockType;
         }
         if (soriginal.beanLevelAccessTimeout != null)
         {
            this.beanLevelAccessTimeout = soriginal.beanLevelAccessTimeout;
         }
         if (soriginal.dependsOn != null)
         {
            this.dependsOn = soriginal.dependsOn;
         }
         if (soriginal.timer != null)
         {
            this.timer = soriginal.timer;
         }
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
         if (joverride.concurrentMethods != null)
         {
            if (this.concurrentMethods == null)
            {
               this.concurrentMethods = new HashMap<NamedMethodMetaData, ConcurrentMethodMetaData>();
            }
            this.concurrentMethods.putAll(joverride.concurrentMethods);
         }
         if (joverride.beanLevelLockType != null)
         {
            this.beanLevelLockType = joverride.beanLevelLockType;
         }
         if (joverride.beanLevelAccessTimeout != null)
         {
            this.beanLevelAccessTimeout = joverride.beanLevelAccessTimeout;
         }
         if (joverride.dependsOn != null)
         {
            this.dependsOn = joverride.dependsOn;
         }
         if (joverride.timer != null)
         {
            this.timer = joverride.timer;
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
         if (soriginal.getConcurrentMethods() != null)
         {
            if (this.concurrentMethods == null)
            {
               this.concurrentMethods = new HashMap<NamedMethodMetaData, ConcurrentMethodMetaData>();
            }
            this.concurrentMethods.putAll(soriginal.getConcurrentMethods());
         }
         if (soriginal.getLockType() != null)
         {
            this.beanLevelLockType = soriginal.getLockType();
         }
         if (soriginal.getAccessTimeout() != null)
         {
            this.beanLevelAccessTimeout = soriginal.getAccessTimeout();
         }
         if (soriginal.getDependsOn() != null)
         {
            this.dependsOn = soriginal.getDependsOn();
         }
         if (soriginal.getTimer() != null)
         {
            this.timer = soriginal.getTimer();
         }
      }

   }

   private void merge(AsyncMethodsMetaData override, AsyncMethodsMetaData original)
   {
      this.asyncMethods = new AsyncMethodsMetaData();
      if (override != null)
         asyncMethods.addAll(override);
      if (original != null)
         asyncMethods.addAll(original);
   }

}
