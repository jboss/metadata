/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
