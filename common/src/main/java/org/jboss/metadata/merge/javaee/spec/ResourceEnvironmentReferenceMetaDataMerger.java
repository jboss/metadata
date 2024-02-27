/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.merge.javaee.support.ResourceInjectionMetaDataWithDescriptionsMerger;

/**
 * ResourceEnvironmentReferenceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ResourceEnvironmentReferenceMetaDataMerger extends ResourceInjectionMetaDataWithDescriptionsMerger {
    public static ResourceEnvironmentReferenceMetaData merge(ResourceEnvironmentReferenceMetaData dest, ResourceEnvironmentReferenceMetaData original) {
        ResourceEnvironmentReferenceMetaData merged = new ResourceEnvironmentReferenceMetaData();
        merge(merged, dest, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(ResourceEnvironmentReferenceMetaData dest, ResourceEnvironmentReferenceMetaData override, ResourceEnvironmentReferenceMetaData original) {
        ResourceInjectionMetaDataWithDescriptionsMerger.merge(dest, override, original);
        if (override != null && override.getType() != null)
            dest.setType(override.getType());
        else if (original != null && original.getType() != null)
            dest.setType(original.getType());
    }
}
