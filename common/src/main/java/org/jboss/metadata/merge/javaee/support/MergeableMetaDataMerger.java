/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.support;

/**
 * MergeableMappedMetaData.
 *
 * @param <T> the type this is mergeable
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public interface MergeableMetaDataMerger<T> {
    /**
     * Merge some metadata
     *
     * @param original the original
     * @return the merged metadata
     */
    T merge(T original);
}
