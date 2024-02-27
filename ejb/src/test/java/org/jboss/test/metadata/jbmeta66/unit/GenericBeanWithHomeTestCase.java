/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.jbmeta66.unit;

import org.jboss.metadata.ejb.jboss.JBossGenericBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.test.metadata.ejb.AbstractEJBEverythingTest;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class GenericBeanWithHomeTestCase extends AbstractEJBEverythingTest {
    public GenericBeanWithHomeTestCase(String name) {
        super(name);
    }

    public void testMerge() throws Exception {
        EjbJarMetaData specMetaData = unmarshal("ejb-jar.xml", EjbJarMetaData.class);
        JBossMetaData metaData = unmarshal("jboss.xml", JBossMetaData.class);

        JBossMetaData mergedMetaData = new JBossMetaData();
        mergedMetaData.merge(metaData, specMetaData);

        JBossSessionBeanMetaData bean = (JBossSessionBeanMetaData) mergedMetaData.getEnterpriseBean("TestBean");
        assertEquals("TestBeanHomeJndiName", bean.getHomeJndiName());
        assertEquals("TestBeanLocalHomeJndiName", bean.getLocalHomeJndiName());
    }

    public void testParse() throws Exception {
        JBossMetaData jboss = unmarshal("jboss.xml", JBossMetaData.class);
        JBossGenericBeanMetaData bean = (JBossGenericBeanMetaData) jboss.getEnterpriseBean("TestBean");
        assertEquals("TestBeanHomeJndiName", bean.getHomeJndiName());
        assertEquals("TestBeanLocalHomeJndiName", bean.getLocalHomeJndiName());
    }
}
