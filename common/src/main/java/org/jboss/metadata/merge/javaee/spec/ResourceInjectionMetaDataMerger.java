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
