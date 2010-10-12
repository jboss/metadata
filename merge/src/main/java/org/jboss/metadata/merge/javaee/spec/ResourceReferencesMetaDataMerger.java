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
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.support.JavaEEMetaDataUtil;

/**
 * ResourceReferencesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ResourceReferencesMetaDataMerger {
    /**
     * Merge resource references
     *
     * @param override the override references
     * @param overriden the overriden references
     * @param overridenFile the overriden file name
     * @param overrideFile the override file
     * @return the merged referencees
     */
    public static ResourceReferencesMetaData merge(ResourceReferencesMetaData override, ResourceReferencesMetaData overriden,
            String overridenFile, String overrideFile, boolean mustOverride) {
        if (override == null && overriden == null)
            return null;

        if (override == null)
            return overriden;

        ResourceReferencesMetaData merged = new ResourceReferencesMetaData();
        return JavaEEMetaDataUtil.merge(merged, overriden, override, "resource-ref", overridenFile, overrideFile, mustOverride);
    }

    public void augment(ResourceReferencesMetaData dest, ResourceReferencesMetaData augment, ResourceReferencesMetaData main, boolean resolveConflicts) {
        for (ResourceReferenceMetaData resourceRef : augment) {
            if (dest.containsKey(resourceRef.getKey())) {
                if (!resolveConflicts && (main == null || !main.containsKey(resourceRef.getKey()))) {
                    throw new IllegalStateException("Unresolved conflict on resource reference named: " + resourceRef.getKey());
                } else {
                    dest.get(resourceRef.getKey()).augment(resourceRef, (main != null) ? main.get(resourceRef.getKey()) : null,
                            resolveConflicts);
                }
            } else {
                dest.add(resourceRef);
            }
        }
    }

}
