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

package org.jboss.metadata.ear.merge;

import org.jboss.metadata.ear.jboss.JBossAppMetaData;
import org.jboss.metadata.ear.spec.EarEnvironmentRefsGroupMetaData;
import org.jboss.metadata.ear.spec.EarMetaData;
import org.jboss.metadata.ear.spec.ModulesMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.merge.javaee.spec.SecurityRolesMetaDataMerger;

/**
 * @author John Bailey
 */
public class JBossAppMetaDataMerger {

    public static void merge(final JBossAppMetaData dest, final JBossAppMetaData override, final EarMetaData original) {
        dest.setEarEnvironmentRefsGroup(new EarEnvironmentRefsGroupMetaData());
        if (original != null && override != null) {
            EarEnvironmentRefsGroupMetaDataMerger.merge(dest.getEarEnvironmentRefsGroup(), override.getEarEnvironmentRefsGroup(), original.getEarEnvironmentRefsGroup(), "", "", false);
        } else if (original != null) {
            dest.setEarEnvironmentRefsGroup(original.getEarEnvironmentRefsGroup());
        } else if (override != null) {
            dest.setEarEnvironmentRefsGroup(override.getEarEnvironmentRefsGroup());
        } else {
            dest.setEarEnvironmentRefsGroup(new EarEnvironmentRefsGroupMetaData());
        }

        if (original != null) {
            dest.setInitializeInOrder(original.getInitializeInOrder());
        }

        if (override != null && override.getId() != null) {
            dest.setId(override.getId());
        } else if (original != null && original.getId() != null) {
            dest.setId(original.getId());
        }

        if (override != null && override.getDescriptionGroup() != null) {
            dest.setDescriptionGroup(override.getDescriptionGroup());
        } else if (original != null && original.getDescriptionGroup() != null) {
            dest.setDescriptionGroup(original.getDescriptionGroup());
        }
        if (override != null && override.getLibraryDirectory() != null) {
            dest.setLibraryDirectory(override.getLibraryDirectory());
        } else if (original != null && original.getLibraryDirectory() != null) {
            dest.setLibraryDirectory(original.getLibraryDirectory());
        }
        if (override != null && override.getApplicationName() != null) {
            dest.setApplicationName(override.getApplicationName());
        } else if (original != null && original.getApplicationName() != null) {
            dest.setApplicationName(original.getApplicationName());
        }

        if (override != null) {
            if (override.getSecurityDomain() != null)
                dest.setSecurityDomain(override.getSecurityDomain());
            if (override.getUnauthenticatedPrincipal() != null)
                dest.setUnauthenticatedPrincipal(override.getUnauthenticatedPrincipal());
        }

        if (dest.getModules() == null) {
            dest.setModules(new ModulesMetaData());
        }

        ModulesMetaData overrideModules = null;
        ModulesMetaData originalModules = null;
        if (override != null)
            overrideModules = override.getModules();
        if (original != null)
            originalModules = original.getModules();
        ModulesMetaDataMerger.merge(dest.getModules(), overrideModules, originalModules);

        SecurityRolesMetaData securityRolesMetaData = null;
        SecurityRolesMetaData overrideSecurityRolesMetaData = null;
        if (original != null)
            securityRolesMetaData = original.getSecurityRoles();
        if (override != null)
            overrideSecurityRolesMetaData = override.getSecurityRoles();
        if (dest.getSecurityRoles() == null)
            dest.setSecurityRoles(new SecurityRolesMetaData());
        SecurityRolesMetaDataMerger.merge(dest.getSecurityRoles(), overrideSecurityRolesMetaData, securityRolesMetaData);
    }
}
