/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.web.spec;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.metadata.javaee.spec.AnnotatedEJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.DataSourceMetaData;
import org.jboss.metadata.javaee.spec.DataSourcesMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferencesMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EmptyMetaData;
import org.jboss.metadata.javaee.spec.Environment;
import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.metadata.javaee.support.AbstractMappedMetaData;
import org.jboss.metadata.javaee.support.AugmentableMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;

/**
 * The web-app spec metadata, common between the fragments and the main web.xml
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 70996 $
 */
public class WebCommonMetaData extends IdMetaDataImplWithDescriptionGroup implements Environment,
        AugmentableMetaData<WebCommonMetaData> {
    private static final long serialVersionUID = 1;

    private String dtdPublicId;
    private String dtdSystemId;
    private String version;
    private EmptyMetaData distributable;
    private List<ParamValueMetaData> contextParams;
    private SessionConfigMetaData sessionConfig;
    private FiltersMetaData filters;
    private List<FilterMappingMetaData> filterMappings;
    private List<ErrorPageMetaData> errorPages;
    private JspConfigMetaData jspConfig;
    private List<ListenerMetaData> listeners;
    private LoginConfigMetaData loginConfig;
    private List<MimeMappingMetaData> mimeMappings;
    private ServletsMetaData servlets;
    private List<ServletMappingMetaData> servletMappings;
    private List<SecurityConstraintMetaData> securityConstraints;
    private SecurityRolesMetaData securityRoles;
    private WelcomeFileListMetaData welcomeFileList;
    private LocaleEncodingsMetaData localEncodings;

    /** The environment */
    private EnvironmentRefsGroupMetaData jndiEnvironmentRefsGroup;

    /** The message destinations */
    private MessageDestinationsMetaData messageDestinations;

    private AnnotationsMetaData annotations;

    /**
     * Callback for the DTD information
     *
     * @param root
     * @param publicId
     * @param systemId
     */
    @XmlTransient
    public void setDTD(String root, String publicId, String systemId) {
        this.dtdPublicId = publicId;
        this.dtdSystemId = systemId;
    }

    /**
     * Get the DTD public id if one was seen
     *
     * @return the value of the web.xml dtd public id
     */
    @XmlTransient
    public String getDtdPublicId() {
        return dtdPublicId;
    }

    /**
     * Get the DTD system id if one was seen
     *
     * @return the value of the web.xml dtd system id
     */
    @XmlTransient
    public String getDtdSystemId() {
        return dtdSystemId;
    }

    public String getVersion() {
        return version;
    }

    @XmlAttribute
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Is this a servlet 2.3 version application
     *
     * @return true if this is a javaee 2.3 version application
     */
    @XmlTransient
    public boolean is23() {
        return dtdPublicId != null && dtdPublicId.equals(JavaEEMetaDataConstants.J2EE_13_WEB);
    }

    @XmlTransient
    public boolean is24() {
        return version != null && version.equals("2.4");
    }

    @XmlTransient
    public boolean is25() {
        return version != null && version.equals("2.5");
    }

    @XmlTransient
    public boolean is30() {
        return version != null && version.equals("3.0");
    }

    public EmptyMetaData getDistributable() {
        return distributable;
    }

    public void setDistributable(EmptyMetaData distributable) {
        this.distributable = distributable;
    }

    public SessionConfigMetaData getSessionConfig() {
        return sessionConfig;
    }

    public void setSessionConfig(SessionConfigMetaData sessionConfig) {
        this.sessionConfig = sessionConfig;
    }

    public List<ParamValueMetaData> getContextParams() {
        return contextParams;
    }

    @XmlElement(name = "context-param")
    public void setContextParams(List<ParamValueMetaData> params) {
        this.contextParams = params;
    }

    public FiltersMetaData getFilters() {
        return filters;
    }

    @XmlElement(name = "filter")
    public void setFilters(FiltersMetaData filters) {
        this.filters = filters;
    }

    public List<FilterMappingMetaData> getFilterMappings() {
        return filterMappings;
    }

    @XmlElement(name = "filter-mapping")
    public void setFilterMappings(List<FilterMappingMetaData> filterMappings) {
        this.filterMappings = filterMappings;
    }

    public List<ErrorPageMetaData> getErrorPages() {
        return errorPages;
    }

    @XmlElement(name = "error-page")
    public void setErrorPages(List<ErrorPageMetaData> errorPages) {
        this.errorPages = errorPages;
    }

    public JspConfigMetaData getJspConfig() {
        return jspConfig;
    }

    public void setJspConfig(JspConfigMetaData jspConfig) {
        this.jspConfig = jspConfig;
    }

    public List<ListenerMetaData> getListeners() {
        return listeners;
    }

    @XmlElement(name = "listener")
    public void setListeners(List<ListenerMetaData> listeners) {
        this.listeners = listeners;
    }

    public LocaleEncodingsMetaData getLocalEncodings() {
        return localEncodings;
    }

    @XmlElement(name = "locale-encoding-mapping-list")
    public void setLocalEncodings(LocaleEncodingsMetaData localEncodings) {
        this.localEncodings = localEncodings;
    }

    public LoginConfigMetaData getLoginConfig() {
        return loginConfig;
    }

    public void setLoginConfig(LoginConfigMetaData loginConfig) {
        this.loginConfig = loginConfig;
    }

    public List<MimeMappingMetaData> getMimeMappings() {
        return mimeMappings;
    }

    @XmlElement(name = "mime-mapping")
    public void setMimeMappings(List<MimeMappingMetaData> mimeMappings) {
        this.mimeMappings = mimeMappings;
    }

    public ServletsMetaData getServlets() {
        return servlets;
    }

    @XmlElement(name = "servlet")
    public void setServlets(ServletsMetaData servlets) {
        this.servlets = servlets;
    }

    public List<ServletMappingMetaData> getServletMappings() {
        return servletMappings;
    }

    @XmlElement(name = "servlet-mapping")
    public void setServletMappings(List<ServletMappingMetaData> servletMappings) {
        this.servletMappings = servletMappings;
    }

    public List<SecurityConstraintMetaData> getSecurityConstraints() {
        return securityConstraints;
    }

    @XmlElement(name = "security-constraint")
    public void setSecurityConstraints(List<SecurityConstraintMetaData> securityConstraints) {
        this.securityConstraints = securityConstraints;
    }

    public SecurityRolesMetaData getSecurityRoles() {
        return securityRoles;
    }

    @XmlElement(name = "security-role")
    public void setSecurityRoles(SecurityRolesMetaData securityRoles) {
        this.securityRoles = securityRoles;
    }

    public WelcomeFileListMetaData getWelcomeFileList() {
        return welcomeFileList;
    }

    public void setWelcomeFileList(WelcomeFileListMetaData welcomeFileList) {
        this.welcomeFileList = welcomeFileList;
    }

    /**
     * Get the jndiEnvironmentRefsGroup.
     *
     * @return the jndiEnvironmentRefsGroup.
     */
    public EnvironmentRefsGroupMetaData getJndiEnvironmentRefsGroup() {
        return jndiEnvironmentRefsGroup;
    }

    /**
     * Set the jndiEnvironmentRefsGroup.
     *
     * @param jndiEnvironmentRefsGroup the jndiEnvironmentRefsGroup.
     * @throws IllegalArgumentException for a null jndiEnvironmentRefsGroup
     */
    public void setJndiEnvironmentRefsGroup(EnvironmentRefsGroupMetaData env) {
        if (env == null)
            throw new IllegalArgumentException("Null jndiEnvironmentRefsGroup");
        if (jndiEnvironmentRefsGroup != null)
            jndiEnvironmentRefsGroup.merge(env, null, "jboss-web.xml", "web.xml", false);
        else
            this.jndiEnvironmentRefsGroup = env;
    }

    public DataSourceMetaData getDataSourceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getDataSources());
    }

    public DataSourcesMetaData getDataSources() {
        if (jndiEnvironmentRefsGroup != null)
            return jndiEnvironmentRefsGroup.getDataSources();
        return null;
    }

    public EJBLocalReferenceMetaData getEjbLocalReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getEjbLocalReferences());
    }

    public EJBLocalReferencesMetaData getEjbLocalReferences() {
        if (jndiEnvironmentRefsGroup != null)
            return jndiEnvironmentRefsGroup.getEjbLocalReferences();
        return null;
    }

    public EJBReferenceMetaData getEjbReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getEjbReferences());
    }

    public EJBReferencesMetaData getEjbReferences() {
        if (jndiEnvironmentRefsGroup != null)
            return jndiEnvironmentRefsGroup.getEjbReferences();
        return null;
    }

    @XmlTransient
    public AnnotatedEJBReferencesMetaData getAnnotatedEjbReferences() {
        AnnotatedEJBReferencesMetaData refs = null;
        if (jndiEnvironmentRefsGroup != null)
            refs = jndiEnvironmentRefsGroup.getAnnotatedEjbReferences();
        return refs;
    }

    public EnvironmentEntriesMetaData getEnvironmentEntries() {
        if (jndiEnvironmentRefsGroup != null)
            return jndiEnvironmentRefsGroup.getEnvironmentEntries();
        return null;
    }

    public EnvironmentEntryMetaData getEnvironmentEntryByName(String name) {
        return AbstractMappedMetaData.getByName(name, getEnvironmentEntries());
    }

    public MessageDestinationReferenceMetaData getMessageDestinationReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getMessageDestinationReferences());
    }

    public MessageDestinationReferencesMetaData getMessageDestinationReferences() {
        if (jndiEnvironmentRefsGroup != null)
            return jndiEnvironmentRefsGroup.getMessageDestinationReferences();
        return null;
    }

    public PersistenceContextReferenceMetaData getPersistenceContextReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getPersistenceContextRefs());
    }

    public PersistenceContextReferencesMetaData getPersistenceContextRefs() {
        if (jndiEnvironmentRefsGroup != null)
            return jndiEnvironmentRefsGroup.getPersistenceContextRefs();
        return null;
    }

    public PersistenceUnitReferenceMetaData getPersistenceUnitReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getPersistenceUnitRefs());
    }

    public PersistenceUnitReferencesMetaData getPersistenceUnitRefs() {
        if (jndiEnvironmentRefsGroup != null)
            return jndiEnvironmentRefsGroup.getPersistenceUnitRefs();
        return null;
    }

    public LifecycleCallbacksMetaData getPostConstructs() {
        if (jndiEnvironmentRefsGroup != null)
            return jndiEnvironmentRefsGroup.getPostConstructs();
        return null;
    }

    public LifecycleCallbacksMetaData getPreDestroys() {
        if (jndiEnvironmentRefsGroup != null)
            return jndiEnvironmentRefsGroup.getPreDestroys();
        return null;
    }

    public ResourceEnvironmentReferenceMetaData getResourceEnvironmentReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getResourceEnvironmentReferences());
    }

    public ResourceEnvironmentReferencesMetaData getResourceEnvironmentReferences() {
        if (jndiEnvironmentRefsGroup != null)
            return jndiEnvironmentRefsGroup.getResourceEnvironmentReferences();
        return null;
    }

    public ResourceReferenceMetaData getResourceReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getResourceReferences());
    }

    public ResourceReferencesMetaData getResourceReferences() {
        if (jndiEnvironmentRefsGroup != null)
            return jndiEnvironmentRefsGroup.getResourceReferences();
        return null;
    }

    public ServiceReferenceMetaData getServiceReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getServiceReferences());
    }

    public ServiceReferencesMetaData getServiceReferences() {
        if (jndiEnvironmentRefsGroup != null)
            return jndiEnvironmentRefsGroup.getServiceReferences();
        return null;
    }

    public MessageDestinationsMetaData getMessageDestinations() {
        return messageDestinations;
    }

    @XmlElement(name = "message-destination")
    public void setMessageDestinations(MessageDestinationsMetaData messageDestinations) {
        this.messageDestinations = messageDestinations;
    }

    public AnnotationsMetaData getAnnotations() {
        return annotations;
    }

    public void setAnnotations(AnnotationsMetaData annotations) {
        this.annotations = annotations;
    }

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
     *        this one
     * @param webMetaData The base web meta data, used for conflict error
     *        checking
     * @param resolveConflicts If true, any conflict will be skipped without an
     *        error. Otherwise, an error will be thrown. If this is true, then
     *        as all conflict will be resolved in favor of this object,
     *        webMetaData will not be used and can be null
     */
    public void augment(WebCommonMetaData webFragmentMetaData, WebCommonMetaData webMetaData, boolean resolveConflicts) {

        // Distributable
        if (!resolveConflicts && webFragmentMetaData.getDistributable() == null && webMetaData != null) {
            webMetaData.setDistributable(null);
        }

        // Context params
        if (getContextParams() == null) {
            setContextParams(webFragmentMetaData.getContextParams());
        } else if (webFragmentMetaData.getContextParams() != null) {
            List<ParamValueMetaData> mergedContextParams = new ArrayList<ParamValueMetaData>();
            for (ParamValueMetaData contextParam : getContextParams()) {
                mergedContextParams.add(contextParam);
            }
            for (ParamValueMetaData contextParam : webFragmentMetaData.getContextParams()) {
                boolean found = false;
                for (ParamValueMetaData check : getContextParams()) {
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
            setContextParams(mergedContextParams);
        }

        // Session config
        if (getSessionConfig() == null) {
            setSessionConfig(webFragmentMetaData.getSessionConfig());
        } else if (webFragmentMetaData.getSessionConfig() != null) {
            getSessionConfig().augment(webFragmentMetaData.getSessionConfig(),
                    (webMetaData != null) ? webMetaData.getSessionConfig() : null, resolveConflicts);
        }

        // Filter mappings
        if (getFilterMappings() == null) {
            setFilterMappings(webFragmentMetaData.getFilterMappings());
        } else if (webFragmentMetaData.getFilterMappings() != null) {
            List<FilterMappingMetaData> mergedFilterMappings = new ArrayList<FilterMappingMetaData>();
            for (FilterMappingMetaData filterMapping : getFilterMappings()) {
                mergedFilterMappings.add(filterMapping);
            }
            for (FilterMappingMetaData filterMapping : webFragmentMetaData.getFilterMappings()) {
                boolean found = false;
                for (FilterMappingMetaData check : getFilterMappings()) {
                    if (check.getFilterName().equals(filterMapping.getFilterName())) {
                        found = true;
                        // Augment unless an overriding descriptor redefines
                        // patterns
                        if (!resolveConflicts) {
                            check.augment(filterMapping, null, resolveConflicts);
                        }
                    }
                }
                if (!found)
                    mergedFilterMappings.add(filterMapping);
            }
            setFilterMappings(mergedFilterMappings);
        }

        // Filters
        if (getFilters() == null) {
            setFilters(webFragmentMetaData.getFilters());
        } else if (webFragmentMetaData.getFilters() != null) {
            getFilters().augment(webFragmentMetaData.getFilters(), (webMetaData != null) ? webMetaData.getFilters() : null,
                    resolveConflicts);
        }

        // Error page
        if (getErrorPages() == null) {
            setErrorPages(webFragmentMetaData.getErrorPages());
        } else if (webFragmentMetaData.getErrorPages() != null) {
            List<ErrorPageMetaData> mergedErrorPages = new ArrayList<ErrorPageMetaData>();
            for (ErrorPageMetaData errorPage : getErrorPages()) {
                mergedErrorPages.add(errorPage);
            }
            for (ErrorPageMetaData errorPage : webFragmentMetaData.getErrorPages()) {
                boolean found = false;
                for (ErrorPageMetaData check : getErrorPages()) {
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
            setErrorPages(mergedErrorPages);
        }

        // JSP config
        if (getJspConfig() == null) {
            setJspConfig(webFragmentMetaData.getJspConfig());
        } else if (webFragmentMetaData.getJspConfig() != null) {
            getJspConfig().augment(webFragmentMetaData.getJspConfig(),
                    (webMetaData != null) ? webMetaData.getJspConfig() : null, resolveConflicts);
        }

        // Listeners
        if (getListeners() == null) {
            setListeners(webFragmentMetaData.getListeners());
        } else if (webFragmentMetaData.getListeners() != null) {
            List<ListenerMetaData> mergedListeners = new ArrayList<ListenerMetaData>();
            for (ListenerMetaData listener : getListeners()) {
                mergedListeners.add(listener);
            }
            for (ListenerMetaData listener : webFragmentMetaData.getListeners()) {
                boolean found = false;
                for (ListenerMetaData check : getListeners()) {
                    if (check.getListenerClass().equals(listener.getListenerClass())) {
                        found = true;
                    }
                }
                if (!found)
                    mergedListeners.add(listener);
            }
            setListeners(mergedListeners);
        }

        // Login config
        if (getLoginConfig() == null) {
            setLoginConfig(webFragmentMetaData.getLoginConfig());
        } else if (webFragmentMetaData.getLoginConfig() != null) {
            getLoginConfig().augment(webFragmentMetaData.getLoginConfig(),
                    (webMetaData != null) ? webMetaData.getLoginConfig() : null, resolveConflicts);
        }

        // Mime mappings
        if (getMimeMappings() == null) {
            setMimeMappings(webFragmentMetaData.getMimeMappings());
        } else if (webFragmentMetaData.getMimeMappings() != null) {
            List<MimeMappingMetaData> mergedMimeMappings = new ArrayList<MimeMappingMetaData>();
            for (MimeMappingMetaData mimeMapping : getMimeMappings()) {
                mergedMimeMappings.add(mimeMapping);
            }
            for (MimeMappingMetaData mimeMapping : webFragmentMetaData.getMimeMappings()) {
                boolean found = false;
                for (MimeMappingMetaData check : getMimeMappings()) {
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
            setMimeMappings(mergedMimeMappings);
        }

        // Servlet mappings
        if (getServletMappings() == null) {
            setServletMappings(webFragmentMetaData.getServletMappings());
        } else if (webFragmentMetaData.getServletMappings() != null) {
            List<ServletMappingMetaData> mergedServletMappings = new ArrayList<ServletMappingMetaData>();
            for (ServletMappingMetaData servletMapping : getServletMappings()) {
                mergedServletMappings.add(servletMapping);
            }
            for (ServletMappingMetaData servletMapping : webFragmentMetaData.getServletMappings()) {
                boolean found = false;
                for (ServletMappingMetaData check : getServletMappings()) {
                    if (check.getServletName().equals(servletMapping.getServletName())) {
                        found = true;
                        // Augment unless an overriding descriptor redefines
                        // patterns
                        if (!resolveConflicts) {
                            check.augment(servletMapping, null, resolveConflicts);
                        }
                    }
                }
                if (!found)
                    mergedServletMappings.add(servletMapping);
            }
            setServletMappings(mergedServletMappings);
        }

        // Servlets
        if (getServlets() == null) {
            setServlets(webFragmentMetaData.getServlets());
        } else if (webFragmentMetaData.getServlets() != null) {
            getServlets().augment(webFragmentMetaData.getServlets(), (webMetaData != null) ? webMetaData.getServlets() : null,
                    resolveConflicts);
        }

        // Security constraints
        if (getSecurityConstraints() == null) {
            setSecurityConstraints(webFragmentMetaData.getSecurityConstraints());
        } else if (webFragmentMetaData.getSecurityConstraints() != null) {
            List<SecurityConstraintMetaData> mergedSecurityConstraints = new ArrayList<SecurityConstraintMetaData>();
            // No conflict, but URL patterns which are already present are
            // ignored
            Set<String> urlPatterns = new HashSet<String>();
            for (SecurityConstraintMetaData securityConstraint : getSecurityConstraints()) {
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
            setSecurityConstraints(mergedSecurityConstraints);
        }

        // Security roles
        if (getSecurityRoles() == null) {
            setSecurityRoles(webFragmentMetaData.getSecurityRoles());
        } else if (webFragmentMetaData.getSecurityRoles() != null) {
            // The merge seems to be doing what we want, and this is additive
            getSecurityRoles().merge(webFragmentMetaData.getSecurityRoles(), null);
        }

        // Welcome file list
        if (getWelcomeFileList() == null) {
            setWelcomeFileList(webFragmentMetaData.getWelcomeFileList());
        } else if (webFragmentMetaData.getWelcomeFileList() != null) {
            getWelcomeFileList().augment(webFragmentMetaData.getWelcomeFileList(), null, resolveConflicts);
        }

        // Locale encoding
        if (getLocalEncodings() == null) {
            setLocalEncodings(webFragmentMetaData.getLocalEncodings());
        } else if (webFragmentMetaData.getLocalEncodings() != null) {
            getLocalEncodings().augment(webFragmentMetaData.getLocalEncodings(),
                    (webMetaData != null) ? webMetaData.getLocalEncodings() : null, resolveConflicts);
        }

        // All ENC elements except message destinations
        if (getJndiEnvironmentRefsGroup() == null) {
            if (webFragmentMetaData.getJndiEnvironmentRefsGroup() != null)
                setJndiEnvironmentRefsGroup(webFragmentMetaData.getJndiEnvironmentRefsGroup());
        } else if (webFragmentMetaData.getJndiEnvironmentRefsGroup() != null) {
            getJndiEnvironmentRefsGroup().augment(webFragmentMetaData.getJndiEnvironmentRefsGroup(),
                    (webMetaData != null) ? webMetaData.getJndiEnvironmentRefsGroup() : null, resolveConflicts);
        }

        // Message destinations
        if (getMessageDestinations() == null) {
            setMessageDestinations(webFragmentMetaData.getMessageDestinations());
        } else if (webFragmentMetaData.getMessageDestinations() != null) {
            getMessageDestinations().augment(webFragmentMetaData.getMessageDestinations(),
                    (webMetaData != null) ? webMetaData.getMessageDestinations() : null, resolveConflicts);
        }

        // Annotations
        if (getAnnotations() == null) {
            setAnnotations(webFragmentMetaData.getAnnotations());
        } else if (webFragmentMetaData.getAnnotations() != null) {
            getAnnotations().augment(webFragmentMetaData.getAnnotations(), null, resolveConflicts);
        }

    }

}
