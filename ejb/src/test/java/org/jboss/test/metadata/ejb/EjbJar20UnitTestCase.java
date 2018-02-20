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

import java.util.HashSet;

import junit.framework.Test;
import org.jboss.metadata.ejb.common.IEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.common.IEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.CommitOption;
import org.jboss.metadata.ejb.jboss.ContainerConfigurationMetaData;
import org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaDataWrapper;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EntityBeanMetaData;
import org.jboss.metadata.ejb.spec.MessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.spec.SubscriptionDurability;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.ResourceAuthorityType;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;

/**
 * 2.0 ejb-jar.xml tests.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 88255 $
 */
@SuppressWarnings("deprecation")
public class EjbJar20UnitTestCase extends AbstractEJBEverythingTest {
    public static Test suite() {
        return suite(EjbJar20UnitTestCase.class);
    }

    public EjbJar20UnitTestCase(String name) {
        super(name);
    }

    protected EjbJarMetaData unmarshal() throws Exception {
        return unmarshal(EjbJarMetaData.class);
    }

    public void testVersion()
            throws Exception {
        EjbJarMetaData result = unmarshal();
        assertEquals("2.0", result.getVersion());
        assertFalse(result.isEJB1x());
        assertTrue(result.isEJB2x());
        assertFalse(result.isEJB21());
        assertFalse(result.isEJB3x());

      /*
      ApplicationMetaData old = new ApplicationMetaData(result);
      assertFalse(old.isEJB1x());
      assertTrue(old.isEJB2x());
      assertFalse(old.isEJB21());
      assertFalse(old.isEJB3x());
      */
    }

    public void testMDB()
            throws Exception {
        EjbJarMetaData result = unmarshal();
        assertEquals("2.0", result.getVersion());

        IEnterpriseBeansMetaData beans = result.getEnterpriseBeans();
        IEnterpriseBeanMetaData strictlyPooledMDB = beans.get("StrictlyPooledMDB");
        assertNotNull("strictlyPooledMDB", strictlyPooledMDB);
        assertEquals("strictlyPooledMDB-id", strictlyPooledMDB.getId());
        assertEquals("Message driven pooling test", strictlyPooledMDB.getDescriptionGroup().getDescription());
        assertEquals("org.jboss.test.cts.ejb.StrictlyPooledMDB", strictlyPooledMDB.getEjbClass());
        assertTrue(strictlyPooledMDB.isMessageDriven());
        assertTrue(strictlyPooledMDB instanceof MessageDrivenBeanMetaData);
        MessageDrivenBeanMetaData strictlyPooledMDBMD = (MessageDrivenBeanMetaData) strictlyPooledMDB;
        assertEquals("AUTO_ACKNOWLEDGE", strictlyPooledMDBMD.getAcknowledgeMode());
        assertEquals("javax.jms.Queue", strictlyPooledMDBMD.getMessageDestinationType());
        assertEquals(SubscriptionDurability.NonDurable, strictlyPooledMDBMD.getSubscriptionDurability());
        EnvironmentEntryMetaData maxActiveCount = strictlyPooledMDBMD.getEnvironmentEntryByName("maxActiveCount");
        assertEquals("maxActiveCountID", maxActiveCount.getId());
        assertEquals("java.lang.Integer", maxActiveCount.getType());
        assertEquals("5", maxActiveCount.getValue());

        IEnterpriseBeanMetaData subclassMDB = beans.get("SubclassMDB");
        assertNotNull("strictlyPooledMDB", subclassMDB);
        assertEquals("SubclassMDB-id", subclassMDB.getId());
        assertEquals("Message driven pooling test", subclassMDB.getDescriptionGroup().getDescription());
        assertEquals("org.jboss.test.cts.ejb.ConcreteMDB", subclassMDB.getEjbClass());
        assertTrue(subclassMDB.isMessageDriven());
        MessageDrivenBeanMetaData mdbMD2 = (MessageDrivenBeanMetaData) subclassMDB;
        assertEquals("AUTO_ACKNOWLEDGE", mdbMD2.getAcknowledgeMode());
        assertEquals("javax.jms.Topic", mdbMD2.getMessageDestinationType());
        assertEquals(SubscriptionDurability.Durable, mdbMD2.getSubscriptionDurability());

      /*
      ApplicationMetaData legacyMD = new ApplicationMetaData(result);
      BeanMetaData strictlyPooledMDB2 = legacyMD.getBeanByEjbName("StrictlyPooledMDB");
      assertNotNull(strictlyPooledMDB2);
      assertTrue(strictlyPooledMDB2.getClass()+" instanceof MessageDrivenMetaData", strictlyPooledMDB2 instanceof MessageDrivenMetaData);
      MessageDrivenMetaData strictlyPooledMDMD = (MessageDrivenMetaData) strictlyPooledMDB2;
      assertEquals("javax.jms.Queue", strictlyPooledMDMD.getDestinationType());
      */
    }

