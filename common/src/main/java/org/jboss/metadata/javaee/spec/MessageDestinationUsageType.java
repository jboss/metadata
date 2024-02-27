/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

/**
 * MessageDestinationUsageType.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public enum MessageDestinationUsageType {
    /**
     * Consumes
     */
    Consumes,

    /**
     * Produces
     */
    Produces,

    /**
     * Consumes and Produces
     */
    ConsumesProduces,
}
