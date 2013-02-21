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

import org.jboss.metadata.javaee.spec.AnnotatedEJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBReferencesMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 76290 $
 * @EJB references metadata.
 */
public class AnnotatedEJBReferencesMetaDataMerger {

    /**
     * Merge ejb references
     *
     * @param override      the override references
     * @param overriden     the overriden references
     * @param overridenFile the overriden file name
     * @param overrideFile  the override file
     * @return the merged referencees
     */
    public static AnnotatedEJBReferencesMetaData merge(AnnotatedEJBReferencesMetaData override,
                                                       AnnotatedEJBReferencesMetaData overriden, String overridenFile, String overrideFile) {
        if (override == null && overriden == null)
            return null;

        if (override == null)
            return overriden;

        AnnotatedEJBReferencesMetaData merged = new AnnotatedEJBReferencesMetaData();
        return merge(merged, overriden, override, "@EJB", overridenFile, overrideFile, false);
    }

    /**
     * Merged mapped metadata
     *
     * @param <T>           the metadata component
     * @param <M>           the metadata map
     * @param merged        the skeleton merged metadata
     * @param overriden     the overriden metadata
     * @param mapped        the the override metadata
     * @param context       a context for error messages
     * @param overridenFile the xml file the overridden metadata comes from for
     *                      error messages
     * @param overrideFile  the xml file the override metadata comes from for
     *                      error messages
     * @param mustOverride  whether the overridden data must exist
     * @return the merged metadata
     */
    public static AnnotatedEJBReferencesMetaData merge(AnnotatedEJBReferencesMetaData merged,
                                                       AnnotatedEJBReferencesMetaData overriden, AnnotatedEJBReferencesMetaData mapped, String context,
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
        for (AnnotatedEJBReferenceMetaData original : overriden) {
            String key = original.getKey();
            if (mapped != null && mapped.containsKey(key)) {
                AnnotatedEJBReferenceMetaData override = mapped.get(key);
                AnnotatedEJBReferenceMetaData tnew = AnnotatedEJBReferenceMetaDataMerger.merge(override, original);
                merged.add(tnew);
            } else {
                merged.add(original);
            }
        }

        // Process the remaining overrides
        if (mapped != null) {
            for (AnnotatedEJBReferenceMetaData override : mapped) {
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

    /**
     * Merge the annotated ejb refs with a xml desriptor
     *
     * @param override the override references
     * @param original the original references
     * @return the merged references.
     */
    public static AnnotatedEJBReferencesMetaData merge(EJBReferencesMetaData override, AnnotatedEJBReferencesMetaData original) {
        if (override == null)
            return original;

        if (original == null)
            return null;

        AnnotatedEJBReferencesMetaData merged = new AnnotatedEJBReferencesMetaData();
        for (AnnotatedEJBReferenceMetaData ref : original) {
            EJBReferenceMetaData ejbRef = override.get(ref.getKey());
            if (ejbRef != null) {
                AnnotatedEJBReferenceMetaData newRef = new AnnotatedEJBReferenceMetaData();
                AnnotatedEJBReferenceMetaDataMerger.merge(newRef, ejbRef, ref);
                if (ref.getBeanInterface() != null)
                    newRef.setBeanInterface(ref.getBeanInterface());

                merged.add(newRef);
            } else {
                merged.add(ref);
            }
        }
        return merged;
    }

    public static void augment(AnnotatedEJBReferencesMetaData dest, AnnotatedEJBReferencesMetaData augment,
                               AnnotatedEJBReferencesMetaData main, boolean resolveConflicts) {
        for (AnnotatedEJBReferenceMetaData ejbReference : augment) {
            if (dest.containsKey(ejbReference.getKey())) {
                if (!resolveConflicts && (main == null || !main.containsKey(ejbReference.getKey()))) {
                    throw new IllegalStateException("Unresolved conflict on EJB reference named: " + ejbReference.getKey());
                } else {
                    AnnotatedEJBReferenceMetaDataMerger.augment(dest.get(ejbReference.getKey()), ejbReference,
                            (main != null) ? main.get(ejbReference.getKey()) : null, resolveConflicts);
                }
            } else {
                dest.add(ejbReference);
            }
        }
    }

}
