/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.ResourceInjectionMetaData;
import org.jboss.metadata.merge.javaee.support.NamedMetaDataMerger;

/**
 * ResourceInjectionMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class ResourceInjectionMetaDataMerger extends NamedMetaDataMerger {

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(ResourceInjectionMetaData dest, ResourceInjectionMetaData override, ResourceInjectionMetaData original) {
        NamedMetaDataMerger.merge(dest, override, original);
        if (override != null && override.getMappedName() != null)
            dest.setMappedName(override.getMappedName());
        else if (original != null && original.getMappedName() != null)
            dest.setMappedName(original.getMappedName());
        if (override != null && override.getIgnoreDependency() != null)
            dest.setIgnoreDependency(override.getIgnoreDependency());
        else if (original != null && original.getIgnoreDependency() != null)
            dest.setIgnoreDependency(original.getIgnoreDependency());
        if (override != null && override.getInjectionTargets() != null)
            dest.setInjectionTargets(override.getInjectionTargets());
        else if (original != null && original.getInjectionTargets() != null)
            dest.setInjectionTargets(original.getInjectionTargets());
    }

    public static void augment(ResourceInjectionMetaData dest, ResourceInjectionMetaData augment, ResourceInjectionMetaData main, boolean resolveConflicts) {
        if (main != null && main.getInjectionTargets() != null) {
            // If main contains injection target, drop the all injection targets
            dest.setInjectionTargets(null);
        } else {
            // Add injection targets
            if (dest.getInjectionTargets() == null) {
                dest.setInjectionTargets(augment.getInjectionTargets());
            } else if (augment.getInjectionTargets() != null) {
                dest.getInjectionTargets().addAll(augment.getInjectionTargets());
            }
        }
    }

}
