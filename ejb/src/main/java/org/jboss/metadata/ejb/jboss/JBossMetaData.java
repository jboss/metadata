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
package org.jboss.metadata.ejb.jboss;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.interceptor.Interceptors;

import org.jboss.logging.Logger;
import org.jboss.metadata.ejb.common.IEjbJarMetaData;
import org.jboss.metadata.common.ejb.ResourceManagerMetaData;
import org.jboss.metadata.common.ejb.ResourceManagersMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokeMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokesMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeansMetaData;
import org.jboss.metadata.ejb.spec.InterceptorBindingMetaData;
import org.jboss.metadata.ejb.spec.InterceptorBindingsMetaData;
import org.jboss.metadata.ejb.spec.InterceptorClassesMetaData;
import org.jboss.metadata.ejb.spec.InterceptorMetaData;
import org.jboss.metadata.ejb.spec.InterceptorsMetaData;
import org.jboss.metadata.ejb.spec.RelationsMetaData;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.javaee.jboss.RunAsIdentityMetaData;
import org.jboss.metadata.javaee.spec.RunAsMetaData;
import org.jboss.metadata.javaee.support.NamedModuleImpl;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplWithDescriptionGroupMerger;
import org.jboss.metadata.merge.javaee.support.NamedModuleImplMerger;

/**
 * JBossMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 81163 $
 */