    public void testResourceRefs()
            throws Exception {
        EjbJarMetaData result = unmarshal();
        IEnterpriseBeanMetaData mdb = result.getEnterpriseBeans().get("TopicPublisher");
        ResourceReferencesMetaData resources = mdb.getResourceReferences();
        ResourceReferenceMetaData jmsRef1 = resources.get("jms/MyTopicConnection");
        assertNotNull(jmsRef1);
        assertEquals("jms/MyTopicConnection", jmsRef1.getResourceRefName());
        assertEquals("javax.jms.TopicConnectionFactory", jmsRef1.getType());
        assertEquals(ResourceAuthorityType.Container, jmsRef1.getResAuth());
        ResourceReferenceMetaData jmsRef2 = resources.get("jms/TopicName");
        assertNotNull(jmsRef2);
        assertEquals("jms/TopicName", jmsRef2.getResourceRefName());
        assertEquals("javax.jms.Topic", jmsRef2.getType());
        assertEquals(ResourceAuthorityType.Container, jmsRef2.getResAuth());
    }

    /**
     * Validate the entity bean configuration seen when combining ejb-jar.xml,
     * jboss.xml and standardjboss.xml
     *
     * @throws Exception
     */
    public void testEntity()
            throws Exception {
        EjbJarMetaData specMetaData = unmarshal();
        JBossMetaData jbossMetaData = unmarshal("JBoss30_entityConfig.xml", JBossMetaData.class, null);
        JBossMetaData stdMetaData = unmarshal("JBoss5xEverything_testStandard.xml", JBossMetaData.class, null);
        JBossMetaData mergedMetaData = new JBossMetaData();
        mergedMetaData.merge(jbossMetaData, specMetaData);
        JBossMetaDataWrapper wrapper = new JBossMetaDataWrapper(mergedMetaData, stdMetaData);

        ContainerConfigurationMetaData conf = wrapper.getContainerConfiguration("TestEntity Container Configuration");
        assertNotNull(conf);
        assertEquals("TestEntity Container Configuration", conf.getContainerName());
        assertEquals("Standard CMP 2.x EntityBean", conf.getExtendsName());

        assertEquals("org.jboss.ejb.plugins.PerTxEntityInstanceCache", conf.getInstanceCache());
        assertEquals("org.jboss.ejb.plugins.EntityInstancePool", conf.getInstancePool());
        assertEquals("org.jboss.ejb.plugins.cmp.jdbc.JDBCStoreManager", conf.getPersistenceManager());
        assertEquals("org.jboss.ejb.plugins.lock.NoLock", conf.getLockingPolicy());
        assertEquals("org.jboss.web.WebClassLoader", conf.getWebClassLoader());
        assertEquals("entity-unified-invoker", conf.getDefaultInvokerName());
        assertEquals(CommitOption.B, conf.getCommitOption());
        assertEquals(30000, conf.getOptiondRefreshRateMillis());

        ContainerConfigurationMetaData conf2 = wrapper.getContainerConfiguration("Clustered CMP EntityBean");
        assertEquals("org.jboss.ejb.plugins.EntityInstanceCache", conf2.getInstanceCache());
        assertEquals("org.jboss.ejb.plugins.EntityInstancePool", conf2.getInstancePool());
        assertEquals("org.jboss.ejb.plugins.cmp.jdbc.JDBCStoreManager", conf2.getPersistenceManager());
        assertEquals("org.jboss.ejb.plugins.lock.QueuedPessimisticEJBLock", conf2.getLockingPolicy());
        assertEquals("org.jboss.web.WebClassLoader", conf2.getWebClassLoader());
        assertEquals("clustered-entity-unified-invoker", conf2.getDefaultInvokerName());
        HashSet<String> conf2Names = new HashSet<String>();
        conf2Names.add("clustered-entity-unified-invoker");
        assertEquals(conf2Names, conf2.getInvokerProxyBindingNames());
        assertEquals(CommitOption.A, conf2.getCommitOption());
        assertEquals(null, conf2.getSecurityDomain());
        assertNotNull(conf2.getClusterConfig());
        assertEquals("DefaultPartition", conf2.getClusterConfig().getPartitionName());

        assertTrue(specMetaData.isEJB2x());
        EntityBeanMetaData entity = (EntityBeanMetaData) specMetaData.getEnterpriseBean("TestEntity");
        assertNotNull(entity);
        assertFalse(entity.isCMP1x());

        entity = (EntityBeanMetaData) specMetaData.getEnterpriseBean("TestEntityCmp1");
        assertNotNull(entity);
        assertTrue(entity.isCMP1x());

        JBossEntityBeanMetaData jbe = (JBossEntityBeanMetaData) mergedMetaData.getEnterpriseBean("TestEntity");
        assertNotNull(jbe);
        assertFalse(jbe.isCMP1x());

        jbe = (JBossEntityBeanMetaData) mergedMetaData.getEnterpriseBean("TestEntityCmp1");
        assertNotNull(jbe);
        assertTrue(jbe.isCMP1x());
    }

