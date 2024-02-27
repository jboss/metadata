/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.ejb;

import junit.framework.TestCase;
import org.jboss.metadata.common.ejb.JBossEnvironmentRefsGroupMetaData;
import org.jboss.metadata.common.ejb.ResourceManagerMetaData;
import org.jboss.metadata.common.ejb.ResourceManagersMetaData;
import org.jboss.metadata.javaee.spec.ResourceAuthorityType;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.merge.ejb.jboss.JBossEnvironmentRefsGroupMetaDataMerger;

/**
 * A JBossEnvironmentRefsGroupOverrideUnitTestCase.
 *
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class JBossEnvironmentRefsGroupOverrideUnitTestCase extends TestCase {
    public void testJndiNameForResourceReferences() {
        JBossEnvironmentRefsGroupMetaData spec = new JBossEnvironmentRefsGroupMetaData();
        ResourceReferencesMetaData refs = new ResourceReferencesMetaData();
        spec.setResourceReferences(refs);
        ResourceReferenceMetaData ref = new ResourceReferenceMetaData();
        ref.setResourceRefName("jms/MyQueueConnection");
        ref.setType("jakarta.jms.QueueConnectionFactory");
        ref.setResAuth(ResourceAuthorityType.Container);
        refs.add(ref);
        ref = new ResourceReferenceMetaData();
        ref.setResourceRefName("jms/QueueName");
        ref.setType("jakarta.jms.Queue");
        ref.setResAuth(ResourceAuthorityType.Container);
        refs.add(ref);

        JBossEnvironmentRefsGroupMetaData jboss = new JBossEnvironmentRefsGroupMetaData();
        refs = new ResourceReferencesMetaData();
        jboss.setResourceReferences(refs);
        ref = new ResourceReferenceMetaData();
        ref.setResourceRefName("jms/MyQueueConnection");
        ref.setResourceName("queuefactoryref");
        refs.add(ref);
        ref = new ResourceReferenceMetaData();
        ref.setResourceRefName("jms/QueueName");
        ref.setResourceName("queueref");
        refs.add(ref);

        ResourceManagersMetaData rms = new ResourceManagersMetaData();
        ResourceManagerMetaData rm = new ResourceManagerMetaData();
        rm.setResName("queuefactoryref");
        rm.setResJndiName("java:/JmsXA");
        rms.add(rm);
        rm = new ResourceManagerMetaData();
        rm.setResName("queueref");
        rm.setResJndiName("queue/testQueue");
        rms.add(rm);

        JBossEnvironmentRefsGroupMetaData merged = new JBossEnvironmentRefsGroupMetaData();
        JBossEnvironmentRefsGroupMetaDataMerger.merge(merged, jboss, spec, rms);
        refs = merged.getResourceReferences();
        assertNotNull(refs);
        assertEquals(2, refs.size());
        ref = refs.get("jms/MyQueueConnection");
        assertNotNull(ref);
        assertEquals("jakarta.jms.QueueConnectionFactory", ref.getType());
        assertEquals(ResourceAuthorityType.Container, ref.getResAuth());
        assertEquals("queuefactoryref", ref.getResourceName());
        assertEquals("java:/JmsXA", ref.getJndiName());
        ref = refs.get("jms/QueueName");
        assertNotNull(ref);
        assertEquals("jakarta.jms.Queue", ref.getType());
        assertEquals(ResourceAuthorityType.Container, ref.getResAuth());
        assertEquals("queueref", ref.getResourceName());
        assertEquals("queue/testQueue", ref.getJndiName());
    }
}
