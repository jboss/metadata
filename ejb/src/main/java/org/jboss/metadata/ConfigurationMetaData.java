/*
* JBoss, Home of Professional Open Source
* Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata;

import java.util.Collection;
import java.util.Collections;

import org.jboss.metadata.ejb.jboss.CommitOption;
import org.jboss.metadata.ejb.jboss.ContainerConfigurationMetaData;
import org.jboss.metadata.spi.MetaData;
import org.w3c.dom.Element;

/**
 * The configuration information for an EJB container.
 * @author <a href="mailto:sebastien.alborini@m4x.org">Sebastien Alborini</a>
 * @author <a href="mailto:scott.stark@jboss.org">Scott Stark</a>
 * @author <a href="mailto:christoph.jung@infor.de">Christoph G. Jung</a>
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 44055 $
 */
@Deprecated
public class ConfigurationMetaData extends OldMetaData<ContainerConfigurationMetaData>
{
   /** The standard CMP2 configuration */
   public static final String CMP_2x_13 = "Standard CMP 2.x EntityBean";

   /** The standard CMP1.1 configuration */
   public static final String CMP_1x_13 = "Standard CMP EntityBean";

   /** The standard BMP configuration */
   public static final String BMP_13 = "Standard BMP EntityBean";

   /** The standard Stateless session configuration */
   public static final String STATELESS_13 = "Standard Stateless SessionBean";

   /** The standard Stateful session configuration */
   public static final String STATEFUL_13 = "Standard Stateful SessionBean";

   /** The message driven bean configuration */
   public static final String MESSAGE_DRIVEN_13 = "Standard Message Driven Bean";

   /** The message inflow driven bean configuration */
   public static final String MESSAGE_INFLOW_DRIVEN_13 = "Standard Message Inflow Driven Bean";

   /** The clustered CMP2 configuration */
   public static final String CLUSTERED_CMP_2x_13 = "Clustered CMP 2.x EntityBean";

   /** The clustered CMP1.1 configuration */
   public static final String CLUSTERED_CMP_1x_13 = "Clustered CMP EntityBean";

   /** The clustered BMP configuration */
   public static final String CLUSTERED_BMP_13 = "Clustered BMP EntityBean";

   /** The clustered stateful session configuration */
   public static final String CLUSTERED_STATEFUL_13 = "Clustered Stateful SessionBean";

   /** The clustered stateless session configuration */
   public static final String CLUSTERED_STATELESS = "Clustered Stateless SessionBean";

   /** Commit option A */
   public static final byte A_COMMIT_OPTION = 0;

   /** Commit option B */
   public static final byte B_COMMIT_OPTION = 1;
   
   /** Commit option C */
   public static final byte C_COMMIT_OPTION = 2;

   /** D_COMMIT_OPTION is a lazy load option. By default it synchronizes every 30 seconds */
   public static final byte D_COMMIT_OPTION = 3;

   /** The commit option strings */
   public static final String[] commitOptionStrings = {"A", "B", "C", "D"};

   /**
    * Create a new ConfigurationMetaData.
    *
    * @param delegate the delegate
    * @return the metadata
    * @throws IllegalArgumentException for a null delegate or an unknown delegate
    */
   public static ConfigurationMetaData create(ContainerConfigurationMetaData delegate)
   {
      if (delegate == null)
         throw new IllegalArgumentException("Null delegate");
      return new ConfigurationMetaData(delegate);
   }

   /**
    * Create a new ConfigurationMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public ConfigurationMetaData(ContainerConfigurationMetaData delegate)
   {
      super(delegate);
   }

   /**
    * Create a new ConfigurationMetaData.
    * 
    * @param metaData the delegate metadata
    * @throws IllegalArgumentException for a null metaData
    * @throws IllegalStateException if the metadata doesn't have an {@link ContainerConfigurationMetaData}
    */
   protected ConfigurationMetaData(MetaData metaData)
   {
      super(metaData, ContainerConfigurationMetaData.class);
   }

   /**
    * Get the containre name
    * 
    * @return the container name
    */
   public String getName()
   {
      return getDelegate().getContainerName();
   }

