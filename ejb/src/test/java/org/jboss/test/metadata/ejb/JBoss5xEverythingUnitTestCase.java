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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Test;

import org.jboss.metadata.common.jboss.LoaderRepositoryConfigMetaData;
import org.jboss.metadata.common.jboss.LoaderRepositoryMetaData;
import org.jboss.metadata.common.jboss.WebserviceDescriptionMetaData;
import org.jboss.metadata.common.jboss.WebserviceDescriptionsMetaData;
import org.jboss.metadata.ejb.jboss.CacheConfigMetaData;
import org.jboss.metadata.ejb.jboss.ClusterConfigMetaData;
import org.jboss.metadata.ejb.jboss.CommitOption;
import org.jboss.metadata.ejb.jboss.ContainerConfigurationMetaData;
import org.jboss.metadata.ejb.jboss.ContainerConfigurationsMetaData;
import org.jboss.metadata.ejb.jboss.InvokerBindingMetaData;
import org.jboss.metadata.ejb.jboss.InvokerBindingsMetaData;
import org.jboss.metadata.ejb.jboss.InvokerProxyBindingMetaData;
import org.jboss.metadata.ejb.jboss.InvokerProxyBindingsMetaData;
import org.jboss.metadata.ejb.jboss.JBoss50DTDMetaData;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossAssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaDataWrapper;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.PoolConfigMetaData;
import org.jboss.metadata.ejb.jboss.ResourceManagerMetaData;
import org.jboss.metadata.ejb.jboss.ResourceManagersMetaData;
import org.jboss.metadata.ejb.jboss.WebservicesMetaData;
import org.jboss.metadata.ejb.jboss.InvokerBindingMetaData.EjbRef;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * JBoss5xEverythingUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class JBoss5xEverythingUnitTestCase extends AbstractEJBEverythingTest
{
   /** Is there a standardjboss.xml default available */
   private boolean hasStandardJBoss = false;

   public static Test suite()
   {
      return suite(JBoss5xEverythingUnitTestCase.class);
   }
      
   static Element getElement(String name, Element parent)
   {
      NodeList elements = parent.getElementsByTagName(name);
      Element element = (Element) elements.item(0);
      return element;
   }
   
   static String getElementText(String name, Element parent)
   {
      Element element = getElement(name, parent);
      NodeList children = element.getChildNodes();
      String result = "";
      for (int i = 0; i < children.getLength(); i++)
      {
         if (children.item(i).getNodeType() == Node.TEXT_NODE || children.item(i).getNodeType() == Node.CDATA_SECTION_NODE)
         {
            result += children.item(i).getNodeValue();
         }
         else if (children.item(i).getNodeType() == Node.COMMENT_NODE)
         {
            // Ignore comment nodes
         }
         else
         {
            result += children.item(i).getFirstChild();
         }
      }
      return result.trim();
   }

   public JBoss5xEverythingUnitTestCase(String name)
   {
      super(name);
   }

   protected JBoss50DTDMetaData unmarshal() throws Exception
   {
      return unmarshal(JBoss50DTDMetaData.class);
   }

   public void testEverything() throws Exception
   {
      JBoss50MetaData jbossMetaData = unmarshal(JBoss50MetaData.class);
      hasStandardJBoss = false;
      assertEverything(jbossMetaData, Mode.JBOSS);
   }

   public void testEverythingDTD() throws Exception
   {
      //enableTrace("org.jboss.xb");
      //enableTrace("org.jboss.xb.builder");
      JBoss50DTDMetaData jbossMetaData = unmarshal();
      hasStandardJBoss = false;
      assertEverything(jbossMetaData, Mode.JBOSS_DTD);
   }
   
   public void testStandard() throws Exception
   {
      //enableTrace("org.jboss.xb");
      //enableTrace("org.jboss.xb.builder");
      JBoss50DTDMetaData stdMetaData = unmarshal();
      JBoss50DTDMetaData jbossMetaData = unmarshal("JBoss5xEverything_testEverythingDTD.xml", JBoss50DTDMetaData.class, null);
      JBossMetaDataWrapper wrapper = new JBossMetaDataWrapper(jbossMetaData, stdMetaData);
      hasStandardJBoss = true;
      assertEverything(wrapper, Mode.JBOSS_DTD);
   }

   /**
    * 
    * @throws Exception
    */
   public void testOverride() throws Exception
   {
      JBoss50DTDMetaData overrideData = unmarshal();
      JBoss50DTDMetaData baseData = unmarshal("JBoss5xEverything_testBaseData.xml", JBoss50DTDMetaData.class, null);
      // Override JBoss5xEverything_testBaseData.xml with JBoss5xEverything_testOverride.xml
      JBossMetaDataWrapper jbossMetaData = new JBossMetaDataWrapper(overrideData, baseData);

      // jmx-name
      assertEquals("jboss-jmx-name-override", jbossMetaData.getJmxName());
      // security-domain
      assertEquals("jboss-security-domain-override", jbossMetaData.getSecurityDomain());
      // missing-method-permissions-excluded-mode
      assertTrue(jbossMetaData.isExcludeMissingMethods());
      // unauthenticated-principal
      assertEquals("jboss-unauthenticated-principal-override", jbossMetaData.getUnauthenticatedPrincipal());
      // exception-on-rollback
      assertFalse(jbossMetaData.isExceptionOnRollback());
      // loader-repository
      LoaderRepositoryMetaData lrmd = jbossMetaData.getLoaderRepository();
      //assertEquals("loaderRepository-id-override", lrmd.getId());
      assertEquals("loaderRepositoryClass-override", lrmd.getLoaderRepositoryClass());
      assertEquals("loaderRepositoryName-override", lrmd.getName());
      Set<LoaderRepositoryConfigMetaData> lrmdConfigs = lrmd.getLoaderRepositoryConfig();
      assertEquals(1, lrmdConfigs.size());
      LoaderRepositoryConfigMetaData lrmdConfig = lrmdConfigs.iterator().next();
      //assertEquals("loaderRepositoryConfig1-id", lrmdConfig.getId());
      assertEquals("loaderRepositoryConfig1-override", lrmdConfig.getConfig());
      assertEquals("loaderRepositoryConfigParserClass1-override", lrmdConfig.getConfigParserClass());
      // webservices
      // enterprise-beans
      // assembly-descriptor
      // resource-managers
      // invoker-proxy-bindings
      InvokerProxyBindingMetaData ipbmd1 = jbossMetaData.getInvokerProxyBinding("invokerProxyBinding1Name");
      assertNotNull(ipbmd1);
      //assertEquals("invokerProxyBinding1-id", ipbmd1.getId());
      assertEquals("invokerProxyBinding1InvokerMBean", ipbmd1.getInvokerMBean());
      assertEquals("invokerProxyBinding1Name", ipbmd1.getInvokerProxyBindingName());
      assertEquals("invokerProxyBinding1ProxyFactory", ipbmd1.getProxyFactory());
      InvokerProxyBindingMetaData ipbmd1Override = jbossMetaData.getInvokerProxyBinding("invokerProxyBinding1Name-override");
      assertNotNull(ipbmd1Override);
      //assertEquals("invokerProxyBinding1-id-override", ipbmd1Override.getId());
      assertEquals("invokerProxyBinding1InvokerMBean-override", ipbmd1Override.getInvokerMBean());
      assertEquals("invokerProxyBinding1Name-override", ipbmd1Override.getInvokerProxyBindingName());
      assertEquals("invokerProxyBinding1ProxyFactory-override", ipbmd1Override.getProxyFactory());
      // container-configurations
      ContainerConfigurationMetaData sssb = jbossMetaData.getContainerConfiguration("Standard Stateless SessionBean");
      assertEquals(null, sssb.getExtendsName());
      assertEquals("containerConfiguration1InstanceCache-override", sssb.getInstanceCache());
      Set<String> invokerNames = sssb.getInvokerProxyBindingNames();
      HashSet<String> expectedInvokerNames = new HashSet<String>();
      expectedInvokerNames.add("containerConfiguration1InvokerProxyBindingName1-override");
      expectedInvokerNames.add("containerConfiguration1InvokerProxyBindingName2-override");
      assertEquals(expectedInvokerNames, invokerNames);
      ContainerConfigurationMetaData session2Configuration = jbossMetaData.getContainerConfiguration("session2ConfigurationName");
      assertNotNull(session2Configuration);
      assertEquals("Standard Stateless SessionBean", session2Configuration.getExtendsName());
      invokerNames = session2Configuration.getInvokerProxyBindingNames();
      expectedInvokerNames.clear();
      expectedInvokerNames.add("containerConfiguration1InvokerProxyBindingName1-override1");
      expectedInvokerNames.add("containerConfiguration1InvokerProxyBindingName2-override1");
      assertEquals(expectedInvokerNames, invokerNames);

      ContainerConfigurationMetaData containerConfiguration1 = jbossMetaData.getContainerConfiguration("containerConfiguration1Name-override");
      assertNotNull(containerConfiguration1);
      // Should be from the containerConfiguration1
      assertEquals("containerConfiguration1InstancePool", containerConfiguration1.getInstancePool());
      ClusterConfigMetaData ccmd = containerConfiguration1.getClusterConfig();
      assertNotNull(ccmd);
      assertEquals("containerConfiguration1ClusterConfigBeanLoadBalancePolicy", ccmd.getBeanLoadBalancePolicy());
      assertEquals("containerConfiguration1ClusterConfigHomeLoadBalancePolicy", ccmd.getHomeLoadBalancePolicy());
      assertEquals("containerConfiguration1ClusterConfigPartitionName", ccmd.getPartitionName());
      assertEquals("containerConfiguration1ClusterConfigSessionStateManagerJndiName", ccmd.getSessionStateManagerJndiName());
      // Should be overriden
      invokerNames = containerConfiguration1.getInvokerProxyBindingNames();
      expectedInvokerNames.clear();
      expectedInvokerNames.add("containerConfiguration1InvokerProxyBindingName1-override");
      expectedInvokerNames.add("containerConfiguration1InvokerProxyBindingName2-override");
      assertEquals(expectedInvokerNames, invokerNames);
   }
   
   public void testClusterConfigOverride() throws Exception
   {
      JBoss50DTDMetaData overrideData = unmarshal();
      JBoss50DTDMetaData baseData = unmarshal("JBoss5xEverything_testClusterConfigBase.xml", JBoss50DTDMetaData.class, null);
      // Override JBoss5xEverything_testBaseData.xml with JBoss5xEverything_testOverride.xml
      JBossMetaDataWrapper jbossMetaData = new JBossMetaDataWrapper(overrideData, baseData);
      
      JBossEnterpriseBeansMetaData enterpriseBeansMetaData = jbossMetaData.getEnterpriseBeans();
      assertNotNull(enterpriseBeansMetaData);
      assertEquals(4, enterpriseBeansMetaData.size());
      
      String beanId = "bean1";
      JBossSessionBeanMetaData session = assertJBossSessionBean(beanId, jbossMetaData);
      ClusterConfigMetaData config = session.determineClusterConfig();
      
      assertPartitionName(config, "bean");
      assertHomeLoadBalancePolicy(config, "container");
      assertBeanLoadBalancePolicy(config, "standard");
      assertSessionStateManagerJndiName(config, null);
      
      beanId = "bean2";
      session = assertJBossSessionBean(beanId, jbossMetaData);
      config = session.determineClusterConfig();
      assertPartitionName(config, "container");
      assertHomeLoadBalancePolicy(config, "standard");
      assertBeanLoadBalancePolicy(config, null);
      assertSessionStateManagerJndiName(config, "bean");
      
      beanId = "bean3";
      session = assertJBossSessionBean(beanId, jbossMetaData);
      config = session.determineClusterConfig();
      assertPartitionName(config, "standard");
      assertHomeLoadBalancePolicy(config, null);
      assertBeanLoadBalancePolicy(config, "bean");
      assertSessionStateManagerJndiName(config, "container");
      
      beanId = "bean4";
      session = assertJBossSessionBean(beanId, jbossMetaData);
      config = session.determineClusterConfig();
      assertPartitionName(config, null);
      assertHomeLoadBalancePolicy(config, "bean");
      assertBeanLoadBalancePolicy(config, "container");
      assertSessionStateManagerJndiName(config, "standard");
   }
   
   public void testCreateDestination() throws Exception
   {
      JBossMetaData metaData = unmarshal(JBoss50MetaData.class);
      JBossMessageDrivenBeanMetaData mdb = (JBossMessageDrivenBeanMetaData) metaData.getEnterpriseBean("MessageDriven");
      assertTrue(mdb.isCreateDestination());
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
      if(mode != Mode.JBOSS_DTD)
      {
         assertId("jboss", jbossMetaData);
         assertDescriptionGroup("jboss", jbossMetaData.getDescriptionGroup());
      }
      assertLoaderRepository(jbossMetaData.getLoaderRepository(), mode);
      assertEquals("jboss-jmx-name", jbossMetaData.getJmxName());
      assertEquals("jboss-security-domain", jbossMetaData.getSecurityDomain());
      assertFalse(jbossMetaData.isExcludeMissingMethods());
      assertEquals("jboss-unauthenticated-principal", jbossMetaData.getUnauthenticatedPrincipal());
      assertTrue(jbossMetaData.isExceptionOnRollback());
      assertWebservices(jbossMetaData.getWebservices(), mode);
      assertJBossEnterpriseBeans(jbossMetaData, mode);
      assertAssemblyDescriptor(jbossMetaData, mode);
      assertResourceManagers(jbossMetaData.getResourceManagers(), mode);
      assertResourceManager("resourceManager1", true, jbossMetaData.getResourceManager("resourceManager1Name"), mode);
      assertResourceManager("resourceManager2", false, jbossMetaData.getResourceManager("resourceManager2Name"), mode);
      if(mode == Mode.JBOSS_DTD)
      {
         if(hasStandardJBoss == false)
            assertInvokerProxyBindings(jbossMetaData.getInvokerProxyBindings(), mode);
         assertInvokerProxyBinding("invokerProxyBinding1", 1, jbossMetaData.getInvokerProxyBinding("invokerProxyBinding1Name"), mode);
         assertInvokerProxyBinding("invokerProxyBinding2", 2, jbossMetaData.getInvokerProxyBinding("invokerProxyBinding2Name"), mode);
         assertContainerConfigurations(jbossMetaData, mode);
      }
   }

   private void assertVersion(JBossMetaData jbossMetaData)
   {
      assertEquals("5.0", jbossMetaData.getVersion());
   }
   
   private void assertLoaderRepository(LoaderRepositoryMetaData loaderRepositoryMetaData, Mode mode)
   {
      assertNotNull(loaderRepositoryMetaData);
      if(mode != Mode.JBOSS_DTD)
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
         if(mode != Mode.JBOSS_DTD)
            config.setId("loaderRepositoryConfig" + count + "-id");
         config.setConfig("loaderRepositoryConfig" + count);
         config.setConfigParserClass("loaderRepositoryConfigParserClass" + count);
         assertTrue(configs + " contains " + config, configs.contains(config));
//         assertId("loaderRepositoryConfig" + count, config);
//         assertEquals("loaderRepositoryConfigParserClass" + count, config.getConfigParserClass());
//         assertEquals("loaderRepositoryConfig" + count, trim(config.getConfig()));
      }
   }
   
   public void assertWebservices(WebservicesMetaData webservices, Mode mode)
   {
      assertNotNull(webservices);
      if(mode != Mode.JBOSS_DTD)
         assertId("webservices", webservices);
      assertEquals("webservicesContextRoot", webservices.getContextRoot());
      WebserviceDescriptionsMetaData webserviceDescriptionsMetaData = webservices.getWebserviceDescriptions();
      assertNotNull(webserviceDescriptionsMetaData);
      assertEquals(2, webserviceDescriptionsMetaData.size());
      int count = 1;
      for (WebserviceDescriptionMetaData description : webserviceDescriptionsMetaData)
      {
         if(mode != Mode.JBOSS_DTD)
            assertId("webserviceDescription" + count, description);
         assertEquals("webserviceDescription" + count + "ConfigName", description.getConfigName());
         assertEquals("webserviceDescription" + count + "ConfigFile", description.getConfigFile());
         assertEquals("webserviceDescription" + count + "WsdlPublishLocation", description.getWsdlPublishLocation());
         ++count;
      }
   }
   
