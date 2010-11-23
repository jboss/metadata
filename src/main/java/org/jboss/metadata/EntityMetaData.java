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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData;
import org.jboss.metadata.ejb.spec.CMPFieldsMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.QueriesMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;
import org.jboss.metadata.spi.MetaData;

/**
 * The meta data information specific to entity beans.
 *
 * @author <a href="mailto:sebastien.alborini@m4x.org">Sebastien Alborini</a>
 * @author <a href="mailto:scott.stark@jboss.org">Scott Stark</a>
 * @author <a href="mailto:dain@daingroup.com">Dain Sundstrom</a>
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @author <a href="mailto:criege@riege.com">Christian Riege</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
@Deprecated
public class EntityMetaData extends BeanMetaData
{
   /** CMP Version 1 */
   public final static int CMP_VERSION_1 = 1;
   
   /** CMP Version 2 */
   public final static int CMP_VERSION_2 = 2;
   
   /** The default entity bean invoker */
   public static final String DEFAULT_ENTITY_INVOKER_PROXY_BINDING = "entity-unified-invoker";
   
   /** The default clustered entity invoker */
   public static final String DEFAULT_CLUSTERED_ENTITY_INVOKER_PROXY_BINDING = "clustered-entity-rmi-invoker";
   
   /**
    * Create a new EntityBeanMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   protected EntityMetaData(ApplicationMetaData app, JBossEnterpriseBeanMetaData delegate)
   {
      super(app, delegate);
   }
   
   /**
    * Create a new EntityMetaData.
    *
    * @param metaData the delegate metadata
    * @throws IllegalArgumentException for a null metaData
    * @throws IllegalStateException if the metadata doesn't have an {@link EnterpriseBeanMetaData}
    */
   protected EntityMetaData(MetaData metaData)
   {
      super(metaData);
   }

   @Override
   public JBossEntityBeanMetaData getDelegate()
   {
      return (JBossEntityBeanMetaData) super.getDelegate();
   }

   @Override
   public String getHome()
   {
      return getDelegate().getHome();
   }

   @Override
   public String getLocal()
   {
      return getDelegate().getLocal();
   }

   @Override
   public String getLocalHome()
   {
      return getDelegate().getLocalHome();
   }

   @Override
   public String getRemote()
   {
      return getDelegate().getRemote();
   }

   @Override
   public String getServiceEndpoint()
   {
      return null;
   }

   /**
    * Is this container managed persistence
    * 
    * @return true for cmp
    */
   public boolean isCMP()
   {
      return getDelegate().isCMP();
   }

   /**
    * Is this CMP1x
    * 
    * @return true for version 1.x
    */
   public boolean isCMP1x()
   {
      return getDelegate().isCMP1x();
   }

   /**
    * Is this CMP2x
    * 
    * @return true for version 2.x
    */
   public boolean isCMP2x()
   {
      JBossEntityBeanMetaData entity = getDelegate();
      boolean isCMP2x = false;
      if(entity.getCmpVersion() != null)
         isCMP2x = entity.getCmpVersion().equals("2.x");
      else
         isCMP2x = entity.getEjbJarMetaData().isEJB2x();
      return isCMP2x;
   }

   public boolean isBMP()
   {
      return getDelegate().isBMP();
   }

   /**
    * Get the primary key class
    * 
    * @return the primary key class
    */
   public String getPrimaryKeyClass()
   {
      return getDelegate().getPrimKeyClass();
   }

   /**
    * Is the entity reentrant
    * 
    * @return true for reentrant
    */
   public boolean isReentrant()
   {
      return getDelegate().isReentrant();
   }

   /**
    * Get the abstract schema name
    * 
    * @return the abstract schema name
    */
   public String getAbstractSchemaName()
   {
      return getDelegate().getAbstractSchemaName();
   }

   /**
    * Gets the container managed fields.
    * 
    * @return iterator over Strings containing names of the fields
    */
   public Iterator<String> getCMPFields()
   {
      CMPFieldsMetaData result = getDelegate().getCmpFields();
      if (result != null)
         return new CMPFieldIterator(result);
      List<String> list = Collections.emptyList();
      return list.iterator();
   }

   /**
    * Get the primary key field
    * 
    * @return the primary key field
    */
   public String getPrimKeyField()
   {
      return getDelegate().getPrimKeyField();
   }

   public Iterator<QueryMetaData> getQueries()
   {
      QueriesMetaData result = getDelegate().getQueries();
      return new OldMetaDataIterator<org.jboss.metadata.ejb.spec.QueryMetaData, QueryMetaData>(result, org.jboss.metadata.ejb.spec.QueryMetaData.class, QueryMetaData.class);
   }

   @Override
   public String getJndiName()
   {
      return getDelegate().determineJndiName();
   }

   @Override
   public boolean isCallByValue()
   {
      return getDelegate().isCallByValue();
   }
   
   @Override
   public boolean isClustered()
   {
      return getDelegate().isClustered();
   }

   @Override
   public ClusterConfigMetaData getClusterConfigMetaData()
   {
      org.jboss.metadata.ejb.jboss.ClusterConfigMetaData config = getDelegate().determineClusterConfig();
      return new ClusterConfigMetaData(config);
   }

   /**
    * Whether the bean is read only
    * 
    * @return true when read only
    */
   public boolean isReadOnly()
   {
      return getDelegate().isReadOnly();
   }

   @Override
   public Iterator<SecurityRoleRefMetaData> getSecurityRoleReferences()
   {
      SecurityRoleRefsMetaData roleRefs = getDelegate().getSecurityRoleRefs();
      return new OldMetaDataIterator<org.jboss.metadata.javaee.spec.SecurityRoleRefMetaData, SecurityRoleRefMetaData>(roleRefs, org.jboss.metadata.javaee.spec.SecurityRoleRefMetaData.class, SecurityRoleRefMetaData.class);
   }

   @Override
   public SecurityIdentityMetaData getEjbTimeoutIdentity()
   {
      throw new UnsupportedOperationException("Entity beans do not have an ejb timeout identity");
   }

   /**
    * Whether to do cache invalidations
    * 
    * @return true when cache invalidations are required
    */
   public boolean doDistributedCacheInvalidations()
   {
      return getDelegate().isCacheInvalidation();
   }

   /**
    * The cache invalidation config
    * 
    * @return the config
    */
   public CacheInvalidationConfigMetaData getDistributedCacheInvalidationConfig()
   {
      org.jboss.metadata.ejb.jboss.CacheInvalidationConfigMetaData config = getDelegate().determineCacheInvalidationConfig();
      if (config == null)
         return null;
      return new CacheInvalidationConfigMetaData(config);
   }
}
