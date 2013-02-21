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

import java.lang.reflect.Method;
import java.util.Set;

import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagementType;

import org.jboss.metadata.ejb.spec.ContainerTransactionsMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.ExcludeListMetaData;
import org.jboss.metadata.ejb.spec.MethodInterfaceType;
import org.jboss.metadata.ejb.spec.MethodPermissionsMetaData;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.javaee.jboss.AnnotationsMetaData;
import org.jboss.metadata.javaee.jboss.IgnoreDependencyMetaData;
import org.jboss.metadata.javaee.jboss.JndiRefsMetaData;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferencesMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.Environment;
import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferencesMetaData;
import org.jboss.metadata.javaee.spec.PortComponent;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;

/**
 * Create a wrapper around a JBossGenericBeanMetaData for MDB meta data.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 75470 $
 */
public class JBossMessageDrivenBeanGenericWrapper extends JBossMessageDrivenBeanMetaData {
    private static final long serialVersionUID = 1L;

    private JBossGenericBeanMetaData delegate;

    public JBossMessageDrivenBeanGenericWrapper(JBossGenericBeanMetaData delegate) {
        assert delegate != null : "delegate is null";

        this.delegate = delegate;
    }

    // custom delegate methods

    @Override
    public String getDestinationJndiName() {
        return getJndiName();
    }

    // generated delegate methods

    public Set<String> determineAllDepends() {
        return delegate.determineAllDepends();
    }

    public String determineConfigurationName() {
        return delegate.determineConfigurationName();
    }

    public ContainerConfigurationMetaData determineContainerConfiguration() {
        return delegate.determineContainerConfiguration();
    }

    public InvokerBindingMetaData determineInvokerBinding(String invokerName) {
        return delegate.determineInvokerBinding(invokerName);
    }

    public InvokerBindingsMetaData determineInvokerBindings() {
        return delegate.determineInvokerBindings();
    }

    public boolean equals(Object obj) {
        return delegate.equals(obj);
    }

    public AnnotatedEJBReferencesMetaData getAnnotatedEjbReferences() {
        return delegate.getAnnotatedEjbReferences();
    }

    public AnnotationsMetaData getAnnotations() {
        return delegate.getAnnotations();
    }

    public String getAopDomainName() {
        return delegate.getAopDomainName();
    }

    public String getConfigurationName() {
        return delegate.getConfigurationName();
    }

    public String getContainerObjectNameJndiName() {
        return delegate.getContainerObjectNameJndiName();
    }

    public ContainerTransactionsMetaData getContainerTransactions() {
        return delegate.getContainerTransactions();
    }

    public String getDefaultConfigurationName() {
        return delegate.getDefaultConfigurationName();
    }

    public Set<String> getDepends() {
        return delegate.getDepends();
    }

    public DescriptionGroupMetaData getDescriptionGroup() {
        return delegate.getDescriptionGroup();
    }

    public String getEjbClass() {
        return delegate.getEjbClass();
    }

    public JBossMetaData getEjbJarMetaData() {
        return delegate.getEjbJarMetaData();
    }

    public EJBLocalReferenceMetaData getEjbLocalReferenceByName(String name) {
        return delegate.getEjbLocalReferenceByName(name);
    }

    public EJBLocalReferencesMetaData getEjbLocalReferences() {
        return delegate.getEjbLocalReferences();
    }

    public String getEjbName() {
        return delegate.getEjbName();
    }

    public EJBReferenceMetaData getEjbReferenceByName(String name) {
        return delegate.getEjbReferenceByName(name);
    }

    public EJBReferencesMetaData getEjbReferences() {
        return delegate.getEjbReferences();
    }

    public JBossEnterpriseBeansMetaData getEnterpriseBeansMetaData() {
        return delegate.getEnterpriseBeansMetaData();
    }

    public EnvironmentEntriesMetaData getEnvironmentEntries() {
        return delegate.getEnvironmentEntries();
    }

    public EnvironmentEntryMetaData getEnvironmentEntryByName(String name) {
        return delegate.getEnvironmentEntryByName(name);
    }

    public ExcludeListMetaData getExcludeList() {
        return delegate.getExcludeList();
    }

    public String getId() {
        return delegate.getId();
    }

    public IgnoreDependencyMetaData getIgnoreDependency() {
        return delegate.getIgnoreDependency();
    }

    public InvokerBindingsMetaData getInvokerBindings() {
        return delegate.getInvokerBindings();
    }

    public IORSecurityConfigMetaData getIorSecurityConfig() {
        return delegate.getIorSecurityConfig();
    }

    public JBossMetaData getJBossMetaData() {
        return delegate.getJBossMetaData();
    }

