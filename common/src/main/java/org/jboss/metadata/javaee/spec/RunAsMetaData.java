/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * RunAsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class RunAsMetaData extends IdMetaDataImplWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 6132381662482264933L;

    /**
     * The role name
     */
    private String roleName;

    /**
     * Create a new SecurityRoleRefMetaData.
     */
    public RunAsMetaData() {
        // For serialization
    }

    /**
     * Get the roleName.
     *
     * @return the roleName.
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Set the roleName.
     *
     * @param roleName the roleName.
     * @throws IllegalArgumentException for a null roleName
     */
    public void setRoleName(String roleName) {
        if (roleName == null)
            throw new IllegalArgumentException("Null roleName");
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("RunAsMetaData(id=");
        tmp.append(getId());
        tmp.append(",roleName=");
        tmp.append(roleName);
        tmp.append(')');
        return tmp.toString();
    }
}
