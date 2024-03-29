/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.merge.javaee.support.ResourceInjectionMetaDataWithDescriptionsMerger;

/**
 * ResourceReferenceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ResourceReferenceMetaDataMerger {

    public static ResourceReferenceMetaData merge(ResourceReferenceMetaData dest, ResourceReferenceMetaData original) {
        ResourceReferenceMetaData merged = new ResourceReferenceMetaData();
        merge(merged, dest, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(ResourceReferenceMetaData dest, ResourceReferenceMetaData override, ResourceReferenceMetaData original) {
        ResourceInjectionMetaDataWithDescriptionsMerger.merge(dest, override, original);
        if (override != null && override.getType() != null)
            dest.setType(override.getType());
        else if (original.getType() != null)
            dest.setType(original.getType());
        if (override != null && override.getResourceName() != null)
            dest.setResourceName(override.getResourceName());
        else if (original.getResourceName() != null)
            dest.setResourceName(original.getResourceName());
        if (override != null && override.getResAuth() != null)
            dest.setResAuth(override.getResAuth());
        else if (original.getResAuth() != null)
            dest.setResAuth(original.getResAuth());
        if (override != null && override.getResSharingScope() != null)
            dest.setResSharingScope(override.getResSharingScope());
        else if (original.getResSharingScope() != null)
            dest.setResSharingScope(original.getResSharingScope());
        if (override != null && override.getResUrl() != null)
            dest.setResUrl(override.getResUrl());
        else if (original.getResUrl() != null)
            dest.setResUrl(original.getResUrl());
    }
}
