/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.web.jboss;

import org.jboss.metadata.merge.web.spec.ServletMetaDataMerger;
import org.jboss.metadata.web.jboss.JBossServletMetaData;
import org.jboss.metadata.web.spec.ServletMetaData;

/**
 * jboss-web/servlet metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class JBossServletMetaDataMerger {
    public static void merge(JBossServletMetaData dest, JBossServletMetaData override, ServletMetaData original) {
        ServletMetaDataMerger.merge(dest, override, original);
        if (override != null && override.getRunAsPrincipal() != null)
            dest.setRunAsPrincipal(override.getRunAsPrincipal());
        if (override != null && override.getServletSecurity() != null)
            dest.setServletSecurity(override.getServletSecurity());
        if(override != null && override.getExecutorName() != null) {
            dest.setExecutorName(override.getExecutorName());
        }
    }
}
