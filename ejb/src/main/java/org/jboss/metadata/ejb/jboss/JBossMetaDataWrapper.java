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

import org.jboss.metadata.common.ejb.ResourceManagerMetaData;
import org.jboss.metadata.common.ejb.ResourceManagersMetaData;
import org.jboss.metadata.ejb.spec.InterceptorsMetaData;
import org.jboss.metadata.ejb.spec.RelationsMetaData;
import org.jboss.metadata.javaee.jboss.RunAsIdentityMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;

/**
 * Read only wrapper that combines a primary and standardjboss.xml defaults into
 * a unified view.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 74705 $
 */
public class JBossMetaDataWrapper extends JBossMetaData {
    private static final long serialVersionUID = 1;
    private JBossMetaData primary;
    private JBossMetaData defaults;
    private ContainerConfigurationsMetaData configurationsWrapper;
    private transient InvokerProxyBindingsMetaData mergedInvokerBindings;


    public JBossMetaDataWrapper(JBossMetaData primary, JBossMetaData defaults) {
        this.primary = primary;
        this.defaults = defaults;
        configurationsWrapper = new ContainerConfigurationsMetaDataWrapper(primary.getContainerConfigurations(),
                defaults.getContainerConfigurations());
        wireOverrides();
    }

    @Override
    public String getDtdPublicId() {
        return primary.getDtdPublicId();
    }

    @Override
    public String getDtdSystemId() {
        return primary.getDtdSystemId();
    }

    @Override
    public String getEjbClientJar() {
        String clientJar = primary.getEjbClientJar();
        if (clientJar == null && defaults != null)
            clientJar = defaults.getEjbClientJar();
        return clientJar;
    }

    @Override
    public InterceptorsMetaData getInterceptors() {
        InterceptorsMetaData interceptors = primary.getInterceptors();
        if (interceptors == null && defaults != null)
            interceptors = defaults.getInterceptors();
        return interceptors;
    }

    @Override
    public String getJaccContextID() {
        String jaccID = primary.getJaccContextID();
        if (jaccID == null && defaults != null)
            jaccID = defaults.getJaccContextID();
        return jaccID;
    }

    @Override
    public RelationsMetaData getRelationships() {
        RelationsMetaData relations = primary.getRelationships();
        if (relations == null && defaults != null)
            relations = defaults.getRelationships();
        return relations;
    }

    @Override
    public boolean isEJB1x() {
        return primary.isEJB1x();
    }

    @Override
    public boolean isEJB21() {
        return primary.isEJB21();
    }

    @Override
    public boolean isEJB2x() {
        return primary.isEJB2x();
    }

    @Override
    public boolean isEJB3x() {
        return primary.isEJB3x();
    }

    @Override
    public JBossAssemblyDescriptorMetaData getAssemblyDescriptor() {
        return primary.getAssemblyDescriptor();
    }

    @Override
    public ContainerConfigurationMetaData getContainerConfiguration(String name) {
        ContainerConfigurationMetaData ccmd = configurationsWrapper.get(name);
        return ccmd;
    }

    @Override
    public ContainerConfigurationsMetaData getContainerConfigurations() {
        return configurationsWrapper;
    }

    @Override
    public JBossEnterpriseBeanMetaData getEnterpriseBean(String name) {
        return primary.getEnterpriseBean(name);
    }

    @Override
    public JBossEnterpriseBeansMetaData getEnterpriseBeans() {
        return primary.getEnterpriseBeans();
    }

    @Override
    public InvokerProxyBindingMetaData getInvokerProxyBinding(String name) {
        InvokerProxyBindingMetaData ipbmd = primary.getInvokerProxyBinding(name);
        if (ipbmd == null && defaults != null)
            ipbmd = defaults.getInvokerProxyBinding(name);
        return ipbmd;
    }

    @Override
    public InvokerProxyBindingsMetaData getInvokerProxyBindings() {
        if (mergedInvokerBindings == null) {
            mergedInvokerBindings = new InvokerProxyBindingsMetaDataWrapper(
                    this.primary.getInvokerProxyBindings(),
                    this.defaults.getInvokerProxyBindings());
        }
        return mergedInvokerBindings;
    }

    @Override
    public String getJmxName() {
        String jmxName = primary.getJmxName();
        if (jmxName == null && defaults != null)
            jmxName = defaults.getJmxName();
        return jmxName;
    }

    @Override
    public ResourceManagerMetaData getResourceManager(String name) {
        return primary.getResourceManager(name);
    }

    @Override
    public ResourceManagersMetaData getResourceManagers() {
        return primary.getResourceManagers();
    }

    @Override
    public String getSecurityDomain() {
        return primary.getSecurityDomain();
    }

    @Override
    public String getUnauthenticatedPrincipal() {
        String unauthenticatedPrincipal = primary.getUnauthenticatedPrincipal();
        if (unauthenticatedPrincipal == null && defaults != null)
            unauthenticatedPrincipal = defaults.getUnauthenticatedPrincipal();
        return unauthenticatedPrincipal;
    }


    @Override
    public String getJndiBindingPolicy() {
        String policy = super.getJndiBindingPolicy();
        if (policy == null && defaults != null)
            policy = defaults.getJndiBindingPolicy();
        return policy;
    }

    @Override
    public RunAsIdentityMetaData getRunAsIdentity(String ejbName) {
        RunAsIdentityMetaData runAs = primary.getRunAsIdentity(ejbName);
        if (runAs == null && defaults != null)
            runAs = defaults.getRunAsIdentity(ejbName);
        return runAs;
    }

    @Override
    public String getJMSResourceAdapter() {
        String defaultJmsRa = primary.getJMSResourceAdapter();
        if (defaultJmsRa == null && defaults != null)
            defaultJmsRa = defaults.getJMSResourceAdapter();
        return defaultJmsRa;
    }

    @Override
    public boolean isMetadataComplete() {
        return primary.isMetadataComplete();
    }

    @Override
    public String getEjbVersion() {
        String ejbVersion = primary.getEjbVersion();
        if (ejbVersion == null)
            ejbVersion = defaults.getEjbVersion();
        return ejbVersion;
    }

    @Override
    public String getVersion() {
        String version = primary.getVersion();
        if (version == null)
            version = defaults.getVersion();
        return version;
    }

    @Override
    public WebservicesMetaData getWebservices() {
        return primary.getWebservices();
    }

    @Override
    public boolean isExceptionOnRollback() {
        return primary.isExceptionOnRollback();
    }

    @Override
    public boolean isExcludeMissingMethods() {
        return primary.isExcludeMissingMethods();
    }

    @Override
    public DescriptionGroupMetaData getDescriptionGroup() {
        return primary.getDescriptionGroup();
    }

    @Override
    public String getId() {
        return primary.getId();
    }

    @Override
    public String toString() {
        return primary.toString();
    }

    protected void wireOverrides() {
        if (primary != null) {
            JBossEnterpriseBeansMetaData beans = (JBossEnterpriseBeansMetaData) primary.getEnterpriseBeans();
            beans.setJBossMetaData(this);
        }
    }
}
