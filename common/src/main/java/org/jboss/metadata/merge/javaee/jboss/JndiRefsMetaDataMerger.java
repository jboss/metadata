/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.jboss;

import org.jboss.metadata.javaee.jboss.JndiRefMetaData;
import org.jboss.metadata.javaee.jboss.JndiRefsMetaData;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;

/**
 * JndiRefsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class JndiRefsMetaDataMerger {
    public static void merge(JndiRefsMetaData dest, JndiRefsMetaData override, JndiRefsMetaData original) {
        IdMetaDataImplMerger.merge(dest, override, original);
        if (original != null) {
            for (JndiRefMetaData property : original)
                dest.add(property);
        }
        if (override != null) {
            for (JndiRefMetaData property : override)
                dest.add(property);
        }
    }
}