   /**
    * Get the instance pool
    * 
    * @return the data or null if there isn't one
    */
   public String getInstancePool()
   {
      return getDelegate().getInstancePool();
   }

   /**
    * Get the instance cache
    * 
    * @return the data or null if there isn't one
    */
   public String getInstanceCache()
   {
      return getDelegate().getInstanceCache();
   }

   /**
    * Get the persistence manager
    * 
    * @return the data or null if there isn't one
    */
    public String getPersistenceManager()
   {
      return getDelegate().getPersistenceManager();
   }

   /**
    * Get the security domain
    * 
    * @return the data or null if there isn't one
    */
   public String getSecurityDomain()
   {
      return getDelegate().getSecurityDomain();
   }

   /**
    * Get the invokers
    * 
    * @return the invokers
    */
   public String[] getInvokers()
   {
      Collection<String> result = getDelegate().getInvokerProxyBindingNames();
      if (result == null)
         return new String[0];
      return result.toArray(new String[result.size()]);
   }

   /**
    * Get the web class loader
    * 
    * @return the data or null if there isn't one
    */
   public String getWebClassLoader()
   {
      return getDelegate().getWebClassLoader();
   }

   /**
    * Get the locking class
    * 
    * @return the data or null if there isn't one
    */
   public String getLockClass()
   {
      return getDelegate().getLockingPolicy();
   }

   public Element getContainerPoolConf()
   {
      return getDelegate().getContainerPoolConf();
   }

   public Element getContainerCacheConf()
   {
      return getDelegate().getContainerCacheConf();
   }

   /**
    * Get the default invoker name
    * 
    * @return the default invoker
    * @throws IllegalStateException when there are no invokers
    */
   public String getDefaultInvokerName()
   {
      return getDelegate().getDefaultInvokerName();
   }

   public Element getContainerInterceptorsConf()
   {
      return getDelegate().getContainerInterceptors();
   }

   /**
    * Get whether call logging is enabled
    * 
    * @return true for enabled
    */
   public boolean getCallLogging()
   {
      return getDelegate().isCallLogging();
   }

   /**
    * Get whether sync on commit only is enabled
    * 
    * @return true for enabled
    */
   public boolean getSyncOnCommitOnly()
   {
      return getDelegate().isSyncOnCommitOnly();
   }

   /**
    * Get whether insert after ejb post create is enabled
    * 
    * @return true for enabled
    */
   public boolean isInsertAfterEjbPostCreate()
   {
      return getDelegate().isInsertAfterEjbPostCreate();
   }

   /**
    * Get whether ejbStore() for not clean is enabled
    * 
    * @return true for enabled
    */
   public boolean isEjbStoreForClean()
   {
      return getDelegate().isEjbStoreOnClean();
   }

   /**
    * Get whether store not flushed is enabled
    * 
    * @return true for enabled
    */
   public boolean isStoreNotFlushed()
   {
      return getDelegate().isStoreNotFlushed();
   }

   /**
    * Get the commit option
    * 
    * @return the commit option
    */
   public byte getCommitOption()
   {
      CommitOption commitOption = getDelegate().getCommitOption();
      switch (commitOption)
      {
         case B: return B_COMMIT_OPTION; 
         case C: return C_COMMIT_OPTION; 
         case D: return D_COMMIT_OPTION;
         default: return A_COMMIT_OPTION;
      }
   }

   /**
    * Get the option d refersh rate
    * 
    * @return the option d refresh rate
    */
   public long getOptionDRefreshRate()
   {
      return getDelegate().getOptiondRefreshRateMillis();
   }

   /**
    * Get the cluster config
    * 
    * @return the cluster config
    */
   public ClusterConfigMetaData getClusterConfigMetaData()
   {
      org.jboss.metadata.ejb.jboss.ClusterConfigMetaData config = getDelegate().getClusterConfig();
      if (config == null)
         return null;
      return new ClusterConfigMetaData(config);
   }

   /**
    * Get the dependencies
    * 
    * @return the dependencies
    */
   public Collection<String> getDepends()
   {
      Collection<String> result = getDelegate().getDepends();
      if (result == null)
         return Collections.emptyList();
      return result;
   }
}
