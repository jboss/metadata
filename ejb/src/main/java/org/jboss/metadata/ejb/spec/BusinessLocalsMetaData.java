/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.support.NonNullLinkedHashSet;

/**
 * BusinessLocalsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class BusinessLocalsMetaData extends NonNullLinkedHashSet<String> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -2258099610577083158L;

    /**
     * Create a new BusinessLocalsMetaData.
     */
    public BusinessLocalsMetaData() {
        // For serialization
    }
}
