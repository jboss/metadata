/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.web.jboss;

import org.jboss.metadata.web.jboss.JBossAnnotationMetaData;
import org.jboss.metadata.web.jboss.JBossAnnotationsMetaData;
import org.jboss.metadata.web.spec.AnnotationMetaData;
import org.jboss.metadata.web.spec.AnnotationsMetaData;

/**
 * @author Remy Maucherat
 * @version $Revision: 65943 $
 */
public class JBossAnnotationsMetaDataMerger {

    public static JBossAnnotationsMetaData merge(JBossAnnotationsMetaData override, AnnotationsMetaData original) {
        JBossAnnotationsMetaData merged = new JBossAnnotationsMetaData();
        if (override == null && original == null)
            return merged;

        if (original != null) {
            for (AnnotationMetaData ann : original) {
                String key = ann.getKey();
                if (override != null && override.containsKey(key)) {
                    JBossAnnotationMetaData overrideANN = override.get(key);
                    JBossAnnotationMetaData jba = JBossAnnotationMetaDataMerger.merge(overrideANN, ann);
                    merged.add(jba);
                } else {
                    JBossAnnotationMetaData jba = new JBossAnnotationMetaData();
                    JBossAnnotationMetaDataMerger.merge(jba, null, ann);
                    merged.add(jba);
                }
            }
        }

        // Process the remaining overrides
        if (override != null) {
            for (JBossAnnotationMetaData jba : override) {
                String key = jba.getKey();
                if (merged.containsKey(key))
                    continue;
                merged.add(jba);
            }
        }

        return merged;
    }

}
