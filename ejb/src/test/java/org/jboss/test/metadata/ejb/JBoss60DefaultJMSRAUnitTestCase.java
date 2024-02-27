/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.ejb;

import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossMessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;

/**
 * A JBoss60DefaultJMSRAUnitTestCase.
 *
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class JBoss60DefaultJMSRAUnitTestCase extends AbstractEJBEverythingTest {
    public JBoss60DefaultJMSRAUnitTestCase(String name) {
        super(name);
    }

    protected JBossMetaData unmarshal() throws Exception {
        return unmarshal(JBossMetaData.class);
    }

    public void testDefaultJMSRA() throws Exception {
        JBossMetaData metadata = unmarshal();
        assertEquals("default-jms-ra.rar", metadata.getJMSResourceAdapter());

        JBossEnterpriseBeansMetaData beans = metadata.getEnterpriseBeans();
        assertNotNull(beans);
        assertEquals(2, beans.size());

        JBossEnterpriseBeanMetaData bean = metadata.getEnterpriseBean("MDBWithTheDefaultRA");
        assertNotNull(bean);
        JBossMessageDrivenBeanMetaData mdb = (JBossMessageDrivenBeanMetaData) bean;
        assertEquals(metadata.getJMSResourceAdapter(), mdb.getResourceAdapterName());

        bean = metadata.getEnterpriseBean("MDBWithNonDefaultRA");
        assertNotNull(bean);
        mdb = (JBossMessageDrivenBeanMetaData) bean;
        assertEquals("non-default-ra.rar", mdb.getResourceAdapterName());
    }
}
