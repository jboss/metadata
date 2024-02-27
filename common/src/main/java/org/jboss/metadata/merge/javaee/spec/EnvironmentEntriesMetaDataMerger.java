/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;

/**
 * EnvironmentEntriesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class EnvironmentEntriesMetaDataMerger {
    public static void augment(EnvironmentEntriesMetaData dest, EnvironmentEntriesMetaData augment, EnvironmentEntriesMetaData main, boolean resolveConflicts) {
        for (EnvironmentEntryMetaData envEntry : augment) {
            if (dest.containsKey(envEntry.getKey())) {
                if (!resolveConflicts && (main == null || !main.containsKey(envEntry.getKey()))) {
                    throw new IllegalStateException("Unresolved conflict on environment entry named: " + envEntry.getKey());
                } else {
                    EnvironmentEntryMetaDataMerger.augment(dest.get(envEntry.getKey()), envEntry, (main != null) ? main.get(envEntry.getKey()) : null,
                            resolveConflicts);
                }
            } else {
                dest.add(envEntry);
            }
        }
    }

}
