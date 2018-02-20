/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jboss.metadata.ejb.jboss.CacheInvalidationConfigMetaData;
import org.jboss.metadata.ejb.jboss.ClusterConfigMetaData;
import org.jboss.metadata.ejb.jboss.ContainerConfigurationMetaData;
import org.jboss.metadata.ejb.jboss.InvokerBindingMetaData;
import org.jboss.metadata.ejb.jboss.InvokerBindingsMetaData;
import org.jboss.metadata.ejb.jboss.InvokerProxyBindingMetaData;
import org.jboss.metadata.ejb.jboss.InvokerProxyBindingsMetaData;
import org.jboss.metadata.ejb.jboss.JBoss50DTDMetaData;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossAssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.jboss.JBossConsumerBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossGenericBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaDataWrapper;
import org.jboss.metadata.ejb.jboss.JBossServiceBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.MessagePropertiesMetaData;
import org.jboss.metadata.ejb.jboss.ProducerMetaData;
import org.jboss.metadata.ejb.jboss.RemoteBindingMetaData;
import org.jboss.metadata.common.ejb.ResourceManagerMetaData;
import org.jboss.metadata.common.ejb.ResourceManagersMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EjbJarVersion;
import org.jboss.metadata.ejb.spec.EjbType;
import org.jboss.metadata.ejb.spec.EnterpriseBeansMetaData;
import org.jboss.metadata.ejb.spec.GenericBeanMetaData;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.ejb.spec.SubscriptionDurability;
import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceType;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.javaee.spec.PortComponent;
import org.jboss.metadata.javaee.spec.ResourceAuthorityType;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.RunAsMetaData;
import org.jboss.metadata.ejb.merge.EjbMergeUtil;

/**
 * Miscellaneous tests with a JBoss 5 xml.
 *
 * @author <a href="carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 88355 $
 */
@SuppressWarnings("deprecation")
public class JBoss50UnitTestCase extends AbstractEJBEverythingTest {
    public JBoss50UnitTestCase(String name) {
        super(name);
    }

    private static ResourceInjectionTargetMetaData createInjectionTarget(String injectionTargetClass, String injectionTargetName) {
        ResourceInjectionTargetMetaData injectionTarget = new ResourceInjectionTargetMetaData();
        injectionTarget.setInjectionTargetClass(injectionTargetClass);
        injectionTarget.setInjectionTargetName(injectionTargetName);
        return injectionTarget;
    }

    protected JBossMetaData unmarshal() throws Exception {
        return unmarshal(JBossMetaData.class);
    }

    /**
     * Test for consumer bean
     */
    public void testConsumer() throws Exception {
        JBossMetaData result = unmarshal();

        assertEquals(1, result.getEnterpriseBeans().size());
        JBossConsumerBeanMetaData bean = (JBossConsumerBeanMetaData) result.getEnterpriseBean("DeploymentDescriptorQueueTestConsumer");
        assertNotNull(bean);
        assertTrue(bean.isConsumer());
        assertEquals("org.jboss.ejb3.test.consumer.DeploymentDescriptorQueueTestConsumer", bean.getEjbClass());
        assertEquals("queue/consumertest", bean.getMessageDestination());
        assertEquals("javax.jms.Queue", bean.getMessageDestinationType());

        assertEquals(2, bean.getProducers().size());
        {
            ProducerMetaData producer = bean.getProducers().get(0);
            assertEquals("org.jboss.ejb3.test.consumer.DeploymentDescriptorQueueTestRemote", producer.getClassName());
        }
        {
            ProducerMetaData producer = bean.getProducers().get(1);
            assertEquals("org.jboss.ejb3.test.consumer.DeploymentDescriptorQueueTestXA", producer.getClassName());
            assertEquals("java:/JmsXA", producer.getConnectionFactory());
        }
        assertEquals(1, bean.getLocalProducers().size());
        {
            ProducerMetaData producer = bean.getLocalProducers().get(0);
            assertEquals("org.jboss.ejb3.test.consumer.DeploymentDescriptorQueueTestLocal", producer.getClassName());
            assertTrue(producer.isLocal());
        }
        assertEquals(2, bean.getCurrentMessage().size());
        assertNotNull(bean.getCurrentMessage().getMethodAttribute("currentMessage"));
        assertNotNull(bean.getCurrentMessage().getMethodAttribute("setMessage"));
        assertEquals(2, bean.getMessageProperties().size());
        {
            MessagePropertiesMetaData messageProperties = bean.getMessageProperties().get(0);
            assertEquals("org.jboss.ejb3.test.consumer.DeploymentDescriptorQueueTest", messageProperties.getClassName());
            assertEquals("method2", messageProperties.getMethod().getMethodName());
            assertEquals("NonPersistent", messageProperties.getDelivery());
        }
        {
            MessagePropertiesMetaData messageProperties = bean.getMessageProperties().get(1);
            assertEquals("org.jboss.ejb3.test.consumer.DeploymentDescriptorQueueTestXA", messageProperties.getClassName());
            assertEquals("method2", messageProperties.getMethod().getMethodName());
            assertEquals("Persistent", messageProperties.getDelivery());
            assertEquals(4, (int) messageProperties.getPriority());
        }

        String pkg = "consumer";
        String injectionTargetClass = "org.jboss.ejb3.test.consumer.DeploymentDescriptorQueueTestConsumer";
        {
            assertEquals(1, bean.getEjbReferences().size());
            EJBReferenceMetaData ejbRef = bean.getEjbReferenceByName("ejb/StatelessRemote");
            assertNotNull(ejbRef);
            //assertEquals("test", ejbRef.getDescriptions().value()[0].value());
            assertEquals(EJBReferenceType.Session, ejbRef.getEjbRefType());
            assertEquals("org.jboss.ejb3.test." + pkg + ".StatelessRemote", ejbRef.getRemote());
            assertEquals("StatelessBean", ejbRef.getLink());
            assertEquals("StatelessBean/remote", ejbRef.getJndiName());
            assertEquals(1, ejbRef.getInjectionTargets().size());
            ResourceInjectionTargetMetaData injectionTarget = ejbRef.getInjectionTargets().iterator().next();
            assertNotNull(injectionTarget);
            assertEquals(injectionTargetClass, injectionTarget.getInjectionTargetClass());
            assertEquals("stateless", injectionTarget.getInjectionTargetName());
        }

        {
            assertEquals(1, bean.getEjbLocalReferences().size());
            EJBLocalReferenceMetaData ejbLocalRef = bean.getEjbLocalReferenceByName("ejb/StatelessLocal");
            assertNotNull(ejbLocalRef);
            assertEquals(EJBReferenceType.Session, ejbLocalRef.getEjbRefType());
            assertEquals("org.jboss.ejb3.test." + pkg + ".StatelessLocal", ejbLocalRef.getLocal());
            assertEquals("StatelessBean", ejbLocalRef.getLink());
            assertEquals("StatelessBean/local", ejbLocalRef.getJndiName());
            assertEquals(1, ejbLocalRef.getInjectionTargets().size());
            ResourceInjectionTargetMetaData injectionTarget = ejbLocalRef.getInjectionTargets().iterator().next();
            assertNotNull(injectionTarget);
            assertEquals(injectionTargetClass, injectionTarget.getInjectionTargetClass());
            assertEquals("setStatelessLocal", injectionTarget.getInjectionTargetName());
        }

        {
            assertEquals(1, bean.getResourceReferences().size());
            ResourceReferenceMetaData resourceRef = bean.getResourceReferenceByName("testDatasource");
            assertNotNull(resourceRef);
            assertEquals("javax.sql.DataSource", resourceRef.getType());
            assertEquals(ResourceAuthorityType.Container, resourceRef.getResAuth());
            assertEquals("java:/DefaultDS", resourceRef.getMappedName());
            assertEquals(1, resourceRef.getInjectionTargets().size());
            ResourceInjectionTargetMetaData injectionTarget = resourceRef.getInjectionTargets().iterator().next();
            assertNotNull(injectionTarget);
            assertEquals(injectionTargetClass, injectionTarget.getInjectionTargetClass());
            assertEquals("testDatasource", injectionTarget.getInjectionTargetName());
        }

        {
            assertEquals(1, bean.getResourceEnvironmentReferences().size());
            ResourceEnvironmentReferenceMetaData resourceEnvRef = bean.getResourceEnvironmentReferenceByName("res/aQueue");
            assertEquals("javax.jms.Queue", resourceEnvRef.getType());
            assertEquals("queue/mdbtest", resourceEnvRef.getJndiName());
        }

      /*
      {
         assertEquals(1, bean.getMessageDestinationReferences().size());
         MessageDestinationReferenceMetaData messageDestinationRef = bean.getMessageDestinationReferenceByName("messageDestinationRef");
         assertNotNull(messageDestinationRef);
         assertEquals("mappedName", messageDestinationRef.getMappedName());
      }
      */
    }

