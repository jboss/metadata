/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.EJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBReferencesMetaData;

/**
 * EJBReferencesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class EJBReferencesMetaDataMerger {
    /**
     * Merge ejb references
     *
     * @param override      the override references
     * @param overriden     the overriden references
     * @param overridenFile the overriden file name
     * @param overrideFile  the override file
     * @return the merged referencees
     */
    public static EJBReferencesMetaData merge(EJBReferencesMetaData override, EJBReferencesMetaData overriden,
                                              String overridenFile, String overrideFile, boolean mustOverride) {
        if (override == null && overriden == null)
            return null;

        if (override == null)
            return overriden;

        EJBReferencesMetaData merged = new EJBReferencesMetaData();
        return merge(merged, overriden, override, "ejb-ref", overridenFile, overrideFile, mustOverride);
    }

    /**
     * From avaEEMetaDataUtil.java
     */
    private static EJBReferencesMetaData merge(EJBReferencesMetaData merged, EJBReferencesMetaData overriden,
                                               EJBReferencesMetaData mapped, String context, String overridenFile, String overrideFile, boolean mustOverride) {
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
        for (EJBReferenceMetaData original : overriden) {
            String key = original.getKey();
            if (mapped != null && mapped.containsKey(key)) {
                EJBReferenceMetaData override = mapped.get(key);
                EJBReferenceMetaData tnew = EJBReferenceMetaDataMerger.merge(override, original);
                merged.add(tnew);
            } else {
                merged.add(original);
            }
        }

        // Process the remaining overrides
        if (mapped != null) {
            for (EJBReferenceMetaData override : mapped) {
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

    public static void augment(EJBReferencesMetaData dest, EJBReferencesMetaData augment, EJBReferencesMetaData main,
                               boolean resolveConflicts) {
        for (EJBReferenceMetaData ejbReference : augment) {
            if (dest.containsKey(ejbReference.getKey())) {
                if (!resolveConflicts && (main == null || !main.containsKey(ejbReference.getKey()))) {
                    throw new IllegalStateException("Unresolved conflict on EJB reference named: " + ejbReference.getKey());
                } else {
                    EJBReferenceMetaDataMerger.augment(dest.get(ejbReference.getKey()), ejbReference,
                            (main != null) ? main.get(ejbReference.getKey()) : null, resolveConflicts);
                }
            } else {
                dest.add(ejbReference);
            }
        }
    }

}
