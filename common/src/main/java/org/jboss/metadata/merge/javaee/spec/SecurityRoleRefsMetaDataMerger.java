/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.SecurityRoleRefMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;

/**
 * SecurityRoleRefsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
public class SecurityRoleRefsMetaDataMerger {
    public static void merge(SecurityRoleRefsMetaData dest, SecurityRoleRefsMetaData override, SecurityRoleRefsMetaData original) {
        IdMetaDataImplMerger.merge(dest, override, original);
        if (original != null) {
            for (SecurityRoleRefMetaData property : original)
                dest.add(property);
        }
        if (override != null) {
            for (SecurityRoleRefMetaData property : override)
                dest.add(property);
        }
    }
}
