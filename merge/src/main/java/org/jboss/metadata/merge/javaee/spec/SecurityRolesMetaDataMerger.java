/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.jboss.metadata.merge.javaee.spec;

import java.util.HashSet;
import java.util.Set;

import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImpl;

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
    public static void merge(SecurityRolesMetaData dest, IdMetaDataImpl override, IdMetaDataImpl original) {
        SecurityRolesMetaData roles1 = (SecurityRolesMetaData) override;
        SecurityRolesMetaData roles0 = (SecurityRolesMetaData) original;
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
        rebuildPrincipalsVersusRolesMap(dest);
    }

    /**
     * Add entries to principalVersusRolesMap for the specified role meta data.
     *
     * @param roleMetaData the security role meta data
     * @param dest
     */
    private static void processSecurityRoleMetaData(SecurityRoleMetaData roleMetaData, SecurityRolesMetaData dest) {
        Set<String> principals = roleMetaData.getPrincipals();
        if (principals == null)
            return;

        for (String principal : principals) {
            Set<String> roles = dest.getPrincipalVersusRolesMap().get(principal);
            if (roles == null) {
                roles = new HashSet<String>();
                dest.getPrincipalVersusRolesMap().put(principal, roles);
            }
            roles.add(roleMetaData.getRoleName());
        }
    }

    private static void rebuildPrincipalsVersusRolesMap(SecurityRolesMetaData dest) {
        dest.clearPrincipalVersusRolesMap();

        for (SecurityRoleMetaData roleMetaData : dest) {
            processSecurityRoleMetaData(roleMetaData ,dest);
        }
    }
}
