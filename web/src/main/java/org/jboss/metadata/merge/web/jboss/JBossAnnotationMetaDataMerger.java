/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.web.jboss;

import org.jboss.metadata.merge.javaee.support.NamedMetaDataMerger;
import org.jboss.metadata.web.jboss.JBossAnnotationMetaData;
import org.jboss.metadata.web.spec.AnnotationMetaData;

/**
 * jboss-web/annotation metadata
 *
 * @author Scott.Stark@jboss.org
 * @author Remy Maucherat
 * @version $Revision: 83549 $
 */
public class JBossAnnotationMetaDataMerger {
    public static JBossAnnotationMetaData merge(JBossAnnotationMetaData dest, AnnotationMetaData original) {
        JBossAnnotationMetaData merged = new JBossAnnotationMetaData();
        merge(merged, dest, original);
        return merged;
    }

    public static void merge(JBossAnnotationMetaData dest, JBossAnnotationMetaData override, AnnotationMetaData original) {
        NamedMetaDataMerger.merge(dest, override, original);

        if (override != null && override.getServletSecurity() != null)
            dest.setServletSecurity(override.getServletSecurity());
        else if (original != null && original.getServletSecurity() != null)
            dest.setServletSecurity(original.getServletSecurity());
        if (override != null && override.getRunAs() != null)
            dest.setRunAs(override.getRunAs());
        else if (original != null && original.getRunAs() != null)
            dest.setRunAs(original.getRunAs());
        if (override != null && override.getMultipartConfig() != null)
            dest.setMultipartConfig(override.getMultipartConfig());
        else if (original != null && original.getMultipartConfig() != null)
            dest.setMultipartConfig(original.getMultipartConfig());

    }
}