    public JBossMetaData getJBossMetaDataWithCheck() {
        return delegate.getJBossMetaDataWithCheck();
    }

    public Environment getJndiEnvironmentRefsGroup() {
        return delegate.getJndiEnvironmentRefsGroup();
    }

    public String getJndiName() {
        return delegate.getJndiName();
    }

    public JndiRefsMetaData getJndiRefs() {
        return delegate.getJndiRefs();
    }

    public String getKey() {
        return delegate.getKey();
    }

    public String getLocalJndiName() {
        return delegate.getLocalJndiName();
    }

    public String getMappedName() {
        return delegate.getMappedName();
    }

    public MessageDestinationReferenceMetaData getMessageDestinationReferenceByName(String name) {
        return delegate.getMessageDestinationReferenceByName(name);
    }

    public MessageDestinationReferencesMetaData getMessageDestinationReferences() {
        return delegate.getMessageDestinationReferences();
    }

    public MethodAttributesMetaData getMethodAttributes() {
        return delegate.getMethodAttributes();
    }

    public MethodPermissionsMetaData getMethodPermissions() {
        return delegate.getMethodPermissions();
    }

    public Set<String> getMethodPermissions(String methodName, Class<?>[] params, MethodInterfaceType interfaceType) {
        return delegate.getMethodPermissions(methodName, params, interfaceType);
    }

    public int getMethodTransactionTimeout(Method method) {
        return delegate.getMethodTransactionTimeout(method);
    }

    public int getMethodTransactionTimeout(String methodName) {
        return delegate.getMethodTransactionTimeout(methodName);
    }

    public TransactionAttributeType getMethodTransactionType(Method m, MethodInterfaceType iface) {
        return delegate.getMethodTransactionType(m, iface);
    }

    public TransactionAttributeType getMethodTransactionType(String methodName, Class<?>[] params,
                                                             MethodInterfaceType iface) {
        return delegate.getMethodTransactionType(methodName, params, iface);
    }

    public String getName() {
        return delegate.getName();
    }

    public PersistenceContextReferenceMetaData getPersistenceContextReferenceByName(String name) {
        return delegate.getPersistenceContextReferenceByName(name);
    }

    public PersistenceContextReferencesMetaData getPersistenceContextRefs() {
        return delegate.getPersistenceContextRefs();
    }

    public PersistenceUnitReferenceMetaData getPersistenceUnitReferenceByName(String name) {
        return delegate.getPersistenceUnitReferenceByName(name);
    }

    public PersistenceUnitReferencesMetaData getPersistenceUnitRefs() {
        return delegate.getPersistenceUnitRefs();
    }

    public PoolConfigMetaData getPoolConfig() {
        return delegate.getPoolConfig();
    }

    public PortComponent getPortComponent() {
        return delegate.getPortComponent();
    }

    public LifecycleCallbacksMetaData getPostConstructs() {
        return delegate.getPostConstructs();
    }

    public LifecycleCallbacksMetaData getPreDestroys() {
        return delegate.getPreDestroys();
    }

    public ResourceEnvironmentReferenceMetaData getResourceEnvironmentReferenceByName(String name) {
        return delegate.getResourceEnvironmentReferenceByName(name);
    }

    public ResourceEnvironmentReferencesMetaData getResourceEnvironmentReferences() {
        return delegate.getResourceEnvironmentReferences();
    }

    public ResourceReferenceMetaData getResourceReferenceByName(String name) {
        return delegate.getResourceReferenceByName(name);
    }

    public ResourceReferencesMetaData getResourceReferences() {
        return delegate.getResourceReferences();
    }

    public String getSecurityDomain() {
        return delegate.getSecurityDomain();
    }

    public SecurityIdentityMetaData getSecurityIdentity() {
        return delegate.getSecurityIdentity();
    }

    public String getSecurityProxy() {
        return delegate.getSecurityProxy();
    }

    public SecurityRoleMetaData getSecurityRole(String roleName) {
        return delegate.getSecurityRole(roleName);
    }

    public Set<String> getSecurityRolePrincipals(String roleName) {
        return delegate.getSecurityRolePrincipals(roleName);
    }

    public SecurityRoleRefsMetaData getSecurityRoleRefs() {
        return delegate.getSecurityRoleRefs();
    }

    public ServiceReferenceMetaData getServiceReferenceByName(String name) {
        return delegate.getServiceReferenceByName(name);
    }

    public ServiceReferencesMetaData getServiceReferences() {
        return delegate.getServiceReferences();
    }

    public TransactionManagementType getTransactionType() {
        return delegate.getTransactionType();
    }

    public int hashCode() {
        return delegate.hashCode();
    }

