/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;
import org.jboss.metadata.merge.javaee.support.NamedMetaDataWithDescriptionGroupMerger;

/**
 * MessageDestinationMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class MessageDestinationMetaDataMerger extends NamedMetaDataWithDescriptionGroupMerger {
    public static MessageDestinationMetaData merge(MessageDestinationMetaData dest, MessageDestinationMetaData original) {
        MessageDestinationMetaData merged = new MessageDestinationMetaData();
        merge(merged, dest, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(MessageDestinationMetaData dest, MessageDestinationMetaData override, MessageDestinationMetaData original) {
        NamedMetaDataWithDescriptionGroupMerger.merge(dest, override, original);
        if (override != null && override.getMappedName() != null)
            dest.setMappedName(override.getMappedName());
        else if (original.getMappedName() != null)
            dest.setMappedName(original.getMappedName());
        if (override != null && override.getLookupName() != null)
            dest.setLookupName(override.getLookupName());
        else if (original.getLookupName() != null)
            dest.setLookupName(original.getLookupName());
    }

    public static void augment(MessageDestinationMetaData dest, MessageDestinationMetaData augment, MessageDestinationMetaData main, boolean resolveConflicts) {
        // Mapped name
        if (dest.getMappedName() == null) {
            if (augment.getMappedName() != null)
                dest.setMappedName(augment.getMappedName());
        } else if (augment.getMappedName() != null) {
            if (!resolveConflicts && !dest.getMappedName().equals(augment.getMappedName())
                    && (main == null || main.getMappedName() == null)) {
                throw new IllegalStateException("Unresolved conflict on mapped name: " + dest.getMappedName());
            }
        }
        // Lookup name
        if (dest.getLookupName() == null) {
            dest.setLookupName(augment.getLookupName());
        } else if (augment.getLookupName() != null) {
            if (!resolveConflicts && !dest.getLookupName().equals(augment.getLookupName())
                    && (main == null || main.getLookupName() == null)) {
                throw new IllegalStateException("Unresolved conflict on lookup name: " + dest.getLookupName());
            }
        }
    }

}
