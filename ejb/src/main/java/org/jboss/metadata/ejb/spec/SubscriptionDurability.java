/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

/**
 * Subscription durability
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public enum SubscriptionDurability {
    /**
     * Durable
     */
    Durable,

    /**
     * Non durable
     */
    NonDurable,
}
