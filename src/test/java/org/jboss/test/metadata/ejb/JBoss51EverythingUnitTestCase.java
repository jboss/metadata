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
package org.jboss.test.metadata.ejb;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import junit.framework.Test;

import org.jboss.metadata.common.jboss.LoaderRepositoryConfigMetaData;
import org.jboss.metadata.common.jboss.LoaderRepositoryMetaData;
import org.jboss.metadata.common.jboss.WebserviceDescriptionMetaData;
import org.jboss.metadata.common.jboss.WebserviceDescriptionsMetaData;
import org.jboss.metadata.ejb.jboss.CacheConfigMetaData;
import org.jboss.metadata.ejb.jboss.ClusterConfigMetaData;
import org.jboss.metadata.ejb.jboss.JBoss51MetaData;
import org.jboss.metadata.ejb.jboss.JBossAssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossMessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.PoolConfigMetaData;
import org.jboss.metadata.ejb.jboss.ResourceManagerMetaData;
import org.jboss.metadata.ejb.jboss.ResourceManagersMetaData;
import org.jboss.metadata.ejb.jboss.WebservicesMetaData;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;


/**
 * JBoss51EverythingUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class JBoss51EverythingUnitTestCase extends AbstractEJBEverythingTest
{
   public static Test suite()
   {
      return suite(JBoss51EverythingUnitTestCase.class);
   }

   public JBoss51EverythingUnitTestCase(String name)
   {
      super(name);
   }

   protected JBoss51MetaData unmarshal() throws Exception
   {
      return unmarshal(JBoss51MetaData.class);
   }

   public void testEverything() throws Exception
   {
      JBoss51MetaData jbossMetaData = unmarshal();
      assertEverything(jbossMetaData, Mode.JBOSS);
   }

   public void assertCacheConfig(String prefix, int num, CacheConfigMetaData cacheConfig)
   {
      assertNotNull(prefix, cacheConfig);
      assertEquals(prefix + "CacheClass", cacheConfig.getValue());
      assertEquals(10 * num, (int) cacheConfig.getMaxSize());
      assertEquals(11 * num, (int) cacheConfig.getIdleTimeoutSeconds());
      assertEquals(12 * num, (int) cacheConfig.getRemoveTimeoutSeconds());
      assertEquals(prefix + "CacheName", cacheConfig.getName());
      assertEquals(prefix + "PersistenceManager", cacheConfig.getPersistenceManager());
   }
   
   public void assertEverything(JBossMetaData jbossMetaData, Mode mode)
   {
      assertVersion(jbossMetaData);
      assertId("jboss", jbossMetaData);
      assertDescriptionGroup("jboss", jbossMetaData.getDescriptionGroup());

      assertLoaderRepository(jbossMetaData.getLoaderRepository(), mode);
      assertEquals("jboss-jmx-name", jbossMetaData.getJmxName());
      assertEquals("jboss-security-domain", jbossMetaData.getSecurityDomain());
      assertFalse(jbossMetaData.isExcludeMissingMethods());
      assertEquals("jboss-unauthenticated-principal", jbossMetaData.getUnauthenticatedPrincipal());
      assertWebservices(jbossMetaData.getWebservices(), mode);
      assertJBossEnterpriseBeans(jbossMetaData, mode);
      assertAssemblyDescriptor(jbossMetaData, mode);
      assertResourceManagers(jbossMetaData.getResourceManagers(), mode);
      assertResourceManager("resourceManager1", true, jbossMetaData.getResourceManager("resourceManager1Name"), mode);
      assertResourceManager("resourceManager2", false, jbossMetaData.getResourceManager("resourceManager2Name"), mode);
   }

   private void assertVersion(JBossMetaData jbossMetaData)
   {
      assertEquals("5.1", jbossMetaData.getVersion());
   }
   
   private void assertLoaderRepository(LoaderRepositoryMetaData loaderRepositoryMetaData, Mode mode)
   {
      assertNotNull(loaderRepositoryMetaData);
      assertId("loaderRepository", loaderRepositoryMetaData);
      assertEquals("loaderRepositoryClass", loaderRepositoryMetaData.getLoaderRepositoryClass());
      assertEquals("loaderRepositoryName", trim(loaderRepositoryMetaData.getName()));
      assertLoaderRepositoryConfig(2, loaderRepositoryMetaData, mode);
   }
   
   private void assertLoaderRepositoryConfig(int size, LoaderRepositoryMetaData loaderRepositoryMetaData, Mode mode)
   {
      Set<LoaderRepositoryConfigMetaData> configs = loaderRepositoryMetaData.getLoaderRepositoryConfig();
      assertNotNull(configs);
      assertEquals(size, configs.size());
      for (int count = 1; count < configs.size(); ++count)
      {
         LoaderRepositoryConfigMetaData config = new LoaderRepositoryConfigMetaData();
         config.setId("loaderRepositoryConfig" + count + "-id");
         config.setConfig("loaderRepositoryConfig" + count);
         config.setConfigParserClass("loaderRepositoryConfigParserClass" + count);
         assertTrue(configs + " contains " + config, configs.contains(config));
      }
   }
   
   public void assertWebservices(WebservicesMetaData webservices, Mode mode)
   {
      assertNotNull(webservices);
      assertId("webservices", webservices);
      assertEquals("webservicesContextRoot", webservices.getContextRoot());
      WebserviceDescriptionsMetaData webserviceDescriptionsMetaData = webservices.getWebserviceDescriptions();
      assertNotNull(webserviceDescriptionsMetaData);
      assertEquals(2, webserviceDescriptionsMetaData.size());
      int count = 1;
      for (WebserviceDescriptionMetaData description : webserviceDescriptionsMetaData)
      {
         assertId("webserviceDescription" + count, description);
         assertEquals("webserviceDescription" + count + "ConfigName", description.getConfigName());
         assertEquals("webserviceDescription" + count + "ConfigFile", description.getConfigFile());
         assertEquals("webserviceDescription" + count + "WsdlPublishLocation", description.getWsdlPublishLocation());
         ++count;
      }
   }
   
   private String trim(String string)
   {
      assertNotNull(string);
      return string.trim();
   }
   
   private void assertJBossEnterpriseBeans(JBossMetaData jbossMetaData, Mode mode)
   {
      JBossEnterpriseBeansMetaData enterpriseBeansMetaData = jbossMetaData.getEnterpriseBeans();
      assertNotNull(enterpriseBeansMetaData);
      // no entities in jboss_5_0.xsd
      int beansTotal = 6;
      assertEquals(beansTotal, enterpriseBeansMetaData.size());

      assertNullSessionBean("session0", jbossMetaData);
      assertFullSessionBean("session1", jbossMetaData, true, mode, 1);
      assertFullSessionBean("session2", jbossMetaData, false, mode, 2);

      assertNullMessageDrivenBean("mdb0", jbossMetaData);
      assertFullMessageDrivenBean("mdb1", jbossMetaData, true, mode);
      assertFullMessageDrivenBean("mdb2", jbossMetaData, false, mode);
   }
   
   private <T extends JBossEnterpriseBeanMetaData> T assertJBossEnterpriseBean(String prefix, JBossMetaData jBossMetaData, Class<T> expected)
   {
      JBossEnterpriseBeanMetaData ejb = jBossMetaData.getEnterpriseBean(prefix + "EjbName");
      assertNotNull(ejb);
      assertEquals(prefix + "EjbName", ejb.getEjbName());
      assertTrue(expected.isInstance(ejb));
      return expected.cast(ejb);
   }
   
   private JBossSessionBeanMetaData assertJBossSessionBean(String prefix, JBossMetaData jbossMetaData)
   {
      JBossSessionBeanMetaData ejb = assertJBossEnterpriseBean(prefix, jbossMetaData, JBossSessionBeanMetaData.class);
      assertTrue(ejb.isSession());
      assertFalse(ejb.isEntity());
      assertFalse(ejb.isMessageDriven());
      return ejb;
   }
   
   private void assertFullSessionBean(String prefix, JBossMetaData jbossMetaData, boolean first, Mode mode, int num)
   {
      JBossSessionBeanMetaData session = assertJBossSessionBean(prefix, jbossMetaData);
      assertId(prefix, session);
      assertDescriptionGroup(prefix, session.getDescriptionGroup());
      assertRemoteBindings(prefix, session.getRemoteBindings());
      assertEquals(prefix + "LocalHomeJndiName", session.getLocalHomeJndiName());
      assertEquals(prefix + "SecurityDomain", session.getSecurityDomain()); // TODO!!! this should be in the DTD as well

      assertAnnotations(prefix, 2, session.getAnnotations());
      assertIgnoreDependency(prefix, session.getIgnoreDependency());
      assertEquals(prefix + "AOPDomain", session.getAopDomainName());
      assertCacheConfig(prefix, num, session.getCacheConfig());
      assertPoolConfig(prefix, session.getPoolConfig());
      assertJndiRefs(prefix, 2, session.getJndiRefs(), mode);
         
      if (first)
         assertTrue(session.isConcurrent());
      else
         assertFalse(session.isConcurrent());
      
      assertEquals(prefix + "JndiName", session.getJndiName());
      assertEquals(prefix + "HomeJndiName", session.getHomeJndiName());
      assertEquals(prefix + "LocalJndiName", session.getLocalJndiName());
      
      assertNull(session.getInvokerBindings());

      assertEnvironment(prefix, session.getJndiEnvironmentRefsGroup(), false, mode);

      assertSecurityIdentity(prefix, "SecurityIdentity", session.getSecurityIdentity(), false, mode);

      ClusterConfigMetaData clusterConfig = null;
      clusterConfig = session.getClusterConfig();
      assertClusterConfig(prefix, clusterConfig, true, mode);
      
      assertMethodAttributes(prefix, session.getMethodAttributes(), mode);

      assertDepends(prefix, 2, session.getDepends());
      
      assertPortComponent(prefix, session.getPortComponent(), mode);

      assertSecurityIdentity(prefix, "EjbTimeoutIdentity", session.getEjbTimeoutIdentity(), false, mode);
      
      //Ensure that we can see the principal versus role map
      Map<String, Set<String>> principalVsRolesMap = session.getSecurityRolesPrincipalVersusRolesMap();
      assertTrue("Keys size > 0", principalVsRolesMap.keySet().size() > 0);
   }

   private void assertNullSessionBean(String prefix, JBossMetaData jbossMetaData)
   {
      JBossSessionBeanMetaData session = assertJBossSessionBean(prefix, jbossMetaData);
      assertNull(session.getId());
      assertNull(session.getDescriptionGroup());

      assertNull(session.getRemoteBindings());
      
      assertNull(session.getJndiName());
      assertNull(session.getHomeJndiName());
      assertNull(session.getLocalJndiName());
      assertNull(session.getLocalHomeJndiName());
      assertNull(session.getConfigurationName());
      assertNull(session.getSecurityProxy());
      assertNull(session.getSecurityDomain());

      assertFalse(session.isCallByValue());
      assertFalse(session.isExceptionOnRollback());
      assertTrue(session.isTimerPersistence());
      assertFalse(session.isClustered());

      assertNull(session.getInvokerBindings());

      assertNullEnvironment(session.getJndiEnvironmentRefsGroup());

      assertNull(session.getMethodAttributes());
      
      assertNull(session.getSecurityIdentity());
      assertNull(session.getEjbTimeoutIdentity());
      
      assertNull(session.getClusterConfig());
      
      assertNull(session.getDepends());

      assertNull(session.getIorSecurityConfig());

      assertNull(session.getAnnotations());
      assertNull(session.getIgnoreDependency());
      assertNull(session.getAopDomainName());
      assertNull(session.isConcurrent());
      assertNull(session.getJndiRefs());
   }
   
   private JBossMessageDrivenBeanMetaData assertJBossMessageDrivenBean(String prefix, JBossMetaData jbossMetaData)
   {
      JBossMessageDrivenBeanMetaData ejb = assertJBossEnterpriseBean(prefix, jbossMetaData, JBossMessageDrivenBeanMetaData.class);
      assertFalse(ejb.isSession());
      assertFalse(ejb.isEntity());
      assertTrue(ejb.isMessageDriven());
      return ejb;
   }
   
   private void assertFullMessageDrivenBean(String prefix, JBossMetaData jbossMetaData, boolean first, Mode mode)
   {
      JBossMessageDrivenBeanMetaData mdb = assertJBossMessageDrivenBean(prefix, jbossMetaData);
      assertId(prefix, mdb);
      
      assertDescriptionGroup(prefix, mdb.getDescriptionGroup());
      assertAnnotations(prefix, 2, mdb.getAnnotations());
      assertIgnoreDependency(prefix, mdb.getIgnoreDependency());
      assertEquals(prefix + "AOPDomain", mdb.getAopDomainName());
      assertJndiRefs(prefix, 2, mdb.getJndiRefs(), mode);
      assertMethodAttributes(prefix, mdb.getMethodAttributes(), mode);
      
      assertEquals(prefix + "DestinationJndiName", mdb.getDestinationJndiName());
      assertEquals(prefix + "User", mdb.getMdbUser());
      assertEquals(prefix + "Password", mdb.getMdbPassword());
      assertEquals(prefix + "ClientId", mdb.getMdbClientId());
      assertEquals(prefix + "SubscriptionId", mdb.getMdbSubscriptionId());
      assertEquals(prefix + "RAR", mdb.getResourceAdapterName());
      
      assertNull(mdb.getInvokerBindings());

      assertEnvironment(prefix, mdb.getJndiEnvironmentRefsGroup(), false, mode);

      assertSecurityIdentity(prefix, "SecurityIdentity", mdb.getSecurityIdentity(), false, mode);

      assertDepends(prefix, 2, mdb.getDepends());
            
      assertSecurityIdentity(prefix, "EjbTimeoutIdentity", mdb.getEjbTimeoutIdentity(), false, mode);
           
      // TODO DOM pool-config
      
      assertActivationConfig(prefix, mdb.getActivationConfig(), mode);
   }
   
   private void assertNullMessageDrivenBean(String prefix, JBossMetaData jbossMetaData)
   {
      JBossMessageDrivenBeanMetaData mdb = assertJBossMessageDrivenBean(prefix, jbossMetaData);
      assertNull(mdb.getId());
      assertNull(mdb.getDescriptionGroup());
      
      assertNull(mdb.getDestinationJndiName());
      assertNull(mdb.getLocalJndiName());
      assertNull(mdb.getMdbUser());
      assertNull(mdb.getMdbPassword());
      assertNull(mdb.getMdbClientId());
      assertNull(mdb.getMdbSubscriptionId());
      assertNull(mdb.getResourceAdapterName());
      assertNull(mdb.getConfigurationName());
      assertNull(mdb.getSecurityProxy());

      assertFalse(mdb.isExceptionOnRollback());
      assertTrue(mdb.isTimerPersistence());

      assertNull(mdb.getInvokerBindings());

      assertNullEnvironment(mdb.getJndiEnvironmentRefsGroup());

      assertNull(mdb.getMethodAttributes());

      assertNull(mdb.getSecurityIdentity());
      assertNull(mdb.getEjbTimeoutIdentity());

      assertNull(mdb.getDepends());

      assertNull(mdb.getIorSecurityConfig());

      assertNull(mdb.getAnnotations());
      assertNull(mdb.getIgnoreDependency());
      assertNull(mdb.getAopDomainName());
      assertNull(mdb.getJndiRefs());
   }
      
   private void assertDepends(String prefix, int size, Collection<String> depends)
   {
      assertNotNull(depends);
      assertEquals(size, depends.size());
      for(int count = 1; count <= depends.size(); ++count)
      {
         assertTrue(depends.contains(prefix + "Depends" + count));
      }
   }
      
   private void assertResourceManagers(ResourceManagersMetaData resources, Mode mode)
   {
      assertNotNull(resources);
      assertId("resource-managers", resources);
      assertDescriptions("resource-managers", resources.getDescriptions());

      assertEquals(2, resources.size());
      int count = 1;
      for (ResourceManagerMetaData resource : resources)
      {
         assertResourceManager("resourceManager" + count, count == 1, resource, mode);
         ++count;
      }
   }
   
   private void assertResourceManager(String prefix, boolean jndi, ResourceManagerMetaData resource, Mode mode)
   {
      assertNotNull(resource);
      assertId(prefix, resource);
      assertDescriptions(prefix, resource.getDescriptions());

      assertEquals(prefix + "Name", resource.getResName());
      if (jndi)
      {
         assertEquals(prefix + "JndiName", resource.getResJndiName());
         assertNull(resource.getResUrl());
         assertEquals(prefix + "JndiName", resource.getResource());
      }
      else
      {
         assertNull(resource.getResJndiName());
         assertEquals(prefix + "URL", resource.getResUrl());
         assertEquals(prefix + "URL", resource.getResource());
      }
   }
   
   protected void assertAssemblyDescriptor(JBossMetaData jbossMetaData, Mode mode)
   {
      JBossAssemblyDescriptorMetaData assemblyDescriptorMetaData = (JBossAssemblyDescriptorMetaData) jbossMetaData.getAssemblyDescriptor();
      assertNotNull(assemblyDescriptorMetaData);
      assertId("assembly-descriptor", assemblyDescriptorMetaData);
      assertSecurityRoles(2, assemblyDescriptorMetaData.getSecurityRoles(), mode);
      assertMessageDestinations(2, assemblyDescriptorMetaData.getMessageDestinations(), mode);
      Map<String,Set<String>> prmap = assemblyDescriptorMetaData.getPrincipalVersusRolesMap();
      assertNotNull(prmap);
      //Check the keys
      assertTrue(prmap.containsKey("securityRole1Principal1"));
      assertTrue(prmap.containsKey("securityRole1Principal2"));
      assertTrue(prmap.containsKey("securityRole2Principal1"));
      assertTrue(prmap.containsKey("securityRole2Principal2"));
      //Check the values
      assertTrue(prmap.get("securityRole1Principal1").size() == 1);
      assertTrue(prmap.get("securityRole1Principal1").contains("securityRoleRef1RoleLink"));
      
      assertTrue(prmap.get("securityRole1Principal2").size() == 1);
      assertTrue(prmap.get("securityRole1Principal2").contains("securityRoleRef1RoleLink"));
      
      assertTrue(prmap.get("securityRole2Principal1").size() == 1);
      assertTrue(prmap.get("securityRole2Principal1").contains("securityRoleRef2RoleLink"));
      
      assertTrue(prmap.get("securityRole2Principal2").size() == 1);
      assertTrue(prmap.get("securityRole2Principal2").contains("securityRoleRef2RoleLink"));
   }
   
   @Override
   protected void assertSecurityRole(String prefix, int count, SecurityRoleMetaData securityRoleMetaData, Mode mode)
   {
      super.assertSecurityRole(prefix, count, securityRoleMetaData, mode);
      assertPrincipals(prefix, count, 2, securityRoleMetaData.getPrincipals());
   }

   protected void assertMessageDestination(String prefix, MessageDestinationMetaData messageDestinationMetaData, Mode mode)
   {
      assertMessageDestination14(prefix, messageDestinationMetaData, mode);
      assertEquals(prefix + "JndiName", messageDestinationMetaData.getMappedName());
   }

   private void assertPoolConfig(String prefix, PoolConfigMetaData poolConfig)
   {
      assertNotNull(prefix + " has no poolConfig", poolConfig);
      assertEquals(prefix + "PoolClass", poolConfig.getValue());
      assertEquals(10, (int) poolConfig.getMaxSize());
      assertEquals(11, (int) poolConfig.getTimeout());
   }
      
   @Override
   protected void assertResourceGroup(String prefix, ResourceInjectionMetaData resourceInjectionMetaData, boolean full, boolean first, Mode mode)
   {
      super.assertResourceGroupNoJndiName(prefix, resourceInjectionMetaData, true, first);
      if (first)
         assertTrue(resourceInjectionMetaData.isDependencyIgnored());
      else
         assertFalse(resourceInjectionMetaData.isDependencyIgnored());
      assertEquals(prefix + "JndiName", resourceInjectionMetaData.getMappedName());
   }
      
   @Override
   protected void assertSecurityIdentity(String ejbName, String type, SecurityIdentityMetaData securityIdentity, boolean full, Mode mode)
   {
      super.assertSecurityIdentity(ejbName, type, securityIdentity, full, mode);
      assertEquals(ejbName + type + "RunAsPrincipal", securityIdentity.getRunAsPrincipal());
   }
}
