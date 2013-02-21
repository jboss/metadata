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
