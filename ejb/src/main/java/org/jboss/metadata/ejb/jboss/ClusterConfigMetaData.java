/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplWithDescriptionsMerger;

/**
 * ClusterConfigMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ClusterConfigMetaData extends IdMetaDataImplWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -614188513386783204L;

    /**
     * The partition name
     */
    private String partitionName;

    /**
     * The home load balancing policy
     */
    private String homeLoadBalancePolicy;

    /**
     * The bean load balancing policy
     */
    private String beanLoadBalancePolicy;

    /**
     * The state manager jndi
     */
    private String sessionStateManagerJndiName;

    /**
     * Get the bean LoadBalancePolicy.
     *
     * @return the beanLoadBalancingPolicy.
     */
    public String getBeanLoadBalancePolicy() {
        return beanLoadBalancePolicy;
    }

    /**
     * Set the bean LoadBalancePolicy.
     *
     * @param beanLoadBalancePolicy the beanLoadBalancingPolicy.
     * @throws IllegalArgumentException for a null beanLoadBalancingPolicy
     */
    public void setBeanLoadBalancePolicy(String beanLoadBalancePolicy) {
        if (beanLoadBalancePolicy == null)
            throw new IllegalArgumentException("Null beanLoadBalancingPolicy");
        this.beanLoadBalancePolicy = beanLoadBalancePolicy;
    }

    /**
     * Get the home LoadBalancePolicy.
     *
     * @return the homeLoadBalancingPolicy.
     */
    public String getHomeLoadBalancePolicy() {
        return homeLoadBalancePolicy;
    }

    /**
     * Set the home LoadBalancePolicy.
     *
     * @param homeLoadBalancePolicy the homeLoadBalancingPolicy.
     * @throws IllegalArgumentException for a null homeLoadBalancingPolicy
     */
    public void setHomeLoadBalancePolicy(String homeLoadBalancePolicy) {
        if (homeLoadBalancePolicy == null)
            throw new IllegalArgumentException("Null homeLoadBalancingPolicy");
        this.homeLoadBalancePolicy = homeLoadBalancePolicy;
    }

    /**
     * Get the EJB 3.x bean LoadBalancePolicy. This is an alias
     * for {@link #getBeanLoadBalancePolicy()}.
     *
     * @return the loadBalancePolicy.
     */
    public String getLoadBalancePolicy() {
        return getBeanLoadBalancePolicy();
    }

    /**
     * Set the EJB 3.x bean LoadBalancePolicy. This is an alias
     * for {@link #setBeanLoadBalancePolicy(String)}.
     *
     * @param loadBalancePolicy The loadBalancePolicy to set.
     */
    public void setLoadBalancePolicy(String loadBalancePolicy) {
        setBeanLoadBalancePolicy(loadBalancePolicy);
    }

    /**
     * Get the partitionName.
     *
     * @return the partitionName.
     */
    public String getPartitionName() {
        return partitionName;
    }

    /**
     * Set the partitionName.
     *
     * @param partitionName the partitionName.
     * @throws IllegalArgumentException for a null partitionName
     */
    public void setPartitionName(String partitionName) {
        if (partitionName == null)
            throw new IllegalArgumentException("Null partitionName");
        this.partitionName = partitionName;
    }

    /**
     * Get the sessionStateManagerJndiName.
     *
     * @return the sessionStateManagerJndiName.
     */
    public String getSessionStateManagerJndiName() {
        return sessionStateManagerJndiName;
    }

    /**
     * Set the sessionStateManagerJndiName.
     *
     * @param sessionStateManagerJndiName the sessionStateManagerJndiName.
     * @throws IllegalArgumentException for a null sessionStateManagerJndiName
     */
    public void setSessionStateManagerJndiName(String sessionStateManagerJndiName) {
        if (sessionStateManagerJndiName == null)
            throw new IllegalArgumentException("Null sessionStateManagerJndiName");
        this.sessionStateManagerJndiName = sessionStateManagerJndiName;
    }

    public void merge(ClusterConfigMetaData override, ClusterConfigMetaData original) {
        IdMetaDataImplWithDescriptionsMerger.merge(this, override, original);

        if (override != null) {
            partitionName = override.getPartitionName();
            beanLoadBalancePolicy = override.getBeanLoadBalancePolicy();
            homeLoadBalancePolicy = override.getHomeLoadBalancePolicy();
            sessionStateManagerJndiName = override.getSessionStateManagerJndiName();
        }

        if (original != null) {
            if (partitionName == null)
                partitionName = original.getPartitionName();
            if (beanLoadBalancePolicy == null)
                beanLoadBalancePolicy = original.getBeanLoadBalancePolicy();
            if (homeLoadBalancePolicy == null)
                homeLoadBalancePolicy = original.getHomeLoadBalancePolicy();
            if (sessionStateManagerJndiName == null)
                sessionStateManagerJndiName = original.getSessionStateManagerJndiName();
        }
    }
}
