package org.jboss.metadata.merge;

import org.jboss.metadata.ear.jboss.JBossAppMetaData;
import org.jboss.metadata.ear.spec.Ear5xMetaData;
import org.jboss.metadata.ear.spec.EarMetaData;
import org.jboss.metadata.ear.spec.ModulesMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.merge.javaee.spec.SecurityRolesMetaDataMerger;

/**
 * @author John Bailey
 */
public class JBossAppMetaDataMerger {

    public static void merge(final JBossAppMetaData dest, final JBossAppMetaData override, final EarMetaData original) {
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

        Ear5xMetaData original5x = null;
        if (original instanceof Ear5xMetaData)
            original5x = (Ear5xMetaData) original;

        if (override != null) {
            if (override.getDtdPublicId() != null)
                dest.setDTD(null, override.getDtdPublicId(), override.getDtdSystemId());
            if (override.getVersion() != null)
                dest.setVersion(override.getVersion());
            if (override.getSecurityDomain() != null)
                dest.setSecurityDomain(override.getSecurityDomain());
            if (override.getLoaderRepository() != null)
                dest.setLoaderRepository(override.getLoaderRepository());
            if (override.getUnauthenticatedPrincipal() != null)
                dest.setUnauthenticatedPrincipal(override.getUnauthenticatedPrincipal());
            if (override.getLibraryDirectory() != null)
                dest.setLibraryDirectory(override.getLibraryDirectory());
            else if (original5x != null && original5x.getLibraryDirectory() != null)
                dest.setLibraryDirectory(original5x.getLibraryDirectory());
            if (override.getJmxName() != null)
                dest.setJmxName(override.getJmxName());
        } else if (original5x != null && original5x.getLibraryDirectory() != null) {
            dest.setLibraryDirectory(original5x.getLibraryDirectory());
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
