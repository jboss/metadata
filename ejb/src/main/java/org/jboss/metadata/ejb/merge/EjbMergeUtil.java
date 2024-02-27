/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.merge;


import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 67878 $
 */
public class EjbMergeUtil {
    public static JBossMetaData merge(JBossMetaData jboss, EjbJarMetaData spec) {
        JBossMetaData merged = new JBossMetaData();
        merged.merge(jboss, spec);
        return merged;
    }
}