    public boolean hasMethodPermissions(String methodName, Class<?>[] params, MethodInterfaceType interfaceType) {
        return delegate.hasMethodPermissions(methodName, params, interfaceType);
    }

    public boolean isBMT() {
        return delegate.isBMT();
    }

    public boolean isCMT() {
        return delegate.isCMT();
    }

    public boolean isConsumer() {
        return delegate.isConsumer();
    }

    public boolean isEntity() {
        return delegate.isEntity();
    }

    public boolean isExceptionOnRollback() {
        return delegate.isExceptionOnRollback();
    }

    public boolean isMessageDriven() {
        return delegate.isMessageDriven();
    }

    public boolean isMethodReadOnly(Method method) {
        return delegate.isMethodReadOnly(method);
    }

    public boolean isMethodReadOnly(String methodName) {
        return delegate.isMethodReadOnly(methodName);
    }

    public boolean isService() {
        return delegate.isService();
    }

    public boolean isSession() {
        return delegate.isSession();
    }

    public boolean isTimerPersistence() {
        return delegate.isTimerPersistence();
    }

    public void merge(JBossEnterpriseBeanMetaData override, EnterpriseBeanMetaData original, String overrideFile,
                      String overridenFile, boolean mustOverride) {
        delegate.merge(override, original, overrideFile, overridenFile, mustOverride);
    }

    public void merge(JBossEnterpriseBeanMetaData override, EnterpriseBeanMetaData original) {
        delegate.merge(override, original);
    }

    public void setAnnotations(AnnotationsMetaData annotations) {
        delegate.setAnnotations(annotations);
    }

    public void setAopDomainName(String aopDomainName) {
        delegate.setAopDomainName(aopDomainName);
    }

    public void setConfigurationName(String configurationName) {
        delegate.setConfigurationName(configurationName);
    }

    public void setDepends(Set<String> depends) {
        delegate.setDepends(depends);
    }

    public void setDescriptionGroup(DescriptionGroupMetaData descriptionGroup) {
        delegate.setDescriptionGroup(descriptionGroup);
    }

    public void setEjbClass(String ejbClass) {
        delegate.setEjbClass(ejbClass);
    }

    public void setEjbName(String ejbName) {
        delegate.setEjbName(ejbName);
    }

    public void setEnterpriseBeansMetaData(JBossEnterpriseBeansMetaData enterpriseBeansMetaData) {
        delegate.setEnterpriseBeansMetaData(enterpriseBeansMetaData);
    }

    public void setExceptionOnRollback(boolean exceptionOnRollback) {
        delegate.setExceptionOnRollback(exceptionOnRollback);
    }

    public void setId(String id) {
        delegate.setId(id);
    }

    public void setIgnoreDependency(IgnoreDependencyMetaData ignoreDependency) {
        delegate.setIgnoreDependency(ignoreDependency);
    }

    public void setInvokerBindings(InvokerBindingsMetaData invokers) {
        delegate.setInvokerBindings(invokers);
    }

    public void setIorSecurityConfig(IORSecurityConfigMetaData iorSecurityConfig) {
        delegate.setIorSecurityConfig(iorSecurityConfig);
    }

    public void setJndiEnvironmentRefsGroup(Environment env) {
        delegate.setJndiEnvironmentRefsGroup(env);
    }

    public void setJndiName(String jndiName) {
        delegate.setJndiName(jndiName);
    }

    public void setJndiRefs(JndiRefsMetaData jndiRefs) {
        delegate.setJndiRefs(jndiRefs);
    }

    public void setLocalJndiName(String localJndiName) {
        delegate.setLocalJndiName(localJndiName);
    }

    public void setMappedName(String mappedName) {
        delegate.setMappedName(mappedName);
    }

    public void setMethodAttributes(MethodAttributesMetaData methodAttributes) {
        delegate.setMethodAttributes(methodAttributes);
    }

    public void setName(String name) {
        delegate.setName(name);
    }

    public void setPoolConfig(PoolConfigMetaData poolConfig) {
        delegate.setPoolConfig(poolConfig);
    }

    public void setPortComponent(PortComponent portComponent) {
        delegate.setPortComponent(portComponent);
    }

    public void setSecurityDomain(String securityDomain) {
        delegate.setSecurityDomain(securityDomain);
    }

    public void setSecurityIdentity(SecurityIdentityMetaData securityIdentity) {
        delegate.setSecurityIdentity(securityIdentity);
    }

    public void setSecurityProxy(String securityProxy) {
        delegate.setSecurityProxy(securityProxy);
    }

    public void setTimerPersistence(boolean timerPersistence) {
        delegate.setTimerPersistence(timerPersistence);
    }

    public void setTransactionType(TransactionManagementType transactionType) {
        delegate.setTransactionType(transactionType);
    }


}
