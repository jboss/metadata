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
package org.jboss.metadata.merge.web.spec;

import java.util.ArrayList;
import java.util.List;

import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefMetaData;
import org.jboss.metadata.merge.javaee.support.NamedMetaDataWithDescriptionGroupMerger;
import org.jboss.metadata.web.spec.ServletMetaData;

/**
 * web-app/servlet metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */
public class ServletMetaDataMerger {

    public static ServletMetaData merge(ServletMetaData dest, ServletMetaData original) {
        ServletMetaData merged = new ServletMetaData();
        merge(merged, dest, original);
        return merged;
    }

    public static void merge(ServletMetaData dest, ServletMetaData override, ServletMetaData original) {
        NamedMetaDataWithDescriptionGroupMerger.merge(dest, override, original);
        if (override != null && override.getServletClass() != null)
            dest.setServletClass(override.getServletClass());
        else if (original != null && original.getServletClass() != null)
            dest.setServletClass(original.getServletClass());
        if (override != null && override.getJspFile() != null)
            dest.setJspFile(override.getJspFile());
        else if (original != null && original.getJspFile() != null)
            dest.setJspFile(original.getJspFile());
        if (override != null && override.getInitParam() != null)
            dest.setInitParam(override.getInitParam());
        else if (original != null && original.getInitParam() != null)
            dest.setInitParam(original.getInitParam());
        if (override != null && override.getLoadOnStartupInt() != dest.getLoadOnStartupDefault())
            dest.setLoadOnStartupInt(override.getLoadOnStartupInt());
        else if (original != null && original.getLoadOnStartupInt() != dest.getLoadOnStartupDefault())
            dest.setLoadOnStartupInt(original.getLoadOnStartupInt());
        if (override != null && override.getRunAs() != null)
            dest.setRunAs(override.getRunAs());
        else if (original != null && original.getRunAs() != null)
            dest.setRunAs(original.getRunAs());
        if (override != null && override.getSecurityRoleRefs() != null)
            dest.setSecurityRoleRefs(override.getSecurityRoleRefs());
        else if (original != null && original.getSecurityRoleRefs() != null)
            dest.setSecurityRoleRefs(original.getSecurityRoleRefs());
        if (override != null && override.isAsyncSupported() != dest.getAsyncSupportedDefault())
            dest.setAsyncSupported(override.isAsyncSupported());
        else if (original != null && original.isAsyncSupported() != dest.getAsyncSupportedDefault())
            dest.setAsyncSupported(original.isAsyncSupported());
        if (override != null && override.isEnabled() != dest.getEnabledDefault())
            dest.setEnabled(override.isEnabled());
        else if (original != null && original.isEnabled() != dest.getEnabledDefault())
            dest.setEnabled(original.isEnabled());
        if (override != null && override.getMultipartConfig() != null)
            dest.setMultipartConfig(override.getMultipartConfig());
        else if (original != null && original.getMultipartConfig() != null)
            dest.setMultipartConfig(original.getMultipartConfig());
    }