    /**
     * Test whether the cardinality of depends and ignore-dependency is correct.
     */
    public void testDependencies() throws Exception {
//      JBossXBTestDelegate xbdelegate = (JBossXBTestDelegate) super.getDelegate();
//      xbdelegate.setValidateSchema(false);

        JBossMetaData result = unmarshal();

        assertEquals(1, result.getEnterpriseBeans().size());
        JBossEnterpriseBeanMetaData bean = (JBossEnterpriseBeanMetaData) result.getEnterpriseBean("MyStatelessBean");
        assertNotNull(bean);
        Set<String> expected = new HashSet<String>();
        expected.add("A");
        expected.add("B");
        assertEquals(expected, bean.getDepends());

        assertNotNull(bean.getIgnoreDependency());
        Set<ResourceInjectionTargetMetaData> expectedInjectionTargets = new HashSet<ResourceInjectionTargetMetaData>();
        for (int i = 1; i <= 2; i++) {
            expectedInjectionTargets.add(createInjectionTarget("Class" + i, Character.toString((char) ('B' + i))));
        }
        assertEquals(expectedInjectionTargets, bean.getIgnoreDependency().getInjectionTargets());
    }

    /**
     * EJBTHREE-936: allow for an unknown bean type
     */
    public void testEjbthree936() throws Exception {
        // normally from the annotation scanner
        EjbJarMetaData ejbJar = new EjbJarMetaData(EjbJarVersion.EJB_3_1);
        ejbJar.setEnterpriseBeans(new EnterpriseBeansMetaData());
        GenericBeanMetaData sessionBean = new GenericBeanMetaData();
        sessionBean.setEjbName("MyStatelessBean");
        ejbJar.getEnterpriseBeans().add(sessionBean);

        JBossMetaData jboss = unmarshal();

        assertEquals(1, jboss.getEnterpriseBeans().size());

        // create a merged view
        JBossMetaData merged = EjbMergeUtil.merge(jboss, ejbJar);
        assertNotNull(merged.getEnterpriseBeans());
        // As of JBMETA-1, this is now a JBossSessionBeanMetaData
        // JBossGenericBeanMetaData bean = (JBossGenericBeanMetaData) merged.getEnterpriseBean("MyStatelessBean");
        JBossSessionBeanMetaData bean = (JBossSessionBeanMetaData) merged.getEnterpriseBean("MyStatelessBean");
        assertEquals(1, bean.getResourceReferences().size());
        ResourceReferenceMetaData ref = bean.getResourceReferenceByName("qFactory");
        assertNotNull(ref);
        assertEquals("ConnectionFactory", ref.getJndiName());
    }

