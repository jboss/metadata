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

import org.jboss.metadata.javaee.spec.Environment;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.merge.javaee.spec.EnvironmentRefsGroupMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.MessageDestinationsMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.SecurityRolesMetaDataMerger;
import org.jboss.metadata.web.spec.DispatcherType;
import org.jboss.metadata.web.spec.FilterMappingMetaData;
import org.jboss.metadata.web.spec.FilterMetaData;
import org.jboss.metadata.web.spec.FiltersMetaData;
import org.jboss.metadata.web.spec.ListenerMetaData;
import org.jboss.metadata.web.spec.ServletMappingMetaData;
import org.jboss.metadata.web.spec.ServletMetaData;
import org.jboss.metadata.web.spec.ServletsMetaData;
import org.jboss.metadata.web.spec.WebCommonMetaData;
import org.jboss.metadata.web.spec.WebMetaData;

/**
 * Create a merged WebMetaData view from an xml + annotation views
 *
 * @author Scott.Stark@jboss.org
 * @author Anil.Saldhana@jboss.org
 * @author Remy Maucherat
 * @version $Revision: 70998 $
 */
public class AnnotationMergedViewMerger {

    public static void merge(WebCommonMetaData merged, WebCommonMetaData xml, WebMetaData annotation) {
        // Merge the servlets meta data
        ServletsMetaData servletsMetaData = new ServletsMetaData();
        merge(servletsMetaData, xml.getServlets(), annotation.getServlets());
        merged.setServlets(servletsMetaData);

        // Merge the servlet mappings meta data
        List<ServletMappingMetaData> servletMappingsMetaData = new ArrayList<ServletMappingMetaData>();
        mergeS(servletMappingsMetaData, xml.getServletMappings(), annotation.getServletMappings());
        merged.setServletMappings(servletMappingsMetaData);

        // Merge the filters meta data
        FiltersMetaData filtersMetaData = new FiltersMetaData();
        merge(filtersMetaData, xml.getFilters(), annotation.getFilters());
        merged.setFilters(filtersMetaData);

        // Merge the filter mappings meta data
        List<FilterMappingMetaData> filterMappingsMetaData = new ArrayList<FilterMappingMetaData>();
        mergeF(filterMappingsMetaData, xml.getFilterMappings(), annotation.getFilterMappings());
        merged.setFilterMappings(filterMappingsMetaData);

        // Listeners meta data
        List<ListenerMetaData> listenerMappingsMetaData = new ArrayList<ListenerMetaData>();
        mergeL(listenerMappingsMetaData, xml.getListeners(), annotation.getListeners());
        merged.setListeners(listenerMappingsMetaData);

        // Security Roles
        SecurityRolesMetaData securityRolesMetaData = new SecurityRolesMetaData();
        merge(securityRolesMetaData, xml.getSecurityRoles(), annotation.getSecurityRoles());
        merged.setSecurityRoles(securityRolesMetaData);

        // Env
        EnvironmentRefsGroupMetaData environmentRefsGroup = new EnvironmentRefsGroupMetaData();
        Environment xmlEnv = xml != null ? xml.getJndiEnvironmentRefsGroup() : null;
        Environment annEnv = annotation != null ? annotation.getJndiEnvironmentRefsGroup() : null;
        EnvironmentRefsGroupMetaDataMerger.merge(environmentRefsGroup, xmlEnv, annEnv, "", "", false);
        merged.setJndiEnvironmentRefsGroup(environmentRefsGroup);

        // Message Destinations
        MessageDestinationsMetaData messageDestinations = new MessageDestinationsMetaData();
        MessageDestinationsMetaDataMerger.merge(messageDestinations, xml.getMessageDestinations(), annotation.getMessageDestinations());
        merged.setMessageDestinations(messageDestinations);

        // merge annotation
        mergeIn(merged, annotation);
        // merge xml override
        mergeIn(merged, xml);
    }

    private static void mergeS(List<ServletMappingMetaData> merged, List<ServletMappingMetaData> xml,
                               List<ServletMappingMetaData> annotation) {
        if (xml != null) {
            for (ServletMappingMetaData servletMapping : xml) {
                ServletMappingMetaData newServletMapping = new ServletMappingMetaData();
                newServletMapping.setServletName(servletMapping.getServletName());
                if (servletMapping.getUrlPatterns() != null) {
                    List<String> urlPatterns = new ArrayList<String>();
                    for (String urlPattern : servletMapping.getUrlPatterns()) {
                        urlPatterns.add(urlPattern);
                    }
                    newServletMapping.setUrlPatterns(urlPatterns);
                }
                merged.add(newServletMapping);
            }
        }
        if (annotation != null) {
            for (ServletMappingMetaData servletMapping : annotation) {
                boolean found = false;
                for (ServletMappingMetaData check : merged) {
                    if (check.getServletName().equals(servletMapping.getServletName())) {
                        found = true;
                        for (String urlPattern : servletMapping.getUrlPatterns()) {
                            if (!check.getUrlPatterns().contains(urlPattern))
                                check.getUrlPatterns().add(urlPattern);
                        }
                    }
                }
                if (!found) {
                    ServletMappingMetaData newServletMapping = new ServletMappingMetaData();
                    newServletMapping.setServletName(servletMapping.getServletName());
                    if (servletMapping.getUrlPatterns() != null) {
                        List<String> urlPatterns = new ArrayList<String>();
                        for (String urlPattern : servletMapping.getUrlPatterns()) {
                            urlPatterns.add(urlPattern);
                        }
                        newServletMapping.setUrlPatterns(urlPatterns);
                    }
                    merged.add(newServletMapping);
                }
            }
        }
    }

