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
package org.jboss.metadata.web.jboss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.metadata.common.jboss.WebserviceDescriptionsMetaData;
import org.jboss.metadata.common.ejb.JBossEnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.jboss.RunAsIdentityMetaData;
import org.jboss.metadata.javaee.spec.EmptyMetaData;
import org.jboss.metadata.javaee.spec.Environment;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;
import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.javaee.spec.RunAsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.javaee.support.NamedModuleImpl;
import org.jboss.metadata.web.spec.AnnotationMetaData;
import org.jboss.metadata.web.spec.ErrorPageMetaData;
import org.jboss.metadata.web.spec.FilterMappingMetaData;
import org.jboss.metadata.web.spec.FiltersMetaData;
import org.jboss.metadata.web.spec.HttpMethodConstraintMetaData;
import org.jboss.metadata.web.spec.JspConfigMetaData;
import org.jboss.metadata.web.spec.ListenerMetaData;
import org.jboss.metadata.web.spec.LocaleEncodingsMetaData;
import org.jboss.metadata.web.spec.LoginConfigMetaData;
import org.jboss.metadata.web.spec.MimeMappingMetaData;
import org.jboss.metadata.web.spec.MultipartConfigMetaData;
import org.jboss.metadata.web.spec.SecurityConstraintMetaData;
import org.jboss.metadata.web.spec.ServletMappingMetaData;
import org.jboss.metadata.web.spec.ServletMetaData;
import org.jboss.metadata.web.spec.ServletSecurityMetaData;
import org.jboss.metadata.web.spec.SessionConfigMetaData;
import org.jboss.metadata.web.spec.WelcomeFileListMetaData;

/**
 * The combined web.xml/jboss-web.xml metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */
public class JBossWebMetaData extends NamedModuleImpl {
    private static final long serialVersionUID = 1;

    // Spec information
    private String servletVersion;
    private EmptyMetaData distributable;
    private boolean metadataComplete;
    private List<ParamValueMetaData> contextParams;
    private SessionConfigMetaData sessionConfig;
    private FiltersMetaData filters;
    private List<FilterMappingMetaData> filterMappings;
    private List<ErrorPageMetaData> errorPages;
    private JspConfigMetaData jspConfig;
    private List<ListenerMetaData> listeners;
    private LoginConfigMetaData loginConfig;
    private List<MimeMappingMetaData> mimeMappings;
    private List<ServletMappingMetaData> servletMappings;
    private List<SecurityConstraintMetaData> securityConstraints;
    private WelcomeFileListMetaData welcomeFileList;
    private LocaleEncodingsMetaData localEncodings;
    private Boolean denyUncoveredHttpMethods;

    // JBoss extended information
    private String dtdPublicId;
    private String dtdSystemId;
    private String version;
    private String contextRoot;
    private boolean disableCrossContext;
    private String alternativeDD;
    private String securityDomain;
    private String jaccContextID;
    /**
     * A list of extra dependencies to wait on
     */
    @Deprecated
    private List<String> depends;
    /** */
    private Map<String, RunAsIdentityMetaData> runAsIdentity = new ConcurrentHashMap<String, RunAsIdentityMetaData>();
    private SecurityRolesMetaData securityRoles = new SecurityRolesMetaData();
    /**
     * The servlets
     */
    private JBossServletsMetaData servlets = new JBossServletsMetaData();
    /**
     * The message destinations
     */
    /**
     * The environment
     */
    private EnvironmentRefsGroupMetaData jndiEnvironmentRefsGroup;
    /**
     * The web app virtual host list
     */
    private List<String> virtualHosts;
    private boolean flushOnSessionInvalidation;
    private ReplicationConfig replicationConfig;
    private WebserviceDescriptionsMetaData webserviceDescriptions = new WebserviceDescriptionsMetaData();
    private Boolean jaccAllStoreRole;
    private JBossAnnotationsMetaData annotations;
    private boolean useJBossAuthorization = false;
    private boolean disableAudit = true;
    private boolean symbolicLinkingEnabled = false;
    private boolean enableWebSockets = true;

    private List<ContainerListenerMetaData> containerListeners;
    private List<ValveMetaData> valves;
    private List<HttpHandlerMetaData> handlers;
    private List<String> overlays;

    /**
     * reference to servlet container name
     */
    private String servletContainerName;

    /**
     * reference to server instance name
     */

    private String serverInstanceName;

    /**
     * Distinct name for this web application
     */
    private String distinctName;

