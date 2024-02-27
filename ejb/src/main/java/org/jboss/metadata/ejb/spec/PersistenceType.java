/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

/**
 * PersistenceType.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public enum PersistenceType {
    /**
     * CMP
     */
    Container,

    /**
     * BMP
     */
    Bean,
}
