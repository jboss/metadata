/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.AbstractEJBReferenceMetaData;
import org.jboss.metadata.merge.javaee.support.ResourceInjectionMetaDataWithDescriptionsMerger;

/**
 * AbstractEJBReferenceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractEJBReferenceMetaDataMerger extends ResourceInjectionMetaDataWithDescriptionsMerger {

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(AbstractEJBReferenceMetaData dest, AbstractEJBReferenceMetaData override, AbstractEJBReferenceMetaData original) {
        ResourceInjectionMetaDataWithDescriptionsMerger.merge(dest, override, original);
        if (override != null && override.getEjbRefName() != null)
            dest.setEjbRefName(override.getEjbRefName());
        else if (original != null && original.getEjbRefName() != null)
            dest.setEjbRefName(original.getEjbRefName());
        if (override != null && override.getEjbRefType() != null)
            dest.setEjbRefType(override.getEjbRefType());
        else if (original != null && original.getEjbRefType() != null)
            dest.setEjbRefType(original.getEjbRefType());
        if (override != null && override.getLink() != null)
            dest.setLink(override.getLink());
        else if (original != null && original.getLink() != null)
            dest.setLink(original.getLink());
    }
}
