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
package org.jboss.metadata.javaee.jboss;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Extended run-as/security-role metadata for defining a run as principal.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 81768 $
 */
public class RunAsIdentityMetaData implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * The name of the run-as principal
     */
    private String principalName;
    /**
     * The run-as role name
     */
    private String roleName;
    /**
     * The extra run-as role names
     */
    private HashSet<String> runAsRoles = new HashSet<String>();

    private static final String ANOYMOUS_PRINCIPAL = "anonymous";

    /**
     * Construct an inmutable instance of a RunAsIdentity
     */
    public RunAsIdentityMetaData(String roleName, String principalName) {
        if (principalName == null)
            principalName = ANOYMOUS_PRINCIPAL;
        if (roleName == null)
            throw new IllegalArgumentException("The run-as identity must have at least one role");

        this.principalName = principalName;
        this.roleName = roleName;
        runAsRoles.add(roleName);
    }

    /**
     * Construct an inmutable instance of a RunAsIdentityMetaData
     */
    public RunAsIdentityMetaData(String roleName, String principalName, Set<String> extraRoleNames) {
        this(roleName, principalName);

        // these come from the assembly-descriptor
        if (extraRoleNames != null) {
            for (String extraRoleName : extraRoleNames) {
                runAsRoles.add(extraRoleName);
            }
        }
    }

    public String getPrincipalName() {
        return principalName;
    }

    public String getRoleName() {
        return roleName;
    }

    /**
     * Return a set with the configured run-as role
     *
     * @return Set<String> for the run-as roles
     */
    public Set<String> getRunAsRoles() {
        return new HashSet<String>(runAsRoles);
    }

    public boolean doesUserHaveRole(String role) {
        return runAsRoles.contains(role);
    }

    /**
     * True if the run-as principal has any of the method roles
     */
    public boolean doesUserHaveRole(Set<String> methodRoles) {
        if (methodRoles != null)
            for (String role : methodRoles) {
                if (doesUserHaveRole(role))
                    return true;
            }
        return false;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "[roles=" + runAsRoles + ",principal=" + getPrincipalName() + "]";
    }
}
