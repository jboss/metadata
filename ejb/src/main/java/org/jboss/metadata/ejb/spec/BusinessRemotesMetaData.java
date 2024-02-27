/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.support.NonNullLinkedHashSet;

/**
 * BusinessRemotesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class BusinessRemotesMetaData extends NonNullLinkedHashSet<String> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -1866760573785099573L;

    /**
     * Create a new BusinessRemotesMetaData.
     */
    public BusinessRemotesMetaData() {
        // For serialization
    }
}
