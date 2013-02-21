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

import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;

/**
 * The service-refGroup type
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ServiceReferencesMetaDataMerger {

    /**
     * Merge resource references
     *
     * @param override      the override references
     * @param overriden     the overriden references
     * @param overridenFile the overriden file name
     * @param overrideFile  the override file
     * @return the merged referencees
     */
    public static ServiceReferencesMetaData merge(ServiceReferencesMetaData override, ServiceReferencesMetaData overriden,
                                                  String overridenFile, String overrideFile) {
        if (override == null && overriden == null)
            return null;

        if (override == null)
            return overriden;

        ServiceReferencesMetaData merged = new ServiceReferencesMetaData();
        return merge(merged, overriden, override, "service-ref", overridenFile, overrideFile, true);
    }

    /**
     * From JavaEEMetaDataUtil.java
     */
    private static ServiceReferencesMetaData merge(ServiceReferencesMetaData merged, ServiceReferencesMetaData overriden,
                                                   ServiceReferencesMetaData mapped, String context, String overridenFile, String overrideFile, boolean mustOverride) {
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
        for (ServiceReferenceMetaData original : overriden) {
            String key = original.getKey();
            if (mapped != null && mapped.containsKey(key)) {
                ServiceReferenceMetaData override = mapped.get(key);
                ServiceReferenceMetaData tnew = ServiceReferenceMetaDataMerger.merge(override, original);
                merged.add(tnew);
            } else {
                merged.add(original);
            }
        }

        // Process the remaining overrides
        if (mapped != null) {
            for (ServiceReferenceMetaData override : mapped) {
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

    public static void augment(ServiceReferencesMetaData dest, ServiceReferencesMetaData augment,
                               ServiceReferencesMetaData main, boolean resolveConflicts) {
        for (ServiceReferenceMetaData serviceRef : augment) {
            if (dest.containsKey(serviceRef.getKey())) {
                if (!resolveConflicts && (main == null || !main.containsKey(serviceRef.getKey()))) {
                    throw new IllegalStateException("Unresolved conflict on service reference named: " + serviceRef.getKey());
                } else {
                    ServiceReferenceMetaDataMerger.augment(dest.get(serviceRef.getKey()), serviceRef,
                            (main != null) ? main.get(serviceRef.getKey()) : null, resolveConflicts);
                }
            } else {
                dest.add(serviceRef);
            }
        }
    }

}
