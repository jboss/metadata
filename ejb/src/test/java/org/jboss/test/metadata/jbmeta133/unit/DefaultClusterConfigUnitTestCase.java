/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.test.metadata.jbmeta133.unit;

import org.jboss.ejb3.annotation.defaults.ClusteredDefaults;
import org.jboss.metadata.ejb.jboss.ClusterConfigMetaData;
import org.jboss.metadata.ejb.jboss.JBoss50DTDMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.process.processor.JBossMetaDataProcessor;
import org.jboss.metadata.process.processor.ejb.jboss.ClusterConfigDefaultValueProcessor;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;

/**
 * [JBMETA-133] generate a default cluster-config metadata,
 * if the xml metadata only specifies clustered=true; 
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class DefaultClusterConfigUnitTestCase extends AbstractJavaEEEverythingTest
{

   public DefaultClusterConfigUnitTestCase(String name)
   {
      super(name);
   }
   
   public void testDefaultClusterConfig() throws Exception
   {
 
      JBossMetaData metadata = unmarshal("jboss.xml", JBoss50DTDMetaData.class, null);
      assertNotNull(metadata);
      
      JBossMetaDataProcessor<JBossMetaData> processor = new ClusterConfigDefaultValueProcessor<JBossMetaData>();
      processor.process(metadata);
      
      for(JBossEnterpriseBeanMetaData bean : metadata.getEnterpriseBeans())
      {
         assertTrue(bean.isSession());
         
         JBossSessionBeanMetaData smd = (JBossSessionBeanMetaData) bean;
         assertDefaultClusterConfig(smd);
      }
   }
   
   private void assertDefaultClusterConfig(JBossSessionBeanMetaData bean)
   {
      assertNotNull(bean);
      ClusterConfigMetaData clustered = bean.getClusterConfig();
      assertNotNull(clustered);
      assertEquals(ClusteredDefaults.LOAD_BALANCE_POLICY_DEFAULT, clustered.getBeanLoadBalancePolicy());
      assertEquals(ClusteredDefaults.LOAD_BALANCE_POLICY_DEFAULT, clustered.getHomeLoadBalancePolicy());
      assertEquals(ClusteredDefaults.PARTITION_NAME_DEFAULT, clustered.getPartitionName());
   }
   
}
