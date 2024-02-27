/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.EJBReferenceMetaData;

/**
 * EJBReferenceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
// unordered for the jboss client 5_0.xsd
public class EJBReferenceMetaDataMerger extends AbstractEJBReferenceMetaDataMerger {

    public static EJBReferenceMetaData merge(EJBReferenceMetaData dest, EJBReferenceMetaData original) {
        EJBReferenceMetaData merged = new EJBReferenceMetaData();
        merge(merged, dest, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(EJBReferenceMetaData dest, EJBReferenceMetaData override, EJBReferenceMetaData original) {
        AbstractEJBReferenceMetaDataMerger.merge(dest, override, original);
        if (override != null && override.getHome() != null)
            dest.setHome(override.getHome());
        else if (original.getHome() != null)
            dest.setHome(original.getHome());
        if (override != null && override.getRemote() != null)
            dest.setRemote(override.getRemote());
        else if (original.getRemote() != null)
            dest.setRemote(original.getRemote());
    }
}
