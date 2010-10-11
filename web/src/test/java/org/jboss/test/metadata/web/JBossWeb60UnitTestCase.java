/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.test.metadata.web;

import org.jboss.metadata.web.jboss.JBoss60WebMetaData;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.metadata.web.jboss.PassivationConfig;
import org.jboss.metadata.web.jboss.ReplicationConfig;
import org.jboss.metadata.web.jboss.ReplicationGranularity;
import org.jboss.metadata.web.jboss.ReplicationMode;
import org.jboss.metadata.web.jboss.ReplicationTrigger;
import org.jboss.metadata.web.jboss.SnapshotMode;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;

/**
 * @author Paul Ferraro
 */
public class JBossWeb60UnitTestCase extends AbstractJavaEEEverythingTest
{
   public JBossWeb60UnitTestCase(String name)
   {
      super(name);
   }

   public void testClustering() throws Exception
   {
      JBossWebMetaData jbossWeb = unmarshal();
      
      ReplicationConfig replConfig = jbossWeb.getReplicationConfig();
      assertNotNull(replConfig);
      assertEquals("testCache", replConfig.getCacheName());
      assertSame(ReplicationTrigger.SET, replConfig.getReplicationTrigger());
      assertSame(ReplicationGranularity.SESSION, replConfig.getReplicationGranularity());
      // Not parsed in AS6
      assertNull(replConfig.getReplicationFieldBatchMode());
      assertSame(ReplicationMode.SYNCHRONOUS, replConfig.getReplicationMode());
      assertNotNull(replConfig.getBackups());
      assertEquals(2, replConfig.getBackups().intValue());
      assertNotNull(replConfig.getUseJK());
      assertTrue(replConfig.getUseJK().booleanValue());
      assertNotNull(replConfig.getMaxUnreplicatedInterval());
      assertEquals(30, replConfig.getMaxUnreplicatedInterval().intValue());
      assertSame(SnapshotMode.INSTANT, replConfig.getSnapshotMode());
      assertNotNull(replConfig.getSnapshotInterval());
      assertEquals(5, replConfig.getSnapshotInterval().intValue());
      assertEquals("org.jboss.test.TestNotificationPolicy", replConfig.getSessionNotificationPolicy());
      
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
   
   protected JBossWebMetaData unmarshal() throws Exception
   {
      return unmarshal(JBoss60WebMetaData.class);
   }
}