    /**
     * EJBTHREE-936: allow for an unknown bean type
     */
    public void testEjbthree936WithoutScanner() throws Exception {
        JBossMetaData jboss = unmarshal("JBoss50_testEjbthree936.xml", JBossMetaData.class);

        assertEquals(1, jboss.getEnterpriseBeans().size());

        // create a merged view
        JBossMetaData merged = EjbMergeUtil.merge(jboss, null);
        assertNotNull(merged.getEnterpriseBeans());
        JBossGenericBeanMetaData bean = (JBossGenericBeanMetaData) merged.getEnterpriseBean("MyStatelessBean");
        assertEquals(1, bean.getResourceReferences().size());
        ResourceReferenceMetaData ref = bean.getResourceReferenceByName("qFactory");
        assertNotNull(ref);
        assertEquals("ConnectionFactory", ref.getJndiName());
    }

    public void testRemoteBindingsWithoutDecorator() throws Exception {
        JBossMetaData jboss = unmarshal("JBoss50_testRemoteBindings.xml", JBossMetaData.class);

        assertEquals(1, jboss.getEnterpriseBeans().size());

        JBossSessionBeanMetaData sessionBean = (JBossSessionBeanMetaData) jboss.getEnterpriseBean("StatefulBean");
        String determinedJndiName = sessionBean.determineJndiName();
        boolean determinedValid = false;
        for (int i = 0; i < 2; i++) {
            RemoteBindingMetaData remoteBinding = sessionBean.getRemoteBindings().get(i);
            String jndiName = "jndiName" + (i + 1);
            assertEquals(jndiName, remoteBinding.getJndiName());
            assertEquals("clientBindUrl" + (i + 1), remoteBinding.getClientBindUrl());
            determinedValid |= jndiName.equals(determinedJndiName);
        }
        assertTrue("determinedJndiName is not one of the remote-binding values", determinedValid);
    }

    /**
     * A test for resource-adapter-name (ejb3 jca/inflow unit test)
     */
    public void testResourceAdapterName() throws Exception {
        JBossMetaData result = unmarshal();

        assertEquals(1, result.getEnterpriseBeans().size());
        JBossEnterpriseBeanMetaData bean = result.getEnterpriseBean("TestMDB");
        assertNotNull(bean);
        assertTrue(bean instanceof JBossMessageDrivenBeanMetaData);
        assertEquals("jcainflow.rar", ((JBossMessageDrivenBeanMetaData) bean).getResourceAdapterName());
    }

    /**
     * A simple test coming from ejb3 naming unit test.
     *
     * @throws Exception
     */
    public void testSimple() throws Exception {
        JBossMetaData result = unmarshal();

        assertEquals(1, result.getEnterpriseBeans().size());
        JBossEnterpriseBeanMetaData bean = result.getEnterpriseBean("StatefulOverrideBean");
        assertNotNull(bean);
        assertTrue(bean instanceof JBossSessionBeanMetaData);
        assertEquals("StatefulOverride", ((JBossSessionBeanMetaData) bean).getJndiName());
    }

    /**
     * Test the service bean from dd.
     */
    public void testService() throws Exception {
//      JBossXBTestDelegate xbdelegate = (JBossXBTestDelegate) super.getDelegate();
//      xbdelegate.setValidateSchema(false);

        JBossMetaData result = unmarshal();

        JBossServiceBeanMetaData bean = (JBossServiceBeanMetaData) result.getEnterpriseBean("ServiceSix");
        assertNotNull(bean);
        assertEquals("ServiceSix", bean.getEjbName());

        assertEquals("service description", bean.getDescriptionGroup().getDescription());

        assertEquals(1, bean.getBusinessLocals().size());
        assertTrue(bean.getBusinessLocals().contains("org.jboss.ejb3.test.service.ServiceSixLocal"));
        assertEquals(1, bean.getBusinessRemotes().size());
        assertTrue(bean.getBusinessRemotes().contains("org.jboss.ejb3.test.service.ServiceSixRemote"));
        assertEquals("org.jboss.ejb3.test.service.ServiceSix", bean.getEjbClass());
        {
            assertEquals(1, bean.getEjbReferences().size());
            EJBReferenceMetaData ejbRef = bean.getEjbReferenceByName("ejb/StatelessBean");
            assertNotNull(ejbRef);
            assertEquals("test", ejbRef.getDescriptions().value()[0].value());
            assertEquals(EJBReferenceType.Session, ejbRef.getEjbRefType());
            assertEquals("org.jboss.ejb3.test.service.StatelessRemote", ejbRef.getRemote());
            assertEquals("StatelessBean", ejbRef.getLink());
            assertEquals("StatelessBean/remote", ejbRef.getJndiName());
            assertEquals(1, ejbRef.getInjectionTargets().size());
            ResourceInjectionTargetMetaData injectionTarget = ejbRef.getInjectionTargets().iterator().next();
            assertNotNull(injectionTarget);
            assertEquals("org.jboss.ejb3.test.service.ServiceSix", injectionTarget.getInjectionTargetClass());
            assertEquals("stateless", injectionTarget.getInjectionTargetName());
        }

        {
            assertEquals(1, bean.getEjbLocalReferences().size());
            EJBLocalReferenceMetaData ejbLocalRef = bean.getEjbLocalReferenceByName("ejb/StatelessLocal");
            assertNotNull(ejbLocalRef);
            assertEquals(EJBReferenceType.Session, ejbLocalRef.getEjbRefType());
            assertEquals("org.jboss.ejb3.test.service.StatelessLocal", ejbLocalRef.getLocal());
            assertEquals("StatelessBean", ejbLocalRef.getLink());
            assertEquals("StatelessBean/local", ejbLocalRef.getJndiName());
            assertEquals(1, ejbLocalRef.getInjectionTargets().size());
            ResourceInjectionTargetMetaData injectionTarget = ejbLocalRef.getInjectionTargets().iterator().next();
            assertNotNull(injectionTarget);
            assertEquals("org.jboss.ejb3.test.service.ServiceSix", injectionTarget.getInjectionTargetClass());
            assertEquals("setStatelessLocal", injectionTarget.getInjectionTargetName());
        }

        {
            assertEquals(1, bean.getResourceReferences().size());
            ResourceReferenceMetaData resourceRef = bean.getResourceReferenceByName("testDatasource");
            assertNotNull(resourceRef);
            assertEquals("javax.sql.DataSource", resourceRef.getType());
            assertEquals(ResourceAuthorityType.Container, resourceRef.getResAuth());
            assertEquals("java:/DefaultDS", resourceRef.getMappedName());
            assertEquals(1, resourceRef.getInjectionTargets().size());
            ResourceInjectionTargetMetaData injectionTarget = resourceRef.getInjectionTargets().iterator().next();
            assertNotNull(injectionTarget);
            assertEquals("org.jboss.ejb3.test.service.ServiceSix", injectionTarget.getInjectionTargetClass());
            assertEquals("testDatasource", injectionTarget.getInjectionTargetName());
        }

        {
            assertEquals(1, bean.getResourceEnvironmentReferences().size());
            ResourceEnvironmentReferenceMetaData resourceEnvRef = bean.getResourceEnvironmentReferenceByName("res/aQueue");
            assertEquals("javax.jms.Queue", resourceEnvRef.getType());
            assertEquals("queue/mdbtest", resourceEnvRef.getJndiName());
        }

        {
            assertEquals(1, bean.getMessageDestinationReferences().size());
            MessageDestinationReferenceMetaData messageDestinationRef = bean.getMessageDestinationReferenceByName("messageDestinationRef");
            assertNotNull(messageDestinationRef);
            assertEquals("mappedName", messageDestinationRef.getMappedName());
        }

        SecurityIdentityMetaData securityIdentity = bean.getSecurityIdentity();
        assertNotNull(securityIdentity);
        RunAsMetaData runAs = securityIdentity.getRunAs();
        assertNotNull(runAs);
        assertEquals("role name", runAs.getRoleName());
        assertEquals("run as principal", securityIdentity.getRunAsPrincipal());

        assertEquals("object name", bean.getObjectName());
        assertEquals("org.jboss.ejb3.test.service.ServiceSixManagement", bean.getManagement());
        assertEquals("xmbean", bean.getXmbean());
        assertEquals(1, bean.getRemoteBindings().size());
        RemoteBindingMetaData remoteBinding = bean.getRemoteBindings().get(0);
        assertEquals("client bind url", remoteBinding.getClientBindUrl());
        assertEquals("serviceSix/remote", bean.getJndiName());
        assertEquals("serviceSix/local", bean.getLocalJndiName());
        assertEquals("security domain", bean.getSecurityDomain());
        assertEquals(1, bean.getMethodAttributes().size());
        assertEquals(1, bean.getMethodTransactionTimeout("test"));
    }

