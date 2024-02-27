/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;

/**
 * EJBLocalReferenceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
public class EJBLocalReferenceMetaDataMerger extends AbstractEJBReferenceMetaDataMerger {

    public static EJBLocalReferenceMetaData merge(EJBLocalReferenceMetaData dest, EJBLocalReferenceMetaData original) {
        EJBLocalReferenceMetaData merged = new EJBLocalReferenceMetaData();
        merge(merged, dest, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(EJBLocalReferenceMetaData dest, EJBLocalReferenceMetaData override, EJBLocalReferenceMetaData original) {
        AbstractEJBReferenceMetaDataMerger.merge(dest, override, original);
        if (override != null && override.getEjbRefName() != null)
            dest.setEjbRefName(override.getEjbRefName());
        else if (original != null && original.getEjbRefName() != null)
            dest.setEjbRefName(original.getEjbRefName());
        if (override != null && override.getLocalHome() != null)
            dest.setLocalHome(override.getLocalHome());
        else if (original != null && original.getLocalHome() != null)
            dest.setLocalHome(original.getLocalHome());
        if (override != null && override.getLocal() != null)
            dest.setLocal(override.getLocal());
        else if (original != null && original.getLocal() != null)
            dest.setLocal(original.getLocal());
    }
}
