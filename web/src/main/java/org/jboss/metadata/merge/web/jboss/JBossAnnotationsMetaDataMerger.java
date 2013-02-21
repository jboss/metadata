/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