    /**
     * Validate an ejb-jar.xml/jboss.xml/standardjboss.xml set of metadata used to
     * obtain an ejb container configuration.
     */
    public void testEjb21MergedContainerDefs()
            throws Exception {
        setValidateSchema(false);
        long start = System.currentTimeMillis();
        EjbJarMetaData specMetaData = unmarshal("EjbJar21Everything_testEverything.xml", EjbJarMetaData.class, null);
        long end = System.currentTimeMillis();
        getLog().info("EjbJar21Everything_testEverything.xml parse time = " + (end - start));
        JBoss50DTDMetaData stdMetaData = unmarshal("JBoss5xEverything_testStandard.xml", JBoss50DTDMetaData.class, null);
        long end2 = System.currentTimeMillis();
        getLog().info("JBoss5xEverything_testStandard.xml parse time = " + (end2 - end));
        JBoss50DTDMetaData jbossMetaData = unmarshal("JBoss5xEverything_testEverythingDTD.xml", JBoss50DTDMetaData.class, null);
        long end3 = System.currentTimeMillis();
        getLog().info("JBoss5xEverything_testEverythingDTD.xml parse time = " + (end3 - end2));
        //jbossMetaData.setOverridenMetaData(specMetaData);
        jbossMetaData.merge(null, specMetaData);
        JBossMetaDataWrapper wrapper = new JBossMetaDataWrapper(jbossMetaData, stdMetaData);

        // Validate version info
        assertEquals("2.1", specMetaData.getVersion());
        assertEquals("2.1", wrapper.getEjbVersion());

        // Test the unified metadata
        InvokerProxyBindingsMetaData bindings = wrapper.getInvokerProxyBindings();
        JBossEnterpriseBeansMetaData beans = wrapper.getEnterpriseBeans();
        JBossEnterpriseBeanMetaData session1Ejb = beans.get("session1EjbName");
        assertNotNull(session1Ejb);
        for (JBossEnterpriseBeanMetaData jbean : beans) {
            ContainerConfigurationMetaData beanCfg = jbean.determineContainerConfiguration();
            assertNotNull(beanCfg);
            InvokerBindingsMetaData invokers = jbean.determineInvokerBindings();
            for (InvokerBindingMetaData invoker : invokers) {
                InvokerProxyBindingMetaData ipbmd = bindings.get(invoker.getName());
                assertNotNull(invoker.getName(), ipbmd);
            }
        }

        // Test the legacy wrapper view
      /*
      ApplicationMetaData appMetaData = new ApplicationMetaData(wrapper);
      Iterator<BeanMetaData> beans2 = appMetaData.getEnterpriseBeans();
      while (beans2.hasNext())
      {
         BeanMetaData bean = (BeanMetaData) beans2.next();
         ConfigurationMetaData beanCfg = bean.getContainerConfiguration();
         assertNotNull(beanCfg);
      }
      */

        assertTrue(bindings.size() > 10);
        // Validate some know invoker bindings
        InvokerProxyBindingMetaData ssui = bindings.get("stateless-unified-invoker");
        assertNotNull(ssui);
        InvokerProxyBindingMetaData sfui = bindings.get("stateful-unified-invoker");
        assertNotNull(sfui);

      /*
      BeanMetaData beanCfg = appMetaData.getBeanByEjbName("session1EjbName");
      assertNotNull(beanCfg);
      Iterator<String> it = beanCfg.getInvokerBindings();
      org.jboss.metadata.InvokerProxyBindingMetaData imd = null;
      while (it.hasNext() && imd == null)
      {
         String invoker = it.next();
         String jndiBinding = beanCfg.getInvokerBinding(invoker);
         imd = beanCfg.getApplicationMetaData().getInvokerProxyBindingMetaDataByName(invoker);
         assertNotNull(imd);
      }
      assertNotNull(imd);
      */
    }