/*   private void assertWebservices(ApplicationMetaData application)
   {
      assertEquals("webservicesContextRoot", application.getWebServiceContextRoot());
      assertEquals("webserviceDescription2ConfigName", application.getConfigName());
      assertEquals("webserviceDescription2ConfigFile", application.getConfigFile());
      Map<String, String> result = application.getWsdlPublishLocations();
      Map<String, String> expected = new HashMap<String, String>();
      expected.put("webserviceDescription1Name", "webserviceDescription1WsdlPublishLocation");
      expected.put("webserviceDescription2Name", "webserviceDescription2WsdlPublishLocation");
      assertEquals(expected, result);
      assertEquals("webserviceDescription1WsdlPublishLocation", application.getWsdlPublishLocationByName("webserviceDescription1Name"));
      assertEquals("webserviceDescription2WsdlPublishLocation", application.getWsdlPublishLocationByName("webserviceDescription2Name"));
   }
*/   
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
      int beansTotal = mode == Mode.JBOSS ? 6 : 9;
      assertEquals(beansTotal, enterpriseBeansMetaData.size());

      assertNullSessionBean("session0", jbossMetaData);
      assertFullSessionBean("session1", jbossMetaData, true, mode, 1);
      assertFullSessionBean("session2", jbossMetaData, false, mode, 2);

      if(mode != Mode.JBOSS)
      {
         assertNullEntityBean("entity0", jbossMetaData);
         assertFullEntityBean("entity1", jbossMetaData, true, mode);
         assertFullEntityBean("entity2", jbossMetaData, false, mode);
      }
      
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
   
