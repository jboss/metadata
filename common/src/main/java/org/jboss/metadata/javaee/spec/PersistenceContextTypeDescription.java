/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.javaee.spec;

/**
 * @author Stuart Douglas
 */
public enum PersistenceContextTypeDescription {
    /**
     * Transaction-scoped persistence context
     */
    TRANSACTION,

    /**
     * Extended persistence context
     */
    EXTENDED
}