    private static void mergeF(List<FilterMappingMetaData> merged, List<FilterMappingMetaData> xml,
                               List<FilterMappingMetaData> annotation) {
        if (xml != null) {
            for (FilterMappingMetaData filterMapping : xml) {
                FilterMappingMetaData newFilterMapping = new FilterMappingMetaData();
                newFilterMapping.setFilterName(filterMapping.getFilterName());
                if (filterMapping.getUrlPatterns() != null) {
                    List<String> urlPatterns = new ArrayList<String>();
                    for (String urlPattern : filterMapping.getUrlPatterns()) {
                        urlPatterns.add(urlPattern);
                    }
                    newFilterMapping.setUrlPatterns(urlPatterns);
                }
                if (filterMapping.getServletNames() != null) {
                    List<String> servletNames = new ArrayList<String>();
                    for (String servletName : filterMapping.getServletNames()) {
                        servletNames.add(servletName);
                    }
                    newFilterMapping.setServletNames(servletNames);
                }
                if (filterMapping.getDispatchers() != null) {
                    List<DispatcherType> dispatchers = new ArrayList<DispatcherType>();
                    for (DispatcherType dispatcher : filterMapping.getDispatchers()) {
                        dispatchers.add(dispatcher);
                    }
                    newFilterMapping.setDispatchers(dispatchers);
                }
                merged.add(newFilterMapping);
            }
        }
        if (annotation != null) {
            for (FilterMappingMetaData filterMapping : annotation) {
                boolean found = false;
                for (FilterMappingMetaData check : merged) {
                    if (check.getFilterName().equals(filterMapping.getFilterName())) {
                        found = true;
                        for (String urlPattern : filterMapping.getUrlPatterns()) {
                            if (!check.getUrlPatterns().contains(urlPattern))
                                check.getUrlPatterns().add(urlPattern);
                        }
                        for (String servletName : filterMapping.getServletNames()) {
                            if (!check.getServletNames().contains(servletName))
                                check.getServletNames().add(servletName);
                        }
                        for (DispatcherType dispatcher : filterMapping.getDispatchers()) {
                            if (!check.getDispatchers().contains(dispatcher))
                                check.getDispatchers().add(dispatcher);
                        }
                    }
                }
                if (!found) {
                    FilterMappingMetaData newFilterMapping = new FilterMappingMetaData();
                    newFilterMapping.setFilterName(filterMapping.getFilterName());
                    if (filterMapping.getUrlPatterns() != null) {
                        List<String> urlPatterns = new ArrayList<String>();
                        for (String urlPattern : filterMapping.getUrlPatterns()) {
                            urlPatterns.add(urlPattern);
                        }
                        newFilterMapping.setUrlPatterns(urlPatterns);
                    }
                    if (filterMapping.getServletNames() != null) {
                        List<String> servletNames = new ArrayList<String>();
                        for (String servletName : filterMapping.getServletNames()) {
                            servletNames.add(servletName);
                        }
                        newFilterMapping.setServletNames(servletNames);
                    }
                    if (filterMapping.getDispatchers() != null) {
                        List<DispatcherType> dispatchers = new ArrayList<DispatcherType>();
                        for (DispatcherType dispatcher : filterMapping.getDispatchers()) {
                            dispatchers.add(dispatcher);
                        }
                        newFilterMapping.setDispatchers(dispatchers);
                    }
                    merged.add(newFilterMapping);
                }
            }
        }
    }

    private static void mergeL(List<ListenerMetaData> merged, List<ListenerMetaData> xml, List<ListenerMetaData> annotation) {
        if (xml != null) {
            for (ListenerMetaData listener : xml) {
                ListenerMetaData newListener = new ListenerMetaData();
                newListener.setListenerClass(listener.getListenerClass());
                merged.add(newListener);
            }
        }
        if (annotation != null) {
            for (ListenerMetaData listener : annotation) {
                boolean found = false;
                for (ListenerMetaData check : merged) {
                    if (check.getListenerClass().equals(listener.getListenerClass())) {
                        found = true;
                    }
                }
                if (!found) {
                    ListenerMetaData newListener = new ListenerMetaData();
                    newListener.setListenerClass(listener.getListenerClass());
                    merged.add(newListener);
                }
            }
        }
    }

