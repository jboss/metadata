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
package org.jboss.metadata.web.spec;

import java.util.List;

import org.jboss.metadata.javaee.spec.AdministeredObjectMetaData;
import org.jboss.metadata.javaee.spec.AdministeredObjectsMetaData;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.ConnectionFactoriesMetaData;
import org.jboss.metadata.javaee.spec.ConnectionFactoryMetaData;
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
import org.jboss.metadata.javaee.spec.JMSConnectionFactoriesMetaData;
import org.jboss.metadata.javaee.spec.JMSConnectionFactoryMetaData;
import org.jboss.metadata.javaee.spec.JMSDestinationMetaData;
import org.jboss.metadata.javaee.spec.JMSDestinationsMetaData;
import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.MailSessionMetaData;
import org.jboss.metadata.javaee.spec.MailSessionsMetaData;
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
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;

/**
 * The web-app spec metadata, common between the fragments and the main web.xml
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 70996 $
 */
public class WebCommonMetaData extends IdMetaDataImplWithDescriptionGroup implements Environment {
    private static final long serialVersionUID = 1;

    private String dtdPublicId;
    private String dtdSystemId;
    private String version;
    private String schemaLocation;
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

    /**
     * The environment
     */
    private EnvironmentRefsGroupMetaData jndiEnvironmentRefsGroup;

    /**
     * The message destinations
     */
    private MessageDestinationsMetaData messageDestinations;

    private AnnotationsMetaData annotations;

    /**
     * Callback for the DTD information
     *
     * @param root
     * @param publicId
     * @param systemId
     */
    public void setDTD(String root, String publicId, String systemId) {
        this.dtdPublicId = publicId;
        this.dtdSystemId = systemId;
    }

    /**
     * Get the DTD public id if one was seen
     *
     * @return the value of the web.xml dtd public id
     */
    public String getDtdPublicId() {
        return dtdPublicId;
    }

    /**
     * Get the DTD system id if one was seen
     *
     * @return the value of the web.xml dtd system id
     */
    public String getDtdSystemId() {
        return dtdSystemId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    /**
     * Is this a servlet 2.3 versionString application
     *
     * @return true if this is a javaee 2.3 versionString application
     */
    public boolean is23() {
        return dtdPublicId != null && dtdPublicId.equals(JavaEEMetaDataConstants.J2EE_13_WEB);
    }

    public boolean is24() {
        return version != null && version.equals("2.4");
    }

    public boolean is25() {
        return version != null && version.equals("2.5");
    }

    public boolean is30() {
        return version != null && version.equals("3.0");
    }

    public boolean is31() {
        return version != null && version.equals("3.1");
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

    public void setContextParams(List<ParamValueMetaData> params) {
        this.contextParams = params;
    }

    public FiltersMetaData getFilters() {
        return filters;
    }

    public void setFilters(FiltersMetaData filters) {
        this.filters = filters;
    }

    public List<FilterMappingMetaData> getFilterMappings() {
        return filterMappings;
    }

    public void setFilterMappings(List<FilterMappingMetaData> filterMappings) {
        this.filterMappings = filterMappings;
    }

    public List<ErrorPageMetaData> getErrorPages() {
        return errorPages;
    }

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

    public void setListeners(List<ListenerMetaData> listeners) {
        this.listeners = listeners;
    }

    public LocaleEncodingsMetaData getLocalEncodings() {
        return localEncodings;
    }

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

    public void setMimeMappings(List<MimeMappingMetaData> mimeMappings) {
        this.mimeMappings = mimeMappings;
    }

    public ServletsMetaData getServlets() {
        return servlets;
    }

    public void setServlets(ServletsMetaData servlets) {
        this.servlets = servlets;
    }

    public List<ServletMappingMetaData> getServletMappings() {
        return servletMappings;
    }

    public void setServletMappings(List<ServletMappingMetaData> servletMappings) {
        this.servletMappings = servletMappings;
    }

    public List<SecurityConstraintMetaData> getSecurityConstraints() {
        return securityConstraints;
    }

    public void setSecurityConstraints(List<SecurityConstraintMetaData> securityConstraints) {
        this.securityConstraints = securityConstraints;
    }

    public SecurityRolesMetaData getSecurityRoles() {
        return securityRoles;
    }

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
     * IT DOESN'T MERGE
     *
     * @param jndiEnvironmentRefsGroup the jndiEnvironmentRefsGroup.
     * @throws IllegalArgumentException for a null jndiEnvironmentRefsGroup
     */
    public void setJndiEnvironmentRefsGroup(EnvironmentRefsGroupMetaData env) {
        if (env == null)
            throw new IllegalArgumentException("Null jndiEnvironmentRefsGroup");
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

    @Override
    public AdministeredObjectsMetaData getAdministeredObjects() {
        return jndiEnvironmentRefsGroup != null ? jndiEnvironmentRefsGroup.getAdministeredObjects() : null;
    }

    @Override
    public AdministeredObjectMetaData getAdministeredObjectByName(String name) throws IllegalArgumentException {
        return jndiEnvironmentRefsGroup != null ? jndiEnvironmentRefsGroup.getAdministeredObjectByName(name) : null;
    }

    @Override
    public ConnectionFactoriesMetaData getConnectionFactories() {
        return jndiEnvironmentRefsGroup != null ? jndiEnvironmentRefsGroup.getConnectionFactories() : null;
    }

    @Override
    public ConnectionFactoryMetaData getConnectionFactoryByName(String name) throws IllegalArgumentException {
        return jndiEnvironmentRefsGroup != null ? jndiEnvironmentRefsGroup.getConnectionFactoryByName(name) : null;
    }

    @Override
    public JMSConnectionFactoriesMetaData getJmsConnectionFactories() {
        return jndiEnvironmentRefsGroup != null ? jndiEnvironmentRefsGroup.getJmsConnectionFactories() : null;
    }

    @Override
    public JMSConnectionFactoryMetaData getJmsConnectionFactoryByName(String name) throws IllegalArgumentException {
        return jndiEnvironmentRefsGroup != null ? jndiEnvironmentRefsGroup.getJmsConnectionFactoryByName(name) : null;
    }

    @Override
    public JMSDestinationsMetaData getJmsDestinations() {
        return jndiEnvironmentRefsGroup != null ? jndiEnvironmentRefsGroup.getJmsDestinations() : null;
    }

    @Override
    public JMSDestinationMetaData getJmsDestinationByName(String name) throws IllegalArgumentException {
        return jndiEnvironmentRefsGroup != null ? jndiEnvironmentRefsGroup.getJmsDestinationByName(name) : null;
    }

    @Override
    public MailSessionsMetaData getMailSessions() {
        return jndiEnvironmentRefsGroup != null ? jndiEnvironmentRefsGroup.getMailSessions() : null;
    }

    @Override
    public MailSessionMetaData getMailSessionByName(String name) throws IllegalArgumentException {
        return jndiEnvironmentRefsGroup != null ? jndiEnvironmentRefsGroup.getMailSessionByName(name) : null;
    }

    public MessageDestinationsMetaData getMessageDestinations() {
        return messageDestinations;
    }

    public void setMessageDestinations(MessageDestinationsMetaData messageDestinations) {
        this.messageDestinations = messageDestinations;
    }

    public AnnotationsMetaData getAnnotations() {
        return annotations;
    }

    public void setAnnotations(AnnotationsMetaData annotations) {
        this.annotations = annotations;
    }
}