    /**
     * Validate an ejb-jar.xml/jboss.xml/standardjboss.xml set of metadata used to
     * obtain an ejb container configuration.
     */
    public void testCts()
            throws Exception {
        setValidateSchema(false);
        long start = System.currentTimeMillis();
        EjbJarMetaData specMetaData = unmarshal("JBoss50_testCtsEjb20Jar.xml", EjbJarMetaData.class, null);
        long end = System.currentTimeMillis();
        getLog().info("JBoss50_testCtsEjb20Jar.xml parse time = " + (end - start));
        JBossMetaData stdMetaData = unmarshal("JBoss5xEverything_testStandard.xml", JBossMetaData.class, null);
        long end2 = System.currentTimeMillis();
        getLog().info("JBoss5xEverything_testStandard.xml parse time = " + (end2 - end));
        JBossMetaData jbossXmlMetaData = unmarshal("JBoss50_testCtsJBoss32.xml", JBossMetaData.class, null);
        long end3 = System.currentTimeMillis();
        getLog().info("JBoss50_testCtsJBoss32.xml parse time = " + (end3 - end2));
        //jbossMetaData.setOverridenMetaData(specMetaData);
        // Merge the jboss.xml, ejb-jar.xml
        JBossMetaData jbossMetaData = new JBossMetaData();
        jbossMetaData.merge(jbossXmlMetaData, specMetaData);
        // Add the standardjboss.xml wrapper for defaults
        JBossMetaDataWrapper wrapper = new JBossMetaDataWrapper(jbossMetaData, stdMetaData);

        // Test the unified metadata view using the wrapper
        InvokerProxyBindingsMetaData bindings = wrapper.getInvokerProxyBindings();
        JBossEnterpriseBeansMetaData beans = wrapper.getEnterpriseBeans();
        JBossEnterpriseBeanMetaData sessionBean = beans.get("StatelessSessionBean");
        assertNotNull(sessionBean);
        for (JBossEnterpriseBeanMetaData jbean : beans) {
            ContainerConfigurationMetaData beanCfg = jbean.determineContainerConfiguration();
            assertNotNull(beanCfg);
            InvokerBindingsMetaData invokers = jbean.determineInvokerBindings();
            assertTrue(jbean.getEjbName() + " has > 0 invokers", invokers.size() > 0);
            for (InvokerBindingMetaData invoker : invokers) {
                InvokerProxyBindingMetaData ipbmd = bindings.get(invoker.getName());
                assertNotNull(invoker.getName(), ipbmd);
            }
        }
        JBossEnterpriseBeanMetaData jsessionBean = (JBossEnterpriseBeanMetaData) sessionBean;
        InvokerBindingsMetaData invokers = jsessionBean.determineInvokerBindings();
        assertTrue(sessionBean.getEjbName() + " has 1 invokers", invokers.size() > 0);

        // Test the legacy wrapper view
      /*
      ApplicationMetaData appMetaData = new ApplicationMetaData(wrapper);
      Iterator<BeanMetaData> beans2 = appMetaData.getEnterpriseBeans();
      while (beans2.hasNext())
      {
         BeanMetaData bean = (BeanMetaData) beans2.next();
         ConfigurationMetaData beanCfg = bean.getContainerConfiguration();
         assertNotNull(beanCfg);
      }
      */

        assertTrue(bindings.size() > 10);
        // Validate some know invoker bindings
        InvokerProxyBindingMetaData ssui = bindings.get("stateless-unified-invoker");
        assertNotNull(ssui);
        InvokerProxyBindingMetaData sfui = bindings.get("stateful-unified-invoker");
        assertNotNull(sfui);

      /*
      BeanMetaData beanCfg = appMetaData.getBeanByEjbName("StatelessSessionBean");
      assertNotNull(beanCfg);
      Iterator<String> it = beanCfg.getInvokerBindings();
      org.jboss.metadata.InvokerProxyBindingMetaData imd = null;
      while (it.hasNext() && imd == null)
      {
         String invoker = it.next();
         imd = beanCfg.getApplicationMetaData().getInvokerProxyBindingMetaDataByName(invoker);
         assertNotNull(imd);
      }
      assertNotNull(imd);
      */

        // Validate the merged StrictlyPooledMDB
        JBossEnterpriseBeanMetaData strictlyPooledMDB = beans.get("StrictlyPooledMDB");
        JBossMessageDrivenBeanMetaData strictlyPooledMDBMD = (JBossMessageDrivenBeanMetaData) strictlyPooledMDB;
        assertNotNull("strictlyPooledMDB", strictlyPooledMDB);
        assertEquals("Message driven pooling test", strictlyPooledMDBMD.getDescriptionGroup().getDescription());
        assertEquals("org.jboss.test.cts.ejb.StrictlyPooledMDB", strictlyPooledMDBMD.getEjbClass());
        assertTrue(strictlyPooledMDB.isMessageDriven());
        assertTrue(strictlyPooledMDB instanceof JBossMessageDrivenBeanMetaData);
        assertEquals("AUTO_ACKNOWLEDGE", strictlyPooledMDBMD.getAcknowledgeMode());
        assertEquals("javax.jms.Queue", strictlyPooledMDBMD.getMessageDestinationType());
        assertEquals(SubscriptionDurability.NonDurable, strictlyPooledMDBMD.getSubscriptionDurability());
        EnvironmentEntryMetaData maxActiveCount = strictlyPooledMDBMD.getEnvironmentEntryByName("maxActiveCount");
        assertEquals("maxActiveCountID", maxActiveCount.getId());
        assertEquals("java.lang.Integer", maxActiveCount.getType());
        assertEquals("5", maxActiveCount.getValue());
        assertEquals("queue/A", strictlyPooledMDBMD.getDestinationJndiName());
        assertEquals("Strictly Pooled Message Driven Bean", strictlyPooledMDBMD.getConfigurationName());

        // Test security-identity information
      /*
      org.jboss.metadata.SecurityIdentityMetaData secMetaData = beanCfg.getSecurityIdentityMetaData();
      assertNull(secMetaData);

      BeanMetaData RunAsStatelessSessionBean = appMetaData.getBeanByEjbName("RunAsStatelessSessionBean");
      assertNotNull(RunAsStatelessSessionBean);
      secMetaData = RunAsStatelessSessionBean.getSecurityIdentityMetaData();
      assertEquals("getUseCallerIdentity", false, secMetaData.getUseCallerIdentity());
      String roleName = secMetaData.getRunAsRoleName();
      assertEquals("RunAsStatelessSessionBean.getRunAsRoleName", "RunAsStatelessSessionBean-role-name", roleName);
      */
    }