    private static void merge(ServletsMetaData merged, ServletsMetaData xml, ServletsMetaData annotation) {
        if (xml == null) {
            if (annotation != null) {
                merged.addAll(annotation);
            }
        } else {
            merged.addAll(xml);
            if (annotation != null) {
                for (ServletMetaData servlet : annotation) {
                    if (xml.get(servlet.getServletName()) == null) {
                        merged.add(servlet);
                    }
                }
            }
        }
    }

    /*
     * No longer appropriate, as the funny matching for the class name can only
     * be done at the very end, after running the listeners. Used to work
     * because web.xml was actually a full representation of the meta data. Now
     * the fake "annotation" servlets are kept for the web deployer, which will
     * generate corresponding meta data. private static void
     * merge(ServletsMetaData merged, ServletsMetaData xml, ServletsMetaData
     * annotation) { HashMap<String,String> servletClassToName = new
     * HashMap<String,String>(); if(xml != null) { if(xml.getId() != null)
     * merged.setId(xml.getId()); for(ServletMetaData servlet : xml) { String
     * className = servlet.getServletName(); if(className != null) { // Use the
     * unqualified name int dot = className.lastIndexOf('.'); if(dot >= 0)
     * className = className.substring(dot+1); servletClassToName.put(className,
     * servlet.getServletName()); } } }
     *
     * // First get the annotation beans without an xml entry if(annotation !=
     * null) { for(ServletMetaData servlet : annotation) { if(xml != null) { //
     * This is either the servlet-name or the servlet-class simple name String
     * servletName = servlet.getServletName(); ServletMetaData match =
     * xml.get(servletName); if(match == null) { // Lookup by the unqualified
     * servlet class String xmlServletName =
     * servletClassToName.get(servletName); if(xmlServletName == null)
     * merged.add(servlet); } } else { merged.add(servlet); } } } // Now merge
     * the xml and annotations if(xml != null) { for(ServletMetaData servlet :
     * xml) { ServletMetaData annServlet = null; if(annotation != null) { String
     * name = servlet.getServletName(); annServlet = annotation.get(name);
     * if(annServlet == null) { // Lookup by the unqualified servlet class
     * String className = servlet.getServletClass(); if(className != null) { //
     * Use the unqualified name int dot = className.lastIndexOf('.'); if(dot >=
     * 0) className = className.substring(dot+1); annServlet =
     * annotation.get(className); } } } // Merge ServletMetaData
     * mergedServletMetaData = servlet; if(annServlet != null) {
     * mergedServletMetaData = new ServletMetaData();
     * mergedServletMetaData.merge(servlet, annServlet); }
     * merged.add(mergedServletMetaData); } } }
     */

    private static void merge(FiltersMetaData merged, FiltersMetaData xml, FiltersMetaData annotation) {
        if (xml == null) {
            if (annotation != null) {
                merged.addAll(annotation);
            }
        } else {
            merged.addAll(xml);
            if (annotation != null) {
                for (FilterMetaData filter : annotation) {
                    if (xml.get(filter.getFilterName()) == null) {
                        merged.add(filter);
                    }
                }
            }
        }
    }

    private static void merge(SecurityRolesMetaData merged, SecurityRolesMetaData xml, SecurityRolesMetaData annotation) {
        SecurityRolesMetaDataMerger.merge(merged, xml, annotation);
    }

    private static void mergeIn(WebCommonMetaData merged, WebCommonMetaData xml) {
        merged.setDTD("", xml.getDtdPublicId(), xml.getDtdSystemId());

        // Version
        if (xml.getVersion() != null)
            merged.setVersion(xml.getVersion());

        // Description Group
        if (xml.getDescriptionGroup() != null)
            merged.setDescriptionGroup(xml.getDescriptionGroup());

        // Merge the Params
        if (xml.getContextParams() != null)
            merged.setContextParams(xml.getContextParams());

        // Distributable
        if (xml.getDistributable() != null)
            merged.setDistributable(xml.getDistributable());

        // Session Config
        if (xml.getSessionConfig() != null)
            merged.setSessionConfig(xml.getSessionConfig());

        // Error Pages
        if (xml.getErrorPages() != null)
            merged.setErrorPages(xml.getErrorPages());

        // JSP Config
        if (xml.getJspConfig() != null)
            merged.setJspConfig(xml.getJspConfig());

        // Login Config
        if (xml.getLoginConfig() != null)
            merged.setLoginConfig(xml.getLoginConfig());

        // Mime
        if (xml.getMimeMappings() != null)
            merged.setMimeMappings(xml.getMimeMappings());

        // Security Constraints
        if (xml.getSecurityConstraints() != null)
            merged.setSecurityConstraints(xml.getSecurityConstraints());

        // Welcome Files
        if (xml.getWelcomeFileList() != null)
            merged.setWelcomeFileList(xml.getWelcomeFileList());

        // Local Encodings
        if (xml.getLocalEncodings() != null)
            merged.setLocalEncodings(xml.getLocalEncodings());

        // Annotations
        if (xml.getAnnotations() != null)
            merged.setAnnotations(xml.getAnnotations());

    }
}
