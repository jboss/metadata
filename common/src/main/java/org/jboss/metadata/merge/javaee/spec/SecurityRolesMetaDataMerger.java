/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;

/**
 * SecurityRolesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class SecurityRolesMetaDataMerger {

    /**
     *
     */
    public static void merge(SecurityRolesMetaData dest, SecurityRolesMetaData override, SecurityRolesMetaData original) {
        SecurityRolesMetaData roles1 = override;
        SecurityRolesMetaData roles0 = original;
        if (roles0 != null) {
            for (SecurityRoleMetaData sr : roles0) {
                SecurityRoleMetaData to = dest.get(sr.getRoleName());
                if (to != null) {
                    SecurityRoleMetaDataMerger.merge(to, sr, null);
                } else {
                    dest.add(sr);
                }
            }
        }
        if (roles1 != null) {
            for (SecurityRoleMetaData sr : roles1) {
                SecurityRoleMetaData to = dest.get(sr.getRoleName());
                if (to != null) {
                    SecurityRoleMetaDataMerger.merge(to, sr, null);
                } else {
                    dest.add(sr);
                }
            }
        }
        // Take the easy way out
        dest.rebuildPrincipalVersusRolesMap();
    }
}
