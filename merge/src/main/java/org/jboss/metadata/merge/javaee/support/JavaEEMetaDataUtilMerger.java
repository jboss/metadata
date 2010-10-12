/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.jboss.metadata.merge.javaee.support;

import org.jboss.metadata.javaee.support.MappableMetaData;
import org.jboss.metadata.javaee.support.MappableMetaDataWithOverride;
import org.jboss.metadata.javaee.support.MappedMetaData;
import org.jboss.metadata.javaee.support.MappedMetaDataWithOverride;
import org.jboss.metadata.javaee.support.MergeableMappedMetaData;

/**
 * Util.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class JavaEEMetaDataUtilMerger {
    /**
     * Merged overriden mapped metadata, using the ejb-jar.xml and jboss.xml
     * file names
     *
     * @param <T> the metadata component
     * @param <M> the metadata map
     * @param merged the skeleton merged metadata
     * @param overriden the overriden metadata
     * @param mapped the the override metadata
     * @param context a context for error messages
     * @param mustOverride whether the overridden data must exist
     * @return the merged metadata
     */
    public static <T extends MergeableMappedMetaData<T>, M extends MappedMetaData<T>> M mergeJBossXml(M merged, M overriden,
            M mapped, String context, boolean mustOverride) {
        return merge(merged, overriden, mapped, context, "ejb-jar.xml", "jboss.xml", mustOverride);
    }

    /**
     * Merged mapped metadata
     *
     * @param <T> the metadata component
     * @param <M> the metadata map
     * @param merged the skeleton merged metadata
     * @param overriden the overriden metadata
     * @param mapped the the override metadata
     * @param context a context for error messages
     * @param overridenFile the xml file the overridden metadata comes from for
     *        error messages
     * @param overrideFile the xml file the override metadata comes from for
     *        error messages
     * @param mustOverride whether the overridden data must exist
     * @return the merged metadata
     */
    public static <T extends MergeableMappedMetaData<T>, M extends MappedMetaData<T>> M merge(M merged, M overriden, M mapped,
            String context, String overridenFile, String overrideFile, boolean mustOverride) {
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
        for (T original : overriden) {
            String key = original.getKey();
            if (mapped != null && mapped.containsKey(key)) {
                T override = mapped.get(key);
                T tnew = override.merge(original);
                merged.add(tnew);
            } else {
                merged.add(original);
            }
        }

        // Process the remaining overrides
        if (mapped != null) {
            for (T override : mapped) {
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
     * Merged overriden mapped metadata, using the ejb-jar.xml and jboss.xml
     * file names
     *
     * @param <C> the original metadata component
     * @param <O> the original metadata map
     * @param <T> the overridden metadata component
     * @param <M> the overridden metadata map
     * @param merged the skeleton merged metadata
     * @param overriden the overriden metadata
     * @param mapped the the override metadata
     * @param context a context for error messages
     * @param mustOverride whether the overridden data must exist
     * @return the merged metadata
     */
    public static <C extends MappableMetaData, O extends MappedMetaData<C>, T extends MappableMetaDataWithOverride<C>, M extends MappedMetaDataWithOverride<C, T, O>> M mergeOverrideJBossXml(
            M merged, O overriden, M mapped, String context, boolean mustOverride) {
        return mergeOverride(merged, overriden, mapped, context, "ejb-jar.xml", "jboss.xml", mustOverride);
    }

    public static <C extends MappableMetaData, O extends MappedMetaData<C>, T extends MappableMetaDataWithOverride<C>, M extends MappedMetaDataWithOverride<C, T, O>> M mergeOverrideJBossCMPXml(
            M merged, O overriden, M mapped, String context, boolean mustOverride) {
        return mergeOverride(merged, overriden, mapped, context, "ejb-jar.xml", "jbosscmp-jdbc.xml", mustOverride);
    }

    /**
     * Merged overriden mapped metadata
     *
     * @param <C> the original metadata component
     * @param <O> the original metadata map
     * @param <T> the overridden metadata component
     * @param <M> the overridden metadata map
     * @param merged the skeleton merged metadata
     * @param overriden the overriden metadata
     * @param mapped the the override metadata
     * @param context a context for error messages
     * @param overridenFile the xml file the overridden metadata comes from for
     *        error messages
     * @param overrideFile the xml file the override metadata comes from for
     *        error messages
     * @param mustOverride whether the overridden data must exist
     * @return the merged metadata
     */
    public static <C extends MappableMetaData, O extends MappedMetaData<C>, T extends MappableMetaDataWithOverride<C>, M extends MappedMetaDataWithOverride<C, T, O>> M mergeOverride(
            M merged, O overriden, M mapped, String context, String overridenFile, String overrideFile, boolean mustOverride) {
        if (merged == null)
            throw new IllegalArgumentException("Null merged");

        // Nothing to do
        if (overriden == null && mapped == null)
            return merged;

        // No override
        if (overriden == null || overriden.isEmpty()) {
            if (mapped.isEmpty() == false && mustOverride)
                throw new IllegalStateException(overridenFile + " has no " + context + "s but " + overrideFile + " has "
                        + mapped.keySet());
            return mapped;
        }

        // Process the originals
        if (overriden != null) {
            for (C c : overriden) {
                // Look for an override
                String key = c.getKey();
                T t = null;
                if (mapped != null)
                    t = mapped.get(key);

                // No override, create one
                if (t == null)
                    t = merged.createOverride(c);

                // give a chance to ignore an instance
                // in case of jbosscmp, there are only entity beans
                if (t != null) {
                    // Return the result
                    t.setOverridenMetaData(c);
                    merged.add(t);
                }
            }
        }

        // Check for unprocessed
        if (mapped != null && mapped.isEmpty() == false) {
            for (T t : mapped) {
                String key = t.getKey();
                if (merged.get(key) == null) {
                    if (mustOverride)
                        throw new IllegalStateException(key + " in " + overrideFile + ", but not in " + overridenFile);
                    else
                        merged.createOriginal(t);
                }
            }
        }

        return merged;
    }
}
