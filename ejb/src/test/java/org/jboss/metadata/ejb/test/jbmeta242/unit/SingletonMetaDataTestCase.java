/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.test.jbmeta242.unit;

import static org.jboss.metadata.ejb.test.common.UnmarshallingHelper.unmarshal;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.junit.Test;

/**
 * SingletonMetaDataTestCase
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class SingletonMetaDataTestCase {
    /**
     * Tests the metadata created out of ejb-jar.xml with the various possible
     * session-type values for session beans, has the correct session type set
     *
     * @throws Exception
     */
    @Test
    public void testInitOnStartup() throws Exception {
        EjbJarMetaData ejb31 = unmarshal(EjbJarMetaData.class, "/org/jboss/metadata/ejb/test/jbmeta242/singleton-beans-ejb-jar.xml");
        assertNotNull("Metadata created out of singleton-beans-ejb-jar.xml is null", ejb31);
        String initOnStartupBeanName = "InitOnStartupBean";
        String nonInitOnStartupBeanName = "NonInitOnStartupBean";
        String undefinedInitOnStartupBeanName = "UnDefinedInitOnStartupBean";

        SessionBean31MetaData initOnStartupBean = (SessionBean31MetaData) ejb31.getEnterpriseBean(initOnStartupBeanName);
        assertNotNull(initOnStartupBeanName + " bean was not available in metadata", initOnStartupBean);
        assertTrue(initOnStartupBeanName + " bean was not considered init-on-startup", initOnStartupBean.isInitOnStartup());

        SessionBean31MetaData nonInitOnStartupBean = (SessionBean31MetaData) ejb31.getEnterpriseBean(nonInitOnStartupBeanName);
        assertNotNull(nonInitOnStartupBeanName + " bean was not available in metadata", nonInitOnStartupBean);
        assertFalse(nonInitOnStartupBeanName + " bean was considered init-on-startup", nonInitOnStartupBean.isInitOnStartup());

        SessionBean31MetaData undefinedInitOnStartupBean = (SessionBean31MetaData) ejb31.getEnterpriseBean(undefinedInitOnStartupBeanName);
        assertNotNull(undefinedInitOnStartupBeanName + " bean was not available in metadata", undefinedInitOnStartupBean);
        assertNull(undefinedInitOnStartupBeanName + " bean had non-null init-on-startup", undefinedInitOnStartupBean.isInitOnStartup());

    }
}
