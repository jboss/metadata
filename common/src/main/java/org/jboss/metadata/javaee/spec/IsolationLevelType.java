/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

/**
 * IsolationLevelType.
 *
 * @author Remy Maucherat
 * @version $Revision: 1.1 $
 */
public enum IsolationLevelType {
    TRANSACTION_READ_UNCOMMITTED, TRANSACTION_READ_COMMITTED, TRANSACTION_REPEATABLE_READ, TRANSACTION_SERIALIZABLE
}
