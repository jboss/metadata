/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.DataSourceMetaData;
import org.jboss.metadata.javaee.spec.DataSourcesMetaData;

/**
 * @author Remy Maucherat
 * @version $Revision: 65928 $
 */
// public class Helper2<T extends DataSourceMetaData, M extends
// DataSourcesMetaData> extends Helper1<M> implements HelperElement<T>
public class DataSourcesMetaDataMerger {

    /**
     * Merge data sources
     *
     * @param override      the override references
     * @param overriden     the overriden references
     * @param overridenFile the overriden file name
     * @param overrideFile  the override file
     * @return the merged referencees
     */
    public static DataSourcesMetaData merge(DataSourcesMetaData override, DataSourcesMetaData overriden, String overridenFile,
                                            String overrideFile) {
        if (override == null && overriden == null)
            return null;

        if (override == null)
            return overriden;

        DataSourcesMetaData merged = new DataSourcesMetaData();
        return merge(merged, overriden, override, "data-source", overridenFile, overrideFile, false);
    }

    /**
     * From JavaEEMetaDataUtil
     */
    private static DataSourcesMetaData merge(DataSourcesMetaData merged, DataSourcesMetaData overriden,
                                             DataSourcesMetaData mapped, String context, String overridenFile, String overrideFile, boolean mustOverride) {
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
        for (DataSourceMetaData original : overriden) {
            String key = original.getKey();
            if (mapped != null && mapped.containsKey(key)) {
                DataSourceMetaData override = mapped.get(key);
                DataSourceMetaData tnew = DataSourceMetaDataMerger.merge(override, original);
                merged.add(tnew);
            } else {
                merged.add(original);
            }
        }

        // Process the remaining overrides
        if (mapped != null) {
            for (DataSourceMetaData override : mapped) {
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

    public static void augment(DataSourcesMetaData dest, DataSourcesMetaData webFragmentMetaData,
                               DataSourcesMetaData webMetaData, boolean resolveConflicts) {
        for (DataSourceMetaData dataSourceMetaData : webFragmentMetaData) {
            if (dest.containsKey(dataSourceMetaData.getKey())) {
                if (!resolveConflicts && (webMetaData == null || !webMetaData.containsKey(dataSourceMetaData.getKey()))) {
                    throw new IllegalStateException("Unresolved conflict on data source named: " + dataSourceMetaData.getKey());
                }
            } else {
                dest.add(dataSourceMetaData);
            }
        }
    }

}