    /**
     * The maximum number of active sessions allowed, or -1 for no limit
     */
    private Integer maxActiveSessions = null;

    /**
     * The name of the executor to use to handle requests from this deployment (Undertow only)
     */
    private String executorName;

    /**
     * Should the context use session cookies or use default
     */
    private int sessionCookies = SESSION_COOKIES_DEFAULT;

    private String defaultEncoding;

    private String requestCharacterEncoding;
    private String responseCharacterEncoding;
    /**
     * If Undertow should always attempt to authenticate if credentials are present
     */
    private Boolean proactiveAuthentication = null;


    public static final int SESSION_COOKIES_DEFAULT = 0;
    public static final int SESSION_COOKIES_ENABLED = 1;
    public static final int SESSION_COOKIES_DISABLED = 2;


    /**
     * Get the DTD public id if one was seen
     *
     * @return the value of the web.xml dtd public id
     */
    public String getDtdPublicId() {
        return dtdPublicId;
    }

    public void setDtdPublicId(String dtdPublicId) {
        this.dtdPublicId = dtdPublicId;
    }

    /**
     * Get the DTD system id if one was seen
     *
     * @return the value of the web.xml dtd system id
     */
    public String getDtdSystemId() {
        return dtdSystemId;
    }

    public void setDtdSystemId(String dtdSystemId) {
        this.dtdSystemId = dtdSystemId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getServletVersion() {
        return servletVersion;
    }

    public void setServletVersion(String servletVersion) {
        this.servletVersion = servletVersion;
    }

    /**
     * Is this a servlet 2.3 versionString application
     *
     * @return true if this is a javaee 2.3 versionString application
     */
    public boolean is23() {
        return servletVersion != null && servletVersion.equals(JavaEEMetaDataConstants.J2EE_13_WEB);
    }

    public boolean is24() {
        return servletVersion != null && servletVersion.equals("2.4");
    }

    public boolean is25() {
        return servletVersion != null && servletVersion.equals("2.5");
    }

    public boolean is30() {
        return servletVersion != null && servletVersion.equals("3.0");
    }

    public boolean isMetadataComplete() {
        return metadataComplete;
    }

    public void setMetadataComplete(boolean metadataComplete) {
        this.metadataComplete = metadataComplete;
    }

    public boolean isDisableCrossContext() {
        return disableCrossContext;
    }

    public void setDisableCrossContext(boolean disableCrossContext) {
        this.disableCrossContext = disableCrossContext;
    }

    public boolean isEnableWebSockets() {
        return enableWebSockets;
    }

    public void setEnableWebSockets(boolean enableWebSockets) {
        this.enableWebSockets = enableWebSockets;
    }

    public JBossAnnotationsMetaData getAnnotations() {
        return annotations;
    }

    public void setAnnotations(JBossAnnotationsMetaData annotations) {
        this.annotations = annotations;
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

    public JBossServletMetaData getServletByName(String name) {
        JBossServletMetaData servlet = null;
        if (servlets != null) { servlet = servlets.get(name); }
        return servlet;
    }

    public JBossServletsMetaData getServlets() {
        return servlets;
    }

    public void setServlets(JBossServletsMetaData servlets) {
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

    public String getAlternativeDD() {
        return alternativeDD;
    }

    public void setAlternativeDD(String alternativeDD) {
        this.alternativeDD = alternativeDD;
    }

    public String getContextRoot() {
        return contextRoot;
    }

    public void setContextRoot(String contextRoot) {
        this.contextRoot = contextRoot;
    }

    public List<String> getDepends() {
        return depends;
    }

    public void setDepends(List<String> depends) {
        this.depends = depends;
    }

    public String getJaccContextID() {
        return jaccContextID;
    }

    public void setJaccContextID(String jaccContextID) {
        this.jaccContextID = jaccContextID;
    }

    public String getSecurityDomain() {
        return securityDomain;
    }

    public void setSecurityDomain(String securityDomain) {
        if (securityDomain == null) { throw new IllegalArgumentException("securityDomain is null"); }
        this.securityDomain = securityDomain.trim();
    }

    public boolean isUseJBossAuthorization() {
        return useJBossAuthorization;
    }

    public void setUseJBossAuthorization(boolean useJBossAuthorization) {
        this.useJBossAuthorization = useJBossAuthorization;
    }

    public boolean isDisableAudit() {
        return disableAudit;
    }

    public void setDisableAudit(boolean disableAudit) {
        this.disableAudit = disableAudit;
    }

    public boolean isFlushOnSessionInvalidation() {
        return flushOnSessionInvalidation;
    }

    public void setFlushOnSessionInvalidation(boolean flushOnSessionInvalidation) {
        this.flushOnSessionInvalidation = flushOnSessionInvalidation;
    }

    public Integer getMaxActiveSessions() {
        return maxActiveSessions;
    }

    public void setMaxActiveSessions(Integer maxActiveSessions) {
        this.maxActiveSessions = maxActiveSessions;
    }

    public ReplicationConfig getReplicationConfig() {
        return replicationConfig;
    }

    public void setReplicationConfig(ReplicationConfig replicationConfig) {
        this.replicationConfig = replicationConfig;
    }

    public int getSessionCookies() {
        return this.sessionCookies;
    }

    public void setSessionCookies(int sessionCookies) {
        this.sessionCookies = sessionCookies;
    }

    public List<String> getVirtualHosts() {
        return virtualHosts;
    }

    public void setVirtualHosts(List<String> virtualHosts) {
        this.virtualHosts = virtualHosts;
    }

    public WebserviceDescriptionsMetaData getWebserviceDescriptions() {
        return webserviceDescriptions;
    }

    public void setWebserviceDescriptions(WebserviceDescriptionsMetaData webserviceDescriptions) {
        this.webserviceDescriptions = webserviceDescriptions;
    }

    public List<ContainerListenerMetaData> getContainerListeners() {
        return containerListeners;
    }

    public void setContainerListeners(
            List<ContainerListenerMetaData> containerListeners) {
        this.containerListeners = containerListeners;
    }

    public List<ValveMetaData> getValves() {
        return valves;
    }

    public void setValves(List<ValveMetaData> valves) {
        this.valves = valves;
    }

    public List<HttpHandlerMetaData> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<HttpHandlerMetaData> handlers) {
        this.handlers = handlers;
    }

    public List<String> getOverlays() {
        return overlays;
    }

    public void setOverlays(List<String> overlays) {
        this.overlays = overlays;
    }

    public String getServletContainerName() {
        return servletContainerName;
    }

    public void setServletContainerName(String servletContainerName) {
        this.servletContainerName = servletContainerName;
    }

    public String getServerInstanceName() {
        return serverInstanceName;
    }

    public void setServerInstanceName(String serverInstanceName) {
        this.serverInstanceName = serverInstanceName;
    }

    /**
     * Get the security-role names from the web.xml descriptor
     *
     * @return Set<String> of the security-role names from the web.xml
     */
    public Set<String> getSecurityRoleNames() {
        return new HashSet<String>(securityRoles.keySet());
    }

    /**
     * Get the servlet/security-role-refs
     *
     * @param servletName
     * @return
     */
    public SecurityRoleRefsMetaData getSecurityRoleRefs(String servletName) {
        SecurityRoleRefsMetaData refs = null;
        if (getServlets() != null) {
            ServletMetaData servlet = getServlets().get(servletName);
            if (servlet != null) {
                refs = servlet.getSecurityRoleRefs();
            }
        }
        return refs;
    }

    public Map<String, Set<String>> getPrincipalVersusRolesMap() {
        Map<String, Set<String>> principalRolesMap = null;

        for (SecurityRoleMetaData srm : securityRoles) {
            String rolename = srm.getRoleName();
            if (principalRolesMap == null) { principalRolesMap = new HashMap<String, Set<String>>(); }
            if (srm.getPrincipals() != null) {
                for (String pr : srm.getPrincipals()) {
                    Set<String> roleset = principalRolesMap.get(pr);
                    if (roleset == null) { roleset = new HashSet<String>(); }
                    if (!roleset.contains(rolename)) { roleset.add(rolename); }
                    principalRolesMap.put(pr, roleset);
                }
            }
        }
        return principalRolesMap;
    }

    public String getRequestCharacterEncoding() {
        return requestCharacterEncoding;
    }

    public void setRequestCharacterEncoding(String requestCharacterEncoding) {
        this.requestCharacterEncoding = requestCharacterEncoding;
    }

    public String getResponseCharacterEncoding() {
        return responseCharacterEncoding;
    }

    public void setResponseCharacterEncoding(String responseCharacterEncoding) {
        this.responseCharacterEncoding = responseCharacterEncoding;
    }

    /**
     * Get the jndiEnvironmentRefsGroup.
     *
     * @return the jndiEnvironmentRefsGroup.
     */
    public Environment getJndiEnvironmentRefsGroup() {
        return jndiEnvironmentRefsGroup;
    }

    /**
     * Set the jndiEnvironmentRefsGroup.
     * IT DOESN't MERGE...
     *
     * @param env the jndiEnvironmentRefsGroup.
     * @throws IllegalArgumentException for a null jndiEnvironmentRefsGroup
     */
    public void setJndiEnvironmentRefsGroup(Environment env) {
        if (env == null) { throw new IllegalArgumentException("Null jndiEnvironmentRefsGroup"); }
        jndiEnvironmentRefsGroup = (EnvironmentRefsGroupMetaData) env;
    }

    // just for the xml binding, to expose correct model group type
    public void setEnvironmentRefsGroup(JBossEnvironmentRefsGroupMetaData env) {
        this.setJndiEnvironmentRefsGroup(env);
    }

    public Boolean getJaccAllStoreRole() {
        return jaccAllStoreRole;
    }

    public boolean isJaccAllStoreRole() {
        if (jaccAllStoreRole == null) { return Boolean.FALSE; }
        return jaccAllStoreRole;
    }

    public void setJaccAllStoreRole(boolean isJaccAllStoreRole) {
        this.jaccAllStoreRole = Boolean.valueOf(isJaccAllStoreRole);
    }


    /**
     * Access the RunAsIdentity associated with the given servlet
     *
     * @param servletName - the servlet-name from the web.xml
     * @return RunAsIdentity for the servet if one exists, null otherwise
     */
    @Deprecated
    public RunAsIdentityMetaData getRunAsIdentity(String servletName) {
        RunAsIdentityMetaData identity = runAsIdentity.get(servletName);
        if (identity == null) {
            JBossServletsMetaData servlets = getServlets();
            if (servlets != null) {
                ServletMetaData servlet = servlets.get(servletName);
                if (servlet != null) {
                    // Check for a web.xml run-as only specification
                    RunAsMetaData runAs = servlet.getRunAs();
                    if (runAs != null) {
                        String roleName = runAs.getRoleName();
                        identity = new RunAsIdentityMetaData(roleName, null);
                        runAsIdentity.put(servletName, identity);
                    }
                }
            }
        }
        return identity;
    }

    /**
     * @return servlet/run-as <String servlet-name, RunAsIdentityMetaData>
     */
    public Map<String, RunAsIdentityMetaData> getRunAsIdentity() {
        return runAsIdentity;
    }

    /**
     * The jboss-web.xml servlet/run-as <String servlet-name,
     * SecurityRoleMetaData>
     */
    public void setRunAsIdentity(Map<String, RunAsIdentityMetaData> runAsIdentity) {
        this.runAsIdentity.clear();
        this.runAsIdentity.putAll(runAsIdentity);
    }

    public void mergeSecurityRoles(SecurityRolesMetaData roles) {
        securityRoles = roles;
    }

    public void resolveAnnotations() {
        if (annotations != null) {
            for (AnnotationMetaData annotation : annotations) {
                String className = annotation.getClassName();
                for (JBossServletMetaData servlet : servlets) {
                    if (servlet.getServletClass() != null && servlet.getServletClass().equals(className)) {
                        // Merge @RunAs
                        if (annotation.getRunAs() != null && servlet.getRunAs() == null) {
                            RunAsMetaData runAs = new RunAsMetaData();
                            runAs.setRoleName(annotation.getRunAs().getRoleName());
                            servlet.setRunAs(runAs);
                        }
                        // Merge @MultipartConfig
                        if (annotation.getMultipartConfig() != null && servlet.getMultipartConfig() == null) {
                            MultipartConfigMetaData multipartConfig = new MultipartConfigMetaData();
                            MultipartConfigMetaData webFragmentMetaData = annotation.getMultipartConfig();
                            multipartConfig.setLocation(webFragmentMetaData.getLocation());
                            if (webFragmentMetaData.getMaxFileSizeSet()) {
                                multipartConfig.setMaxFileSize(webFragmentMetaData.getMaxFileSize());
                            }
                            if (webFragmentMetaData.getMaxRequestSizeSet()) {
                                multipartConfig.setMaxRequestSize(webFragmentMetaData.getMaxRequestSize());
                            }
                            if (webFragmentMetaData.getFileSizeThresholdSet()) {
                                multipartConfig.setFileSizeThreshold(webFragmentMetaData.getFileSizeThreshold());
                            }
                            servlet.setMultipartConfig(multipartConfig);
                        }
                        // Merge @ServletSecurity
                        if (annotation.getServletSecurity() != null && servlet.getServletSecurity() == null) {
                            ServletSecurityMetaData servletSecurityAnnotation = annotation.getServletSecurity();
                            ServletSecurityMetaData servletSecurity = new ServletSecurityMetaData();
                            servletSecurity.setEmptyRoleSemantic(servletSecurityAnnotation.getEmptyRoleSemantic());
                            servletSecurity.setTransportGuarantee(servletSecurityAnnotation.getTransportGuarantee());
                            List<String> roleNames = new ArrayList<String>();
                            roleNames.addAll(servletSecurityAnnotation.getRolesAllowed());
                            servletSecurity.setRolesAllowed(roleNames);
                            if (servletSecurityAnnotation.getHttpMethodConstraints() != null) {
                                List<HttpMethodConstraintMetaData> methodConstraints = new ArrayList<HttpMethodConstraintMetaData>();
                                for (HttpMethodConstraintMetaData annotationMethodConstraint : servletSecurityAnnotation
                                        .getHttpMethodConstraints()) {
                                    HttpMethodConstraintMetaData methodConstraint = new HttpMethodConstraintMetaData();
                                    methodConstraint.setMethod(annotationMethodConstraint.getMethod());
                                    methodConstraint.setEmptyRoleSemantic(annotationMethodConstraint.getEmptyRoleSemantic());
                                    methodConstraint.setTransportGuarantee(annotationMethodConstraint.getTransportGuarantee());
                                    roleNames = new ArrayList<String>();
                                    roleNames.addAll(annotationMethodConstraint.getRolesAllowed());
                                    methodConstraint.setRolesAllowed(roleNames);
                                    methodConstraints.add(methodConstraint);
                                }
                                servletSecurity.setHttpMethodConstraints(methodConstraints);
                            }
                            servlet.setServletSecurity(servletSecurity);
                        }
                    }
                }
            }
        }
    }

    @Deprecated
    public void resolveRunAs() {
        // Update run-as indentity for a run-as-principal
        if (servlets != null) {
            for (JBossServletMetaData servlet : servlets) {
                String servletName = servlet.getServletName();
                String principalName = servlet.getRunAsPrincipal();
                // Get the web.xml run-as primary role
                String webXmlRunAs = null;
                if (servlet.getRunAs() != null) { webXmlRunAs = servlet.getRunAs().getRoleName(); }
                if (principalName != null) {
                    // Update the run-as indentity to use the principal name
                    if (webXmlRunAs == null) {
                        // Needs to be merged from Annotations
                        webXmlRunAs = "PLACEHOLDER_FOR_ANNOTATION";
                        // throw new IllegalStateException("run-as-principal: "
                        // + principalName +
                        // " found in jboss-web.xml but there was no run-as in web.xml");
                    }
                    // See if there are any additional roles for this principal
                    Set<String> extraRoles = securityRoles.getSecurityRoleNamesByPrincipal(principalName);
                    RunAsIdentityMetaData runAsId = new RunAsIdentityMetaData(webXmlRunAs, principalName, extraRoles);
                    runAsIdentity.put(servletName, runAsId);
                } else if (webXmlRunAs != null) {
                    RunAsIdentityMetaData runAsId = new RunAsIdentityMetaData(webXmlRunAs, null);
                    runAsIdentity.put(servletName, runAsId);
                }
            }
        }
    }

    public void setDistinctName(final String distinctName) {
        this.distinctName = distinctName;
    }

    public String getDistinctName() {
        return this.distinctName;
    }

    public boolean isSymbolicLinkingEnabled() {
        return symbolicLinkingEnabled;
    }

    public void setSymbolicLinkingEnabled(boolean symbolicLinkingEnabled) {
        this.symbolicLinkingEnabled = symbolicLinkingEnabled;
    }

    public Boolean getDenyUncoveredHttpMethods() {
        return denyUncoveredHttpMethods;
    }

    public void setDenyUncoveredHttpMethods(final Boolean denyUncoveredHttpMethods) {
        this.denyUncoveredHttpMethods = denyUncoveredHttpMethods;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(final String executorName) {
        this.executorName = executorName;
    }

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

    public Boolean getProactiveAuthentication() {
        return proactiveAuthentication;
    }

    public void setProactiveAuthentication(Boolean proactiveAuthentication) {
        this.proactiveAuthentication = proactiveAuthentication;
    }
}
