/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * SecurityRoleRefMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class SecurityRoleRefMetaData extends NamedMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -8092072767419265555L;

    /**
     * The role link
     */
    private String roleLink;

    /**
     * Create a new SecurityRoleRefMetaData.
     */
    public SecurityRoleRefMetaData() {
        // For serialization
    }

    /**
     * Get the roleLink.
     *
     * @return the roleLink.
     */
    public String getRoleLink() {
        return roleLink;
    }

    /**
     * Set the roleLink.
     *
     * @param roleLink the roleLink.
     * @throws IllegalArgumentException for a null roleLink
     */
    public void setRoleLink(String roleLink) {
        if (roleLink == null)
            throw new IllegalArgumentException("Null roleLink");
        this.roleLink = roleLink;
    }

    /**
     * Get the roleNames.
     *
     * @return the roleNames.
     */
    public String getRoleName() {
        return getName();
    }

    /**
     * Set the roleName.
     *
     * @param roleName the roleName.
     * @throws IllegalArgumentException for a null roleName
     */
    public void setRoleName(String roleName) {
        setName(roleName);
    }
}
