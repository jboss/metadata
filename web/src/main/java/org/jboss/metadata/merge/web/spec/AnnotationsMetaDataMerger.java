/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.web.spec;

import org.jboss.metadata.web.spec.AnnotationMetaData;
import org.jboss.metadata.web.spec.AnnotationsMetaData;

/**
 * @author Remy Maucherat
 * @version $Revision: 65943 $
 */
public class AnnotationsMetaDataMerger {

    public static void augment(AnnotationsMetaData dest, AnnotationsMetaData webFragmentMetaData,
                               AnnotationsMetaData webMetaData, boolean resolveConflicts) {
        for (AnnotationMetaData annotationMetaData : webFragmentMetaData) {
            if (dest.containsKey(annotationMetaData.getKey())) {
                continue;
            } else {
                dest.add(annotationMetaData);
            }
        }
    }

}
