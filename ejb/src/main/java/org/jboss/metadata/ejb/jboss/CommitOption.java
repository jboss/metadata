/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

/**
 * CommitOption.<p>
 * <p/>
 * The descriptions come from Marc Fleury
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public enum CommitOption {
    /**
     * Dumb
     */
    A,

    /**
     * Dumber
     */
    B,

    /**
     * Dumbest
     */
    C,

    /**
     * Dumb with a period flush
     */
    D,

    /* An undefined commit option */
    Undefined
}