    /**
     * Test the merged resource-ref view of the EjbJar20_testResourceRefs.xml
     * + JBoss50_testResourceRefs.xml with the merged JBossMetaData coming
     * from merging the JBoss50_testResourceRefs.xml parsed object with the
     * EjbJar20_testResourceRefs.xml EjbJar2xMetaData
     *
     * @throws Exception
     */
    public void testResourceRefsMergeSelf()
            throws Exception {
        EjbJarMetaData specData = unmarshal("EjbJar20_testResourceRefs.xml", EjbJarMetaData.class, null);
        setValidateSchema(false);
        JBossMetaData jbossMetaData = unmarshal("JBoss50_testResourceRefs.xml", JBossMetaData.class, null);
        jbossMetaData.merge(null, specData);
        validateResourceRefs(jbossMetaData);
    }

    /**
     * Test the merged resource-ref view of the EjbJar20_testREsourceRefs.xml
     * + JBoss50_testResourceRefs.xml with the merged JBossMetaData coming
     * from merging the JBoss50_testResourceRefs.xml parsed object with the
     * EjbJar20_testResourceRefs.xml EjbJar2xMetaData into a new empty
     * JBossMetaData instance.
     *
     * @throws Exception
     */
    public void testResourceRefs()
            throws Exception {
        EjbJarMetaData specData = unmarshal("EjbJar20_testResourceRefs.xml", EjbJarMetaData.class, null);
        setValidateSchema(false);
        JBossMetaData jbossXmlMetaData = unmarshal();
        JBossMetaData jbossMetaData = new JBossMetaData();
        jbossMetaData.merge(jbossXmlMetaData, specData);
        validateResourceRefs(jbossMetaData);
    }

    public void testEjbJndiName()
            throws Exception {
        setValidateSchema(true);
        JBossMetaData jbossXmlMetaData = unmarshal();
        JBossEnterpriseBeansMetaData enterpriseBeans = jbossXmlMetaData.getEnterpriseBeans();
        assertNotNull(enterpriseBeans);
        assertEquals(2, enterpriseBeans.size());
        for (JBossEnterpriseBeanMetaData bean : enterpriseBeans) {
            assertEquals("servlet_annotation_" + bean.getEjbName(), ((JBossGenericBeanMetaData) bean).getJndiName());
        }
    }

    public void testMDBActivationConfigMerge() {
        EjbJarMetaData ejbJar = new EjbJarMetaData(EjbJarVersion.EJB_3_1);
        ejbJar.setEnterpriseBeans(new EnterpriseBeansMetaData());
        GenericBeanMetaData messageBean = new GenericBeanMetaData(EjbType.MESSAGE_DRIVEN);
        messageBean.setEjbName("MyMDB");
        // Do not add an activationConfig
//      ActivationConfigMetaData activationConfig = new ActivationConfigMetaData();
//      ActivationConfigPropertiesMetaData activationConfigProperties = new ActivationConfigPropertiesMetaData();
//      ActivationConfigPropertyMetaData activationConfigProperty = new ActivationConfigPropertyMetaData();
//      activationConfigProperty.setName("aName");
//      activationConfigProperty.setValue("aValue");
//      activationConfigProperties.add(activationConfigProperty);
//      activationConfig.setActivationConfigProperties(activationConfigProperties);
//      messageBean.setActivationConfig(activationConfig);
        ejbJar.getEnterpriseBeans().add(messageBean);

        JBoss50MetaData metaData = new JBoss50MetaData();
        metaData.setEnterpriseBeans(new JBossEnterpriseBeansMetaData());
        JBossMessageDrivenBeanMetaData jMessageBean = new JBossMessageDrivenBeanMetaData();
        jMessageBean.setEjbName("MyMDB");
        jMessageBean.setDestinationJndiName("destinationJndiName");
        metaData.getEnterpriseBeans().add(jMessageBean);

        metaData.merge(null, ejbJar);

        JBossMessageDrivenBeanMetaData mergedBean = (JBossMessageDrivenBeanMetaData) metaData.getEnterpriseBean("MyMDB");
        assertNull(mergedBean.getActivationConfig());
    }

