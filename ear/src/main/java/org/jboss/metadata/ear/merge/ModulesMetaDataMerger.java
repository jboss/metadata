/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
