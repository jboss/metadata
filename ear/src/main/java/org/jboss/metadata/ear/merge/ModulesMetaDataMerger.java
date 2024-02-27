/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ear.merge;

import org.jboss.metadata.ear.spec.ModuleMetaData;
import org.jboss.metadata.ear.spec.ModulesMetaData;

/**
 * @author John Bailey
 */
public class ModulesMetaDataMerger {
    /**
     * Merge original + override into this.
     *
     * @param original
     * @param override
     */
    public static void merge(final ModulesMetaData dest, final ModulesMetaData override, final ModulesMetaData original) {
        if (original == null) {
            if (override != null)
                dest.addAll(override);
        } else if (override == null) {
            dest.addAll(original);
        } else {
            for (ModuleMetaData module : original) {
                // Only include modules not override in the override map
                if (override.get(module.getFileName()) == null)
                    dest.add(module);
                else
                    dest.add(override.get(module.getFileName()));
            }
            // Add any modules from override not already added
            for (ModuleMetaData module : override) {
                if (dest.get(module.getFileName()) == null)
                    dest.add(module);
            }
        }
    }
}
