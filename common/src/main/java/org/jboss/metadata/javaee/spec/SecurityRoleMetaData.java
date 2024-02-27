/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import java.util.Set;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * SecurityRoleMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
public class SecurityRoleMetaData extends NamedMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -4349954695900237831L;

    /**
     * The prinicpal names that have this role
     */
    private Set<String> principals;

    /**
     * Create a new SecurityRoleMetaData.
     */
    public SecurityRoleMetaData() {
        // For serialization
    }

    /**
     * Get the roleName.
     *
     * @return the roleName.
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

    /**
     * Get the principals.
     *
     * @return the principals.
     */
    public Set<String> getPrincipals() {
        return principals;
    }

    /**
     * Set the principals.
     *
     * @param principals the principals.
     * @throws IllegalArgumentException for a null principals
     */
    public void setPrincipals(Set<String> principals) {
        if (principals == null)
            throw new IllegalArgumentException("Null principals");
        this.principals = principals;
    }

    /**
     * Whether this roles has the principal
     *
     * @param userName the principal
     * @return true when it has the principal
     * @throws IllegalArgumentException for a null principal
     */
    public boolean hasPrincipal(String userName) {
        if (userName == null)
            throw new IllegalArgumentException("Null userName");
        if (principals == null)
            return false;
        return principals.contains(userName);
    }
}