public class JBossMetaData extends NamedModuleImpl
        implements IEjbJarMetaData<JBossAssemblyDescriptorMetaData, JBossEnterpriseBeansMetaData, JBossEnterpriseBeanMetaData, JBossMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 598759931857080298L;

    /**
     * The log
     */
    private static final Logger log = Logger.getLogger(JBossMetaData.class);

    /**
     * DTD information
     */
    private String dtdPublicId;
    private String dtdSystemId;
    /**
     * The version
     */
    private String version;
    /**
     * The ejb jar version
     */
    private String ejbVersion;

    /**
     * The ejb client jar
     */
    private String ejbClientJar;

    /**
     * The relations
     */
    private RelationsMetaData relationships;

    /**
     * The jmx name
     */
    private String jmxName;

    /**
     * The security domain
     */
    private String securityDomain;

    /**
     * The jacc context id
     */
    private String jaccContextID;

    /**
     * Whether to exclude missing methods
     */
    private boolean excludeMissingMethods = true;

    /**
     * The unauthenticated principal
     */
    private String unauthenticatedPrincipal;

    /**
     * Whether to throw an exception when marked rollback
     */
    private boolean exceptionOnRollback;

    /**
     * default JMS RA deployment name
     */
    private String jmsResourceAdapter;

    /**
     * The webservices
     */
    private WebservicesMetaData webservices;

    /**
     * The enterprise beans
     */
    private JBossEnterpriseBeansMetaData enterpriseBeans;

    /**
     * The assembly descriptor
     */
    private JBossAssemblyDescriptorMetaData assemblyDescriptor;

    /**
     * The resource manager
     */
    private ResourceManagersMetaData resourceManagers;

    /**
     * The invoker-proxy-bindings
     */
    private InvokerProxyBindingsMetaData invokerProxyBindings;

    /**
     * The container configurations
     */
    private ContainerConfigurationsMetaData containerConfigurations;

    /**
     * The interceptors
     */
    private InterceptorsMetaData interceptors;
    /** */
    private Map<String, RunAsIdentityMetaData> runAsIdentity = new HashMap<String, RunAsIdentityMetaData>();
    /**
     * Is this a complete metadata description
     */
    private boolean metadataComplete;

    /**
     * the class name that implements the default JNDI binding policy for this ejb unit
     */
    private String jndiBindingPolicy;

    /**
     * Create a new JBossMetaData.
     */
    public JBossMetaData() {
        // For serialization
    }

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
        // Set the version based on the public id
        if (dtdPublicId != null && dtdPublicId.contains("3.0"))
            setVersion("3.0");
        if (dtdPublicId != null && dtdPublicId.contains("3.2"))
            setVersion("3.2");
        if (dtdPublicId != null && dtdPublicId.contains("4.0"))
            setVersion("4.0");
        if (dtdPublicId != null && dtdPublicId.contains("4.2"))
            setVersion("4.2");
        if (dtdPublicId != null && dtdPublicId.contains("5.0"))
            setVersion("5.0");
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

    public InterceptorsMetaData getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(InterceptorsMetaData interceptors) {
        if (interceptors == null) {
            throw new IllegalArgumentException("Cannot set null InterceptorsMetaData");
        }
        this.interceptors = interceptors;
    }

    /**
     * Get the jboss.xml version.
     *
     * @return the version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set the version.
     *
     * @param version the version.
     * @throws IllegalArgumentException for a null version
     */
    public void setVersion(String version) {
        if (version == null)
            throw new IllegalArgumentException("Null version");
        this.version = version;
    }

    public JBossAssemblyDescriptorMetaData getAssemblyDescriptor() {
        return assemblyDescriptor;
    }

    public String getEjbClientJar() {
        return this.ejbClientJar;
    }

    public void setEjbClientJar(String ejbClientJar) {
        this.ejbClientJar = ejbClientJar;
    }

    /**
     * The spec metadata version information
     *
     * @return
     */
    public String getEjbVersion() {
        return ejbVersion;
    }

    public void setEjbVersion(String ejbVersion) {
        this.ejbVersion = ejbVersion;
    }

    public RelationsMetaData getRelationships() {
        return this.relationships;
    }

    public void setRelationships(RelationsMetaData relationships) {
        this.relationships = relationships;
    }

    public boolean isEJB1x() {
        return ejbVersion != null && ejbVersion.contains("1.");
    }

    public boolean isEJB21() {
        return ejbVersion != null && ejbVersion.contains("2.1");
    }

    public boolean isEJB2x() {
        return ejbVersion != null && ejbVersion.contains("2.");
    }

    public boolean isEJB3x() {
        return ejbVersion != null && ejbVersion.contains("3.");
    }

    public boolean isEJB31() {
        return ejbVersion != null && ejbVersion.trim().equals("3.1");
    }


    /**
     * Get the jmxName.
     *
     * @return the jmxName.
     */
    public String getJmxName() {
        return jmxName;
    }

    /**
     * Set the jmxName.
     *
     * @param jmxName the jmxName.
     * @throws IllegalArgumentException for a null jmxName
     */
    public void setJmxName(String jmxName) {
        if (jmxName == null)
            throw new IllegalArgumentException("Null jmxName");
        this.jmxName = jmxName;
    }

    public String getJaccContextID() {
        return jaccContextID;
    }

    /**
     * Set the JACC context id
     *
     * @param jaccContextID the id to use for the bean JACC context
     */
    public void setJaccContextID(String jaccContextID) {
        this.jaccContextID = jaccContextID;
    }

    /**
     * Get the securityDomain.
     *
     * @return the securityDomain.
     */
    public String getSecurityDomain() {
        return securityDomain;
    }

    /**
     * Set the securityDomain.
     *
     * @param securityDomain the securityDomain.
     * @throws IllegalArgumentException for a null securityDomain
     */
    public void setSecurityDomain(String securityDomain) {
        if (securityDomain == null)
            throw new IllegalArgumentException("Null securityDomain");
        this.securityDomain = securityDomain.trim();
    }

    /**
     * Get the excludeMissingMethods.
     *
     * @return the excludeMissingMethods.
     */
    public boolean isExcludeMissingMethods() {
        return excludeMissingMethods;
    }

    /**
     * Set the excludeMissingMethods.
     *
     * @param excludeMissingMethods the excludeMissingMethods.
     */
    public void setExcludeMissingMethods(boolean excludeMissingMethods) {
        this.excludeMissingMethods = excludeMissingMethods;
    }

    /**
     * Get the unauthenticatedPrincipal.
     *
     * @return the unauthenticatedPrincipal.
     */
    public String getUnauthenticatedPrincipal() {
        return unauthenticatedPrincipal;
    }

    /**
     * Set the unauthenticatedPrincipal.
     *
     * @param unauthenticatedPrincipal the unauthenticatedPrincipal.
     * @throws IllegalArgumentException for a null unauthenticatedPrincipal
     */
    public void setUnauthenticatedPrincipal(String unauthenticatedPrincipal) {
        if (unauthenticatedPrincipal == null)
            throw new IllegalArgumentException("Null unauthenticatedPrincipal");
        this.unauthenticatedPrincipal = unauthenticatedPrincipal;
    }

    /**
     * Get the exceptionOnRollback.
     *
     * @return the exceptionOnRollback.
     */
    public boolean isExceptionOnRollback() {
        return exceptionOnRollback;
    }

    /**
     * Set the exceptionOnRollback.
     *
     * @param exceptionOnRollback the exceptionOnRollback.
     */
    public void setExceptionOnRollback(boolean exceptionOnRollback) {
        this.exceptionOnRollback = exceptionOnRollback;
    }

    public boolean isMetadataComplete() {
        return metadataComplete;
    }

    public void setMetadataComplete(boolean metadataComplete) {
        this.metadataComplete = metadataComplete;
    }

    /**
     * Get the enterpriseBeans.
     *
     * @return the enterpriseBeans.
     */
    public JBossEnterpriseBeansMetaData getEnterpriseBeans() {
        return enterpriseBeans;
    }

    /**
     * Set the enterpriseBeans.
     *
     * @param enterpriseBeans the enterpriseBeans.
     * @throws IllegalArgumentException for a null enterpriseBeans
     */
    public void setEnterpriseBeans(JBossEnterpriseBeansMetaData enterpriseBeans) {
        if (enterpriseBeans == null)
            throw new IllegalArgumentException("Null enterpriseBeans");
        this.enterpriseBeans = enterpriseBeans;
        this.enterpriseBeans.setJBossMetaData(this);
    }

    /**
     * Get an enterprise bean
     *
     * @param name the name
     * @return the container configuration
     */
    public JBossEnterpriseBeanMetaData getEnterpriseBean(String name) {
        if (enterpriseBeans == null)
            return null;
        return enterpriseBeans.get(name);
    }

    /**
     * Set the enforceEjbRestrictions.
     *
     * @param enforceEjbRestrictions the enforceEjbRestrictions.
     * @throws IllegalArgumentException for a null enforceEjbRestrictions
     */
    public void setEnforceEjbRestrictions(String enforceEjbRestrictions) {
        log.warn("<enforce-ejb-restrictions/> in jboss.xml is no longer used and will be removed in a future version.");
    }

    /**
     * Get the webservices.
     *
     * @return the webservices.
     */
    public WebservicesMetaData getWebservices() {
        return webservices;
    }

    /**
     * Set the webservices.
     *
     * @param webservices the webservices.
     * @throws IllegalArgumentException for a null webservices
     */
    public void setWebservices(WebservicesMetaData webservices) {
        if (webservices == null)
            throw new IllegalArgumentException("Null webservices");
        this.webservices = webservices;
    }

    /**
     * Get the containerConfigurations.
     *
     * @return the containerConfigurations.
     */
    public ContainerConfigurationsMetaData getContainerConfigurations() {
        return containerConfigurations;
    }

    /**
     * Set the containerConfigurations.
     *
     * @param containerConfigurations the containerConfigurations.
     * @throws IllegalArgumentException for a null containerConfigurations
     */
    public void setContainerConfigurations(ContainerConfigurationsMetaData containerConfigurations) {
        if (containerConfigurations == null)
            throw new IllegalArgumentException("Null containerConfigurations");
        this.containerConfigurations = containerConfigurations;
    }

    /**
     * Get a container configuration
     *
     * @param name the name
     * @return the container configuration
     */
    public ContainerConfigurationMetaData getContainerConfiguration(String name) {
        if (containerConfigurations == null)
            return null;
        return containerConfigurations.get(name);
    }

    /**
     * Get the invokerProxyBindings.
     *
     * @return the invokerProxyBindings.
     */
    public InvokerProxyBindingsMetaData getInvokerProxyBindings() {
        return invokerProxyBindings;
    }

    /**
     * Set the invokerProxyBindings.
     *
     * @param invokerProxyBindings the invokerProxyBindings.
     * @throws IllegalArgumentException for a null invokerProxyBindings
     */
    public void setInvokerProxyBindings(InvokerProxyBindingsMetaData invokerProxyBindings) {
        if (invokerProxyBindings == null)
            throw new IllegalArgumentException("Null invokerProxyBindings");
        this.invokerProxyBindings = invokerProxyBindings;
    }

    /**
     * Get an invoker proxy binding
     *
     * @param name the name
     * @return the invoker proxy binding
     */
    public InvokerProxyBindingMetaData getInvokerProxyBinding(String name) {
        if (invokerProxyBindings == null)
            return null;
        return invokerProxyBindings.get(name);
    }

    /**
     * Get the resourceManagers.
     *
     * @return the resourceManagers.
     */
    public ResourceManagersMetaData getResourceManagers() {
        return resourceManagers;
    }

    /**
     * Set the resourceManagers.
     *
     * @param resourceManagers the resourceManagers.
     * @throws IllegalArgumentException for a null resourceManagers
     */
    public void setResourceManagers(ResourceManagersMetaData resourceManagers) {
        if (resourceManagers == null)
            throw new IllegalArgumentException("Null resourceManagers");
        this.resourceManagers = resourceManagers;
    }

    /**
     * Get a resource manager
     *
     * @param name the name
     * @return the resource manager
     */
    public ResourceManagerMetaData getResourceManager(String name) {
        if (resourceManagers == null)
            return null;
        return resourceManagers.get(name);
    }

    /**
     * Set the assemblyDescriptor.
     *
     * @param assemblyDescriptor the assemblyDescriptor.
     * @throws IllegalArgumentException for a null assemblyDescriptor
     */
    public void setAssemblyDescriptor(JBossAssemblyDescriptorMetaData assemblyDescriptor) {
        if (assemblyDescriptor == null)
            throw new IllegalArgumentException("Null assemblyDescriptor");
        this.assemblyDescriptor = assemblyDescriptor;
    }

    public String getJndiBindingPolicy() {
        return jndiBindingPolicy;
    }

    public void setJndiBindingPolicy(String jndiBindingPolicy) {
        this.jndiBindingPolicy = jndiBindingPolicy;
    }

    public String getJMSResourceAdapter() {
        return this.jmsResourceAdapter;
    }

    public void setJMSResourceAdapter(String jmsResourceAdapter) {
        this.jmsResourceAdapter = jmsResourceAdapter;
    }

    /**
     * Access the RunAsIdentity associated with the given servlet
     *
     * @param ejbName - the servlet-name from the web.xml
     * @return RunAsIdentity for the servet if one exists, null otherwise
     */
    public RunAsIdentityMetaData getRunAsIdentity(String ejbName) {
        RunAsIdentityMetaData identity = runAsIdentity.get(ejbName);
        if (identity == null) {
            JBossEnterpriseBeanMetaData ejb = this.getEnterpriseBean(ejbName);
            if (ejb != null) {
                // Check for a ejb-jar.xml run-as only specification
                synchronized (runAsIdentity) {
                    SecurityIdentityMetaData si = ejb.getSecurityIdentity();
                    if (si != null) {
                        RunAsMetaData runAs = si.getRunAs();
                        if (runAs != null) {
                            String roleName = runAs.getRoleName();
                            identity = new RunAsIdentityMetaData(roleName, null);
                            runAsIdentity.put(ejbName, identity);
                        }
                    }
                }
            }
        }
        return identity;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public void merge(JBossMetaData override, EjbJarMetaData original) {
        IdMetaDataImplWithDescriptionGroupMerger.merge(this, override, original);

        if (override != null && override.getModuleName() != null) {
            setModuleName(override.getModuleName());
        } else if (original != null) {
            setModuleName(original.getModuleName());
        }

        if (override != null && override.getVersion() != null)
            version = override.getVersion();
        else if (original != null && original.getVersion() != null)
            version = original.getVersion();
        if (assemblyDescriptor == null)
            assemblyDescriptor = new JBossAssemblyDescriptorMetaData();
        if (resourceManagers == null)
            resourceManagers = new ResourceManagersMetaData();
        if (containerConfigurations == null)
            containerConfigurations = new ContainerConfigurationsMetaData();
        if (override != null && override.containerConfigurations != null)
            containerConfigurations.merge(override.containerConfigurations);
        if (invokerProxyBindings == null)
            invokerProxyBindings = new InvokerProxyBindingsMetaData();
        if (override != null && override.invokerProxyBindings != null)
            invokerProxyBindings.merge(override.invokerProxyBindings);
        if (interceptors == null)
            interceptors = new InterceptorsMetaData();
        if (override != null && override.webservices != null)
            webservices = new WebservicesMetaData();

        if (original != null) {
            ejbVersion = original.getVersion();
            relationships = original.getRelationships();
            metadataComplete = original.isMetadataComplete();
        }

        if (override != null && override.assemblyDescriptor != null) {
            assemblyDescriptor.merge(override.assemblyDescriptor, original.getAssemblyDescriptor());
        } else if (original != null && original.getAssemblyDescriptor() != null) {
            assemblyDescriptor.merge(null, original.getAssemblyDescriptor());
        }
        //
      /* FIXME
      if(override != null && override.resourceManagers != null)
         resourceManagers.merge(override.resourceManagers);
      */

        if (override != null && override.interceptors != null)
            interceptors.merge(override.interceptors);
        else if (original != null && original.getInterceptors() != null)
            interceptors.merge(original.getInterceptors());

        if (override != null) {
            if (override.jaccContextID != null)
                jaccContextID = override.jaccContextID;
            if (override.jmxName != null)
                jmxName = override.jmxName;
            if (override.securityDomain != null)
                securityDomain = override.securityDomain;
            if (override.unauthenticatedPrincipal != null)
                unauthenticatedPrincipal = override.unauthenticatedPrincipal;
            if (override.metadataComplete)
                metadataComplete = true;
        }

        if (webservices != null && override != null)
            webservices.merge(override.webservices);

        if (enterpriseBeans == null) {
            enterpriseBeans = new JBossEnterpriseBeansMetaData();
            enterpriseBeans.setJBossMetaData(this);
        }

        JBossEnterpriseBeansMetaData jbeans = null;
        if (override != null) {
            jbeans = override.enterpriseBeans;

            //Ensure that there is no customization of the Unspecified method permissions
            if (override.excludeMissingMethods == false)
                this.excludeMissingMethods = false;
        }

        EnterpriseBeansMetaData beans = null;
        if (original != null)
            beans = original.getEnterpriseBeans();

        boolean isEJB3x = (original == null || original.isEJB3x());
        enterpriseBeans.merge(jbeans, beans, "ejb-jar.xml", "jboss.xml", !isEJB3x);

        // Update run-as indentity for a run-as-principal
        if (enterpriseBeans != null) {
            for (JBossEnterpriseBeanMetaData ejb : enterpriseBeans) {
                String ejbName = ejb.getEjbName();
                SecurityIdentityMetaData si = ejb.getSecurityIdentity();
                String principalName = si != null ? si.getRunAsPrincipal() : null;
                // Get the run-as primary role
                String ejbXmlRunAs = null;
                if (si != null && si.getRunAs() != null)
                    ejbXmlRunAs = si.getRunAs().getRoleName();
                if (principalName != null) {
                    // Update the run-as indentity to use the principal name
                    if (ejbXmlRunAs == null) {
                        // Can't make this check since the name may come from an annotation
                        //throw new IllegalStateException("run-as-principal: " + principalName + " found in jboss.xml but there was no run-as in ejb-jar.xml");
                        ejbXmlRunAs = "anonymous";
                    }
                    // See if there are any additional roles for this principal
                    Set<String> extraRoles = null;
                    if (getAssemblyDescriptor() != null) {
                        extraRoles = getAssemblyDescriptor().getSecurityRoleNamesByPrincipal(principalName);
                    }
                    RunAsIdentityMetaData runAsId = new RunAsIdentityMetaData(ejbXmlRunAs, principalName, extraRoles);
                    runAsIdentity.put(ejbName, runAsId);
                } else if (ejbXmlRunAs != null) {
                    RunAsIdentityMetaData runAsId = new RunAsIdentityMetaData(ejbXmlRunAs, null);
                    runAsIdentity.put(ejbName, runAsId);
                }
            }
        }

        if (override != null && override.jmsResourceAdapter != null)
            jmsResourceAdapter = override.jmsResourceAdapter;
    }

    public void merge(JBossMetaData override, JBossMetaData original) {
        NamedModuleImplMerger.merge(this, override, original);

        if (override != null && override.getVersion() != null)
            version = override.getVersion();
        else if (original != null && original.getVersion() != null)
            version = original.getVersion();

        JBossAssemblyDescriptorMetaData originalAssembly = null;
        InvokerProxyBindingsMetaData originalInvokerProxyBindings = null;
        InterceptorsMetaData originalInterceptors = null;
        WebservicesMetaData originalWebservices = null;
        JBossEnterpriseBeansMetaData originalBeans = null;
        if (original != null) {
            originalAssembly = original.assemblyDescriptor;
            originalInvokerProxyBindings = original.invokerProxyBindings;
            originalInterceptors = original.interceptors;
            originalWebservices = original.webservices;
            originalBeans = original.enterpriseBeans;

            if (original.ejbVersion != null)
                ejbVersion = original.ejbVersion;
            if (original.metadataComplete)
                metadataComplete = true;
            if (original.relationships != null)
                relationships = original.relationships;
            if (original.jaccContextID != null)
                jaccContextID = original.jaccContextID;
            if (original.jmxName != null)
                jmxName = original.jmxName;
            if (original.securityDomain != null)
                securityDomain = original.securityDomain;
            if (original.unauthenticatedPrincipal != null)
                unauthenticatedPrincipal = original.unauthenticatedPrincipal;

            if (original.containerConfigurations != null) {
                if (containerConfigurations == null)
                    containerConfigurations = new ContainerConfigurationsMetaData();
                containerConfigurations.merge(original.containerConfigurations);
            }

            if (original.resourceManagers != null) {
                if (resourceManagers == null)
                    resourceManagers = new ResourceManagersMetaData();
                resourceManagers.addAll(original.resourceManagers);
            }
        }

        JBossAssemblyDescriptorMetaData overrideAssembly = null;
        InvokerProxyBindingsMetaData overrideInvokerProxyBindings = null;
        InterceptorsMetaData overrideInterceptors = null;
        WebservicesMetaData overrideWebservices = null;
        JBossEnterpriseBeansMetaData overrideBeans = null;
        if (override != null) {
            overrideAssembly = override.assemblyDescriptor;
            overrideInvokerProxyBindings = override.invokerProxyBindings;
            overrideInterceptors = override.interceptors;
            overrideWebservices = override.webservices;
            overrideBeans = override.enterpriseBeans;

            if (override.ejbVersion != null)
                ejbVersion = override.ejbVersion;
            if (override.metadataComplete)
                metadataComplete = true;
            // TODO: relationships should be merged
            if (override.relationships != null)
                relationships = original.relationships;
            if (override.jaccContextID != null)
                jaccContextID = override.jaccContextID;
            if (override.jmxName != null)
                jmxName = override.jmxName;
            if (override.securityDomain != null)
                securityDomain = override.securityDomain;
            if (override.unauthenticatedPrincipal != null)
                unauthenticatedPrincipal = override.unauthenticatedPrincipal;
            //Ensure that there is no customization of the Unspecified method permissions
            if (override.excludeMissingMethods == false)
                this.excludeMissingMethods = false;

            if (override.containerConfigurations != null) {
                if (containerConfigurations == null)
                    containerConfigurations = new ContainerConfigurationsMetaData();
                containerConfigurations.merge(override.containerConfigurations);
            }

            if (override.resourceManagers != null) {
                if (resourceManagers == null)
                    resourceManagers = new ResourceManagersMetaData();
                resourceManagers.addAll(override.resourceManagers);
            }
        }

        if (assemblyDescriptor == null)
            assemblyDescriptor = new JBossAssemblyDescriptorMetaData();
        if (overrideAssembly != null || originalAssembly != null)
            assemblyDescriptor.merge(overrideAssembly, originalAssembly);

        if (invokerProxyBindings == null)
            invokerProxyBindings = new InvokerProxyBindingsMetaData();
        if (overrideInvokerProxyBindings != null || originalInvokerProxyBindings != null)
            invokerProxyBindings.merge(overrideInvokerProxyBindings, originalInvokerProxyBindings);

        if (interceptors == null)
            interceptors = new InterceptorsMetaData();
        if (overrideInterceptors != null || originalInterceptors != null)
            interceptors.merge(overrideInterceptors, originalInterceptors);

        if (overrideWebservices != null || originalWebservices != null) {
            if (webservices == null)
                webservices = new WebservicesMetaData();
            webservices.merge(overrideWebservices, originalWebservices);
        }

        if (enterpriseBeans == null) {
            enterpriseBeans = new JBossEnterpriseBeansMetaData();
            enterpriseBeans.setJBossMetaData(this);
        }

        if (originalBeans != null || overrideBeans != null)
            enterpriseBeans.merge(overrideBeans, originalBeans);

        // Update run-as indentity for a run-as-principal
        if (enterpriseBeans != null) {
            for (JBossEnterpriseBeanMetaData ejb : enterpriseBeans) {
                String ejbName = ejb.getEjbName();
                SecurityIdentityMetaData si = ejb.getSecurityIdentity();
                String principalName = si != null ? si.getRunAsPrincipal() : null;
                // Get the run-as primary role
                String ejbXmlRunAs = null;
                if (si != null && si.getRunAs() != null)
                    ejbXmlRunAs = si.getRunAs().getRoleName();
                if (principalName != null) {
                    // Update the run-as indentity to use the principal name
                    if (ejbXmlRunAs == null) {
                        // Can't make this check since the name may come from an annotation
                        //throw new IllegalStateException("run-as-principal: " + principalName + " found in jboss.xml but there was no run-as in ejb-jar.xml");
                        ejbXmlRunAs = "anonymous";
                    }
                    // See if there are any additional roles for this principal
                    Set<String> extraRoles = null;
                    if (getAssemblyDescriptor() != null) {
                        extraRoles = getAssemblyDescriptor().getSecurityRoleNamesByPrincipal(principalName);
                    }
                    RunAsIdentityMetaData runAsId = new RunAsIdentityMetaData(ejbXmlRunAs, principalName, extraRoles);
                    runAsIdentity.put(ejbName, runAsId);
                } else if (ejbXmlRunAs != null) {
                    RunAsIdentityMetaData runAsId = new RunAsIdentityMetaData(ejbXmlRunAs, null);
                    runAsIdentity.put(ejbName, runAsId);
                }
            }
        }

        if (override != null && override.getJMSResourceAdapter() != null)
            jmsResourceAdapter = override.getJMSResourceAdapter();
        else if (original != null && original.getJMSResourceAdapter() != null)
            jmsResourceAdapter = original.getJMSResourceAdapter();
    }

    /**
     * @return
     */
    protected JBossEnterpriseBeanMetaData newBean() {
        return null;
    }

    /**
     * Returns the {@link InterceptorsMetaData} which are applicable for the <code>beanName</code>
     * in the <code>jbossMetaData</code>
     * <p>
     * An interceptor is considered as bound to an EJB if there's atleast one interceptor
     * binding between the EJB and the interceptor class. The interceptor binding can either
     * be through the use of <interceptor-binding> element in ejb-jar.xml or through the
     * use of {@link Interceptors} annotation(s) in the EJB class.
     * </p>
     * <p>
     * If the EJB has an around-invoke element which uses class name other than the EJB class name,
     * then even that class is considered as an interceptor class and is considered to be bound to
     * the EJB.
     * </p>
     * <p>
     * For example:
     * <session>
     * <ejb-name>Dummy</ejb-name>
     * <ejb-class>org.myapp.ejb.MyBean</ejb-class>
     * <around-invoke>
     * <class>org.myapp.SomeClass</class>
     * <method-name>blah</method-name>
     * </around-invoke>
     * </session>
     * <p/>
     * The <code>org.myapp.SomeClass</code> will be considered as a interceptor class bound to the EJB,
     * <code>org.myapp.ejb.MyBean</code>, even if there is no explicit interceptor binding between that EJB
     * and the <code>org.myapp.SomeClass</code>
     * </p>
     *
     * @param beanName      The EJB name
     * @param jbossMetaData The {@link JBossMetaData} corresponding to the <code>beanName</code>
     * @return
     * @throws NullPointerException If either of <code>beanName</code> or <code>jbossMetaData</code>
     *                              is null
     */
    public static InterceptorsMetaData getInterceptors(String beanName, JBossMetaData jbossMetaData) {
        InterceptorsMetaData beanApplicableInterceptors = new InterceptorsMetaData();
        if (jbossMetaData.getAssemblyDescriptor() == null) {
            return beanApplicableInterceptors;
        }
        InterceptorBindingsMetaData allInterceptorBindings = jbossMetaData.getAssemblyDescriptor()
                .getInterceptorBindings();
        if (allInterceptorBindings == null) {
            return beanApplicableInterceptors;
        }
        InterceptorsMetaData allInterceptors = jbossMetaData.getInterceptors();
        if (allInterceptors == null || allInterceptors.isEmpty()) {
            return beanApplicableInterceptors;
        }
        return getInterceptors(beanName, allInterceptors, allInterceptorBindings);
    }


    /**
     * Returns all interceptor classes which are present in the passed <code>jbossMetaData</code>.
     * <p>
     * A class is considered an interceptor class, if it is listed in either of the following:
     * <ul>
     * <li>In the <interceptor> element of ejb-jar.xml</li>
     * <li>In the <interceptor-binding> element of ejb-jar.xml</li>
     * <li>In the <class> sub-element of <around-invoke> element in the ejb-jar.xml</li>
     * <li>Marked as an interceptor class through the use of {@link Interceptors} annotation
     * in a bean class</li>
     * </ul>
     * </p>
     *
     * @param jbossMetaData The {@link JBossMetaData} which will scanned for interceptor classes
     * @return
     */
    public static Collection<String> getAllInterceptorClasses(JBossMetaData jbossMetaData) {
        Collection<String> allInterceptorClassNames = new HashSet<String>();

        // process <interceptors>
        InterceptorsMetaData interceptorsMetadata = jbossMetaData.getInterceptors();
        if (interceptorsMetadata != null) {
            for (InterceptorMetaData interceptor : interceptorsMetadata) {
                if (interceptor.getInterceptorClass() != null) {
                    allInterceptorClassNames.add(interceptor.getInterceptorClass());
                }
            }
        }
        // process <interceptor-bindings> (a.k.a @Interceptors)
        JBossAssemblyDescriptorMetaData assemblyDescriptor = jbossMetaData.getAssemblyDescriptor();
        if (assemblyDescriptor != null) {
            InterceptorBindingsMetaData interceptorBindings = assemblyDescriptor.getInterceptorBindings();
            if (interceptorBindings != null) {
                for (InterceptorBindingMetaData interceptorBinding : interceptorBindings) {
                    if (interceptorBinding != null) {
                        InterceptorClassesMetaData interceptorClasses = interceptorBinding.getInterceptorClasses();
                        if (interceptorClasses != null) {
                            for (String interceptorClass : interceptorClasses) {
                                allInterceptorClassNames.add(interceptorClass);
                            }

                        }
                    }
                }
            }
        }
        // process around-invoke
        JBossEnterpriseBeansMetaData enterpriseBeans = jbossMetaData.getEnterpriseBeans();
        if (enterpriseBeans != null) {
            for (JBossEnterpriseBeanMetaData enterpriseBean : enterpriseBeans) {
                String enterpriseBeanClassName = enterpriseBean.getEjbClass();
                AroundInvokesMetaData aroundInvokes = null;
                if (enterpriseBean.isSession()) {
                    JBossSessionBeanMetaData sessionBean = (JBossSessionBeanMetaData) enterpriseBean;
                    aroundInvokes = sessionBean.getAroundInvokes();
                } else if (enterpriseBean.isMessageDriven()) {
                    JBossMessageDrivenBeanMetaData messageDrivenBean = (JBossMessageDrivenBeanMetaData) enterpriseBean;
                    aroundInvokes = messageDrivenBean.getAroundInvokes();
                }

                if (aroundInvokes == null || aroundInvokes.isEmpty()) {
                    continue;
                }

                for (AroundInvokeMetaData aroundInvoke : aroundInvokes) {
                    String targetClass = aroundInvoke.getClassName();
                    if (targetClass == null) {
                        continue;
                    }
                    // if the target class name is not the class name of the EJB,
                    // then as per the xsd, it is considered an interceptor class
                    if (targetClass.equals(enterpriseBeanClassName) == false) {
                        // it's an interceptor class
                        allInterceptorClassNames.add(targetClass);
                    }
                }
            }
        }
        // return the interceptor class names
        return allInterceptorClassNames;
    }

    /**
     * Utility method which, given a bean name, all interceptors available in a deployment
     * and the all the interceptor binding information, will return only those interceptors
     * which are applicable to the EJB corresponding to the bean name
     *
     * @param ejbName                Name of the EJB
     * @param allInterceptors        {@link InterceptorsMetaData} of all interceptors
     * @param allInterceptorBindings {@link InterceptorBindingsMetaData} of all interceptor bindings
     * @return
     */
    private static InterceptorsMetaData getInterceptors(String ejbName, InterceptorsMetaData allInterceptors,
                                                        InterceptorBindingsMetaData allInterceptorBindings) {
        InterceptorsMetaData beanApplicableInterceptors = new InterceptorsMetaData();
        // the default interceptors (ejbname = *) will be
        // considered as *not* applicable for a bean, if *all* the interceptor
        // bindings for that bean, have set the exclude-default-interceptors to true
        boolean includeDefaultInterceptors = false;
        InterceptorsMetaData defaultInterceptors = new InterceptorsMetaData();
        for (InterceptorBindingMetaData binding : allInterceptorBindings) {
            // the interceptor binding corresponds to the bean we are interested in
            if (ejbName != null && ejbName.equals(binding.getEjbName())) {
                // atleast one interceptor binding on the bean, is interested
                // in the default interceptors (ejbname = *). So set the flag to include the default
                // interceptors in the list of applicable interceptors
                if (binding.isExcludeDefaultInterceptors() == false) {
                    includeDefaultInterceptors = true;
                }
                InterceptorClassesMetaData interceptorClasses = null;
                if (binding.isTotalOrdering()) {
                    interceptorClasses = binding.getInterceptorOrder();
                } else {
                    interceptorClasses = binding.getInterceptorClasses();
                }
                // interceptor binding has no classes, so move on to the next interceptor binding
                if (interceptorClasses == null || interceptorClasses.isEmpty()) {
                    continue;
                }
                for (String interceptorClass : interceptorClasses) {
                    // get the corresponding interceptor metadata for the interceptor class
                    InterceptorMetaData interceptorMetaData = allInterceptors.get(interceptorClass);
                    // TODO: the interceptor metadata for a interceptor class will only be
                    // null, if the metadata isn't fully populated/processed. Let's not thrown
                    // any errors and just ignore such cases for now
                    if (interceptorMetaData != null) {
                        // include this interceptor metadata as applicable for the bean
                        beanApplicableInterceptors.add(interceptorMetaData);
                    }
                }
            } else if (binding.getEjbName().equals("*")) // binding for default interceptors
            {
                InterceptorClassesMetaData interceptorClasses = null;
                if (binding.isTotalOrdering()) {
                    interceptorClasses = binding.getInterceptorOrder();
                } else {
                    interceptorClasses = binding.getInterceptorClasses();
                }
                // no interceptor class, so skip to next interceptor binding
                if (interceptorClasses == null || interceptorClasses.isEmpty()) {
                    continue;
                }
                // interceptor binding on the bean, is interested
                // in the default interceptors (ejbname = *). So set the flag to include the default
                // interceptors in the list of applicable interceptors
                if (binding.isExcludeDefaultInterceptors() == false) {
                    includeDefaultInterceptors = true;
                }
                for (String interceptorClass : interceptorClasses) {
                    InterceptorMetaData interceptorMetaData = allInterceptors.get(interceptorClass);
                    // TODO: the interceptor metadata for a interceptor class will only be
                    // null, if the metadata isn't fully populated/processed. Let's not thrown
                    // any errors and just ignore such cases for now
                    if (interceptorMetaData != null) {
                        // add the interceptor metadata to the set of default interceptors.
                        // Whether or not these default interceptors are applicable for
                        // the bean being processed, will be decide later
                        defaultInterceptors.add(interceptorMetaData);
                    }
                }
            }
        }
        // if the default interceptors (ejb name= *) are to be included
        // for this bean.
        // the default interceptors (ejbname = *) will be
        // considered as *not* applicable for a bean, if *all* the interceptor
        // bindings for that bean, have set the exclude-default-interceptors to true
        if (includeDefaultInterceptors) {
            beanApplicableInterceptors.addAll(defaultInterceptors);
        }

        // return the interceptors which are applicable for the bean
        return beanApplicableInterceptors;
    }

}
