/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.ejb;

import junit.framework.TestCase;
import org.jboss.metadata.ejb.jboss.ContainerConfigurationMetaData;
import org.jboss.metadata.ejb.jboss.ContainerConfigurationsMetaData;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.common.ejb.ResourceManagerMetaData;
import org.jboss.metadata.common.ejb.ResourceManagersMetaData;

/**
 * A JBossMetaDataOverrideUnitTestCase.
 *
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class JBossMetaDataOverrideUnitTestCase extends TestCase {
    public void testContainerConfigurations() throws Exception {
        JBoss50MetaData original = new JBoss50MetaData();
        ContainerConfigurationsMetaData ccs = new ContainerConfigurationsMetaData();
        original.setContainerConfigurations(ccs);
        ContainerConfigurationMetaData cc = new ContainerConfigurationMetaData();
        cc.setContainerName("original1");
        cc.setSecurityDomain("originalDomain1");
        ccs.add(cc);
        cc = new ContainerConfigurationMetaData();
        cc.setContainerName("override1");
        cc.setSecurityDomain("originalDomain2");
        ccs.add(cc);

        JBoss50MetaData override = new JBoss50MetaData();
        ccs = new ContainerConfigurationsMetaData();
        override.setContainerConfigurations(ccs);
        cc = new ContainerConfigurationMetaData();
        cc.setContainerName("override1");
        cc.setSecurityDomain("overrideDomain1");
        ccs.add(cc);
        cc = new ContainerConfigurationMetaData();
        cc.setContainerName("override2");
        cc.setSecurityDomain("overrideDomain2");
        ccs.add(cc);

        JBoss50MetaData merged = new JBoss50MetaData();
        merged.merge(override, original);
        ccs = merged.getContainerConfigurations();
        assertNotNull(ccs);
        assertEquals(3, ccs.size());
        cc = ccs.get("original1");
        assertNotNull(cc);
        assertEquals("originalDomain1", cc.getSecurityDomain());
        cc = ccs.get("override1");
        assertNotNull(cc);
        assertEquals("overrideDomain1", cc.getSecurityDomain());
        cc = ccs.get("override2");
        assertNotNull(cc);
        assertEquals("overrideDomain2", cc.getSecurityDomain());
    }

    public void testResourceManagers() throws Exception {
        JBoss50MetaData original = new JBoss50MetaData();
        ResourceManagersMetaData rms = new ResourceManagersMetaData();
        original.setResourceManagers(rms);
        ResourceManagerMetaData rm = new ResourceManagerMetaData();
        rm.setResName("original1");
        rms.add(rm);
        rm = new ResourceManagerMetaData();
        rm.setResName("original2");
        rms.add(rm);

        JBoss50MetaData override = new JBoss50MetaData();
        rms = new ResourceManagersMetaData();
        override.setResourceManagers(rms);
        rm = new ResourceManagerMetaData();
        rm.setResName("override1");
        rms.add(rm);
        rm = new ResourceManagerMetaData();
        rm.setResName("override2");
        rms.add(rm);

        JBoss50MetaData merged = new JBoss50MetaData();
        merged.merge(override, original);
        rms = merged.getResourceManagers();
        assertNotNull(rms);
        assertEquals(4, rms.size());
        System.out.println("rms: " + rms.keySet());
        assertTrue(rms.containsKey("original1"));
        assertTrue(rms.containsKey("original2"));
        assertTrue(rms.containsKey("override1"));
        assertTrue(rms.containsKey("override2"));
    }
}
