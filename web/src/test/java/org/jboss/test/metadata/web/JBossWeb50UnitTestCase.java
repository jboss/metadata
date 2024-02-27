/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.web;

import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;


/**
 * Miscellaneous tests with a JBoss 5 jboss-web.xml.
 *
 * @author Brian Stansberry
 * @author Scott.Stark@jboss.org
 * @version $Revision: 88255 $
 */
public class JBossWeb50UnitTestCase extends AbstractJavaEEEverythingTest {

    public void testClustering() throws Exception {
        System.out.println("JBossWeb50UnitTestCase: testClustering skipped");
    }
/*
      //enableTrace("org.jboss.xb.builder");
      JBossWebMetaData jbossWeb = JBossWebMetaDataParser.parse(getReader());

      ReplicationConfig replConfig = jbossWeb.getReplicationConfig();
      assertNotNull(replConfig);
      assertEquals("testCache", replConfig.getCacheName());
      assertEquals(ReplicationTrigger.SET, replConfig.getReplicationTrigger());
      assertEquals(ReplicationGranularity.FIELD, replConfig.getReplicationGranularity());
      assertNotNull(replConfig.getReplicationFieldBatchMode());
      assertTrue(replConfig.getReplicationFieldBatchMode().booleanValue());
      assertNotNull(replConfig.getUseJK());
      assertTrue(replConfig.getUseJK().booleanValue());
      assertNotNull(replConfig.getMaxUnreplicatedInterval());
      assertEquals(30, replConfig.getMaxUnreplicatedInterval().intValue());
      assertEquals(SnapshotMode.INTERVAL, replConfig.getSnapshotMode());
      assertNotNull(replConfig.getSnapshotInterval());
      assertEquals(5, replConfig.getSnapshotInterval().intValue());
      assertEquals("org.jboss.test.TestNotificationPolicy", replConfig.getSessionNotificationPolicy());
      assertNull(replConfig.getReplicationMode());
      assertNull(replConfig.getBackups());

      assertNotNull(jbossWeb.getMaxActiveSessions());
      assertEquals(20, jbossWeb.getMaxActiveSessions().intValue());

      PassivationConfig passConfig = jbossWeb.getPassivationConfig();
      assertNotNull(passConfig);
      assertNotNull(passConfig.getUseSessionPassivation());
      assertTrue(passConfig.getUseSessionPassivation().booleanValue());
      assertNotNull(passConfig.getPassivationMinIdleTime());
      assertEquals(2, passConfig.getPassivationMinIdleTime().intValue());
      assertNotNull(passConfig.getPassivationMaxIdleTime());
      assertEquals(5, passConfig.getPassivationMaxIdleTime().intValue());

   }

   public void testClassLoading()
      throws Exception
   {
      JBossWebMetaData jbossWeb = JBossWebMetaDataParser.parse(getReader());
      ClassLoadingMetaData classLoading = jbossWeb.getClassLoading();
      assertNotNull(classLoading);
      assertEquals(true, classLoading.isJava2ClassLoadingCompliance());
      assertEquals(true, classLoading.wasJava2ClassLoadingComplianceSet());
   }

   public void testClassLoading42()
      throws Exception
   {
      JBossWebMetaData jbossWeb = JBossWebMetaDataParser.parse(getReader());
      ClassLoadingMetaData classLoading = jbossWeb.getClassLoading();
      assertNotNull(classLoading);
      assertEquals(false, classLoading.isJava2ClassLoadingCompliance());
      assertEquals(false, classLoading.wasJava2ClassLoadingComplianceSet());
      LoaderRepositoryMetaData lrmd = classLoading.getLoaderRepository();
      assertNull(lrmd.getName());
   }

   public void testIsJaacAllStoreRole() throws Exception
   {

      JBossWebMetaData jbossWeb = JBossWebMetaDataParser.parse(getReader());

      assertNotNull(jbossWeb);
      assertTrue(jbossWeb.isJaccAllStoreRole());

   }
 */
}
