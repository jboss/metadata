/*
* JBoss, Home of Professional Open Source
* Copyright 2005, JBoss Inc., and individual contributors as indicated
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
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
package org.jboss.metadata;

import org.jboss.metadata.spi.MetaData;

/**
 * The meta data object for the cluster-config element.
 * This element only defines the HAPartition name at this time.  It will be
 * expanded to include other cluster configuration parameters later on.
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>.
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 * @version $Revision: 37390 $
 */
@Deprecated
public class ClusterConfigMetaData extends OldMetaData<org.jboss.metadata.ejb.jboss.ClusterConfigMetaData>
{
   public final static String JNDI_PREFIX_FOR_SESSION_STATE = "/HASessionState/";
   public final static String DEFAULT_SESSION_STATE_NAME = JNDI_PREFIX_FOR_SESSION_STATE + "Default";

   /**
    * Create a new ClusterConfigMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public ClusterConfigMetaData(org.jboss.metadata.ejb.jboss.ClusterConfigMetaData delegate)
   {
      super(delegate);
   }
   
   /**
    * Create a new ClusterConfigMetaData.
    * 
    * @param metaData the delegate metadata
    * @throws IllegalArgumentException for a null metaData
    * @throws IllegalStateException if the metadata doesn't have an {@link org.jboss.metadata.ejb.jboss.ClusterConfigMetaData}
    */
   protected ClusterConfigMetaData(MetaData metaData)
   {
      super(metaData, org.jboss.metadata.ejb.jboss.ClusterConfigMetaData.class);
   }

   /**
    * Get the partition name
    * 
    * @return the partition name
    */
   public String getPartitionName()
   {
      return getDelegate().getPartitionName();
   }

   /**
    * Get the home load balancing policy
    * 
    * @return the home load balancing policy
    */
   public String getHomeLoadBalancePolicy()
   {
      return getDelegate().getHomeLoadBalancePolicy();
   }

   /**
    * Get the bean load balancing policy
    * 
    * @return the bean load balancing policy
    */
   public String getBeanLoadBalancePolicy()
   {
      return getDelegate().getBeanLoadBalancePolicy();
   }

   /**
    * Get the ha session state name
    * 
    * @return the ha session state name
    */
   public String getHaSessionStateName()
   {
      return getDelegate().getSessionStateManagerJndiName();
   }
}
