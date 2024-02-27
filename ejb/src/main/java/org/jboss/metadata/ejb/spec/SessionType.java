/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

/**
 * SessionType.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public enum SessionType {
    /**
     * Stateless session bean
     */
    Stateless,

    /**
     * Stateful session bean
     */
    Stateful,

    /**
     * EJB3.1 singleton type
     */
    Singleton
}