/*   private <T extends BeanMetaData> T assertBeanMetaData(String prefix, ApplicationMetaData application, Class<T> expected)
   {
      BeanMetaData ejb = application.getBeanByEjbName(prefix + "EjbName");
      assertNotNull(ejb);
      assertEquals(prefix + "EjbName", ejb.getEjbName());
      assertTrue(expected.isInstance(ejb));
      return expected.cast(ejb);
   }
*/   
   private JBossSessionBeanMetaData assertJBossSessionBean(String prefix, JBossMetaData jbossMetaData)
   {
      JBossSessionBeanMetaData ejb = assertJBossEnterpriseBean(prefix, jbossMetaData, JBossSessionBeanMetaData.class);
      assertTrue(ejb.isSession());
      assertFalse(ejb.isEntity());
      assertFalse(ejb.isMessageDriven());
      return ejb;
   }
   
/*   private SessionMetaData assertJBossSessionBean(String prefix, ApplicationMetaData application)
   {
      SessionMetaData ejb = assertBeanMetaData(prefix, application, SessionMetaData.class);
      assertTrue(ejb.isSession());
      assertFalse(ejb.isEntity());
      assertFalse(ejb.isMessageDriven());
      return ejb;
   }
*/   
   private void assertFullSessionBean(String prefix, JBossMetaData jbossMetaData, boolean first, Mode mode, int num)
   {
      JBossSessionBeanMetaData session = assertJBossSessionBean(prefix, jbossMetaData);
      assertId(prefix, session);
      if(mode != Mode.JBOSS_DTD)
      {
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
      }
      
      assertEquals(prefix + "JndiName", session.getJndiName());
      assertEquals(prefix + "HomeJndiName", session.getHomeJndiName());
      assertEquals(prefix + "LocalJndiName", session.getLocalJndiName());
      assertEquals(prefix + "ConfigurationName", session.getConfigurationName());
      assertEquals(prefix + "SecurityProxy", session.getSecurityProxy());

      if (first)
      {
         assertTrue(session.isCallByValue());
         assertTrue(session.isExceptionOnRollback());
         assertTrue(session.isTimerPersistence());
         assertTrue(session.isClustered());
      }
      else
      {
         assertFalse(session.isCallByValue());
         assertFalse(session.isExceptionOnRollback());
         assertFalse(session.isTimerPersistence());
         assertFalse(session.isClustered());
      }
      
      assertInvokerBindings(prefix, session.getInvokerBindings(), mode);

      assertEnvironment(prefix, session.getJndiEnvironmentRefsGroup(), false, mode);

      assertSecurityIdentity(prefix, "SecurityIdentity", session.getSecurityIdentity(), false, mode);

      ClusterConfigMetaData clusterConfig = null;
      if(mode == Mode.JBOSS)
         clusterConfig = session.getClusterConfig();
      else
         clusterConfig = session.determineClusterConfig();
      assertClusterConfig(prefix, clusterConfig, true, mode);
      
      assertMethodAttributes(prefix, session.getMethodAttributes(), mode);

      assertDepends(prefix, 2, session.getDepends());
      
      assertIORSecurityConfig(prefix, session.getIorSecurityConfig(), mode);
      
      assertPortComponent(prefix, session.getPortComponent(), mode);

      assertSecurityIdentity(prefix, "EjbTimeoutIdentity", session.getEjbTimeoutIdentity(), false, mode);
            
      if (this.hasStandardJBoss)
      {
         ContainerConfigurationMetaData ccmd = session.determineContainerConfiguration();
         this.assertContainerPoolConf(prefix, ccmd.getContainerPoolConf(), mode);
      }
      
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
   
   private JBossEntityBeanMetaData assertJBossEntityBean(String prefix, JBossMetaData jbossMetaData)
   {
      JBossEntityBeanMetaData ejb = assertJBossEnterpriseBean(prefix, jbossMetaData, JBossEntityBeanMetaData.class);
      assertFalse(ejb.isSession());
      assertTrue(ejb.isEntity());
      assertFalse(ejb.isMessageDriven());
      return ejb;
   }
   
/*   private EntityMetaData assertJBossEntityBean(String prefix, ApplicationMetaData application)
   {
      EntityMetaData ejb = assertBeanMetaData(prefix, application, EntityMetaData.class);
      assertFalse(ejb.isSession());
      assertTrue(ejb.isEntity());
      assertFalse(ejb.isMessageDriven());
      return ejb;
   }
*/
   private void assertFullEntityBean(String prefix, JBossMetaData jbossMetaData, boolean first, Mode mode)
   {
      JBossEntityBeanMetaData entity = assertJBossEntityBean(prefix, jbossMetaData);
      assertId(prefix, entity);
      
      assertEquals(prefix + "JndiName", entity.getJndiName());
      assertEquals(prefix + "LocalJndiName", entity.getLocalJndiName());
      assertEquals(prefix + "ConfigurationName", entity.getConfigurationName());
      assertEquals(prefix + "SecurityProxy", entity.getSecurityProxy());
      // TODO assertEquals(prefix + "SecurityDomain", entity.getSecurityDomain());

      if (first)
      {
         assertTrue(entity.isCallByValue());
         assertTrue(entity.isReadOnly());
         assertTrue(entity.isExceptionOnRollback());
         assertTrue(entity.isTimerPersistence());
         assertTrue(entity.isClustered());
         assertTrue(entity.isCacheInvalidation());
      }
      else
      {
         assertFalse(entity.isCallByValue());
         assertFalse(entity.isReadOnly());
         assertFalse(entity.isExceptionOnRollback());
         assertFalse(entity.isTimerPersistence());
         assertFalse(entity.isClustered());
         assertFalse(entity.isCacheInvalidation());
      }
      
      assertInvokerBindings(prefix, entity.getInvokerBindings(), mode);

      assertEnvironment(prefix, entity.getJndiEnvironmentRefsGroup(), false, mode);

      assertSecurityIdentity(prefix, "SecurityIdentity", entity.getSecurityIdentity(), false, mode);

      assertClusterConfig(prefix, entity.getClusterConfig(), false, mode);
      
      assertMethodAttributes(prefix, entity.getMethodAttributes(), mode);

      assertDepends(prefix, 2, entity.getDepends());
      
      assertIORSecurityConfig(prefix, entity.getIorSecurityConfig(), mode);
      
      assertCacheInvalidationConfig(prefix, entity.getCacheInvalidationConfig(), mode);

      // TODO DOM cache-config
      
      // TODO DOM pool-config
   }
   
   private void assertNullEntityBean(String prefix, JBossMetaData jbossMetaData)
   {
      JBossEntityBeanMetaData entity = assertJBossEntityBean(prefix, jbossMetaData);
      assertNull(entity.getId());
      assertNull(entity.getDescriptionGroup());
      
      assertNull(entity.getJndiName());
      assertNull(entity.getLocalJndiName());
      assertNull(entity.getConfigurationName());
      assertNull(entity.getSecurityProxy());
      assertNull(entity.getSecurityDomain());

      assertFalse(entity.isCallByValue());
      assertFalse(entity.isReadOnly());
      assertFalse(entity.isExceptionOnRollback());
      assertTrue(entity.isTimerPersistence());
      assertFalse(entity.isClustered());
      assertFalse(entity.isCacheInvalidation());
      
      assertNull(entity.getInvokerBindings());

      assertNullEnvironment(entity.getJndiEnvironmentRefsGroup());

      assertNull(entity.getMethodAttributes());
      
      assertNull(entity.getSecurityIdentity());
      
      assertNull(entity.getClusterConfig());

      assertNull(entity.getCacheInvalidationConfig());

      assertNull(entity.getDepends());

      assertNull(entity.getIorSecurityConfig());

      assertNull(entity.getAnnotations());
      assertNull(entity.getIgnoreDependency());
      assertNull(entity.getAopDomainName());
      assertNull(entity.getJndiRefs());
   }
   
/*   private void assertFullEntityBean(String prefix, ApplicationMetaData application, boolean first)
   {
      EntityMetaData entity = assertJBossEntityBean(prefix, application);

      assertEquals(prefix + "JndiName", entity.getJndiName());
      assertEquals(prefix + "LocalJndiName", entity.getLocalJndiName());
      assertEquals(prefix + "ConfigurationName", entity.getConfigurationName());
      assertEquals(prefix + "SecurityProxy", entity.getSecurityProxy());

      if (first)
      {
         assertTrue(entity.isCallByValue());
         assertTrue(entity.isReadOnly());
         assertTrue(entity.getExceptionRollback());
         assertTrue(entity.getTimerPersistence());
         assertTrue(entity.isClustered());
      }
      else
      {
         assertFalse(entity.isCallByValue());
         assertFalse(entity.isReadOnly());
         assertFalse(entity.getExceptionRollback());
         assertFalse(entity.getTimerPersistence());
         assertFalse(entity.isClustered());
      }

      assertInvokerBindings(prefix, entity, entity.getInvokerBindings());

      assertEnvironment(prefix, entity, false);

      assertSecurityIdentity(prefix, "SecurityIdentity", entity.getSecurityIdentityMetaData(), false);
      
      assertClusterConfig(prefix, entity.getClusterConfigMetaData(), false);
      
      assertMethodAttributes(prefix, entity);

      assertCacheInvalidationConfig(prefix, entity.getDistributedCacheInvalidationConfig());

      assertDepends(prefix, 2, entity.getDepends());
      
      assertIORSecurityConfig(prefix, entity.getIorSecurityConfigMetaData());
   }
*/   
/*   private void assertNullEntityBean(String prefix, ApplicationMetaData application)
   {
      EntityMetaData entity = assertJBossEntityBean(prefix, application);
      
      String ejbName = entity.getEjbName();
      assertEquals(entity.getEjbName(), entity.getJndiName());
      String localName = "local/" + ejbName + '@' + System.identityHashCode(ejbName);
      assertEquals(localName, entity.getLocalJndiName());
      assertEquals(ContainerConfigurationMetaData.CMP_2x, entity.getConfigurationName());
      assertNull(entity.getSecurityProxy());

      assertFalse(entity.isCallByValue());
      assertFalse(entity.isReadOnly());
      assertFalse(entity.getExceptionRollback());
      assertTrue(entity.getTimerPersistence());
      assertFalse(entity.isClustered());
      assertFalse(entity.doDistributedCacheInvalidations());
      
      assertDefaultInvoker(InvokerBindingMetaData.CMP_2x, entity);
      
      assertNullEnvironment(entity);

      assertNull(entity.getSecurityIdentityMetaData());

      assertNull(entity.getDistributedCacheInvalidationConfig());
     
      assertEmpty(entity.getDepends());

      assertNull(entity.getIorSecurityConfigMetaData());
   }
*/   
   private JBossMessageDrivenBeanMetaData assertJBossMessageDrivenBean(String prefix, JBossMetaData jbossMetaData)
   {
      JBossMessageDrivenBeanMetaData ejb = assertJBossEnterpriseBean(prefix, jbossMetaData, JBossMessageDrivenBeanMetaData.class);
      assertFalse(ejb.isSession());
      assertFalse(ejb.isEntity());
      assertTrue(ejb.isMessageDriven());
      return ejb;
   }
   
/*   private MessageDrivenMetaData assertJBossMessageDrivenBean(String prefix, ApplicationMetaData application)
   {
      MessageDrivenMetaData ejb = assertBeanMetaData(prefix, application, MessageDrivenMetaData.class);
      assertFalse(ejb.isSession());
      assertFalse(ejb.isEntity());
      assertTrue(ejb.isMessageDriven());
      return ejb;
   }
*/
   private void assertFullMessageDrivenBean(String prefix, JBossMetaData jbossMetaData, boolean first, Mode mode)
   {
      JBossMessageDrivenBeanMetaData mdb = assertJBossMessageDrivenBean(prefix, jbossMetaData);
      assertId(prefix, mdb);
      
      if(mode != Mode.JBOSS_DTD)
      {
         assertDescriptionGroup(prefix, mdb.getDescriptionGroup());
         assertAnnotations(prefix, 2, mdb.getAnnotations());
         assertIgnoreDependency(prefix, mdb.getIgnoreDependency());
         assertEquals(prefix + "AOPDomain", mdb.getAopDomainName());
         assertJndiRefs(prefix, 2, mdb.getJndiRefs(), mode);
         assertMethodAttributes(prefix, mdb.getMethodAttributes(), mode);
      }
      
      assertEquals(prefix + "DestinationJndiName", mdb.getDestinationJndiName());
      assertEquals(prefix + "LocalJndiName", mdb.getLocalJndiName());
      assertEquals(prefix + "User", mdb.getMdbUser());
      assertEquals(prefix + "Password", mdb.getMdbPassword());
      assertEquals(prefix + "ClientId", mdb.getMdbClientId());
      assertEquals(prefix + "SubscriptionId", mdb.getMdbSubscriptionId());
      assertEquals(prefix + "RAR", mdb.getResourceAdapterName());
      assertEquals(prefix + "ConfigurationName", mdb.getConfigurationName());
      assertEquals(prefix + "SecurityProxy", mdb.getSecurityProxy());

      if (first)
      {
         assertTrue(mdb.isExceptionOnRollback());
         assertTrue(mdb.isTimerPersistence());
      }
      else
      {
         assertFalse(mdb.isExceptionOnRollback());
         assertFalse(mdb.isTimerPersistence());
      }
      
      assertInvokerBindings(prefix, mdb.getInvokerBindings(), mode);

      assertEnvironment(prefix, mdb.getJndiEnvironmentRefsGroup(), false, mode);

      assertSecurityIdentity(prefix, "SecurityIdentity", mdb.getSecurityIdentity(), false, mode);

      assertDepends(prefix, 2, mdb.getDepends());
      
      assertIORSecurityConfig(prefix, mdb.getIorSecurityConfig(), mode);
      
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

   private void assertContainerConfigurations(JBossMetaData jbossMetaData, Mode mode)
   {
      ContainerConfigurationsMetaData configurations = jbossMetaData.getContainerConfigurations();
      assertNotNull(configurations);
      int expectedSize = hasStandardJBoss ? 34 : 13;
      assertEquals(expectedSize, configurations.size());
      assertNullContainerConfiguration("containerConfiguration0", jbossMetaData);
      assertFullContainerConfiguration("containerConfiguration1", jbossMetaData, true, mode);
      assertFullContainerConfiguration("containerConfiguration2", jbossMetaData, false, mode);
      ContainerConfigurationMetaData configuration = assertContainerConfiguration("containerConfiguration3", jbossMetaData);
      assertEquals(CommitOption.B, configuration.getCommitOption());
      configuration = assertContainerConfiguration("containerConfiguration4", jbossMetaData);
      assertEquals(CommitOption.C, configuration.getCommitOption());
   }
   
   private ContainerConfigurationMetaData assertContainerConfiguration(String prefix, JBossMetaData jBossMetaData)
   {
      ContainerConfigurationMetaData configuration = jBossMetaData.getContainerConfiguration(prefix + "Name");
      assertNotNull(configuration);
      assertEquals(prefix + "Name", configuration.getContainerName());
      String policy = configuration.getLockingPolicy();
      if(hasStandardJBoss && policy.startsWith("org.jboss"))
         assertEquals("org.jboss.ejb.plugins.lock.QueuedPessimisticEJBLock", policy);
      else if(policy != null)
         assertEquals(prefix+"LockingPolicy", policy);
      return configuration;
   }
   
   private void assertFullContainerConfiguration(String prefix, JBossMetaData jBossMetaData, boolean first, Mode mode)
   {
      ContainerConfigurationMetaData configuration = assertContainerConfiguration(prefix, jBossMetaData);
      if(mode != Mode.JBOSS_DTD)
      {
         assertId(prefix, configuration);
         assertDescriptions(prefix, configuration.getDescriptions());
      }
      assertEquals(prefix + "Extends", configuration.getExtendsName());
      assertEquals(prefix + "InstancePool", configuration.getInstancePool());
      assertEquals(prefix + "InstanceCache", configuration.getInstanceCache());
      assertEquals(prefix + "PersistenceManager", configuration.getPersistenceManager());
      assertEquals(prefix + "WebClassLoader", configuration.getWebClassLoader());
      assertEquals(prefix + "LockingPolicy", configuration.getLockingPolicy());
      assertEquals(prefix + "SecurityDomain", configuration.getSecurityDomain());
      if (first)
      {
         assertTrue(configuration.isCallLogging());
         assertTrue(configuration.isSyncOnCommitOnly());
         assertTrue(configuration.isInsertAfterEjbPostCreate());
         assertTrue(configuration.isEjbStoreOnClean());
         assertTrue(configuration.isStoreNotFlushed());
      }
      else
      {
         assertFalse(configuration.isCallLogging());
         assertFalse(configuration.isSyncOnCommitOnly());
         assertFalse(configuration.isInsertAfterEjbPostCreate());
         assertFalse(configuration.isEjbStoreOnClean());
         assertFalse(configuration.isStoreNotFlushed());
      }
      
      assertInvokerProxyBindingNames(prefix, 2, configuration.getInvokerProxyBindingNames());
      Element interceptors = configuration.getContainerInterceptors();
      assertContainerInterceptors(prefix, interceptors);
      Element cacheConf = configuration.getContainerCacheConf();
      assertContainerCacheConf(prefix, cacheConf);
      Element poolConf = configuration.getContainerPoolConf();
      assertContainerPoolConf(prefix, poolConf, mode);
      assertEquals(CommitOption.D, configuration.getCommitOption());
      assertEquals(10000, configuration.getOptiondRefreshRateMillis());
      assertClusterConfig(prefix, configuration.getClusterConfig(), true, mode);
      assertDepends(prefix, 2, configuration.getDepends());
   }
   
   private void assertNullContainerConfiguration(String prefix, JBossMetaData jBossMetaData)
   {
      ContainerConfigurationMetaData configuration = assertContainerConfiguration(prefix, jBossMetaData);
      assertNull(configuration.getId());
      assertNull(configuration.getDescriptions());
      assertNull(configuration.getExtendsName());
      assertNull(configuration.getInstancePool());
      assertNull(configuration.getInstanceCache());
      assertNull(configuration.getPersistenceManager());
      if(this.hasStandardJBoss)
         assertEquals("org.jboss.web.WebClassLoader", configuration.getWebClassLoader());
      else
         assertEquals(null, configuration.getWebClassLoader());
      if(this.hasStandardJBoss)
         assertEquals("org.jboss.ejb.plugins.lock.QueuedPessimisticEJBLock", configuration.getLockingPolicy());
      else
         assertEquals(null, configuration.getLockingPolicy());
      assertNull(configuration.getSecurityDomain());
      assertFalse(configuration.isCallLogging());
      assertFalse(configuration.isSyncOnCommitOnly());
      assertFalse(configuration.isInsertAfterEjbPostCreate());
      assertFalse(configuration.isEjbStoreOnClean());
      assertTrue(configuration.isStoreNotFlushed());
      assertNull(configuration.getInvokerProxyBindingNames());
      assertNull(configuration.getContainerInterceptors());
      assertNull(configuration.getContainerPoolConf());
      assertNull(configuration.getContainerCacheConf());
      assertEquals(CommitOption.A, configuration.getCommitOption());
      if(this.hasStandardJBoss)
         assertEquals(30000, configuration.getOptiondRefreshRateMillis());
      else
         assertEquals(0, configuration.getOptiondRefreshRateMillis());
      assertNull(configuration.getClusterConfig());
      if(this.hasStandardJBoss)
         assertTrue(configuration.getDepends().isEmpty());
      else      
         assertNull(configuration.getDepends());
   }

   /**
    * Validate the container-configuration/container-interceptors config
    * @param prefix
    * @param config
    */
   private void assertContainerInterceptors(String prefix, Element config)
   {
      assertNotNull(config);
      NodeList interceptors = config.getElementsByTagName("interceptor");
      assertEquals("interceptors count is 5", 5, interceptors.getLength());
      for(int n = 0; n < interceptors.getLength(); n ++)
      {
         Element interceptor = (Element) interceptors.item(n);
         String tx = interceptor.getAttribute("transaction");
         String text = interceptor.getTextContent();
         String expected;
         if (tx.length() == 0 || tx.equals("Both"))
            expected = prefix+".Interceptor"+(n+1);
         else if (tx.equals("Container"))
            expected = prefix+".Interceptor"+(n+1)+"c";
         else
            expected = prefix+".Interceptor"+(n+1)+"b";
         assertEquals(tx, expected, text);
      }
   }
   /**
    * Validate the container-configuration/container-cache-conf
    * @param prefix
    * @param config
    */
   private void assertContainerCacheConf(String prefix, Element config)
   {
      assertNotNull(config);
      assertEquals(prefix+".Policy", getElementText("cache-policy", config));
      Element policyConf = getElement("cache-policy-conf", config);
      assertNotNull(policyConf);
      assertEquals("50", getElementText("min-capacity", policyConf));
      assertEquals("1000000", getElementText("max-capacity", policyConf));
      assertEquals("1800", getElementText("remover-period", policyConf));
      assertEquals("1800", getElementText("max-bean-life", policyConf));
      assertEquals("300", getElementText("overager-period", policyConf));
      assertEquals("600", getElementText("max-bean-age", policyConf));
      assertEquals("400", getElementText("resizer-period", policyConf));
      assertEquals("60", getElementText("max-cache-miss-period", policyConf));
      assertEquals("1", getElementText("min-cache-miss-period", policyConf));
      assertEquals("0.75", getElementText("cache-load-factor", policyConf));
   }

   /**
    * Validate the container-configuration/container-pool-conf
    * @param prefix
    * @param config
    */
   private void assertContainerPoolConf(String prefix, Element config, Mode mode)
   {
      assertNotNull(prefix+" container-pool-conf", config);
      String id = config.getAttribute("id");
      // standardjboss.xml does not specify ids
      if ((mode != Mode.JBOSS_DTD && hasStandardJBoss == false) || id.length() > 0)
         assertEquals(prefix+"-pool-conf", id);
      assertEquals("100", getElementText("MaximumSize", config));
   }

   private void assertInvokerProxyBindingNames(String prefix, int size, Collection<String> names)
   {
      assertNotNull(names);
      assertEquals(size, names.size());
      for(int count = 1; count <= names.size(); ++count)
      {
         assertTrue(names.contains(prefix + "InvokerProxyBindingName" + count));
      }
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
   
   private void assertInvokerProxyBindings(InvokerProxyBindingsMetaData bindings, Mode mode)
   {
      assertNotNull(bindings);
      if(mode != Mode.JBOSS_DTD)
      {
         assertId("invoker-proxy-bindings", bindings);
         assertDescriptions("invoker-proxy-bindings", bindings.getDescriptions());
      }
      assertEquals(2, bindings.size());
      int count = 1;
      for (InvokerProxyBindingMetaData binding : bindings)
      {
         assertInvokerProxyBinding("invokerProxyBinding" + count, count, binding, mode);
         ++count;
      }
   }
   
   private void assertInvokerProxyBinding(String prefix, int count, InvokerProxyBindingMetaData binding, Mode mode)
   {
      assertNotNull(binding);
      if(mode != Mode.JBOSS_DTD)
      {
         assertId(prefix, binding);
         assertDescriptions(prefix, binding.getDescriptions());
      }
      assertEquals(prefix + "Name", binding.getInvokerProxyBindingName());
      assertEquals(prefix + "InvokerMBean", binding.getInvokerMBean());
      assertEquals(prefix + "ProxyFactory", binding.getProxyFactory());
      // The DOM invoker-proxy-config
      Element config = binding.getProxyFactoryConfig();
      if (config == null)
         return;
      if (config.getElementsByTagName("client-interceptors").getLength() > 0)
         assertInvokerProxyBindingPFCClientInterceptor(prefix, count, config);
   }
   private void assertInvokerProxyBindingPFCClientInterceptor(String prefix, int count, Element config)
   {
      NodeList ci = config.getElementsByTagName("client-interceptors");
      assertEquals("client-interceptors count is 1", 1, ci.getLength());
      Element cis = (Element) ci.item(0);
      NodeList home = cis.getElementsByTagName("home");
      Element homeE = (Element) home.item(0);
      NodeList homeInterceptors = homeE.getElementsByTagName("interceptor");
      assertEquals("home count is 4", 4, homeInterceptors.getLength());
      for(int n = 0; n < homeInterceptors.getLength(); n ++)
      {
         Element interceptor = (Element) homeInterceptors.item(n);
         String callByValue = interceptor.getAttribute("call-by-value");
         String text = interceptor.getTextContent();
         String expected;
         if (callByValue.length() == 0)
            expected = "org.jboss.proxy.ejb.HomeInterceptor"+(n+1)+"."+count;
         else
            expected = "org.jboss.proxy.ejb.HomeInterceptor"+(Boolean.valueOf(callByValue)?"cbvt" : "cbvf")+(n+1)+"."+count;
         assertEquals(expected, text);
      }
      NodeList bean = cis.getElementsByTagName("bean");
      Element beanE = (Element) bean.item(0);
      NodeList beanInterceptors = beanE.getElementsByTagName("interceptor");
      assertEquals("bean count is 4", 4, beanInterceptors.getLength());
      for(int n = 0; n < beanInterceptors.getLength(); n ++)
      {
         Element interceptor = (Element) beanInterceptors.item(n);
         String callByValue = interceptor.getAttribute("call-by-value");
         String text = interceptor.getTextContent();
         String expected;
         if (callByValue.length() == 0)
            expected = "org.jboss.proxy.ejb.BeanInterceptor"+(n+1)+"."+count;
         else
            expected = "org.jboss.proxy.ejb.BeanInterceptor"+(Boolean.valueOf(callByValue)?"cbvt" : "cbvf")+(n+1)+"."+count;
         assertEquals(expected, text);
      }
   }

   private void assertInvokerBindings(String prefix, InvokerBindingsMetaData bindings, Mode mode)
   {
      if(mode != Mode.JBOSS_DTD)
      {
         assertId(prefix + "InvokerBindings", bindings);
         assertDescriptions(prefix + "InvokerBindings", bindings.getDescriptions());
      }
      assertNotNull(bindings);
      assertEquals(2, bindings.size());
      int count = 1;
      for (InvokerBindingMetaData binding : bindings)
      {
         assertInvokerBinding(prefix, count, binding, mode);
         ++count;
      }
   }
   
   private void assertInvokerBinding(String ejbPrefix, int count, InvokerBindingMetaData binding, Mode mode)
   {
      String invokerPrefix = ejbPrefix + "Invoker" + count;
      assertNotNull(binding);
      if(mode != Mode.JBOSS_DTD)
      {
         assertId(invokerPrefix, binding);
         assertDescriptions(invokerPrefix, binding.getDescriptions());
      }
      assertEquals("invokerProxyBinding"+count+"Name", binding.getInvokerProxyBindingName());
      assertEquals(invokerPrefix + "JndiName", binding.getJndiName());
      // TODO LAST ejb-ref - needs a seperate test
      List<EjbRef> ejbRefs = binding.getEjbRefs();
      assertNotNull(ejbRefs);
      assertEquals(count, ejbRefs.size());
      int i = 1;
      for(InvokerBindingMetaData.EjbRef ejbRef : ejbRefs)
      {
         assertNotNull(ejbRef);
         assertEquals(ejbPrefix + "EjbRef" + i + "Name", ejbRef.getEjbRefName());
         assertEquals(invokerPrefix + "EjbName" + i, ejbRef.getJndiName());
         ++i;
      }
   }
   
   private void assertResourceManagers(ResourceManagersMetaData resources, Mode mode)
   {
      assertNotNull(resources);
      if(mode != Mode.JBOSS_DTD)
      {
         assertId("resource-managers", resources);
         assertDescriptions("resource-managers", resources.getDescriptions());
      }
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
      if(mode != Mode.JBOSS_DTD)
      {
         assertId(prefix, resource);
         assertDescriptions(prefix, resource.getDescriptions());
      }
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
      if(mode != Mode.JBOSS_DTD)
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

/*   @Override
   protected void assertSecurityRole(String prefix, org.jboss.security.SecurityRoleMetaData securityRoleMetaData)
   {
      super.assertSecurityRole(prefix, securityRoleMetaData);
      assertPrincipals(prefix, 2, securityRoleMetaData.getPrincipals());
   }
*/
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
   
   private void assertPartitionName(ClusterConfigMetaData config, String expectedSource)
   {
      String value= config.getPartitionName();      
      assertExpectedSource(value, expectedSource, "PartitionName");
   }
   
   private void assertHomeLoadBalancePolicy(ClusterConfigMetaData config, String expectedSource)
   {
      String value = config.getHomeLoadBalancePolicy();      
      assertExpectedSource(value, expectedSource, "HomeLoadBalancePolicy");
   }
   
   private void assertBeanLoadBalancePolicy(ClusterConfigMetaData config, String expectedSource)
   {
      String value = config.getBeanLoadBalancePolicy();      
      assertExpectedSource(value, expectedSource, "BeanLoadBalancePolicy");
   }
   
   private void assertSessionStateManagerJndiName(ClusterConfigMetaData config, String expectedSource)
   {
      String value = config.getSessionStateManagerJndiName();     
      assertExpectedSource(value, expectedSource, "SessionStateManagerJndiName");
   }
   
   private void assertExpectedSource(String value, String expectedPrefix, String suffix)
   {
      if (expectedPrefix == null)
         assertNull(value);
      else
         assertEquals(expectedPrefix + "Configuration" + suffix, value);
   }
   
   @Override
   protected void assertResourceGroup(String prefix, ResourceInjectionMetaData resourceInjectionMetaData, boolean full, boolean first, Mode mode)
   {
      if(mode == Mode.JBOSS)
      {
         super.assertResourceGroupNoJndiName(prefix, resourceInjectionMetaData, true, first);
         if (first)
            assertTrue(resourceInjectionMetaData.isDependencyIgnored());
         else
            assertFalse(resourceInjectionMetaData.isDependencyIgnored());
      }
      assertEquals(prefix + "JndiName", resourceInjectionMetaData.getMappedName());
   }
      
   @Override
   protected void assertSecurityIdentity(String ejbName, String type, SecurityIdentityMetaData securityIdentity, boolean full, Mode mode)
   {
      super.assertSecurityIdentity(ejbName, type, securityIdentity, full, mode);
      assertEquals(ejbName + type + "RunAsPrincipal", securityIdentity.getRunAsPrincipal());
   }
   
/*   @Override
   protected void assertSecurityIdentity(String ejbName, String type, org.jboss.metadata.SecurityIdentityMetaData securityIdentity, boolean full)
   {
      super.assertSecurityIdentity(ejbName, type, securityIdentity, full);
      assertEquals(ejbName + type + "RunAsPrincipal", securityIdentity.getRunAsPrincipalName());
   }
*/
/*   @Override
   protected void assertMethodAttributes(String ejbName, BeanMetaData bean)
   {
      assertTrue(bean.isMethodReadOnly("getSomething"));
      assertEquals(5000, bean.getTransactionTimeout("getSomething"));
      assertFalse(bean.isMethodReadOnly("setSomething"));
      assertEquals(0, bean.getTransactionTimeout("setSomething"));
   }
*/}
