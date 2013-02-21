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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.merge.javaee.spec.EnvironmentRefsGroupMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.MessageDestinationsMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.SecurityRolesMetaDataMerger;
import org.jboss.metadata.web.spec.ErrorPageMetaData;
import org.jboss.metadata.web.spec.FilterMappingMetaData;
import org.jboss.metadata.web.spec.ListenerMetaData;
import org.jboss.metadata.web.spec.MimeMappingMetaData;
import org.jboss.metadata.web.spec.SecurityConstraintMetaData;
import org.jboss.metadata.web.spec.ServletMappingMetaData;
import org.jboss.metadata.web.spec.WebCommonMetaData;
import org.jboss.metadata.web.spec.WebResourceCollectionMetaData;

/**
 * The web-app spec metadata, common between the fragments and the main web.xml
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 70996 $
 */
public class WebCommonMetaDataMerger {
    /**
     * Merge web meta data, according to the merging rules specified by the
     * Servlet 3.0 specification. This is a special type of merging, where non
     * conflicting meta data augment existing meta data, rather than overriding
     * it. If there is a conflict when merging the fragment meta data, and the
     * main webMetaData does not resolve the conflict, an error will be thrown
     * or the conflict will be resolved in favor of this object, depending on
     * the value of the resolve flag.
     *
     * @param webFragmentMetaData The web meta data which will be merged into
     *                            this one
     * @param webMetaData         The base web meta data, used for conflict error
     *                            checking
     * @param resolveConflicts    If true, any conflict will be skipped without an
     *                            error. Otherwise, an error will be thrown. If this is true, then
     *                            as all conflict will be resolved in favor of this object,
     *                            webMetaData will not be used and can be null
     */
    public static void augment(WebCommonMetaData dest, WebCommonMetaData webFragmentMetaData, WebCommonMetaData webMetaData,
                               boolean resolveConflicts) {

        // Distributable
        if (!resolveConflicts && webFragmentMetaData.getDistributable() == null && webMetaData != null) {
            webMetaData.setDistributable(null);
        }

        // Context params
        if (dest.getContextParams() == null) {
            dest.setContextParams(webFragmentMetaData.getContextParams());
        } else if (webFragmentMetaData.getContextParams() != null) {
            List<ParamValueMetaData> mergedContextParams = new ArrayList<ParamValueMetaData>();
            for (ParamValueMetaData contextParam : dest.getContextParams()) {
                mergedContextParams.add(contextParam);
            }
            for (ParamValueMetaData contextParam : webFragmentMetaData.getContextParams()) {
                boolean found = false;
                for (ParamValueMetaData check : dest.getContextParams()) {
                    if (check.getParamName().equals(contextParam.getParamName())) {
                        found = true;
                        // Check for a conflict
                        if (!resolveConflicts && !check.getParamValue().equals(contextParam.getParamValue())) {
                            // If the parameter name does not exist in the main
                            // web, it's an error
                            boolean found2 = false;
                            if (webMetaData.getContextParams() != null) {
                                for (ParamValueMetaData check1 : webMetaData.getContextParams()) {
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
                    mergedContextParams.add(contextParam);
            }
            dest.setContextParams(mergedContextParams);
        }

        // Session config
        if (dest.getSessionConfig() == null) {
            dest.setSessionConfig(webFragmentMetaData.getSessionConfig());
        } else if (webFragmentMetaData.getSessionConfig() != null) {
            SessionConfigMetaDataMerger.augment(dest.getSessionConfig(), webFragmentMetaData.getSessionConfig(),
                    (webMetaData != null) ? webMetaData.getSessionConfig() : null, resolveConflicts);
        }

        // Filter mappings
        if (dest.getFilterMappings() == null) {
            dest.setFilterMappings(webFragmentMetaData.getFilterMappings());
        } else if (webFragmentMetaData.getFilterMappings() != null) {
            List<FilterMappingMetaData> mergedFilterMappings = new ArrayList<FilterMappingMetaData>();
            for (FilterMappingMetaData filterMapping : dest.getFilterMappings()) {
                mergedFilterMappings.add(filterMapping);
            }
            for (FilterMappingMetaData filterMapping : webFragmentMetaData.getFilterMappings()) {
                boolean found = false;
                for (FilterMappingMetaData check : dest.getFilterMappings()) {
                    if (check.getFilterName().equals(filterMapping.getFilterName())) {
                        found = true;
                        // Augment unless an overriding descriptor redefines
                        // patterns
                        if (!resolveConflicts) {
                            FilterMappingMetaDataMerger.augment(check, filterMapping, null, resolveConflicts);
                        }
                    }
                }
                if (!found)
                    mergedFilterMappings.add(filterMapping);
            }
            dest.setFilterMappings(mergedFilterMappings);
        }

        // Filters
        if (dest.getFilters() == null) {
            dest.setFilters(webFragmentMetaData.getFilters());
        } else if (webFragmentMetaData.getFilters() != null) {
            FiltersMetaDataMerger.augment(dest.getFilters(), webFragmentMetaData.getFilters(),
                    (webMetaData != null) ? webMetaData.getFilters() : null, resolveConflicts);
        }

        // Error page
        if (dest.getErrorPages() == null) {
            dest.setErrorPages(webFragmentMetaData.getErrorPages());
        } else if (webFragmentMetaData.getErrorPages() != null) {
            List<ErrorPageMetaData> mergedErrorPages = new ArrayList<ErrorPageMetaData>();
            for (ErrorPageMetaData errorPage : dest.getErrorPages()) {
                mergedErrorPages.add(errorPage);
            }
            for (ErrorPageMetaData errorPage : webFragmentMetaData.getErrorPages()) {
                boolean found = false;
                for (ErrorPageMetaData check : dest.getErrorPages()) {
                    if (check.getErrorCode() != null && check.getErrorCode().equals(errorPage.getErrorCode())) {
                        found = true;
                        if (!resolveConflicts && check.getLocation() != null
                                && !check.getLocation().equals(errorPage.getLocation())) {
                            boolean found2 = false;
                            if (webMetaData.getErrorPages() != null) {
                                for (ErrorPageMetaData check1 : webMetaData.getErrorPages()) {
                                    if (check1.getErrorCode() != null && check1.getErrorCode().equals(check.getErrorCode())) {
                                        found2 = true;
                                        break;
                                    }
                                }
                            }
                            if (!found2)
                                throw new IllegalStateException("Unresolved conflict on error page for code: "
                                        + errorPage.getErrorCode());
                        }
                    }
                    if (check.getExceptionType() != null && check.getExceptionType().equals(errorPage.getExceptionType())) {
                        found = true;
                        if (!resolveConflicts && check.getLocation() != null
                                && !check.getLocation().equals(errorPage.getLocation())) {
                            boolean found2 = false;
                            if (webMetaData.getErrorPages() != null) {
                                for (ErrorPageMetaData check1 : webMetaData.getErrorPages()) {
                                    if (check1.getExceptionType() != null
                                            && check1.getExceptionType().equals(check.getExceptionType())) {
                                        found2 = true;
                                        break;
                                    }
                                }
                            }
                            if (!found2)
                                throw new IllegalStateException("Unresolved conflict on error page for exception type: "
                                        + errorPage.getExceptionType());
                        }
                    }
                }
                if (!found)
                    mergedErrorPages.add(errorPage);
            }
            dest.setErrorPages(mergedErrorPages);
        }

        // JSP config
        if (dest.getJspConfig() == null) {
            dest.setJspConfig(webFragmentMetaData.getJspConfig());
        } else if (webFragmentMetaData.getJspConfig() != null) {
            JspConfigMetaDataMerger.augment(dest.getJspConfig(), webFragmentMetaData.getJspConfig(),
                    (webMetaData != null) ? webMetaData.getJspConfig() : null, resolveConflicts);
        }

        // Listeners
        if (dest.getListeners() == null) {
            dest.setListeners(webFragmentMetaData.getListeners());
        } else if (webFragmentMetaData.getListeners() != null) {
            List<ListenerMetaData> mergedListeners = new ArrayList<ListenerMetaData>();
            for (ListenerMetaData listener : dest.getListeners()) {
                mergedListeners.add(listener);
            }
            for (ListenerMetaData listener : webFragmentMetaData.getListeners()) {
                boolean found = false;
                for (ListenerMetaData check : dest.getListeners()) {
                    if (check.getListenerClass().equals(listener.getListenerClass())) {
                        found = true;
                    }
                }
                if (!found)
                    mergedListeners.add(listener);
            }
            dest.setListeners(mergedListeners);
        }

        // Login config
        if (dest.getLoginConfig() == null) {
            dest.setLoginConfig(webFragmentMetaData.getLoginConfig());
        } else if (webFragmentMetaData.getLoginConfig() != null) {
            LoginConfigMetaDataMerger.augment(dest.getLoginConfig(), webFragmentMetaData.getLoginConfig(),
                    (webMetaData != null) ? webMetaData.getLoginConfig() : null, resolveConflicts);
        }

        // Mime mappings
        if (dest.getMimeMappings() == null) {
            dest.setMimeMappings(webFragmentMetaData.getMimeMappings());
        } else if (webFragmentMetaData.getMimeMappings() != null) {
            List<MimeMappingMetaData> mergedMimeMappings = new ArrayList<MimeMappingMetaData>();
            for (MimeMappingMetaData mimeMapping : dest.getMimeMappings()) {
                mergedMimeMappings.add(mimeMapping);
            }
            for (MimeMappingMetaData mimeMapping : webFragmentMetaData.getMimeMappings()) {
                boolean found = false;
                for (MimeMappingMetaData check : dest.getMimeMappings()) {
                    if (check.getExtension().equals(mimeMapping.getExtension())) {
                        found = true;
                        if (!resolveConflicts && check.getMimeType() != null
                                && !check.getMimeType().equals(mimeMapping.getMimeType())) {
                            boolean found2 = false;
                            if (webMetaData.getMimeMappings() != null) {
                                for (MimeMappingMetaData check1 : webMetaData.getMimeMappings()) {
                                    if (check1.getExtension() != null && check1.getExtension().equals(check.getExtension())) {
                                        found2 = true;
                                        break;
                                    }
                                }
                            }
                            if (!found2)
                                throw new IllegalStateException("Unresolved conflict on mime mapping for extension: "
                                        + mimeMapping.getExtension());
                        }
                    }
                }
                if (!found)
                    mergedMimeMappings.add(mimeMapping);
            }
            dest.setMimeMappings(mergedMimeMappings);
        }

        // Servlet mappings
        if (dest.getServletMappings() == null) {
            dest.setServletMappings(webFragmentMetaData.getServletMappings());
        } else if (webFragmentMetaData.getServletMappings() != null) {
            List<ServletMappingMetaData> mergedServletMappings = new ArrayList<ServletMappingMetaData>();
            for (ServletMappingMetaData servletMapping : dest.getServletMappings()) {
                mergedServletMappings.add(servletMapping);
            }
            for (ServletMappingMetaData servletMapping : webFragmentMetaData.getServletMappings()) {
                boolean found = false;
                for (ServletMappingMetaData check : dest.getServletMappings()) {
                    if (check.getServletName().equals(servletMapping.getServletName())) {
                        found = true;
                        // Augment unless an overriding descriptor redefines
                        // patterns
                        if (!resolveConflicts) {
                            ServletMappingMetaDataMerger.augment(check, servletMapping, null, resolveConflicts);
                        }
                    }
                }
                if (!found)
                    mergedServletMappings.add(servletMapping);
            }
            dest.setServletMappings(mergedServletMappings);
        }

        // Servlets
        if (dest.getServlets() == null) {
            dest.setServlets(webFragmentMetaData.getServlets());
        } else if (webFragmentMetaData.getServlets() != null) {
            ServletsMetaDataMerger.augment(dest.getServlets(), webFragmentMetaData.getServlets(),
                    (webMetaData != null) ? webMetaData.getServlets() : null, resolveConflicts);
        }

        // Security constraints
        if (dest.getSecurityConstraints() == null) {
            dest.setSecurityConstraints(webFragmentMetaData.getSecurityConstraints());
        } else if (webFragmentMetaData.getSecurityConstraints() != null) {
            List<SecurityConstraintMetaData> mergedSecurityConstraints = new ArrayList<SecurityConstraintMetaData>();
            // No conflict, but URL patterns which are already present are
            // ignored
            Set<String> urlPatterns = new HashSet<String>();
            for (SecurityConstraintMetaData securityConstraint : dest.getSecurityConstraints()) {
                mergedSecurityConstraints.add(securityConstraint);
                // Collect URL patterns for existing constraints
                if (securityConstraint.getResourceCollections() != null) {
                    for (WebResourceCollectionMetaData wrc : securityConstraint.getResourceCollections()) {
                        if (wrc.getUrlPatterns() != null) {
                            urlPatterns.addAll(wrc.getUrlPatterns());
                        }
                    }
                }
            }
            for (SecurityConstraintMetaData securityConstraint : webFragmentMetaData.getSecurityConstraints()) {
                if (securityConstraint.getResourceCollections() != null && urlPatterns.size() > 0) {
                    for (WebResourceCollectionMetaData wrc : securityConstraint.getResourceCollections()) {
                        if (wrc.getUrlPatterns() != null) {
                            List<String> newUrlPatterns = new ArrayList<String>();
                            for (String urlPattern : wrc.getUrlPatterns()) {
                                if (!urlPatterns.contains(urlPattern)) {
                                    newUrlPatterns.add(urlPattern);
                                }
                            }
                            wrc.setUrlPatterns(newUrlPatterns);
                        }
                    }
                }
                mergedSecurityConstraints.add(securityConstraint);
            }
            dest.setSecurityConstraints(mergedSecurityConstraints);
        }

        // Security roles
        if (dest.getSecurityRoles() == null) {
            dest.setSecurityRoles(webFragmentMetaData.getSecurityRoles());
        } else if (webFragmentMetaData.getSecurityRoles() != null) {
            // The merge seems to be doing what we want, and this is additive
            SecurityRolesMetaDataMerger.merge(dest.getSecurityRoles(), webFragmentMetaData.getSecurityRoles(), null);
        }

        // Welcome file list
        if (dest.getWelcomeFileList() == null) {
            dest.setWelcomeFileList(webFragmentMetaData.getWelcomeFileList());
        } else if (webFragmentMetaData.getWelcomeFileList() != null) {
            WelcomeFileListMetaDataMerger.augment(dest.getWelcomeFileList(), webFragmentMetaData.getWelcomeFileList(), null, resolveConflicts);
        }

        // Locale encoding
        if (dest.getLocalEncodings() == null) {
            dest.setLocalEncodings(webFragmentMetaData.getLocalEncodings());
        } else if (webFragmentMetaData.getLocalEncodings() != null) {
            LocaleEncodingsMetaDataMerger.augment(dest.getLocalEncodings(), webFragmentMetaData.getLocalEncodings(),
                    (webMetaData != null) ? webMetaData.getLocalEncodings() : null, resolveConflicts);
        }

        // All ENC elements except message destinations
        if (dest.getJndiEnvironmentRefsGroup() == null) {
            if (webFragmentMetaData.getJndiEnvironmentRefsGroup() != null)
                dest.setJndiEnvironmentRefsGroup(webFragmentMetaData.getJndiEnvironmentRefsGroup());
        } else if (webFragmentMetaData.getJndiEnvironmentRefsGroup() != null) {
            EnvironmentRefsGroupMetaDataMerger.augment(dest.getJndiEnvironmentRefsGroup(),
                    webFragmentMetaData.getJndiEnvironmentRefsGroup(),
                    (webMetaData != null) ? webMetaData.getJndiEnvironmentRefsGroup() : null, resolveConflicts);
        }

        // Message destinations
        if (dest.getMessageDestinations() == null) {
            dest.setMessageDestinations(webFragmentMetaData.getMessageDestinations());
        } else if (webFragmentMetaData.getMessageDestinations() != null) {
            MessageDestinationsMetaDataMerger.augment(dest.getMessageDestinations(), webFragmentMetaData
                    .getMessageDestinations(), (webMetaData != null) ? webMetaData.getMessageDestinations() : null,
                    resolveConflicts);
        }

        // Annotations
        if (dest.getAnnotations() == null) {
            dest.setAnnotations(webFragmentMetaData.getAnnotations());
        } else if (webFragmentMetaData.getAnnotations() != null) {
            AnnotationsMetaDataMerger.augment(dest.getAnnotations(), webFragmentMetaData.getAnnotations(), null,
                    resolveConflicts);
        }

    }

}
