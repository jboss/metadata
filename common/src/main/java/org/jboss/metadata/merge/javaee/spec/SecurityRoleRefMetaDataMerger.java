/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.SecurityRoleRefMetaData;
import org.jboss.metadata.merge.javaee.support.NamedMetaDataMerger;

/**
 * SecurityRoleRefMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class SecurityRoleRefMetaDataMerger {

    public static SecurityRoleRefMetaData merge(SecurityRoleRefMetaData dest, SecurityRoleRefMetaData original) {
        SecurityRoleRefMetaData merged = new SecurityRoleRefMetaData();
        merge(merged, dest, original);
        return merged;
    }

    public static void merge(SecurityRoleRefMetaData dest, SecurityRoleRefMetaData override, SecurityRoleRefMetaData original) {
        NamedMetaDataMerger.merge(dest, override, original);
        if (override != null && override.getRoleLink() != null)
            dest.setRoleLink(override.getRoleLink());
        else if (original != null && original.getRoleLink() != null)
            dest.setRoleLink(original.getRoleLink());
    }

}
