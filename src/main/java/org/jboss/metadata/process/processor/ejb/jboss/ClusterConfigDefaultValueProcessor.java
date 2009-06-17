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
package org.jboss.metadata.process.processor.ejb.jboss;

import org.jboss.ejb3.annotation.defaults.ClusteredDefaults;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.ejb.jboss.ClusterConfigMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.process.ProcessingException;
import org.jboss.metadata.process.processor.JBossMetaDataProcessor;

/**
 * Generate a default cluster-configuration if the xml
 * specifies clustered=true only; [JBMETA-133]
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class ClusterConfigDefaultValueProcessor<T extends JBossMetaData> implements JBossMetaDataProcessor<T>
{

   // --------------------------------------------------------------------------------||
   // Class Members ------------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   /**
    * Convenience instance
    */
   public static final ClusterConfigDefaultValueProcessor<JBossMetaData> INSTANCE = new ClusterConfigDefaultValueProcessor<JBossMetaData>();

   // --------------------------------------------------------------------------------||
   // Required Implementations -------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   public T process(T metadata) throws ProcessingException
   {
      // Sanity Check
      assert metadata != null : "Specified metadata was null";

      // For each EJB
      for (JBossEnterpriseBeanMetaData ejb : metadata.getEnterpriseBeans())
      {
         // Only applies to Session beans
         if (!ejb.isSession())
         {
            continue;
         }

         // Cast
         JBossSessionBeanMetaData smd = (JBossSessionBeanMetaData) ejb;

         // Create a default cluster configuration
         if (smd.isClustered() && smd.getClusterConfig() == null)
         {
            ClusterConfigMetaData clusterConfig = new ClusterConfigMetaData();

            clusterConfig.setBeanLoadBalancePolicy(ClusteredDefaults.LOAD_BALANCE_POLICY_DEFAULT);
            clusterConfig.setHomeLoadBalancePolicy(ClusteredDefaults.LOAD_BALANCE_POLICY_DEFAULT);
            clusterConfig.setPartitionName(ClusteredDefaults.PARTITION_NAME_DEFAULT);
            clusterConfig.setDescriptions(ProcessorUtils.getDescription("cluster-config for: " + smd.getEjbClass()));

            smd.setClusterConfig(clusterConfig);
         }
      }

      // Return
      return metadata;
   }
}
