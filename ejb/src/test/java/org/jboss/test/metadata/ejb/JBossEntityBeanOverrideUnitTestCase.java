/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.test.metadata.ejb;


import org.jboss.metadata.ejb.jboss.CacheInvalidationConfigMetaData;
import org.jboss.metadata.ejb.jboss.ClusterConfigMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData;
import org.jboss.metadata.ejb.spec.CMPFieldMetaData;
import org.jboss.metadata.ejb.spec.CMPFieldsMetaData;
import org.jboss.metadata.ejb.spec.PersistenceType;
import org.jboss.metadata.ejb.spec.QueriesMetaData;
import org.jboss.metadata.ejb.spec.QueryMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;


/**
 * A JBossEntityBeanOverrideUnitTestCase.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class JBossEntityBeanOverrideUnitTestCase
   extends AbstractJBossEnterpriseBeanOverrideTest
{
   public void testSimpleProperties() throws Exception
   {
      simplePropertiesTest(JBossEntityBeanMetaData.class, JBossEnterpriseBeanMetaData.class, null);
   }
   
   public void testPersistenceType() throws Exception
   {
      JBossEntityBeanMetaData original = new JBossEntityBeanMetaData();
      original.setEjbName("entity");
      original.setPersistenceType(PersistenceType.Container);

      JBossEntityBeanMetaData override = new JBossEntityBeanMetaData();
      override.setEjbName("entity");
      override.setPersistenceType(PersistenceType.Bean);

      JBossEntityBeanMetaData merged = new JBossEntityBeanMetaData();
      merged.merge(override, original);
      assertEquals("entity", merged.getEjbName());
      PersistenceType persistenceType = merged.getPersistenceType();
      assertNotNull(persistenceType);
      assertEquals(PersistenceType.Bean, persistenceType);
   }
   
   public void testCMPFields() throws Exception
   {
      JBossEntityBeanMetaData original = new JBossEntityBeanMetaData();
      original.setEjbName("entity");

      CMPFieldsMetaData cmpFields = new CMPFieldsMetaData();
      CMPFieldMetaData cmpField = new CMPFieldMetaData();
      cmpField.setFieldName("field1");
      cmpFields.add(cmpField);
      original.setCmpFields(cmpFields);
      
      JBossEntityBeanMetaData override = new JBossEntityBeanMetaData();
      override.setEjbName("entity");

      cmpFields = new CMPFieldsMetaData();
      cmpField = new CMPFieldMetaData();
      cmpField.setFieldName("field2");
      cmpFields.add(cmpField);
      override.setCmpFields(cmpFields);
      
      JBossEntityBeanMetaData merged = new JBossEntityBeanMetaData();
      merged.merge(override, original);
      cmpFields = merged.getCmpFields();
      assertNotNull(cmpFields);
      
      // not sure it's right
      assertEquals(2, cmpFields.size());
   }
   
   public void testSecurityRoleRefs() throws Exception
   {
      JBossEntityBeanMetaData original = new JBossEntityBeanMetaData();
      original.setEjbName("entity");

      SecurityRoleRefsMetaData roleRefs = new SecurityRoleRefsMetaData();
      SecurityRoleRefMetaData roleRef = new SecurityRoleRefMetaData();
      roleRef.setRoleName("role1");
      roleRef.setRoleLink(roleRef.getRoleName() + "Original");
      roleRefs.add(roleRef);
      roleRef = new SecurityRoleRefMetaData();
      roleRef.setRoleName("role2");
      roleRef.setRoleLink(roleRef.getRoleName() + "Original");
      roleRefs.add(roleRef);
      original.setSecurityRoleRefs(roleRefs);
      
      JBossEntityBeanMetaData override = new JBossEntityBeanMetaData();
      override.setEjbName("entity");
      
      roleRefs = new SecurityRoleRefsMetaData();
      roleRef = new SecurityRoleRefMetaData();
      roleRef.setRoleName("role2");
      roleRef.setRoleLink(roleRef.getRoleName() + "Override");
      roleRefs.add(roleRef);
      roleRef = new SecurityRoleRefMetaData();
      roleRef.setRoleName("role3");
      roleRef.setRoleLink(roleRef.getRoleName() + "Override");
      roleRefs.add(roleRef);
      override.setSecurityRoleRefs(roleRefs);

      JBossEntityBeanMetaData merged = new JBossEntityBeanMetaData();
      merged.merge(override, original);
      roleRefs = merged.getSecurityRoleRefs();
      assertNotNull(roleRefs);
      assertEquals(3, roleRefs.size());
      roleRef = roleRefs.get("role1");
      assertNotNull(roleRef);
      assertEquals(roleRef.getRoleName() + "Original", roleRef.getRoleLink());
      roleRef = roleRefs.get("role2");
      assertNotNull(roleRef);
      assertEquals(roleRef.getRoleName() + "Override", roleRef.getRoleLink());
      roleRef = roleRefs.get("role3");
      assertNotNull(roleRef);
      assertEquals(roleRef.getRoleName() + "Override", roleRef.getRoleLink());
   }
   
   public void testClusterConfig() throws Exception
   {
      JBossEntityBeanMetaData original = new JBossEntityBeanMetaData();
      original.setEjbName("entity");

      ClusterConfigMetaData clusterConfig = new ClusterConfigMetaData();
      clusterConfig.setBeanLoadBalancePolicy("beanPolicyOriginal");
      clusterConfig.setHomeLoadBalancePolicy("homePolicyOriginal");
      //clusterConfig.setLoadBalancePolicy("policyOriginal");
      clusterConfig.setPartitionName("partitionOriginal");
      clusterConfig.setSessionStateManagerJndiName("stateManagerOriginal");
      original.setClusterConfig(clusterConfig);
      
      JBossEntityBeanMetaData override = new JBossEntityBeanMetaData();
      override.setEjbName("entity");
      clusterConfig = new ClusterConfigMetaData();
      clusterConfig.setBeanLoadBalancePolicy("beanPolicyOverride");
      clusterConfig.setHomeLoadBalancePolicy("homePolicyOverride");
      //clusterConfig.setLoadBalancePolicy("policyOverride");
      clusterConfig.setPartitionName("partitionOverride");
      clusterConfig.setSessionStateManagerJndiName("stateManagerOverride");
      override.setClusterConfig(clusterConfig);
      
      JBossEntityBeanMetaData merged = new JBossEntityBeanMetaData();
      merged.merge(override, original);
      clusterConfig = merged.getClusterConfig();
      assertNotNull(clusterConfig);
      assertEquals("beanPolicyOverride", clusterConfig.getBeanLoadBalancePolicy());
      assertEquals("homePolicyOverride", clusterConfig.getHomeLoadBalancePolicy());
      assertEquals("beanPolicyOverride", clusterConfig.getLoadBalancePolicy());
      assertEquals("partitionOverride", clusterConfig.getPartitionName());
      assertEquals("stateManagerOverride", clusterConfig.getSessionStateManagerJndiName());
   }
   
   public void testCacheInvalidation() throws Exception
   {
      JBossEntityBeanMetaData original = new JBossEntityBeanMetaData();
      original.setEjbName("entity");

      CacheInvalidationConfigMetaData cacheInv = new CacheInvalidationConfigMetaData();
      cacheInv.setInvalidationGroupName("originalGroup");
      cacheInv.setInvalidationManagerName("originalManager");
      original.setCacheInvalidationConfig(cacheInv);
      
      JBossEntityBeanMetaData override = new JBossEntityBeanMetaData();
      override.setEjbName("entity");
      
      cacheInv = new CacheInvalidationConfigMetaData();
      //cacheInv.setInvalidationGroupName("overrideGroup");
      cacheInv.setInvalidationManagerName("overrideManager");
      override.setCacheInvalidationConfig(cacheInv);
      
      JBossEntityBeanMetaData merged = new JBossEntityBeanMetaData();
      merged.merge(override, original);
      cacheInv = merged.getCacheInvalidationConfig();
      assertNotNull(cacheInv);
      assertEquals("originalGroup", cacheInv.getInvalidationGroupName());
      assertEquals("overrideManager", cacheInv.getInvalidationManagerName());
   }

   public void testQueries() throws Exception
   {
      JBossEntityBeanMetaData original = new JBossEntityBeanMetaData();
      original.setEjbName("entity");

      QueriesMetaData queries = new QueriesMetaData();
      original.setQueries(queries);
      QueryMetaData query = new QueryMetaData();
      query.setEjbQL("select from original");
      queries.add(query);
      
      JBossEntityBeanMetaData override = new JBossEntityBeanMetaData();
      override.setEjbName("entity");

      queries = new QueriesMetaData();
      override.setQueries(queries);
      query = new QueryMetaData();
      query.setEjbQL("select from override");
      queries.add(query);
      
      JBossEntityBeanMetaData merged = new JBossEntityBeanMetaData();
      merged.merge(override, original);
      queries = merged.getQueries();
      assertNotNull(queries);
      assertEquals(1, queries.size());
      query = queries.get(0);
      assertNotNull(query);
      assertEquals("select from override", query.getEjbQL());
   }
}
