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
package org.jboss.metadata.merge.web.jboss;

import org.jboss.metadata.javaee.jboss.NamedModule;
import org.jboss.metadata.javaee.spec.Environment;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.merge.javaee.spec.EnvironmentRefsGroupMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.SecurityRolesMetaDataMerger;
import org.jboss.metadata.merge.javaee.support.NamedModuleImplMerger;
import org.jboss.metadata.web.jboss.JBossAnnotationsMetaData;
import org.jboss.metadata.web.jboss.JBossServletsMetaData;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.metadata.web.spec.AnnotationsMetaData;
import org.jboss.metadata.web.spec.ServletsMetaData;
import org.jboss.metadata.web.spec.WebMetaData;

/**
 * The combined web.xml/jboss-web.xml metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */
public class JBossWebMetaDataMerger extends NamedModuleImplMerger {

    public static void merge(JBossWebMetaData dest, JBossWebMetaData override, WebMetaData original) {
        merge(dest, override, original, "jboss-web.xml", "web.xml", false);
    }

    public static void merge(JBossWebMetaData dest, JBossWebMetaData override, WebMetaData original, String overrideFile, String overridenFile,
                             boolean mustOverride) {
        NamedModuleImplMerger.merge(dest, override, original);

        if (override != null && override.getServletVersion() != null)
            dest.setServletVersion(override.getServletVersion());
        else if (original != null && original.getVersion() != null)
            dest.setServletVersion(original.getVersion());

        if (override != null && override.getDistributable() != null)
            dest.setDistributable(override.getDistributable());
        else if (original != null && original.getDistributable() != null)
            dest.setDistributable(original.getDistributable());

        if (override != null && override.isMetadataComplete())
            dest.setMetadataComplete(override.isMetadataComplete());
        else if (original != null && original.isMetadataComplete()) {
            //if either original or override is MD complete the result is MD complete
            dest.setMetadataComplete(original.isMetadataComplete());
        }
        if (override != null && override.isDisableCrossContext())
            dest.setDisableCrossContext(override.isDisableCrossContext());

        if (override != null && override.getModuleName() != null)
            dest.setModuleName(override.getModuleName());
        else if (original instanceof NamedModule && ((NamedModule) original).getModuleName() != null)
            dest.setModuleName(((NamedModule) original).getModuleName());

        if (override != null && override.getContextParams() != null)
            dest.setContextParams(override.getContextParams());
        else if (original != null && original.getContextParams() != null)
            dest.setContextParams(original.getContextParams());

        if (override != null && override.getSessionConfig() != null)
            dest.setSessionConfig(override.getSessionConfig());
        else if (original != null && original.getSessionConfig() != null)
            dest.setSessionConfig(original.getSessionConfig());

        if (override != null && override.getFilters() != null)
            dest.setFilters(override.getFilters());
        else if (original != null && original.getFilters() != null)
            dest.setFilters(original.getFilters());

        if (override != null && override.getFilterMappings() != null)
            dest.setFilterMappings(override.getFilterMappings());
        else if (original != null && original.getFilterMappings() != null)
            dest.setFilterMappings(original.getFilterMappings());

        if (override != null && override.getErrorPages() != null)
            dest.setErrorPages(override.getErrorPages());
        else if (original != null && original.getErrorPages() != null)
            dest.setErrorPages(original.getErrorPages());

        if (override != null && override.getJspConfig() != null)
            dest.setJspConfig(override.getJspConfig());
        else if (original != null && original.getJspConfig() != null)
            dest.setJspConfig(original.getJspConfig());

        if (override != null && override.getListeners() != null)
            dest.setListeners(override.getListeners());
        else if (original != null && original.getListeners() != null)
            dest.setListeners(original.getListeners());

        if (override != null && override.getLoginConfig() != null)
            dest.setLoginConfig(override.getLoginConfig());
        else if (original != null && original.getLoginConfig() != null)
            dest.setLoginConfig(original.getLoginConfig());

        if (override != null && override.getMimeMappings() != null)
            dest.setMimeMappings(override.getMimeMappings());
        else if (original != null && original.getMimeMappings() != null)
            dest.setMimeMappings(original.getMimeMappings());

        if (override != null && override.getServletMappings() != null)
            dest.setServletMappings(override.getServletMappings());
        else if (original != null && original.getServletMappings() != null)
            dest.setServletMappings(original.getServletMappings());

        if (override != null && override.getSecurityConstraints() != null)
            dest.setSecurityConstraints(override.getSecurityConstraints());
        else if (original != null && original.getSecurityConstraints() != null)
            dest.setSecurityConstraints(original.getSecurityConstraints());

        if (override != null && override.getWelcomeFileList() != null)
            dest.setWelcomeFileList(override.getWelcomeFileList());
        else if (original != null && original.getWelcomeFileList() != null)
            dest.setWelcomeFileList(original.getWelcomeFileList());

        if (override != null && override.getLocalEncodings() != null)
            dest.setLocalEncodings(override.getLocalEncodings());
        else if (original != null && original.getLocalEncodings() != null)
            dest.setLocalEncodings(original.getLocalEncodings());

        if (override != null && override.getJaccAllStoreRole() != null)
            dest.setJaccAllStoreRole(override.isJaccAllStoreRole());

        if (override != null && override.getDtdPublicId() != null)
            dest.setDtdPublicId(override.getDtdPublicId());

        if (override != null && override.getDtdSystemId() != null)
            dest.setDtdSystemId(override.getDtdSystemId());

        if (override != null && override.getVersion() != null)
            dest.setVersion(override.getVersion());
        else if (original != null && original.getVersion() != null)
            dest.setVersion(original.getVersion());

        if (override != null && override.getContextRoot() != null) {
            dest.setContextRoot(override.getContextRoot());
        } else if(original != null && original.getDefaultContextPath() != null) {
            dest.setContextRoot(original.getDefaultContextPath());
        }

        if (override != null && override.getAlternativeDD() != null)
            dest.setAlternativeDD(override.getAlternativeDD());

        if (override != null && override.getSecurityDomain() != null)
            dest.setSecurityDomain(override.getSecurityDomain());

        if (override != null && override.getJaccContextID() != null)
            dest.setJaccContextID(override.getJaccContextID());


        if (override != null && override.getDepends() != null)
            dest.setDepends(override.getDepends());

        if (override != null && override.getRunAsIdentity() != null)
            dest.setRunAsIdentity(override.getRunAsIdentity());

        if (dest.getSecurityRoles() == null)
            dest.setSecurityRoles(new SecurityRolesMetaData());
        SecurityRolesMetaData overrideRoles = null;
        SecurityRolesMetaData originalRoles = null;
        if (override != null)
            overrideRoles = override.getSecurityRoles();
        if (original != null)
            originalRoles = original.getSecurityRoles();
        SecurityRolesMetaDataMerger.merge(dest.getSecurityRoles(), overrideRoles, originalRoles);

        JBossServletsMetaData soverride = null;
        ServletsMetaData soriginal = null;
        if (override != null)
            soverride = override.getServlets();
        if (original != null)
            soriginal = original.getServlets();
        dest.setServlets(JBossServletsMetaDataMerger.merge(soverride, soriginal));

        if (dest.getJndiEnvironmentRefsGroup() == null)
            dest.setJndiEnvironmentRefsGroup(new EnvironmentRefsGroupMetaData());
        Environment env = null;
        EnvironmentRefsGroupMetaData jenv = null;
        if (override != null)
            jenv = (EnvironmentRefsGroupMetaData) override.getJndiEnvironmentRefsGroup();
        if (original != null)
            env = original.getJndiEnvironmentRefsGroup();
        EnvironmentRefsGroupMetaDataMerger.merge((EnvironmentRefsGroupMetaData) dest.getJndiEnvironmentRefsGroup(), jenv, env, overrideFile, overridenFile, mustOverride);

        if (override != null && override.getVirtualHosts() != null)
            dest.setVirtualHosts(override.getVirtualHosts());

        if (override != null && override.isFlushOnSessionInvalidation())
            dest.setFlushOnSessionInvalidation(override.isFlushOnSessionInvalidation());

        if (override != null && override.getReplicationConfig() != null)
            dest.setReplicationConfig(override.getReplicationConfig());

        if (override != null && override.getWebserviceDescriptions() != null)
            dest.setWebserviceDescriptions(override.getWebserviceDescriptions());

        if (override != null && override.getMaxActiveSessions() != null)
            dest.setMaxActiveSessions(override.getMaxActiveSessions());

        if (override != null && override.getSessionCookies() != -1)
            dest.setSessionCookies(override.getSessionCookies());

        JBossAnnotationsMetaData aoverride = null;
        AnnotationsMetaData aoriginal = null;
        if (override != null)
            aoverride = override.getAnnotations();
        if (original != null)
            aoriginal = original.getAnnotations();
        dest.setAnnotations(JBossAnnotationsMetaDataMerger.merge(aoverride, aoriginal));

        if (override != null && override.getContainerListeners() != null)
            dest.setContainerListeners(override.getContainerListeners());

        if (override != null && override.getValves() != null)
            dest.setValves(override.getValves());


        if (override != null && override.getHandlers() != null)
            dest.setHandlers(override.getHandlers());

        if (override != null && override.getOverlays() != null)
            dest.setOverlays(override.getOverlays());

        if (override != null && override.isUseJBossAuthorization())
            dest.setUseJBossAuthorization(override.isUseJBossAuthorization());

        if (override != null && !override.isDisableAudit())
            dest.setDisableAudit(override.isDisableAudit());

        if (override != null && override.isSymbolicLinkingEnabled())
            dest.setSymbolicLinkingEnabled(override.isSymbolicLinkingEnabled());

        if(override != null && override.getDenyUncoveredHttpMethods() != null) {
            dest.setDenyUncoveredHttpMethods(override.getDenyUncoveredHttpMethods());
        } else if(original != null && original.getDenyUncoveredHttpMethods() != null) {
            dest.setDenyUncoveredHttpMethods(original.getDenyUncoveredHttpMethods());
        }

        if(override != null && override.getExecutorName() != null) {
            dest.setExecutorName(override.getExecutorName());
        }

        if(override != null && override.getServerInstanceName() != null) {
            dest.setServerInstanceName(override.getServerInstanceName());
        }
        if(override != null && override.getServletContainerName() != null) {
            dest.setServletContainerName(override.getServletContainerName());
        }
        if(override != null && override.getDefaultEncoding() != null) {
            dest.setDefaultEncoding(override.getDefaultEncoding());
        }
        if(override != null && override.getProactiveAuthentication() != null) {
            dest.setProactiveAuthentication(override.getProactiveAuthentication());
        }

        if(original != null) {
            dest.setRequestCharacterEncoding(original.getRequestCharacterEncoding());
            dest.setResponseCharacterEncoding(original.getResponseCharacterEncoding());
        }
    }
}
