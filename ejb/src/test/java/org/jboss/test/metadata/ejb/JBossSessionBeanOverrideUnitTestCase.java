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


import org.jboss.metadata.ejb.jboss.CacheConfigMetaData;
import org.jboss.metadata.ejb.jboss.ClusterConfigMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokeMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokesMetaData;
import org.jboss.metadata.ejb.spec.BusinessLocalsMetaData;
import org.jboss.metadata.ejb.spec.BusinessRemotesMetaData;
import org.jboss.metadata.ejb.spec.InitMethodMetaData;
import org.jboss.metadata.ejb.spec.InitMethodsMetaData;
import org.jboss.metadata.ejb.spec.MethodParametersMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.ejb.spec.RemoveMethodMetaData;
import org.jboss.metadata.ejb.spec.RemoveMethodsMetaData;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.EmptyMetaData;
import org.jboss.metadata.javaee.spec.PortComponent;
import org.jboss.metadata.javaee.spec.RunAsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;


/**
 * A JBossSessionBeanOverrideUnitTestCase.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class JBossSessionBeanOverrideUnitTestCase
   extends AbstractJBossEnterpriseBeanOverrideTest
{
   public void testSimpleProperties() throws Exception
   {
      simplePropertiesTest(JBossSessionBeanMetaData.class, JBossEnterpriseBeanMetaData.class, null);
   }
   
   public void testBusinessLocals()
   {
      // Override
      BusinessLocalsMetaData overrideBusinessLocal = new BusinessLocalsMetaData();
      overrideBusinessLocal.add("override1");
      overrideBusinessLocal.add("override2");
      overrideBusinessLocal.add("override3");
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setBusinessLocals(overrideBusinessLocal);
      override.setName("overrideName");
      
      //Original
      BusinessLocalsMetaData originalBusinessLocal = new BusinessLocalsMetaData();
      originalBusinessLocal.add("original1");
      originalBusinessLocal.add("original2");
      originalBusinessLocal.add("original3");
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setBusinessLocals(originalBusinessLocal);
      
      // Merged 
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);
      BusinessLocalsMetaData businessLocals = merged.getBusinessLocals();
      assertNotNull(businessLocals);
      assertEquals(3, businessLocals.size());
      assertTrue(businessLocals.contains("override1"));
      assertTrue(businessLocals.contains("override2"));
      assertTrue(businessLocals.contains("override3"));
   }
   
   public void testBusinessRemotes()
   {
      // Override
      BusinessRemotesMetaData overrideBusinessRemotes = new BusinessRemotesMetaData();
      overrideBusinessRemotes.add("override1");
      overrideBusinessRemotes.add("override2");
      overrideBusinessRemotes.add("override3");
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setBusinessRemotes(overrideBusinessRemotes);
      override.setName("overrideName");
      
      // Original
      BusinessRemotesMetaData originalBusinessRemotes = new BusinessRemotesMetaData();
      originalBusinessRemotes.add("original1");
      originalBusinessRemotes.add("original2");
      originalBusinessRemotes.add("original3");
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setBusinessRemotes(originalBusinessRemotes);
      
      // Merged
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);
      BusinessRemotesMetaData businessRemotes = merged.getBusinessRemotes();
      assertNotNull(businessRemotes);
      assertEquals(3, businessRemotes.size());
      assertTrue(businessRemotes.contains("override1"));
      assertTrue(businessRemotes.contains("override2"));
      assertTrue(businessRemotes.contains("override3"));
   }
   
   public void testTimeoutMethod()
   {
      // Override
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      NamedMethodMetaData overrideTimeoutMethod = new NamedMethodMetaData();
      overrideTimeoutMethod.setId("override");
      overrideTimeoutMethod.setMethodName("override");
      MethodParametersMetaData parameter2 = new MethodParametersMetaData();
      parameter2.add("override");
      parameter2.add("parameter");
      overrideTimeoutMethod.setMethodParams(parameter2);
      overrideTimeoutMethod.setName("override");
      override.setTimeoutMethod(overrideTimeoutMethod);
      override.setName("override");
      
      // Original
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();      
      NamedMethodMetaData originalTimeoutMethod = new NamedMethodMetaData();
      originalTimeoutMethod.setId("id");
      originalTimeoutMethod.setMethodName("methodName");
      MethodParametersMetaData parameter = new MethodParametersMetaData();
      parameter.add("originalParameter");
      originalTimeoutMethod.setMethodParams(parameter);
      originalTimeoutMethod.setName("name");
      original.setTimeoutMethod(originalTimeoutMethod);
      
      // Merged
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);
      NamedMethodMetaData namedMethod = merged.getTimeoutMethod();
      assertEquals("override", merged.getName());
      assertNotNull(namedMethod);
      assertEquals("method id", "override", namedMethod.getId());
      assertEquals("method name", "override", namedMethod.getMethodName());
      assertEquals("override", namedMethod.getName());
      MethodParametersMetaData methodParameters = namedMethod.getMethodParams();
      assertEquals("override", methodParameters.get(0));
      assertEquals("parameter", methodParameters.get(1));
   }
   
   public void testInitMethods()
   {
      // Override
      InitMethodsMetaData overrideInitMethods = new InitMethodsMetaData();
      InitMethodMetaData overrideInitMethod = new InitMethodMetaData();
      NamedMethodMetaData overrideNamedMethod = new NamedMethodMetaData();
      overrideNamedMethod.setMethodName("overrideMethodName");
      overrideInitMethod.setId("overrideId");
      overrideInitMethods.add(overrideInitMethod);
      
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setInitMethods(overrideInitMethods);
      override.setName("name");
      
      // Original
      InitMethodsMetaData originalInitMethods = new InitMethodsMetaData();
      InitMethodMetaData originalInitMethod = new InitMethodMetaData();
      NamedMethodMetaData originalNamedMethod = new NamedMethodMetaData();
      originalNamedMethod.setMethodName("originalMethodName");
      originalInitMethod.setId("originalId");
      originalInitMethods.add(originalInitMethod);
      
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setInitMethods(originalInitMethods);
      
      // Merged
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);
      InitMethodsMetaData initMethods = merged.getInitMethods();
      assertNotNull(initMethods);
      InitMethodMetaData initMethod = initMethods.get(0);
      assertNotNull(initMethod);
      assertEquals("overrideId", initMethod.getId());
   }
   
   public void testRemoveMethods()
   {
      // Override
      RemoveMethodsMetaData overrideRemoveMethods = new RemoveMethodsMetaData();
      RemoveMethodMetaData overrideRemoveMethod = new RemoveMethodMetaData();
      overrideRemoveMethod.setId("overrideId");
      overrideRemoveMethod.setRetainIfException(true);
      NamedMethodMetaData overrideNamedMethod = new NamedMethodMetaData();
      overrideNamedMethod.setMethodName("overrideMethodName");
      overrideRemoveMethod.setBeanMethod(overrideNamedMethod);
      overrideRemoveMethods.add(overrideRemoveMethod);

      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setRemoveMethods(overrideRemoveMethods);
      override.setName("overrideName");

      // Original
      RemoveMethodsMetaData originalRemoveMethods = new RemoveMethodsMetaData();
      RemoveMethodMetaData originalRemoveMethod = new RemoveMethodMetaData();
      originalRemoveMethod.setId("originalId");
      originalRemoveMethod.setRetainIfException(false);
      NamedMethodMetaData originalNamedMethod = new NamedMethodMetaData();
      originalNamedMethod.setId("originalId");
      originalRemoveMethod.setBeanMethod(originalNamedMethod);
      originalRemoveMethods.add(originalRemoveMethod);
      
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setRemoveMethods(originalRemoveMethods);
      
      // Merged
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);
      RemoveMethodsMetaData removeMethods = merged.getRemoveMethods();
      assertNotNull(removeMethods);
      RemoveMethodMetaData removeMethod = removeMethods.get(1);
      assertNotNull(removeMethod);
      assertEquals("overrideId", removeMethod.getId());
      assertTrue(removeMethod.isRetainIfException());
      NamedMethodMetaData namedMethod = removeMethod.getBeanMethod();
      assertNotNull(namedMethod);
      assertEquals("overrideMethodName", namedMethod.getMethodName());
      
   }
   
   public void testRemoveMethodsRetainOverrideIsNull()
   {
      // Override
      RemoveMethodsMetaData overrideRemoveMethods = new RemoveMethodsMetaData();
      RemoveMethodMetaData overrideRemoveMethod = new RemoveMethodMetaData();
      overrideRemoveMethod.setId("overrideId");
      NamedMethodMetaData overrideNamedMethod = new NamedMethodMetaData();
      overrideNamedMethod.setMethodName("overrideMethodName");
      overrideRemoveMethod.setBeanMethod(overrideNamedMethod);
      overrideRemoveMethods.add(overrideRemoveMethod);

      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setRemoveMethods(overrideRemoveMethods);
      override.setName("overrideName");

      // Original
      RemoveMethodsMetaData originalRemoveMethods = new RemoveMethodsMetaData();
      RemoveMethodMetaData originalRemoveMethod = new RemoveMethodMetaData();
      originalRemoveMethod.setId("originalId");
      originalRemoveMethod.setRetainIfException(true);
      NamedMethodMetaData originalNamedMethod = new NamedMethodMetaData();
      originalNamedMethod.setMethodName("overrideMethodName");
      originalNamedMethod.setId("originalId");
      originalRemoveMethod.setBeanMethod(originalNamedMethod);
      originalRemoveMethods.add(originalRemoveMethod);
      
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setRemoveMethods(originalRemoveMethods);
      
      // Merged
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);
      RemoveMethodsMetaData removeMethods = merged.getRemoveMethods();
      assertNotNull(removeMethods);
      RemoveMethodMetaData removeMethod = removeMethods.get(0);
      assertNotNull(removeMethod);
      assertEquals("overrideId", removeMethod.getId());
      assertTrue(removeMethod.isRetainIfException());
      NamedMethodMetaData namedMethod = removeMethod.getBeanMethod();
      assertNotNull(namedMethod);
      assertEquals("overrideMethodName", namedMethod.getMethodName());
      
   }
   
   public void testAroundInvokes()
   {
      // Override
      AroundInvokesMetaData overrideInvokes = new AroundInvokesMetaData();
      AroundInvokeMetaData overrideInvoke = new AroundInvokeMetaData();
      overrideInvoke.setClassName("overrideClassName");
      overrideInvoke.setMethodName("overrideMethodName");
      overrideInvokes.add(overrideInvoke);
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setAroundInvokes(overrideInvokes);
      override.setName("sbmd");
      
      // Original
      AroundInvokesMetaData originalInvokes = new AroundInvokesMetaData();
      AroundInvokeMetaData originalInvoke = new AroundInvokeMetaData();
      originalInvoke.setClassName("originalClassName");
      originalInvoke.setMethodName("originalMethodName");
      originalInvokes.add(originalInvoke);
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setAroundInvokes(originalInvokes);
      
      // Merged
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);
      AroundInvokesMetaData aroundInvokes = merged.getAroundInvokes();
      assertNotNull(aroundInvokes);
      assertEquals(2, aroundInvokes.size());
      AroundInvokeMetaData aroundInvoke = aroundInvokes.get(0);
      assertNotNull(aroundInvoke);
      assertEquals("originalClassName", aroundInvoke.getClassName());
      assertEquals("originalMethodName", aroundInvoke.getMethodName());
      aroundInvoke = aroundInvokes.get(1);
      assertNotNull(aroundInvoke);
      assertEquals("overrideClassName", aroundInvoke.getClassName());
      assertEquals("overrideMethodName", aroundInvoke.getMethodName());

   }
   
   public void testSecurityRoleRefs()
   {
      // Override
      SecurityRoleRefsMetaData overrideSecurity = new SecurityRoleRefsMetaData();
      overrideSecurity.setId("override");
      SecurityRoleRefMetaData overrideSecurityRoleRef = new SecurityRoleRefMetaData();
      overrideSecurityRoleRef.setDescriptions(new DescriptionsImpl());
      overrideSecurityRoleRef.setId("overrideId");
      overrideSecurityRoleRef.setRoleLink("overrideLink");
      overrideSecurityRoleRef.setRoleName("overrideRoleName");
      overrideSecurity.add(overrideSecurityRoleRef);
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setSecurityRoleRefs(overrideSecurity);
      
      // Original
      SecurityRoleRefMetaData originalSecurityRoleRef = new SecurityRoleRefMetaData();
      originalSecurityRoleRef.setDescriptions(new DescriptionsImpl());
      originalSecurityRoleRef.setId("originalId");
      originalSecurityRoleRef.setName("originalName");
      originalSecurityRoleRef.setRoleLink("originalLink");
      originalSecurityRoleRef.setRoleName("originalRoleName");
      SecurityRoleRefsMetaData originalSecurityRoleRefsMetaData = new SecurityRoleRefsMetaData();
      originalSecurityRoleRefsMetaData.setId("originalId");
      originalSecurityRoleRefsMetaData.add(originalSecurityRoleRef);
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setSecurityRoleRefs(originalSecurityRoleRefsMetaData);
      original.setName("original");
      
      // Merged
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);
      SecurityRoleRefsMetaData securityRoleRefs = merged.getSecurityRoleRefs();
      assertNotNull(securityRoleRefs);
      assertEquals(1, securityRoleRefs.size());
      assertEquals("override", securityRoleRefs.getId());
      SecurityRoleRefMetaData securityRoleRef = securityRoleRefs.get(overrideSecurityRoleRef.getKey());
      assertNotNull(securityRoleRef);
      assertEquals("overrideId", securityRoleRef.getId());
      assertEquals("overrideLink", securityRoleRef.getRoleLink());
      assertEquals("overrideRoleName", securityRoleRef.getRoleName());
   }
   
   public void testClusterConfig()
   {
      // Override
      ClusterConfigMetaData overrideClusterConfig = new ClusterConfigMetaData();
      overrideClusterConfig.setBeanLoadBalancePolicy("overrideBeanLoadBalancePolicy");
      overrideClusterConfig.setDescriptions(new DescriptionsImpl());
      overrideClusterConfig.setHomeLoadBalancePolicy("overrideHomeLoadBalancePolicy");
      overrideClusterConfig.setId("overrideId");
      overrideClusterConfig.setPartitionName("overridePartitionName");
      overrideClusterConfig.setSessionStateManagerJndiName("overrideSessionStateManagerJndiName");
      
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setClusterConfig(overrideClusterConfig);
      override.setClustered(false);
      override.setName("override");
      
      // Original
      ClusterConfigMetaData originalClusterConfig = new ClusterConfigMetaData();
      originalClusterConfig.setBeanLoadBalancePolicy("originalBeanLoadBalancePolicy");
      originalClusterConfig.setDescriptions(new DescriptionsImpl());
      originalClusterConfig.setHomeLoadBalancePolicy("originalHomeLoadBalancePolicy");
      originalClusterConfig.setId("originalId");
      originalClusterConfig.setPartitionName("originalPartitionName");
      originalClusterConfig.setSessionStateManagerJndiName("originalSessionStateManagerJndiName");
      
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setClusterConfig(originalClusterConfig);
      original.setClustered(true);
      
      // Merged
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);
      ClusterConfigMetaData clusterConfig = merged.getClusterConfig();
      assertNotNull(clusterConfig);
      assertFalse(merged.isClustered());
      assertEquals("overrideBeanLoadBalancePolicy", clusterConfig.getBeanLoadBalancePolicy());
      assertEquals("overrideHomeLoadBalancePolicy", clusterConfig.getHomeLoadBalancePolicy());
      assertEquals("overridePartitionName", clusterConfig.getPartitionName());
      assertEquals("overrideSessionStateManagerJndiName", clusterConfig.getSessionStateManagerJndiName());
      assertEquals("overrideId", clusterConfig.getId());
      
   }
   
   public void testPortComponent()
   {
      // Override
      PortComponent overridePortComponent = new PortComponent();
      overridePortComponent.setAuthMethod("overrideauthMethod");
      overridePortComponent.setId("overrideId");
      overridePortComponent.setPortComponentName("overridePortComponentName");
      overridePortComponent.setPortComponentURI("overridePortComponentURI");
      overridePortComponent.setSecureWSDLAccess(true);
      overridePortComponent.setTransportGuarantee("overrideTransportGuarantee");
      
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setPortComponent(overridePortComponent);
      override.setName("overrideName");
      
      // Original
      PortComponent originalPortComponent = new PortComponent();
      originalPortComponent.setAuthMethod("originalAuthMethod");
      originalPortComponent.setPortComponentName("originalPortComponentName");
      originalPortComponent.setPortComponentURI("originalPortComponentURI");
      originalPortComponent.setSecureWSDLAccess(false);
      originalPortComponent.setTransportGuarantee("originalTransportGuarantee");
      
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setPortComponent(originalPortComponent);
      
      // Merged
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);
      PortComponent portComponent = merged.getPortComponent();
      assertEquals("overrideauthMethod", portComponent.getAuthMethod());
      assertEquals("overridePortComponentName", portComponent.getPortComponentName());
      assertEquals("overridePortComponentURI", portComponent.getPortComponentURI());
      assertEquals("overrideTransportGuarantee", portComponent.getTransportGuarantee());
      assertEquals(true, portComponent.getSecureWSDLAccess());
      
   }
   
   public void testEjbTimeout()
   {
      // Override
      SecurityIdentityMetaData overrideEjbTimeout = new SecurityIdentityMetaData();
      overrideEjbTimeout.setDescriptions(new DescriptionsImpl());
      overrideEjbTimeout.setRunAsPrincipal("overrideRunAsPrincipal");
      
      RunAsMetaData overrideRunAs = new RunAsMetaData();
      overrideRunAs.setDescriptions(new DescriptionsImpl());
      overrideRunAs.setRoleName("overrideRoleName");
      overrideRunAs.setId("overrideId");
      
      EmptyMetaData overrideEmpty = new EmptyMetaData();
      overrideEmpty.setId("overrideId");
      
      overrideEjbTimeout.setRunAs(overrideRunAs);
      overrideEjbTimeout.setUseCallerIdentity(overrideEmpty);
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setEjbTimeoutIdentity(overrideEjbTimeout);
      override.setName("overrideName");
      
      // Original
      SecurityIdentityMetaData originalEjbTimeout = new SecurityIdentityMetaData();
      originalEjbTimeout.setRunAsPrincipal("originalRunAsPrincipal");
      
      RunAsMetaData originalRunAs = new RunAsMetaData();
      originalRunAs.setDescriptions(new DescriptionsImpl());
      originalRunAs.setRoleName("originalRoleName");
      originalRunAs.setId("originalId");
      
      EmptyMetaData originalEmpty = new EmptyMetaData();
      originalEmpty.setId("originalId");
      
      originalEjbTimeout.setRunAs(originalRunAs);
      originalEjbTimeout.setUseCallerIdentity(originalEmpty);
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setEjbTimeoutIdentity(originalEjbTimeout);
      original.setName("originalName");
      
      // Merged
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);
      SecurityIdentityMetaData ejbTimeout = merged.getEjbTimeoutIdentity();
      assertNotNull(ejbTimeout);
      assertNotNull(ejbTimeout.getDescriptions());
      assertEquals("overrideRunAsPrincipal", ejbTimeout.getRunAsPrincipal());
      assertEquals("overrideRoleName", ejbTimeout.getRunAs().getRoleName());
      assertEquals("overrideId", ejbTimeout.getRunAs().getId());
      assertEquals("overrideId", ejbTimeout.getUseCallerIdentity().getId());
   }
   
   public void todotestCacheConfig()
   {
      // Override
      CacheConfigMetaData overrideCacheConfig = new CacheConfigMetaData();
      overrideCacheConfig.setIdleTimeoutSeconds(Integer.valueOf(5));
      overrideCacheConfig.setMaxSize(Integer.valueOf(5));
      overrideCacheConfig.setRemoveTimeoutSeconds(Integer.valueOf(5));
      overrideCacheConfig.setName("overrideName");
      overrideCacheConfig.setPersistenceManager("overridePersistenceManager");
      overrideCacheConfig.setReplicationIsPassivation("overrideReplicationIsPassivation");
      overrideCacheConfig.setValue("overrideValue");
      
      JBossSessionBeanMetaData override = new JBossSessionBeanMetaData();
      override.setCacheConfig(overrideCacheConfig);
      override.setName("overrideName");
    
      // Original
      CacheConfigMetaData originalCacheConfig = new CacheConfigMetaData();
      originalCacheConfig.setIdleTimeoutSeconds(Integer.valueOf(6));
      originalCacheConfig.setMaxSize(Integer.valueOf(6));
      originalCacheConfig.setRemoveTimeoutSeconds(Integer.valueOf(6));
      originalCacheConfig.setName("originalName");
      originalCacheConfig.setPersistenceManager("originalPersistenceManager");
      originalCacheConfig.setReplicationIsPassivation("originalReplicationIsPassivation");
      originalCacheConfig.setValue("originalValue");
      
      JBossSessionBeanMetaData original = new JBossSessionBeanMetaData();
      original.setCacheConfig(originalCacheConfig);
      
      // Merged
      JBossSessionBeanMetaData merged = new JBossSessionBeanMetaData();
      merged.merge(override, original);
      CacheConfigMetaData cacheConfig = merged.getCacheConfig();
      assertNotNull(cacheConfig);
      assertEquals(Integer.valueOf(5), cacheConfig.getIdleTimeoutSeconds());
      assertEquals(Integer.valueOf(5), cacheConfig.getMaxSize());
      assertEquals(Integer.valueOf(5), cacheConfig.getRemoveTimeoutSeconds());
      assertEquals("overrideName", cacheConfig.getName());
      assertEquals("overridePersistenceManager", cacheConfig.getPersistenceManager());
      assertEquals("overrideReplicationIsPassivation", cacheConfig.getReplicationIsPassivation());
      assertEquals("overrideValue", cacheConfig.getValue());
   }
}
