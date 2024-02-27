/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.support;

import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImpl;

// HACK import org.jboss.util.UnreachableStatementException;

/**
 * IdMetaDataImpl.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class IdMetaDataImplMerger {

    public static void merge(IdMetaData dest, IdMetaData override, IdMetaData original) {
        if (override != null && override.getId() != null)
            dest.setId(override.getId());
        else if (original != null && original.getId() != null)
            dest.setId(original.getId());
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(IdMetaDataImpl dest, IdMetaDataImpl override, IdMetaDataImpl original) {
        if (override != null && override.getId() != null)
            dest.setId(override.getId());
        else if (original != null && original.getId() != null)
            dest.setId(original.getId());
    }
}