    public void testInvokerDefaultJndiName() throws Exception {
        setValidateSchema(false);

        JBossMetaData result = unmarshal();
        JBoss50DTDMetaData stdMetaData = unmarshal("JBoss5xEverything_testStandard.xml", JBoss50DTDMetaData.class, null);

        JBossEnterpriseBeanMetaData bean = result.getEnterpriseBean("SessionEjbName");
        assertNotNull(bean);
        InvokerBindingsMetaData invokerBindings = bean.getInvokerBindings();
        assertNotNull(invokerBindings);
        assertEquals(1, invokerBindings.size());
        InvokerBindingMetaData invokerBindingMetaData = invokerBindings.get("sessionInvokerProxyBindingName");
        assertNotNull(invokerBindingMetaData);
        assertEquals("sessionInvokerProxyBindingName", invokerBindingMetaData.getInvokerProxyBindingName());
        assertNull(invokerBindingMetaData.getJndiName());

      /*
      JBossMetaDataWrapper jbossWrapper = new JBossMetaDataWrapper(result, stdMetaData);
      ApplicationMetaData app = new ApplicationMetaData(jbossWrapper);
      BeanMetaData wrapper = app.getBeanByEjbName("SessionEjbName");
      assertNotNull(wrapper);
      String invokerJndiName = wrapper.getInvokerBinding("sessionInvokerProxyBindingName");
      assertEquals("SessionEjbName", invokerJndiName);
      */

        bean = result.getEnterpriseBean("MdbEjbName");
        assertNotNull(bean);
        invokerBindings = bean.getInvokerBindings();
        assertNull(invokerBindings);
        invokerBindings = bean.determineInvokerBindings();
        assertNotNull(invokerBindings);
        assertEquals(1, invokerBindings.size());
        InvokerBindingMetaData invokerBinding = invokerBindings.get(InvokerBindingMetaData.MESSAGE_DRIVEN);
        assertNotNull(invokerBinding);
        assertEquals(bean.getEjbName(), invokerBinding.getJndiName());

      /*
      wrapper = app.getBeanByEjbName("MdbEjbName");
      assertNotNull(wrapper);
      invokerJndiName = wrapper.getInvokerBinding(InvokerBindingMetaData.MESSAGE_DRIVEN);
      assertEquals(wrapper.getEjbName(), invokerJndiName);
      */
    }

    protected void validateResourceRefs(JBossMetaData jbossMetaData) {
        // Validate the resource managers
        ResourceManagersMetaData resourceMgrs = jbossMetaData.getResourceManagers();
        assertEquals(5, resourceMgrs.size());
        ResourceManagerMetaData qfmgr = resourceMgrs.get("queuefactoryref");
        assertNotNull(qfmgr);
        assertEquals("java:/JmsXA", qfmgr.getResJndiName());
        ResourceManagerMetaData qmgr = resourceMgrs.get("queueref");
        assertNotNull(qmgr);
        assertEquals("queue/testQueue", qmgr.getResJndiName());
        ResourceManagerMetaData tfmgr = resourceMgrs.get("topicfactoryref");
        assertNotNull(tfmgr);
        assertEquals("java:/JmsXA", tfmgr.getResJndiName());
        ResourceManagerMetaData tmgr = resourceMgrs.get("topicref");
        assertNotNull(tmgr);
        assertEquals("topic/testTopic", tmgr.getResJndiName());


        JBossEnterpriseBeanMetaData mdb = jbossMetaData.getEnterpriseBean("TopicPublisher");

        ResourceReferencesMetaData resources = mdb.getResourceReferences();
        ResourceReferenceMetaData jmsRef1 = resources.get("jms/MyTopicConnection");
        assertNotNull(jmsRef1);
        assertEquals("jms/MyTopicConnection", jmsRef1.getResourceRefName());
        assertEquals("javax.jms.TopicConnectionFactory", jmsRef1.getType());
        assertEquals(ResourceAuthorityType.Container, jmsRef1.getResAuth());
        assertEquals("topicfactoryref", jmsRef1.getResourceName());
        assertEquals("java:/JmsXA", jmsRef1.getJndiName());
        ResourceReferenceMetaData jmsRef2 = resources.get("jms/TopicName");
        assertNotNull(jmsRef2);
        assertEquals("jms/TopicName", jmsRef2.getResourceRefName());
        assertEquals("javax.jms.Topic", jmsRef2.getType());
        assertEquals(ResourceAuthorityType.Container, jmsRef2.getResAuth());
        assertEquals("topicref", jmsRef2.getResourceName());
        assertEquals("topic/testTopic", jmsRef2.getJndiName());
    }

    public void testPrincipalVersusRolesMap() throws Exception {
        JBossMetaData jbossXmlMetaData = unmarshal();
        JBossAssemblyDescriptorMetaData assemblyDescriptor = jbossXmlMetaData.getAssemblyDescriptor();
        assertNotNull(assemblyDescriptor);
        Map<String, Set<String>> principalVersusRolesMap = assemblyDescriptor.getPrincipalVersusRolesMap();
        assertNotNull(principalVersusRolesMap);
        Map<String, Set<String>> expected = new HashMap<String, Set<String>>();
        expected.put("principal1", toSet("test1", "test2", "test3"));
        expected.put("principal2", toSet("test2", "test3"));
        expected.put("principal3", toSet("test3"));
        assertEquals(expected, principalVersusRolesMap);
    }

    public void testEjbPortComponent() throws Exception {
        setValidateSchema(true);
        JBossMetaData jboss = unmarshal();
        JBossGenericBeanMetaData ejb = (JBossGenericBeanMetaData) jboss.getEnterpriseBean("EjbName");
        assertNotNull(ejb);
        PortComponent portComponent = ejb.getPortComponent();
        assertNotNull(portComponent);
        assertEquals("port.component.name", portComponent.getPortComponentName());
        assertEquals("port/component/uri", portComponent.getPortComponentURI());
    }