    public static void augment(ServletMetaData dest, ServletMetaData webFragmentMetaData, ServletMetaData webMetaData,
                               boolean resolveConflicts) {
        // Servlet class
        if (dest.getServletClass() == null) {
            dest.setServletClass(webFragmentMetaData.getServletClass());
        } else if (webFragmentMetaData.getServletClass() != null) {
            if (!resolveConflicts && !dest.getServletClass().equals(webFragmentMetaData.getServletClass())
                    && (webMetaData == null || webMetaData.getServletClass() == null)) {
                throw new IllegalStateException("Unresolved conflict on servlet class: " + dest.getServletClass());
            }
        }
        // Jsp file
        if (dest.getJspFile() == null) {
            dest.setJspFile(webFragmentMetaData.getJspFile());
        } else if (webFragmentMetaData.getJspFile() != null) {
            if (!resolveConflicts && !dest.getJspFile().equals(webFragmentMetaData.getJspFile())
                    && (webMetaData == null || webMetaData.getJspFile() == null)) {
                throw new IllegalStateException("Unresolved conflict on jsp file: " + dest.getJspFile());
            }
        }
        // Init params
        if (dest.getInitParam() == null) {
            dest.setInitParam(webFragmentMetaData.getInitParam());
        } else if (webFragmentMetaData.getInitParam() != null) {
            List<ParamValueMetaData> mergedInitParams = new ArrayList<ParamValueMetaData>();
            for (ParamValueMetaData initParam : dest.getInitParam()) {
                mergedInitParams.add(initParam);
            }
            for (ParamValueMetaData initParam : webFragmentMetaData.getInitParam()) {
                boolean found = false;
                for (ParamValueMetaData check : dest.getInitParam()) {
                    if (check.getParamName().equals(initParam.getParamName())) {
                        found = true;
                        // Check for a conflict
                        if (!resolveConflicts && !check.getParamValue().equals(initParam.getParamValue())) {
                            // If the parameter name does not exist in the main
                            // web, it's an error
                            boolean found2 = false;
                            if (webMetaData.getInitParam() != null) {
                                for (ParamValueMetaData check1 : webMetaData.getInitParam()) {
                                    if (check1.getParamName().equals(check.getParamName())) {
                                        found2 = true;
                                        break;
                                    }
                                }
                            }
                            if (!found2)
                                throw new IllegalStateException("Unresolved conflict on init parameter: "
                                        + check.getParamName());
                        }
                    }
                }
                if (!found)
                    mergedInitParams.add(initParam);
            }
            dest.setInitParam(mergedInitParams);
        }
        // Load on startup
        if (!dest.getLoadOnStartupSet()) {
            if (webFragmentMetaData.getLoadOnStartupSet()) {
                dest.setLoadOnStartup(webFragmentMetaData.getLoadOnStartup());
                dest.setLoadOnStartupInt(webFragmentMetaData.getLoadOnStartupInt());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.getLoadOnStartupSet()
                    && (dest.getLoadOnStartup() != webFragmentMetaData.getLoadOnStartup())
                    && (webMetaData == null || !webMetaData.getLoadOnStartupSet())) {
                throw new IllegalStateException("Unresolved conflict on load on startup");
            }
        }
        // Run as
        if (dest.getRunAs() == null) {
            dest.setRunAs(webFragmentMetaData.getRunAs());
        } else if (webFragmentMetaData.getRunAs() != null) {
            if (!resolveConflicts && dest.getRunAs().getRoleName() != null
                    && !dest.getRunAs().getRoleName().equals(webFragmentMetaData.getRunAs().getRoleName())) {
                if (webMetaData == null || webMetaData.getRunAs() == null) {
                    throw new IllegalStateException("Unresolved conflict on run as role name");
                }
            }

        }
        // Security role ref
        if (dest.getSecurityRoleRefs() == null) {
            dest.setSecurityRoleRefs(webFragmentMetaData.getSecurityRoleRefs());
        } else if (webFragmentMetaData.getSecurityRoleRefs() != null) {
            for (SecurityRoleRefMetaData securityRoleRef : webFragmentMetaData.getSecurityRoleRefs()) {
                if (dest.getSecurityRoleRefs().containsKey(securityRoleRef.getKey())) {
                    SecurityRoleRefMetaData check = dest.getSecurityRoleRefs().get(securityRoleRef.getKey());
                    if (!resolveConflicts && check.getRoleLink() != null
                            && !check.getRoleLink().equals(securityRoleRef.getRoleLink())) {
                        if (webMetaData == null || webMetaData.getSecurityRoleRefs() == null
                                || !webMetaData.getSecurityRoleRefs().containsKey(securityRoleRef.getKey())) {
                            throw new IllegalStateException("Unresolved conflict on role ref: " + securityRoleRef.getKey());
                        }
                    }
                } else {
                    dest.getSecurityRoleRefs().add(securityRoleRef);
                }
            }
        }
        // Async supported
        if (!dest.getAsyncSupportedSet()) {
            if (webFragmentMetaData.getAsyncSupportedSet()) {
                dest.setAsyncSupported(webFragmentMetaData.isAsyncSupported());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.getAsyncSupportedSet()
                    && (dest.isAsyncSupported() != webFragmentMetaData.isAsyncSupported())
                    && (webMetaData == null || !webMetaData.getAsyncSupportedSet())) {
                throw new IllegalStateException("Unresolved conflict on async supported");
            }
        }
        // Enabled
        if (!dest.getEnabledSet()) {
            if (webFragmentMetaData.getEnabledSet()) {
                dest.setEnabled(webFragmentMetaData.isEnabled());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.getEnabledSet()
                    && (dest.isEnabled() != webFragmentMetaData.isEnabled())
                    && (webMetaData == null || !webMetaData.getEnabledSet())) {
                throw new IllegalStateException("Unresolved conflict on enabled");
            }
        }
        // Multipart config
        if (dest.getMultipartConfig() == null) {
            dest.setMultipartConfig(webFragmentMetaData.getMultipartConfig());
        } else if (webFragmentMetaData.getMultipartConfig() != null) {
            MultipartConfigMetaDataMerger.augment(dest.getMultipartConfig(), webFragmentMetaData.getMultipartConfig(), webMetaData.getMultipartConfig(),
                    resolveConflicts);
        }
    }
}
