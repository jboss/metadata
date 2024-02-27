/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.AnnotatedEJBReferenceMetaData;

/**
 * Metadata for an @EJB reference
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 72960 $
 * @EJB.name = ejbRefName
 */
public class AnnotatedEJBReferenceMetaDataMerger extends AbstractEJBReferenceMetaDataMerger {

    public static AnnotatedEJBReferenceMetaData merge(AnnotatedEJBReferenceMetaData dest, AnnotatedEJBReferenceMetaData original) {
        AnnotatedEJBReferenceMetaData merged = new AnnotatedEJBReferenceMetaData();
        AbstractEJBReferenceMetaDataMerger.merge(merged, dest, original);
        if (dest.getBeanInterface() != null)
            merged.setBeanInterface(dest.getBeanInterface());
        else if (original != null && original.getBeanInterface() != null)
            merged.setBeanInterface(original.getBeanInterface());
        return merged;
    }
}
