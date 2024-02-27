/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.web;

import org.jboss.metadata.parser.jbossweb.JBossWebMetaDataParser;
import org.jboss.metadata.property.PropertyReplacers;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.metadata.web.jboss.ReplicationConfig;
import org.jboss.metadata.web.jboss.ReplicationGranularity;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;

/**
 * @author Paul Ferraro
 */
public class JBossWeb60UnitTestCase extends AbstractJavaEEEverythingTest {
    public void testClustering() throws Exception {
//      System.out.println("JBossWeb60UnitTestCase.java skipped");
        JBossWebMetaData jbossWeb = JBossWebMetaDataParser.parse(getReader(), PropertyReplacers.noop());
        ReplicationConfig replConfig = jbossWeb.getReplicationConfig();
        assertNotNull(replConfig);
        assertEquals("testCache", replConfig.getCacheName());
        assertSame(ReplicationGranularity.SESSION, replConfig.getReplicationGranularity());

        assertNotNull(jbossWeb.getMaxActiveSessions());
        assertEquals(20, jbossWeb.getMaxActiveSessions().intValue());
    }
}
