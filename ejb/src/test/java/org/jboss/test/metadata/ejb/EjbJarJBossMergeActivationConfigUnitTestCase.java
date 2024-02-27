/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.ejb;

import junit.framework.Test;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertiesMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertyMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;

/**
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class EjbJarJBossMergeActivationConfigUnitTestCase
        extends AbstractEJBEverythingTest {
    public static Test suite() {
        return suite(EjbJarJBossMergeActivationConfigUnitTestCase.class);
    }

    public EjbJarJBossMergeActivationConfigUnitTestCase(String name) {
        super(name);
    }

    public void testEJB3xMergeActivationConfig() throws Exception {
        EjbJarMetaData ejbJarMetaData = unmarshal("EjbJar3xMergeActivationConfig.xml", EjbJarMetaData.class, null);
        JBoss50MetaData jbossMetaData = unmarshal("JBoss5xMergeActivationConfig.xml", JBoss50MetaData.class, null);
        jbossMetaData.merge(jbossMetaData, ejbJarMetaData);
        testMergeActivationConfig(jbossMetaData);
    }

    public void testEJB21MergeActivationConfig() throws Exception {
        EjbJarMetaData ejbJarMetaData = unmarshal("EjbJar21MergeActivationConfig.xml", EjbJarMetaData.class, null);
        JBoss50MetaData jbossMetaData = unmarshal("JBoss5xMergeActivationConfig.xml", JBoss50MetaData.class, null);
        jbossMetaData.merge(jbossMetaData, ejbJarMetaData);
        testMergeActivationConfig(jbossMetaData);
    }

    protected void testMergeActivationConfig(JBoss50MetaData jbossMetaData) {
        JBossEnterpriseBeanMetaData enterpriseBeanMetaData = jbossMetaData.getEnterpriseBean("testmdb");
        assertNotNull(enterpriseBeanMetaData);
        assertTrue(enterpriseBeanMetaData.isMessageDriven());
        JBossMessageDrivenBeanMetaData messageDrivenBeanMetaData = (JBossMessageDrivenBeanMetaData) enterpriseBeanMetaData;
        ActivationConfigMetaData activationConfigMetaData = messageDrivenBeanMetaData.getActivationConfig();
        assertNotNull(activationConfigMetaData);
        ActivationConfigPropertiesMetaData properties = activationConfigMetaData.getActivationConfigProperties();
        assertNotNull(properties);
        assertEquals(6, properties.size());
        assertProperty(properties, "ejbjar1", "FromEJBJAR1");
        assertProperty(properties, "ejbjar2", "FromEJBJAR2");
        assertProperty(properties, "jboss1", "FromJBoss1");
        assertProperty(properties, "jboss2", "FromJBoss2");
        assertProperty(properties, "shared1", "FromJBossShared1");
        assertProperty(properties, "shared2", "FromJBossShared2");
    }

    protected void assertProperty(ActivationConfigPropertiesMetaData properties, String key, String value) {
        ActivationConfigPropertyMetaData property = properties.get(key);
        assertNotNull("Expected property: " + key, property);
        assertEquals(value, property.getValue());
    }
}
