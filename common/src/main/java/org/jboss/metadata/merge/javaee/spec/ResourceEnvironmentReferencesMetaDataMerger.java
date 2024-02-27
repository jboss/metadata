/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;

/**
 * ResourceEnvironmentReferencesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ResourceEnvironmentReferencesMetaDataMerger {
    /**
     * Merge resource environment references
     *
     * @param override      the override references
     * @param overriden     the overriden references
     * @param overridenFile the overriden file name
     * @param overrideFile  the override file
     * @return the merged referencees
     */
    public static ResourceEnvironmentReferencesMetaData merge(ResourceEnvironmentReferencesMetaData override,
                                                              ResourceEnvironmentReferencesMetaData overriden, String overridenFile, String overrideFile) {
        if (override == null && overriden == null)
            return null;

        if (override == null)
            return overriden;

        ResourceEnvironmentReferencesMetaData merged = new ResourceEnvironmentReferencesMetaData();
        return merge(merged, overriden, override, "resource-env-ref", overridenFile, overrideFile, false);
    }

    /**
     * From avaEEMetaDataUtil.java
     */
    private static ResourceEnvironmentReferencesMetaData merge(ResourceEnvironmentReferencesMetaData merged,
                                                               ResourceEnvironmentReferencesMetaData overriden, ResourceEnvironmentReferencesMetaData mapped, String context,
                                                               String overridenFile, String overrideFile, boolean mustOverride) {
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
        for (ResourceEnvironmentReferenceMetaData original : overriden) {
            String key = original.getKey();
            if (mapped != null && mapped.containsKey(key)) {
                ResourceEnvironmentReferenceMetaData override = mapped.get(key);
                ResourceEnvironmentReferenceMetaData tnew = ResourceEnvironmentReferenceMetaDataMerger
                        .merge(override, original);
                merged.add(tnew);
            } else {
                merged.add(original);
            }
        }

        // Process the remaining overrides
        if (mapped != null) {
            for (ResourceEnvironmentReferenceMetaData override : mapped) {
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

    public static void augment(ResourceEnvironmentReferencesMetaData dest, ResourceEnvironmentReferencesMetaData augment,
                               ResourceEnvironmentReferencesMetaData main, boolean resolveConflicts) {
        for (ResourceEnvironmentReferenceMetaData resourceEnvRef : augment) {
            if (dest.containsKey(resourceEnvRef.getKey())) {
                if (!resolveConflicts && (main == null || !main.containsKey(resourceEnvRef.getKey()))) {
                    throw new IllegalStateException("Unresolved conflict on resource environment reference named: "
                            + resourceEnvRef.getKey());
                } else {
                    ResourceEnvironmentReferenceMetaDataMerger.augment(dest.get(resourceEnvRef.getKey()), resourceEnvRef,
                            (main != null) ? main.get(resourceEnvRef.getKey()) : null, resolveConflicts);
                }
            } else {
                dest.add(resourceEnvRef);
            }
        }
    }

}
