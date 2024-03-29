/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.merge.MergeUtil;

/**
 * MessageDestinationsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 67878 $
 */
public class MessageDestinationsMetaDataMerger {
    /**
     * /** Merge the contents of override with original into a new
     * MessageDestinationsMetaData.
     *
     * @param override      - metadata augmenting overriden
     * @param overriden     - the base metadata
     * @param overridenFile - the source of the override destinations
     * @param overrideFile  - the source of the overriden destinations
     * @return a new merged MessageDestinationsMetaData if either override and
     *         overriden is not null, null otherwise.
     */
    public static MessageDestinationsMetaData merge(MessageDestinationsMetaData override,
                                                    MessageDestinationsMetaData overriden, String overridenFile, String overrideFile) {
        if (override == null && overriden == null)
            return null;

        if (override == null)
            return overriden;

        MessageDestinationsMetaData merged = new MessageDestinationsMetaData();
        // mustOverride is false because legacy jboss descriptors not having a
        // message-destination
        return merge(merged, overriden, override, "message-destination", overridenFile, overrideFile, false);
    }

    /**
     * From JavaEEMetaDataUtil.java
     */
    private static MessageDestinationsMetaData merge(MessageDestinationsMetaData merged, MessageDestinationsMetaData overriden,
                                                     MessageDestinationsMetaData mapped, String context, String overridenFile, String overrideFile, boolean mustOverride) {
        if (merged == null)
            throw new IllegalArgumentException("Null merged");

        // Nothing to do
        if (overriden == null && mapped == null)
            return merged;

        // No override
        if (overriden == null || overriden.isEmpty()) {
            // There are no overrides and no overriden
            // Note: it has been really silly to call upon merge, but allas
            if (mapped == null)
                return merged;

            if (mapped.isEmpty() == false && mustOverride)
                throw new IllegalStateException(overridenFile + " has no " + context + "s but " + overrideFile + " has "
                        + mapped.keySet());
            if (mapped != merged)
                merged.addAll(mapped);
            return merged;
        }

        // Wolf: I want to maintain the order of originals (/ override)
        // Process the originals
        for (MessageDestinationMetaData original : overriden) {
            String key = original.getKey();
            if (mapped != null && mapped.containsKey(key)) {
                MessageDestinationMetaData override = mapped.get(key);
                MessageDestinationMetaData tnew = MessageDestinationMetaDataMerger.merge(override, original);
                merged.add(tnew);
            } else {
                merged.add(original);
            }
        }

        // Process the remaining overrides
        if (mapped != null) {
            for (MessageDestinationMetaData override : mapped) {
                String key = override.getKey();
                if (merged.containsKey(key))
                    continue;
                if (mustOverride)
                    throw new IllegalStateException(key + " in " + overrideFile + ", but not in " + overridenFile);
                merged.add(override);
            }
        }

        return merged;
    }

    public static void merge(MessageDestinationsMetaData dest, MessageDestinationsMetaData override,
                             MessageDestinationsMetaData original) {
        MergeUtil.merge(dest, override, original);
    }

    public static void augment(MessageDestinationsMetaData dest, MessageDestinationsMetaData augment,
                               MessageDestinationsMetaData main, boolean resolveConflicts) {
        for (MessageDestinationMetaData messageDestinationMetaData : augment) {
            if (dest.containsKey(messageDestinationMetaData.getKey())) {
                MessageDestinationMetaDataMerger.augment(dest.get(messageDestinationMetaData.getKey()),
                        messageDestinationMetaData, (main != null) ? main.get(messageDestinationMetaData.getKey()) : null,
                        resolveConflicts);
            } else {
                dest.add(messageDestinationMetaData);
            }
        }
    }
}
