/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.jboss;

import org.jboss.metadata.javaee.jboss.AnnotationMetaData;
import org.jboss.metadata.javaee.jboss.AnnotationsMetaData;
import org.jboss.metadata.merge.javaee.support.NamedMetaDataMerger;

/**
 * AnnotationsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class AnnotationsMetaDataMerger {

    public static void merge(AnnotationsMetaData dest, AnnotationsMetaData override, AnnotationsMetaData original) {
        NamedMetaDataMerger.merge(dest, override, original);
        if (original != null) {
            for (AnnotationMetaData property : original)
                dest.add(property);
        }
        if (override != null) {
            for (AnnotationMetaData property : override)
                dest.add(property);
        }
    }
}
