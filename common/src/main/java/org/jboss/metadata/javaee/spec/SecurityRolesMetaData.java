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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * SecurityRolesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class SecurityRolesMetaData extends AbstractMappedMetaData<SecurityRoleMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 4551308976124434096L;

    private Map<String, Set<String>> principalVersusRolesMap = new HashMap<String, Set<String>>();
    private Map<String, Set<String>> readOnlyPrincipalVersusRolesMap = Collections.unmodifiableMap(principalVersusRolesMap);

    /**
     * Create a new SecurityRolesMetaData.
     */
    public SecurityRolesMetaData() {
        super("role name for security role");
    }

    @Override
    protected void addNotification(SecurityRoleMetaData added) {
        super.addNotification(added);

        // update the roles map
        processSecurityRoleMetaData(added);
    }

    public Map<String, Set<String>> getPrincipalVersusRolesMap() {
        return readOnlyPrincipalVersusRolesMap;
    }

    private void clearPrincipalVersusRolesMap() {
        principalVersusRolesMap.clear();
    }

    public void rebuildPrincipalVersusRolesMap() {
        clearPrincipalVersusRolesMap();
        for (SecurityRoleMetaData roleMetaData : this) {
            processSecurityRoleMetaData(roleMetaData);
        }
    }

    /**
     * Get the security roles by principal
     *
     * @param userName the principal name
     * @return the security roles containing the principal or null for no roles
     * @throws IllegalArgumentException for a null user name
     */
    @Deprecated
    public SecurityRolesMetaData getSecurityRolesByPrincipal(String userName) {
        if (userName == null)
            throw new IllegalArgumentException("Null userName");
        if (isEmpty())
            return null;
        SecurityRolesMetaData result = new SecurityRolesMetaData();
        for (SecurityRoleMetaData role : this) {
            if (role.hasPrincipal(userName))
                result.add(role);
        }
        return result;
    }

    /**
     * Get the security role names by principal
     *
     * @param userName the principal name
     * @return the security role names containing the principal
     * @throws IllegalArgumentException for a null user name
     */
    public Set<String> getSecurityRoleNamesByPrincipal(String userName) {
        if (userName == null)
            throw new IllegalArgumentException("Null userName");
        Set<String> roles = readOnlyPrincipalVersusRolesMap.get(userName);
        if (roles == null)
            return Collections.emptySet();
        return roles;
    }

    /**
     * Add entries to principalVersusRolesMap for the specified role meta data.
     *
     * @param roleMetaData the security role meta data
     */
    private void processSecurityRoleMetaData(SecurityRoleMetaData roleMetaData) {
        Set<String> principals = roleMetaData.getPrincipals();
        if (principals == null)
            return;

        for (String principal : principals) {
            Set<String> roles = principalVersusRolesMap.get(principal);
            if (roles == null) {
                roles = new HashSet<String>();
                principalVersusRolesMap.put(principal, roles);
            }
            roles.add(roleMetaData.getRoleName());
        }
    }

    @Override
    protected void removeNotification(SecurityRoleMetaData removed) {
        super.removeNotification(removed);

        Set<String> principals = removed.getPrincipals();
        if (principals == null)
            return;

        for (String principal : principals) {
            Set<String> roles = principalVersusRolesMap.get(principal);
            if (roles != null) {
                roles.remove(removed.getRoleName());
            }
        }
    }
}
