/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.ManagedExecutorMetaData;
import org.jboss.metadata.javaee.spec.ManagedScheduledExecutorMetaData;

/**
 *
 * @author emmartins
 *
 */
public class ManagedScheduledExecutorMetaDataMerger {

    public static ManagedScheduledExecutorMetaData merge(ManagedExecutorMetaData dest, ManagedExecutorMetaData original) {
        ManagedScheduledExecutorMetaData merged = new ManagedScheduledExecutorMetaData();
        ManagedExecutorMetaDataMerger.merge(merged, dest, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(ManagedScheduledExecutorMetaData dest, ManagedScheduledExecutorMetaData override, ManagedScheduledExecutorMetaData original) {
        ManagedExecutorMetaDataMerger.merge(dest, override, original);
    }
}
