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

import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;
import org.w3c.dom.Element;

/**
 * ConfigurationMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="container-configurationType",
      propOrder={"descriptions", "containerName",
      "callLogging", "invokerProxyBindingNames",
      "syncOnCommitOnly", "insertAfterEjbPostCreate",
      "ejbStoreOnClean", "storeNotFlushed", "containerInterceptors",
      "instancePool", "instanceCache", "persistenceManager",
      "webClassLoader", "lockingPolicy",
      "containerCacheConf", "containerPoolConf",
      "commitOption", "optiondRefreshRate", "securityDomain",
      "clusterConfig", "depends"}
)
public class ContainerConfigurationMetaData extends NamedMetaDataWithDescriptions
{
   /** The standard CMP2 configuration */
   public static final String CMP_2x = "Standard CMP 2.x EntityBean";

   /** The standard CMP1.1 configuration */
   public static final String CMP_1x = "Standard CMP EntityBean";

   /** The standard BMP configuration */
   public static final String BMP = "Standard BMP EntityBean";

   /** The standard Stateless session configuration */
   public static final String STATELESS = "Standard Stateless SessionBean";

   /** The standard Stateful session configuration */
   public static final String STATEFUL = "Standard Stateful SessionBean";

   /** The message driven bean configuration */
   public static final String MESSAGE_DRIVEN = "Standard Message Driven Bean";

   /** The message inflow driven bean configuration */
   public static final String MESSAGE_INFLOW_DRIVEN = "Standard Message Inflow Driven Bean";

   /** The clustered CMP2 configuration */
   public static final String CLUSTERED_CMP_2x = "Clustered CMP 2.x EntityBean";

   /** The clustered CMP1.1 configuration */
   public static final String CLUSTERED_CMP_1x = "Clustered CMP EntityBean";

   /** The clustered BMP configuration */
   public static final String CLUSTERED_BMP = "Clustered BMP EntityBean";

   /** The clustered stateful session configuration */
   public static final String CLUSTERED_STATEFUL = "Clustered Stateful SessionBean";

   /** The clustered stateless session configuration */
   public static final String CLUSTERED_STATELESS = "Clustered Stateless SessionBean";

   /** The serialVersionUID */
   private static final long serialVersionUID = 5189417462407375043L;

   /** The extends */
   private String extendsName;

   /** Call logging */
   private boolean callLogging;
   
   /** The invoker proxy binding names */
   private Set<String> invokerProxyBindingNames;
   
   /** Sync on commit only */
   private boolean syncOnCommitOnly;

   /** Insert after ejb post create */
   private boolean insertAfterEjbPostCreate;
   
   /** ejbStore on clean */
   private boolean ejbStoreOnClean;
   
   /** Store not flushed */
   private boolean storeNotFlushed = true;
   
   /** The instance pool */
   private String instancePool;
   
   /** The instance cache */
   private String instanceCache;

   /** The persistence manager */
   private String persistenceManager;
   
   /** The web classloader */
   private String webClassLoader;
   
   /** The locking policy */
   private String lockingPolicy;
   
   /** The InstancePool configuration */
   private Element containerPoolConf;
   /** The InstanceCache configuration */
   private Element containerCacheConf;
   /** The ejb container interceptor stack configuration */
   private Element containerInterceptorsConf;

   /** The commit option */
   private CommitOption commitOption = CommitOption.A;
   
   /** The option d refresh rate in milliseconds */
   private long optiondRefreshRateMillis = 0;
   
   /** The security domain */
   private String securityDomain;
   
   /** The cluster config */
   private ClusterConfigMetaData clusterConfig;
   
   /** The depends */
   private Set<String> depends;
   // Flags for
   private boolean commitOptionWasSet;
   private boolean callLoggingWasSet;
   private boolean ejbStoreOnCleanWasSet;
   private boolean insertAfterEjbPostCreateWasSet;
   private boolean storeNotFlushedWasSet;
   private boolean syncOnCommitOnlyWasSet;
   
   /**
    * Get the containerName.
    * 
    * @return the containerName.
    */
   public String getContainerName()
   {
      return getName();
   }

   /**
    * Set the containerName.
    * 
    * @param containerName the containerName.
    * @throws IllegalArgumentException for a null containerName
    */
   public void setContainerName(String containerName)
   {
      setName(containerName);
   }

   /**
    * Get the extendsName.
    * 
    * @return the extendsName.
    */
   public String getExtendsName()
   {
      return extendsName;
   }

   /**
    * Set the extendsName.
    * 
    * @param extendsName the extendsName.
    * @throws IllegalArgumentException for a null extendsName
    */
   @XmlAttribute(name="extends")
   public void setExtendsName(String extendsName)
   {
      if (extendsName == null)
         throw new IllegalArgumentException("Null extendsName");
      this.extendsName = extendsName;
   }

   /**
    * Get the callLogging.
    * 
    * @return the callLogging.
    */
   public boolean isCallLogging()
   {
      return callLogging;
   }
   @XmlTransient
   public boolean callLoggingWasSet()
   {
      return callLoggingWasSet;
   }

   /**
    * Set the callLogging.
    * 
    * @param callLogging the callLogging.
    */
   public void setCallLogging(boolean callLogging)
   {
      this.callLogging = callLogging;
      callLoggingWasSet = true;
   }

   /**
    * Get the syncOnCommitOnly.
    * 
    * @return the syncOnCommitOnly.
    */
   public boolean isSyncOnCommitOnly()
   {
      return syncOnCommitOnly;
   }
   @XmlTransient
   public boolean syncOnCommitOnlyWasSet()
   {
      return syncOnCommitOnlyWasSet;
   }

   /**
    * Set the syncOnCommitOnly.
    * 
    * @param syncOnCommitOnly the syncOnCommitOnly.
    */
   public void setSyncOnCommitOnly(boolean syncOnCommitOnly)
   {
      this.syncOnCommitOnly = syncOnCommitOnly;
      syncOnCommitOnlyWasSet = true;
   }

   /**
    * Get the insertAfterEjbPostCreate.
    * 
    * @return the insertAfterEjbPostCreate.
    */
   public boolean isInsertAfterEjbPostCreate()
   {
      return insertAfterEjbPostCreate;
   }
   @XmlTransient
   public boolean insertAfterEjbPostCreateWasSet()
   {
      return insertAfterEjbPostCreateWasSet;
   }

   /**
    * Set the insertAfterEjbPostCreate.
    * 
    * @param insertAfterEjbPostCreate the insertAfterEjbPostCreate.
    */
   public void setInsertAfterEjbPostCreate(boolean insertAfterEjbPostCreate)
   {
      this.insertAfterEjbPostCreate = insertAfterEjbPostCreate;
      insertAfterEjbPostCreateWasSet = true;
   }

   /**
    * Get the ejbStoreOnClean.
    * 
    * @return the ejbStoreOnClean.
    */
   public boolean isEjbStoreOnClean()
   {
      return ejbStoreOnClean;
   }
   @XmlTransient
   public boolean ejbStoreOnCleanWasSet()
   {
      return ejbStoreOnCleanWasSet;
   }

   /**
    * Set the ejbStoreOnClean.
    * 
    * @param ejbStoreOnClean the ejbStoreOnClean.
    */
   @XmlElement(name="call-ejb-store-on-clean")
   public void setEjbStoreOnClean(boolean ejbStoreOnClean)
   {
      this.ejbStoreOnClean = ejbStoreOnClean;
      ejbStoreOnCleanWasSet = true;
   }

   /**
    * Get the storeNotFlushed.
    * 
    * @return the storeNotFlushed.
    */
   public boolean isStoreNotFlushed()
   {
      return storeNotFlushed;
   }
   @XmlTransient
   public boolean storeNotFlushedWasSet()
   {
      return storeNotFlushedWasSet;
   }

   /**
    * Set the storeNotFlushed.
    * 
    * @param storeNotFlushed the storeNotFlushed.
    * @throws IllegalArgumentException for a null storeNotFlushed
    */
   public void setStoreNotFlushed(boolean storeNotFlushed)
   {
      this.storeNotFlushed = storeNotFlushed;
      storeNotFlushedWasSet = true;
   }

   /**
    * Get the instancePool.
    * 
    * @return the instancePool.
    */
   public String getInstancePool()
   {
      return instancePool;
   }

   /**
    * Set the instancePool.
    * 
    * @param instancePool the instancePool.
    * @throws IllegalArgumentException for a null instancePool
    */
   public void setInstancePool(String instancePool)
   {
      if (instancePool == null)
         throw new IllegalArgumentException("Null instancePool");
      this.instancePool = instancePool;
   }

   /**
    * Get the instanceCache.
    * 
    * @return the instanceCache.
    */
   public String getInstanceCache()
   {
      return instanceCache;
   }

   /**
    * Set the instanceCache.
    * 
    * @param instanceCache the instanceCache.
    * @throws IllegalArgumentException for a null instanceCache
    */
   public void setInstanceCache(String instanceCache)
   {
      if (instanceCache == null)
         throw new IllegalArgumentException("Null instanceCache");
      this.instanceCache = instanceCache;
   }

   /**
    * Get the persistenceManager.
    * 
    * @return the persistenceManager.
    */
   public String getPersistenceManager()
   {
      return persistenceManager;
   }

   /**
    * Set the persistenceManager.
    * 
    * @param persistenceManager the persistenceManager.
    * @throws IllegalArgumentException for a null persistenceManager
    */
   public void setPersistenceManager(String persistenceManager)
   {
      if (persistenceManager == null)
         throw new IllegalArgumentException("Null persistenceManager");
      this.persistenceManager = persistenceManager;
   }

   /**
    * Get the webClassLoader.
    * 
    * @return the webClassLoader.
    */
   public String getWebClassLoader()
   {
      return webClassLoader;
   }

   /**
    * Set the webClassLoader.
    * 
    * @param webClassLoader the webClassLoader.
    * @throws IllegalArgumentException for a null webClassLoader
    */
   @XmlElement(defaultValue="org.jboss.web.WebClassLoader")
   public void setWebClassLoader(String webClassLoader)
   {
      if (webClassLoader == null)
         throw new IllegalArgumentException("Null webClassLoader");
      this.webClassLoader = webClassLoader;
   }

   /**
    * Get the lockingPolicy.
    * 
    * @return the lockingPolicy.
    */
   public String getLockingPolicy()
   {
      return lockingPolicy;
   }

   /**
    * Set the lockingPolicy.
    * 
    * @param lockingPolicy the lockingPolicy.
    * @throws IllegalArgumentException for a null lockingPolicy
    */
   @XmlElement(defaultValue="org.jboss.ejb.plugins.lock.QueuedPessimisticEJBLock")
   public void setLockingPolicy(String lockingPolicy)
   {
      if (lockingPolicy == null)
         throw new IllegalArgumentException("Null lockingPolicy");
      this.lockingPolicy = lockingPolicy;
   }

   /**
    * Get the securityDomain.
    * 
    * @return the securityDomain.
    */
   public String getSecurityDomain()
   {
      return securityDomain;
   }

   /**
    * Set the securityDomain.
    * 
    * @param securityDomain the securityDomain.
    * @throws IllegalArgumentException for a null securityDomain
    */
   public void setSecurityDomain(String securityDomain)
   {
      if (securityDomain == null)
         throw new IllegalArgumentException("Null securityDomain");
      this.securityDomain = securityDomain;
   }

   /**
    * Get the commitOption.
    * 
    * @return the commitOption.
    */
   public CommitOption getCommitOption()
   {
      return commitOption;
   }
   @XmlTransient
   boolean commitOptionWasSet()
   {
      return commitOptionWasSet;
   }

   /**
    * Set the commitOption.
    * 
    * @param commitOption the commitOption.
    * @throws IllegalArgumentException for a null commitOption
    */
   public void setCommitOption(CommitOption commitOption)
   {
      if (commitOption == null)
         throw new IllegalArgumentException("Null commitOption");
      this.commitOption = commitOption;
      commitOptionWasSet = true;
   }

   /**
    * Get the optiondRefreshRateMillis.
    * 
    * @return the optiondRefreshRateMillis.
    */
   public long getOptiondRefreshRateMillis()
   {
      return optiondRefreshRateMillis;
   }

   /**
    * Set the optiondRefreshRateMillis.
    * 
    * @param optiondRefreshRateMillis the optiondRefreshRateMillis.
    * @throws IllegalArgumentException if the refresh rate is not positive
    */
   @XmlTransient
   public void setOptiondRefreshRateMillis(long optiondRefreshRateMillis)
   {
      if (optiondRefreshRateMillis <= 0)
         throw new IllegalArgumentException("optionD-refresh-rate must be positive got " + optiondRefreshRateMillis);
      this.optiondRefreshRateMillis = optiondRefreshRateMillis;
   }

   /**
    * Get the optionDRefreshRate in seconds
    * 
    * @return the optionDRefreshRate in seconds
    */
   public int getOptiondRefreshRate()
   {
      return (int) optiondRefreshRateMillis / 1000;
   }
   
   /**
    * Set the optiondRefreshRateMillis in seconds.
    * 
    * @param optionDRefreshRateSeconds the optionDRefreshRate in seconds
    * @throws IllegalArgumentException if the refresh rate is not positive
    */
   public void setOptiondRefreshRate(int optionDRefreshRateSeconds)
   {
      if (optionDRefreshRateSeconds <= 0)
         throw new IllegalArgumentException("optionD-refresh-rate must be positive got " + optionDRefreshRateSeconds);
      setOptiondRefreshRateMillis(optionDRefreshRateSeconds * 1000);
   }

   public Element getContainerPoolConf()
   {
      return containerPoolConf;
   }

   public void setContainerPoolConf(Element containerPoolConf)
   {
      this.containerPoolConf = containerPoolConf;
   }

   public Element getContainerCacheConf()
   {
      return containerCacheConf;
   }

   public void setContainerCacheConf(Element containerCacheConf)
   {
      this.containerCacheConf = containerCacheConf;
   }

   public Element getContainerInterceptors()
   {
      return containerInterceptorsConf;
   }

   public void setContainerInterceptors(Element containerInterceptorsConf)
   {
      this.containerInterceptorsConf = containerInterceptorsConf;
   }

   /**
    * Get the depends.
    * 
    * @return the depends.
    */
   public Set<String> getDepends()
   {
      return depends;
   }

   /**
    * Set the depends.
    * 
    * @param depends the depends.
    * @throws IllegalArgumentException for a null depends
    */
   //@XmlElement(type=NonNullLinkedHashSet.class)
   public void setDepends(Set<String> depends)
   {
      if (depends == null)
         throw new IllegalArgumentException("Null depends");
      this.depends = depends;
   }

   /**
    * Get the invokerProxyBindingNames.
    * 
    * @return the invokerProxyBindingNames.
    */
   public Set<String> getInvokerProxyBindingNames()
   {
      return invokerProxyBindingNames;
   }

   /**
    * Set the invokerProxyBindingNames.
    * 
    * @param invokerProxyBindingNames the invokerProxyBindingNames.
    * @throws IllegalArgumentException for a null invokerProxyBindingNames
    */
   @XmlElement(name="invoker-proxy-binding-name"/*, type=NonNullLinkedHashSet.class*/)
   public void setInvokerProxyBindingNames(Set<String> invokerProxyBindingNames)
   {
      if (invokerProxyBindingNames == null)
         throw new IllegalArgumentException("Null invokerProxyBindingNames");
      this.invokerProxyBindingNames = invokerProxyBindingNames;
   }

   /**
    * Get the default invoker name
    * 
    * @return the default invoker
    * @throws IllegalStateException when there are no invoker proxy binding names
    */
   public String getDefaultInvokerName()
   {
      String name = null;
      if(invokerProxyBindingNames != null && invokerProxyBindingNames.isEmpty() == false)
         name = invokerProxyBindingNames.iterator().next();
      return name;
   }

   /**
    * Get the clusterConfig.
    * 
    * @return the clusterConfig.
    */
   public ClusterConfigMetaData getClusterConfig()
   {
      return clusterConfig;
   }

   /**
    * Set the clusterConfig.
    * 
    * @param clusterConfig the clusterConfig.
    * @throws IllegalArgumentException for a null clusterConfig
    */
   public void setClusterConfig(ClusterConfigMetaData clusterConfig)
   {
      if (clusterConfig == null)
         throw new IllegalArgumentException("Null clusterConfig");
      this.clusterConfig = clusterConfig;
   }
}
