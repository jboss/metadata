/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.support;

import org.jboss.metadata.javaee.support.ResourceInjectionMetaDataWithDescriptions;
import org.jboss.metadata.merge.javaee.spec.ResourceInjectionMetaDataMerger;

/**
 * ResourceInjectionMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class ResourceInjectionMetaDataWithDescriptionsMerger extends ResourceInjectionMetaDataMerger {
    /**
     * Merge override + original into this.
     *
     * @param override
     * @param original
     */
    public static void merge(ResourceInjectionMetaDataWithDescriptions dest, ResourceInjectionMetaDataWithDescriptions override, ResourceInjectionMetaDataWithDescriptions original) {
        ResourceInjectionMetaDataMerger.merge(dest, override, original);
        if (override != null && override.getDescriptions() != null)
            dest.setDescriptions(override.getDescriptions());
        else if (original != null && original.getDescriptions() != null)
            dest.setDescriptions(original.getDescriptions());
    }
}
