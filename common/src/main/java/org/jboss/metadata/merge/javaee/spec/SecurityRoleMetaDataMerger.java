/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import java.util.HashSet;

import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;

/**
 * SecurityRoleMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
public class SecurityRoleMetaDataMerger {

    public static SecurityRoleMetaData merge(SecurityRoleMetaData dest, SecurityRoleMetaData original) {
        SecurityRoleMetaData merged = new SecurityRoleMetaData();
        merge(merged, dest, original);
        return merged;
    }

    public static void merge(SecurityRoleMetaData dest, SecurityRoleMetaData override, SecurityRoleMetaData original) {
        IdMetaDataImplMerger.merge(dest, override, original);
        if (override != null && override.getPrincipals() != null) {
            if (dest.getPrincipals() == null)
                dest.setPrincipals(new HashSet<String>());
            dest.getPrincipals().addAll(override.getPrincipals());
        }
        if (original != null && original.getPrincipals() != null) {
            if (dest.getPrincipals() == null)
                dest.setPrincipals(new HashSet<String>());
            dest.getPrincipals().addAll(original.getPrincipals());
        }
    }
}
