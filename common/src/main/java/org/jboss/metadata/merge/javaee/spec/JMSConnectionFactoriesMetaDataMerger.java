/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.JMSConnectionFactoriesMetaData;
import org.jboss.metadata.javaee.spec.JMSConnectionFactoryMetaData;

/**
 *
 * @author Eduardo Martins
 *
 */
public class JMSConnectionFactoriesMetaDataMerger {

    /**
     * Merge data sources
     *
     * @param override      the override references
     * @param overriden     the overriden references
     * @param overridenFile the overriden file name
     * @param overrideFile  the override file
     * @return the merged referencees
     */
    public static JMSConnectionFactoriesMetaData merge(JMSConnectionFactoriesMetaData override, JMSConnectionFactoriesMetaData overriden, String overridenFile,
                                            String overrideFile) {
        if (override == null && overriden == null)
            return null;

        if (override == null)
            return overriden;

        JMSConnectionFactoriesMetaData merged = new JMSConnectionFactoriesMetaData();
        return merge(merged, overriden, override, "jms-connection-factory", overridenFile, overrideFile, false);
    }

    /**
     * From JavaEEMetaDataUtil
     */
    private static JMSConnectionFactoriesMetaData merge(JMSConnectionFactoriesMetaData merged, JMSConnectionFactoriesMetaData overriden,
            JMSConnectionFactoriesMetaData mapped, String context, String overridenFile, String overrideFile, boolean mustOverride) {
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
        for (JMSConnectionFactoryMetaData original : overriden) {
            String key = original.getKey();
            if (mapped != null && mapped.containsKey(key)) {
                JMSConnectionFactoryMetaData override = mapped.get(key);
                JMSConnectionFactoryMetaData tnew = JMSConnectionFactoryMetaDataMerger.merge(override, original);
                merged.add(tnew);
            } else {
                merged.add(original);
            }
        }

        // Process the remaining overrides
        if (mapped != null) {
            for (JMSConnectionFactoryMetaData override : mapped) {
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

    public static void augment(JMSConnectionFactoriesMetaData dest, JMSConnectionFactoriesMetaData augment,
            JMSConnectionFactoriesMetaData main, boolean resolveConflicts) {
        for (JMSConnectionFactoryMetaData metaData : augment) {
            if (dest.containsKey(metaData.getKey())) {
                if (!resolveConflicts && (main == null || !main.containsKey(metaData.getKey()))) {
                    throw new IllegalStateException("Unresolved conflict on metadata named: " + metaData.getKey());
                }
            } else {
                dest.add(metaData);
            }
        }
    }

}