    /**
     * Validate the entity bean configuration seen when combining ejb-jar.xml,
     * jboss.xml and standardjboss.xml
     *
     * @throws Exception
     */
    public void testRedefinedContainer()
            throws Exception {
        EjbJarMetaData specMetaData = unmarshal();
        JBossMetaData jbossMetaData = unmarshal("JBoss32_redefinedContainer.xml", JBossMetaData.class, null);
        JBossMetaData stdMetaData = unmarshal("JBoss5xEverything_testStandard.xml", JBossMetaData.class, null);
        JBossMetaData mergedMetaData = new JBossMetaData();
        mergedMetaData.merge(jbossMetaData, specMetaData);
        JBossMetaDataWrapper wrapper = new JBossMetaDataWrapper(mergedMetaData, stdMetaData);

        ContainerConfigurationMetaData conf = wrapper.getContainerConfiguration("Standard CMP 2.x EntityBean");
        assertNotNull(conf);
        assertEquals("Standard CMP 2.x EntityBean", conf.getContainerName());

        assertEquals("org.jboss.ejb.plugins.PerTxEntityInstanceCache", conf.getInstanceCache());
        assertEquals("org.jboss.ejb.plugins.EntityInstancePool", conf.getInstancePool());
        assertEquals("org.jboss.ejb.plugins.cmp.jdbc.JDBCStoreManager", conf.getPersistenceManager());
        assertEquals("org.jboss.ejb.plugins.lock.NoLock", conf.getLockingPolicy());
        assertEquals("org.jboss.web.WebClassLoader", conf.getWebClassLoader());
        assertEquals("entity-unified-invoker", conf.getDefaultInvokerName());
        assertEquals(CommitOption.C, conf.getCommitOption());
        assertEquals(30000, conf.getOptiondRefreshRateMillis());

        assertNotNull(conf.getDepends());
        assertEquals(1, conf.getDepends().size());
        assertTrue(conf.getDepends().contains("test:name=Test"));

        assertTrue(specMetaData.isEJB2x());
        EntityBeanMetaData entity = (EntityBeanMetaData) specMetaData.getEnterpriseBean("TestEntity");
        assertNotNull(entity);
        assertFalse(entity.isCMP1x());
    }

    public void testOneMany()
            throws Exception {
        EjbJarMetaData specMetaData = unmarshal();

    }
}