    public void testEjbthreeCacheInvalidationConfig() throws Exception {
        setValidateSchema(false);
        JBossMetaData jbossXmlMetaData = unmarshal();
        JBossEnterpriseBeansMetaData enterpriseBeans = jbossXmlMetaData.getEnterpriseBeans();
        assertNotNull(enterpriseBeans);
        assertEquals(2, enterpriseBeans.size());

        // Test that we get a proper default config when there is no
        // <cache-invalidation-config> block in jboss.xml
        JBossEntityBeanMetaData bean = (JBossEntityBeanMetaData) enterpriseBeans.get("TestEntity1");
        assertNotNull(bean);
        CacheInvalidationConfigMetaData cicmd = bean.getCacheInvalidationConfig();
        assertNull(cicmd);
        cicmd = bean.determineCacheInvalidationConfig();
        assertNotNull(cicmd);
        assertNull(cicmd.getInvalidationGroupName());
        assertNull(cicmd.getInvalidationManagerName());
        assertEquals("TestEntity1", cicmd.determineInvalidationGroupName());
        assertEquals(CacheInvalidationConfigMetaData.DEFAULT_INVALIDATION_MANAGER_NAME,
                cicmd.determineInvalidationManagerName());

        // Test that a <cache-invalidation-config> block in jboss.xml
        // is respected
        bean = (JBossEntityBeanMetaData) enterpriseBeans.get("TestEntity2");
        assertNotNull(bean);
        cicmd = bean.getCacheInvalidationConfig();
        assertNotNull(cicmd);
        CacheInvalidationConfigMetaData gotten = cicmd;
        cicmd = bean.determineCacheInvalidationConfig();
        assertEquals(gotten, cicmd);
        assertEquals("TestEntityGroup2", cicmd.getInvalidationGroupName());
        assertEquals("TestInvalidationManager", cicmd.getInvalidationManagerName());
        assertEquals("TestEntityGroup2", cicmd.determineInvalidationGroupName());
        assertEquals("TestInvalidationManager", cicmd.determineInvalidationManagerName());
    }

    public void testEjbthreeClusterConfig() throws Exception {
        setValidateSchema(true);
        JBossMetaData jbossXmlMetaData = unmarshal();
        JBossEnterpriseBeansMetaData enterpriseBeans = jbossXmlMetaData.getEnterpriseBeans();
        assertNotNull(enterpriseBeans);
        assertEquals(4, enterpriseBeans.size());

        JBossSessionBeanMetaData bean = (JBossSessionBeanMetaData) enterpriseBeans.get("SimpleBean");
        assertNotNull(bean);
        ClusterConfigMetaData clusterConfig = bean.getClusterConfig();
        assertNotNull(clusterConfig);
        assertEquals("ClusterConfigPartition", clusterConfig.getPartitionName());
        assertEquals("SimpleLBP", clusterConfig.getLoadBalancePolicy());
        assertEquals("SimpleLBP", clusterConfig.getBeanLoadBalancePolicy());
        assertNull(clusterConfig.getHomeLoadBalancePolicy());

        bean = (JBossSessionBeanMetaData) enterpriseBeans.get("HomeAndBeanBean");
        assertNotNull(bean);
        clusterConfig = bean.getClusterConfig();
        assertNotNull(clusterConfig);
        assertEquals("ClusterConfigPartition", clusterConfig.getPartitionName());
        assertEquals("BeanLBP", clusterConfig.getLoadBalancePolicy());
        assertEquals("BeanLBP", clusterConfig.getBeanLoadBalancePolicy());
        assertEquals("HomeLBP", clusterConfig.getHomeLoadBalancePolicy());

        bean = (JBossSessionBeanMetaData) enterpriseBeans.get("HomeAndSimpleBean");
        assertNotNull(bean);
        clusterConfig = bean.getClusterConfig();
        assertNotNull(clusterConfig);
        assertEquals("ClusterConfigPartition", clusterConfig.getPartitionName());
        assertEquals("SimpleLBP", clusterConfig.getLoadBalancePolicy());
        assertEquals("SimpleLBP", clusterConfig.getBeanLoadBalancePolicy());
        assertEquals("HomeLBP", clusterConfig.getHomeLoadBalancePolicy());

        bean = (JBossSessionBeanMetaData) enterpriseBeans.get("HomeOnlyBean");
        assertNotNull(bean);
        clusterConfig = bean.getClusterConfig();
        assertNotNull(clusterConfig);
        assertNull(clusterConfig.getPartitionName());
        assertNull(clusterConfig.getLoadBalancePolicy());
        assertNull(clusterConfig.getBeanLoadBalancePolicy());
        assertEquals("HomeLBP", clusterConfig.getHomeLoadBalancePolicy());
    }

    public void testJndiBindingPolicy() throws Exception {
        setValidateSchema(true);
        JBossMetaData jbossXmlMetaData = unmarshal();
        assertEquals("org.jboss.metadata.test.AppJndiBindingPolicy", jbossXmlMetaData.getJndiBindingPolicy());
        JBossEnterpriseBeansMetaData enterpriseBeans = jbossXmlMetaData.getEnterpriseBeans();
        assertNotNull(enterpriseBeans);
        assertEquals(3, enterpriseBeans.size());

        JBossEnterpriseBeanMetaData ejb = enterpriseBeans.get("EjbBean");
        assertNotNull(ejb);
        assertEquals("org.jboss.metadata.test.EjbJndiBindingPolicy", ejb.getJndiBindingPolicy());

        //ejb = enterpriseBeans.get("MdbName");
        //assertNotNull(ejb);
        //assertEquals("org.jboss.metadata.test.MdbJndiBindingPolicy", ejb.getJndiBindingPolicy());

        ejb = enterpriseBeans.get("SessionBean");
        assertNotNull(ejb);
        assertEquals("org.jboss.metadata.test.SessionJndiBindingPolicy", ejb.getJndiBindingPolicy());
    }

    public void testRunAsPrincipal() throws Exception {
        JBossMetaData result = unmarshal();
        JBossEnterpriseBeanMetaData bean = result.getEnterpriseBean("ServiceSix");
        assertNotNull(bean);
        SecurityIdentityMetaData secid = bean.getSecurityIdentity();
        assertNotNull(secid);
        assertNull(secid.getUseCallerIdentity());
        assertNull(secid.getRunAs());
        assertEquals("run as principal", secid.getRunAsPrincipal());
    }

    private <T> Set<T> toSet(T... obj) {
        Set<T> set = new HashSet<T>();
        for (T o : obj) {
            set.add(o);
        }
        return set;
    }
}
