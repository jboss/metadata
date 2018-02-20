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
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagementType;

import org.jboss.metadata.ejb.common.IEnterpriseBeanMetaData;
import org.jboss.metadata.common.ejb.JBossEnvironmentRefsGroupMetaData;
import org.jboss.metadata.common.ejb.ResourceManagersMetaData;
import org.jboss.metadata.ejb.spec.ContainerTransactionMetaData;
import org.jboss.metadata.ejb.spec.ContainerTransactionsMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.EntityBeanMetaData;
import org.jboss.metadata.ejb.spec.ExcludeListMetaData;
import org.jboss.metadata.ejb.spec.MessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.spec.MethodInterfaceType;
import org.jboss.metadata.ejb.spec.MethodMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionsMetaData;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.javaee.jboss.AnnotationsMetaData;
import org.jboss.metadata.javaee.jboss.IgnoreDependencyMetaData;
import org.jboss.metadata.javaee.jboss.JndiRefsMetaData;
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
import org.jboss.metadata.javaee.spec.Environment;
import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.JMSConnectionFactoriesMetaData;
import org.jboss.metadata.javaee.spec.JMSConnectionFactoryMetaData;
import org.jboss.metadata.javaee.spec.JMSDestinationMetaData;
import org.jboss.metadata.javaee.spec.JMSDestinationsMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.MailSessionMetaData;
import org.jboss.metadata.javaee.spec.MailSessionsMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.metadata.javaee.support.AbstractMappedMetaData;
import org.jboss.metadata.javaee.support.NamedMetaData;
import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptionGroup;
import org.jboss.metadata.javaee.support.NonNullLinkedHashSet;
import org.jboss.metadata.merge.ejb.jboss.JBossEnvironmentRefsGroupMetaDataMerger;
import org.jboss.metadata.merge.javaee.jboss.AnnotationsMetaDataMerger;
import org.jboss.metadata.merge.javaee.jboss.IgnoreDependencyMetaDataMerger;
import org.jboss.metadata.merge.javaee.jboss.JndiRefsMetaDataMerger;
import org.jboss.metadata.merge.javaee.support.NamedMetaDataWithDescriptionGroupMerger;

/**
 * enterprise-bean/{session,entity,message-driven} metadata
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */
@Deprecated
public abstract class JBossEnterpriseBeanMetaData extends NamedMetaDataWithDescriptionGroup
        implements Environment,
        IEnterpriseBeanMetaData<JBossAssemblyDescriptorMetaData, JBossEnterpriseBeansMetaData, JBossEnterpriseBeanMetaData, JBossMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 6909774842926430193L;

    /**
     * The enterprise bean container
     */
    private JBossEnterpriseBeansMetaData enterpriseBeansMetaData;

    /**
     * The mapped name
     */
    private String mappedName;

    /**
     * The ejb class
     */
    private String ejbClass;

    /**
     * The local jndi name
     */
    private String localJndiName;

    /**
     * Whether to throw an exception if the transaction is marked for rollback
     */
    private boolean exceptionOnRollback;

    /**
     * Whether to persist timers
     */
    private boolean timerPersistence = true;

    /**
     * The configuration name
     */
    private String configurationName;

    /**
     * The invokers
     */
    private InvokerBindingsMetaData invokers;

    /**
     * The determined invokers
     */
    private transient InvokerBindingsMetaData determinedInvokers;

    /**
     * The ior security config
     */
    private IORSecurityConfigMetaData iorSecurityConfig;

    /**
     * The security proxy
     */
    private String securityProxy;

    /**
     * The environment
     */
    private JBossEnvironmentRefsGroupMetaData jndiEnvironmentRefsGroup;

    /**
     * The method attributes
     */
    private MethodAttributesMetaData methodAttributes;

    /**
     * The security domain
     */
    private String securityDomain;

    /**
     * The dependencies
     */
    private Set<String> depends;

    /**
     * The annotations
     */
    private AnnotationsMetaData annotations;

    /**
     * Ignore dependency
     */
    private IgnoreDependencyMetaData ignoreDependency;

    /**
     * The aop domain name
     */
    private String aopDomainName;

    /**
     * The pool configuration
     */
    private PoolConfigMetaData poolConfig;

    /**
     * The jndi refs
     */
    private JndiRefsMetaData jndiRefs;

    /**
     * The security identity
     */
    private SecurityIdentityMetaData securityIdentity;

    /**
     * The cached container transactions
     */
    private transient ContainerTransactionsMetaData cachedContainerTransactions;

    /**
     * The transaction type cache
     */
    private transient ConcurrentHashMap<Method, TransactionAttributeType> methodTx;
    /**
     * The transaction type
     */
    private TransactionManagementType transactionType;

    /**
     * the class name that implements the JNDI binding policy for this ejb
     */
    private String jndiBindingPolicy;
    /**
     * The runtime name of the ejb container as known to the 'kernel', what ever it is
     */
    private String containerName;
    /**
     * A non-managed generated container name as known to the 'kernel', what ever it is
     */
    private String generatedContainerName;

    /**
     * Create the correct JBossEnterpriseBeanMetaData for the input
     * standard bean metadata.
     *
     * @param bean the standard bean metadata
     * @return the corresponding jboss extenstion metadata
     */
    public static JBossEnterpriseBeanMetaData newBean(EnterpriseBeanMetaData bean) {
        if (bean instanceof EntityBeanMetaData)
            return new JBossEntityBeanMetaData();
        if (bean instanceof MessageDrivenBeanMetaData)
            return new JBossMessageDrivenBean31MetaData();
        if (bean instanceof SessionBean31MetaData)
            return new JBossSessionBean31MetaData();
        if (bean instanceof SessionBeanMetaData)
            return new JBossSessionBeanMetaData();
        throw new IllegalArgumentException("Can't handle " + bean.getClass() + " on " + bean);
    }

    /**
     * @return returns a new instance of the same type
     */
    public JBossEnterpriseBeanMetaData newBean() {
        try {
            return getClass().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create a new instance of " + getClass(), e);
        }
    }

    /**
     * Create a new EnterpriseBeanMetaData.
     */
    public JBossEnterpriseBeanMetaData() {
        // For serialization
    }

    /**
     * Get the enterpriseBeansMetaData.
     *
     * @return the enterpriseBeansMetaData.
     */
    public JBossEnterpriseBeansMetaData getEnterpriseBeansMetaData() {
        return enterpriseBeansMetaData;
    }

    /**
     * Set the enterpriseBeansMetaData.
     *
     * @param enterpriseBeansMetaData the enterpriseBeansMetaData.
     */
    public void setEnterpriseBeansMetaData(JBossEnterpriseBeansMetaData enterpriseBeansMetaData) {
        assert enterpriseBeansMetaData != null : "enterpriseBeansMetaData is null";

        this.enterpriseBeansMetaData = enterpriseBeansMetaData;
    }

    /**
     * Get the jbossMetaData.
     *
     * @return the jbossMetaData.
     */
    public JBossMetaData getJBossMetaData() {
        if (enterpriseBeansMetaData == null)
            return null;
        return enterpriseBeansMetaData.getEjbJarMetaData();
    }

    /**
     * Get the jbossMetaData.
     *
     * @return the jbossMetaData with check
     */
    public JBossMetaData getJBossMetaDataWithCheck() {
        JBossMetaData jbossMetaData = getJBossMetaData();
        if (jbossMetaData == null)
            throw new IllegalStateException("This bean is not a part of a deployment " + this);
        return jbossMetaData;
    }

    /**
     * Get the jndiEnvironmentRefsGroup.
     *
     * @return the jndiEnvironmentRefsGroup.
     */
    public Environment getJndiEnvironmentRefsGroup() {
        return jndiEnvironmentRefsGroup;
    }

    public void setJndiEnvironmentRefsGroup(Environment env) {
        if (env == null)
            throw new IllegalArgumentException("Null jndiEnvironmentRefsGroup");
        this.jndiEnvironmentRefsGroup = (JBossEnvironmentRefsGroupMetaData) env;
    }

    // just for XML binding, to expose the type of the model group
    public void setEnvironmentRefsGroup(JBossEnvironmentRefsGroupMetaData env) {
        this.setJndiEnvironmentRefsGroup(env);
    }

    /**
     * Get the ejbName.
     *
     * @return the ejbName.
     */
    public String getEjbName() {
        return getName();
    }

    /**
     * Set the ejbName.
     *
     * @param ejbName the ejbName.
     * @throws IllegalArgumentException for a null ejbName
     */
    public void setEjbName(String ejbName) {
        setName(ejbName);
    }

    public String getEjbClass() {
        return ejbClass;
    }

    public JBossMetaData getEjbJarMetaData() {
        return enterpriseBeansMetaData.getEjbJarMetaData();
    }

    /**
     * Get the container transactions
     *
     * @return the container transactions or null for no result
     */
    public ContainerTransactionsMetaData getContainerTransactions() {
        if (cachedContainerTransactions != null)
            return cachedContainerTransactions;
        JBossAssemblyDescriptorMetaData assemblyDescriptor = getAssemblyDescriptor();
        if (assemblyDescriptor == null)
            return null;
        return assemblyDescriptor.getContainerTransactionsByEjbName(getEjbName());
    }

    /**
     * Get the method transaction type
     *
     * @param methodName the method name
     * @param params     the parameters
     * @param iface      the interface type
     * @return the method transaction type
     */
    public TransactionAttributeType getMethodTransactionType(String methodName, Class<?>[] params, MethodInterfaceType iface) {
        // default value
        TransactionAttributeType result = null;

        ContainerTransactionsMetaData containerTransactions = getContainerTransactions();
        if (containerTransactions == null || containerTransactions.isEmpty())
            return result;

        ContainerTransactionMetaData bestMatchTransaction = null;
        MethodMetaData bestMatch = null;
        for (ContainerTransactionMetaData transaction : containerTransactions) {
            MethodMetaData match = transaction.bestMatch(methodName, params, iface, bestMatch);
            if (match != bestMatch) {
                bestMatchTransaction = transaction;
                bestMatch = match;
            }
        }

        if (bestMatchTransaction != null)
            result = bestMatchTransaction.getTransAttribute();

        return result;
    }

    /**
     * Get the transaction type
     *
     * @param m     the method
     * @param iface the interface type
     * @return the transaction type
     */
    public TransactionAttributeType getMethodTransactionType(Method m, MethodInterfaceType iface) {
        if (m == null)
            return TransactionAttributeType.SUPPORTS;

        TransactionAttributeType result = null;
        if (methodTx != null) {
            result = methodTx.get(m);
            if (result != null)
                return result;
        }

        result = getMethodTransactionType(m.getName(), m.getParameterTypes(), iface);

        // provide default if method is not found in descriptor
        if (result == null)
            result = TransactionAttributeType.REQUIRED;

        if (methodTx == null)
            methodTx = new ConcurrentHashMap<Method, TransactionAttributeType>();
        methodTx.put(m, result);
        return result;
    }

    /**
     * Get the exclude list
     *
     * @return the exclude list or null for no result
     */
    public ExcludeListMetaData getExcludeList() {
        JBossAssemblyDescriptorMetaData assemblyDescriptor = getAssemblyDescriptor();
        if (assemblyDescriptor == null)
            return null;
        return assemblyDescriptor.getExcludeListByEjbName(getEjbName());
    }

    public String getMappedName() {
        return mappedName;
    }

    public void setEjbClass(String ejbClass) {
        this.ejbClass = ejbClass;
    }

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
    }

    /**
     * Whether this is a consumer bean
     *
     * @return true when a consumer bean
     */
    public boolean isConsumer() {
        return false;
    }

    /**
     * Whether this is a service bean
     *
     * @return true when a service bean
     */
    public boolean isService() {
        return false;
    }

    /**
     * Whether this is a session bean
     *
     * @return true when a session bean
     */
    public boolean isSession() {
        return false;
    }

    /**
     * Whether this is a message driven bean
     *
     * @return true when a message driven bean
     */
    public boolean isMessageDriven() {
        return false;
    }

    /**
     * Whether this is an entity bean
     *
     * @return true when an entity bean
     */
    public boolean isEntity() {
        return false;
    }

    public boolean isGeneric() {
        return false;
    }

    /**
     * Is this container managed transactions
     *
     * @return true when CMT
     */
    public boolean isCMT() {
        TransactionManagementType type = getTransactionType();
        if (type == null)
            return true;
        else
            return type == TransactionManagementType.CONTAINER;
    }

    /**
     * Is this bean managed transactions
     *
     * @return true when BMT
     */
    public boolean isBMT() {
        return isCMT() == false;
    }

    public TransactionManagementType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionManagementType transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Get the localJndiName.
     *
     * @return the localJndiName.
     */
    public String getLocalJndiName() {
        return localJndiName;
    }

    /**
     * Set the localJndiName.
     *
     * @param localJndiName the localJndiName.
     * @throws IllegalArgumentException for a null localJndiName
     */
    public void setLocalJndiName(String localJndiName) {
        if (localJndiName == null)
            throw new IllegalArgumentException("Null localJndiName");
        this.localJndiName = localJndiName;
    }

    /**
     * Determine the localJndiName.
     *
     * @return the localJndiName.
     * @deprecated JBMETA-68
     */
    @Deprecated
    public String determineLocalJndiName() {
        if (localJndiName != null)
            return localJndiName;

        String ejbName = getEjbName();
        return localJndiName = "local/" + ejbName + '@' + System.identityHashCode(ejbName);
    }

    /**
     * Get the base jndi name for the bean if one exists. Not all ejbs have
     * a jndi name notion.
     *
     * @return the base jndi name for the ejb it one exists, null otherwise.
     * @deprecated Handled by Decorators, JBMETA-68
     */
    @Deprecated
    public abstract String determineJndiName();

    /**
     * Determine the container jndi name used in the object name. This is
     * really obsolete as there is no need for jmx names.
     *
     * @return the jndi name suitable for use in the object name
     */
    public String getContainerObjectNameJndiName() {
        return getLocalJndiName();
    }

    /**
     * Get the kernel name for the ejb container. This is the managed property
     * version admin tools may use to control the name. Generally its not set
     * and the server will set the name via the generatedContainerName
     * non-managed property.
     *
     * @return containerName property value.
     * @see #setGeneratedContainerName(String)
     */
    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    /**
     * Get the generated kernel name for the ejb container. This is the
     * non-managed property version that the server would use to set the name
     * it generated when no containerName property existed.
     *
     * @return generatedContainerName property value.
     * @see #getContainerName()
     */
    public String getGeneratedContainerName() {
        return generatedContainerName;
    }

    public void setGeneratedContainerName(String containerName) {
        this.generatedContainerName = containerName;
    }

    /**
     * Get the kernel name for the ejb container. This is either the
     * containerName managed property, or a runtime generated name set via
     * the non-managed generatedContainerName property.
     *
     * @return the kernel name for the ejb container
     */
    public String determineContainerName() {
        String name = containerName;
        if (name == null)
            name = generatedContainerName;
        return name;
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

    /**
     * Get the timerPersistence.
     *
     * @return the timerPersistence.
     */
    public boolean isTimerPersistence() {
        return timerPersistence;
    }

    /**
     * Set the timerPersistence.
     *
     * @param timerPersistence the timerPersistence.
     */
    public void setTimerPersistence(boolean timerPersistence) {
        this.timerPersistence = timerPersistence;
    }

    /**
     * Get the configurationName.
     *
     * @return the configurationName.
     */
    public String getConfigurationName() {
        return configurationName;
    }

    /**
     * Set the configurationName.
     *
     * @param configurationName the configurationName.
     * @throws IllegalArgumentException for a null configurationName
     */
    public void setConfigurationName(String configurationName) {
        if (configurationName == null)
            throw new IllegalArgumentException("Null configurationName");
        this.configurationName = configurationName;
    }

    /**
     * Determine the configuration name
     *
     * @return the configuration name
     */
    public String determineConfigurationName() {
        if (configurationName != null)
            return configurationName;

        return getDefaultConfigurationName();
    }

    /**
     * Get the container configuration
     *
     * @return the container configuration
     */
    public ContainerConfigurationMetaData determineContainerConfiguration() {
        String name = determineConfigurationName();
        ContainerConfigurationMetaData result = getJBossMetaDataWithCheck().getContainerConfiguration(name);
        if (result == null)
            throw new IllegalStateException("Container configuration not found: " + name + " available: " + getJBossMetaDataWithCheck().getContainerConfigurations());
        return result;
    }

    public void setPoolConfig(PoolConfigMetaData poolConfig) {
        this.poolConfig = poolConfig;
    }

    /**
     * Get the default configuration name
     *
     * @return the default name
     */
    public abstract String getDefaultConfigurationName();

    /**
     * Get the securityProxy.
     *
     * @return the securityProxy.
     */
    public String getSecurityProxy() {
        return securityProxy;
    }

    /**
     * Set the securityProxy.
     *
     * @param securityProxy the securityProxy.
     * @throws IllegalArgumentException for a null securityProxy
     */
    public void setSecurityProxy(String securityProxy) {
        if (securityProxy == null)
            throw new IllegalArgumentException("Null securityProxy");
        this.securityProxy = securityProxy;
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
        this.securityDomain = securityDomain;
    }

    /**
     * Get the depends.
     *
     * @return the depends.
     */
    public Set<String> getDepends() {
        return depends;
    }

    /**
     * Set the depends.
     *
     * @param depends the depends.
     * @throws IllegalArgumentException for a null depends
     */
    public void setDepends(Set<String> depends) {
        if (depends == null)
            throw new IllegalArgumentException("Null depends");
        this.depends = depends;
    }

    /**
     * Get the depends.
     *
     * @return the depends.
     */
    public Set<String> determineAllDepends() {
        NonNullLinkedHashSet<String> result = new NonNullLinkedHashSet<String>();

        Set<String> depends = getDepends();
        if (depends != null)
            result.addAll(depends);

        ContainerConfigurationMetaData containerConfigurationMetaData = determineContainerConfiguration();
        if (containerConfigurationMetaData != null) {
            depends = containerConfigurationMetaData.getDepends();
            if (depends != null)
                result.addAll(depends);
        }

        return result;
    }

    /**
     * Get the invokers.
     *
     * @return the invokers.
     */
    public InvokerBindingsMetaData getInvokerBindings() {
        return invokers;
    }

    /**
     * Set the invokers.
     *
     * @param invokers the invokers.
     * @throws IllegalArgumentException for a null invokers
     */
    public void setInvokerBindings(InvokerBindingsMetaData invokers) {
        if (invokers == null)
            throw new IllegalArgumentException("Null invokers");
        this.invokers = invokers;
    }

    /**
     * Determine the invokers
     *
     * @return the invokers.
     */
    public InvokerBindingsMetaData determineInvokerBindings() {
        // We already worked it out
        if (determinedInvokers != null)
            return determinedInvokers;

        // Use anything configured
        if (invokers != null) {
            determinedInvokers = invokers;
            return determinedInvokers;
        }

        // Look at the container configuration
        ContainerConfigurationMetaData containerConfiguration = determineContainerConfiguration();
        Set<String> invokerProxyBindingNames = containerConfiguration.getInvokerProxyBindingNames();
        if (invokerProxyBindingNames != null && invokerProxyBindingNames.isEmpty() == false) {
            determinedInvokers = new InvokerBindingsMetaData();

            // Like the original code, they all get bound with the same name?
            String jndiName = getDefaultInvokerJndiName();
            for (String name : invokerProxyBindingNames) {
                InvokerBindingMetaData invoker = new InvokerBindingMetaData();
                invoker.setInvokerProxyBindingName(name);
                if (jndiName != null)
                    invoker.setJndiName(jndiName);
                determinedInvokers.add(invoker);
            }
        } else {
            determinedInvokers = getDefaultInvokers();
        }
        return determinedInvokers;
    }

    /**
     * Determine an invoker binding
     *
     * @param invokerName the invoker proxy binding name
     * @return the invoke binding
     * @throws IllegalStateException if there is no such binding
     */
    public InvokerBindingMetaData determineInvokerBinding(String invokerName) {
        InvokerBindingMetaData binding = determineInvokerBindings().get(invokerName);
        if (binding == null)
            throw new IllegalStateException("No such binding: " + invokerName + " available: " + determinedInvokers);
        return binding;
    }

    /**
     * Determine the jndi name for invoker bindings that come from the container configuration
     *
     * @return the jndi name suitable for use on the default invoker
     */
    protected String getDefaultInvokerJndiName() {
        return null;
    }

    /**
     * Get the default invokers
     *
     * @return the default invokers
     */
    protected InvokerBindingsMetaData getDefaultInvokers() {
        InvokerBindingsMetaData bindings = new InvokerBindingsMetaData();
        InvokerBindingMetaData binding = new InvokerBindingMetaData();
        binding.setInvokerProxyBindingName(getDefaultInvokerName());
        String jndiName = getDefaultInvokerJndiName();
        if (jndiName != null)
            binding.setJndiName(getDefaultInvokerJndiName());
        bindings.add(binding);
        return bindings;
    }

    /**
     * Get the default invokers
     *
     * @return the default invokers
     */
    protected abstract String getDefaultInvokerName();

    public EJBLocalReferenceMetaData getEjbLocalReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getEjbLocalReferences());
    }

    public EJBLocalReferencesMetaData getEjbLocalReferences() {
        EJBLocalReferencesMetaData refs = null;
        if (jndiEnvironmentRefsGroup != null)
            refs = jndiEnvironmentRefsGroup.getEjbLocalReferences();
        return refs;
    }

    public EJBReferenceMetaData getEjbReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getEjbReferences());
    }

    public EJBReferencesMetaData getEjbReferences() {
        EJBReferencesMetaData refs = null;
        if (jndiEnvironmentRefsGroup != null)
            refs = jndiEnvironmentRefsGroup.getEjbReferences();
        return refs;
    }

    // TODO?
    public AnnotatedEJBReferencesMetaData getAnnotatedEjbReferences() {
        AnnotatedEJBReferencesMetaData refs = null;
        if (jndiEnvironmentRefsGroup != null)
            refs = jndiEnvironmentRefsGroup.getAnnotatedEjbReferences();
        return refs;
    }

    public EnvironmentEntriesMetaData getEnvironmentEntries() {
        EnvironmentEntriesMetaData env = null;
        if (jndiEnvironmentRefsGroup != null)
            env = jndiEnvironmentRefsGroup.getEnvironmentEntries();
        return env;
    }

    public EnvironmentEntryMetaData getEnvironmentEntryByName(String name) {
        return AbstractMappedMetaData.getByName(name, getEnvironmentEntries());
    }

    public MessageDestinationReferenceMetaData getMessageDestinationReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getMessageDestinationReferences());
    }

    public MessageDestinationReferencesMetaData getMessageDestinationReferences() {
        MessageDestinationReferencesMetaData refs = null;
        if (jndiEnvironmentRefsGroup != null)
            refs = jndiEnvironmentRefsGroup.getMessageDestinationReferences();
        return refs;
    }

    public PersistenceContextReferenceMetaData getPersistenceContextReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getPersistenceContextRefs());
    }

    public PersistenceContextReferencesMetaData getPersistenceContextRefs() {
        PersistenceContextReferencesMetaData refs = null;
        if (jndiEnvironmentRefsGroup != null)
            refs = jndiEnvironmentRefsGroup.getPersistenceContextRefs();
        return refs;
    }

    public PersistenceUnitReferenceMetaData getPersistenceUnitReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getPersistenceUnitRefs());
    }

    public PersistenceUnitReferencesMetaData getPersistenceUnitRefs() {
        PersistenceUnitReferencesMetaData refs = null;
        if (jndiEnvironmentRefsGroup != null)
            refs = jndiEnvironmentRefsGroup.getPersistenceUnitRefs();
        return refs;
    }

    public PoolConfigMetaData getPoolConfig() {
        return poolConfig;
    }

    public LifecycleCallbacksMetaData getPostConstructs() {
        LifecycleCallbacksMetaData lcs = null;
        if (jndiEnvironmentRefsGroup != null)
            lcs = jndiEnvironmentRefsGroup.getPostConstructs();
        return lcs;
    }

    public LifecycleCallbacksMetaData getPreDestroys() {
        LifecycleCallbacksMetaData lcs = null;
        if (jndiEnvironmentRefsGroup != null)
            lcs = jndiEnvironmentRefsGroup.getPreDestroys();
        return lcs;
    }

    public ResourceEnvironmentReferenceMetaData getResourceEnvironmentReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getResourceEnvironmentReferences());
    }

    public ResourceEnvironmentReferencesMetaData getResourceEnvironmentReferences() {
        ResourceEnvironmentReferencesMetaData refs = null;
        if (jndiEnvironmentRefsGroup != null)
            refs = jndiEnvironmentRefsGroup.getResourceEnvironmentReferences();
        return refs;
    }

    public ResourceReferenceMetaData getResourceReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getResourceReferences());
    }

    public ResourceReferencesMetaData getResourceReferences() {
        ResourceReferencesMetaData refs = null;
        if (jndiEnvironmentRefsGroup != null)
            refs = jndiEnvironmentRefsGroup.getResourceReferences();
        return refs;
    }

    public ServiceReferenceMetaData getServiceReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getServiceReferences());
    }

    public ServiceReferencesMetaData getServiceReferences() {
        ServiceReferencesMetaData refs = null;
        if (jndiEnvironmentRefsGroup != null)
            refs = jndiEnvironmentRefsGroup.getServiceReferences();
        return refs;
    }

    /**
     * Provies a common accessor that returns an empty security role refs.
     * Subclasses that support security-role-refs must override.
     *
     * @return An empty security role refs.
     */
    public SecurityRoleRefsMetaData getSecurityRoleRefs() {
        return new SecurityRoleRefsMetaData();
    }

    /**
     * Get the annotations.
     *
     * @return the annotations.
     */
    public AnnotationsMetaData getAnnotations() {
        return annotations;
    }

    /**
     * Set the annotations.
     *
     * @param annotations the annotations.
     * @throws IllegalArgumentException for a null annotations
     */
    public void setAnnotations(AnnotationsMetaData annotations) {
        if (annotations == null)
            throw new IllegalArgumentException("Null annotations");
        this.annotations = annotations;
    }

    /**
     * Get the aopDomainName.
     *
     * @return the aopDomainName.
     */
    public String getAopDomainName() {
        return aopDomainName;
    }

    /**
     * Set the aopDomainName.
     *
     * @param aopDomainName the aopDomainName.
     * @throws IllegalArgumentException for a null aopDomainName
     */
    public void setAopDomainName(String aopDomainName) {
        if (aopDomainName == null)
            throw new IllegalArgumentException("Null aopDomainName");
        this.aopDomainName = aopDomainName;
    }

    /**
     * Get the jndiRefs.
     *
     * @return the jndiRefs.
     */
    public JndiRefsMetaData getJndiRefs() {
        return jndiRefs;
    }

    /**
     * Set the jndiRefs.
     *
     * @param jndiRefs the jndiRefs.
     * @throws IllegalArgumentException for a null jndiRefs
     */
    public void setJndiRefs(JndiRefsMetaData jndiRefs) {
        if (jndiRefs == null)
            throw new IllegalArgumentException("Null jndiRefs");
        this.jndiRefs = jndiRefs;
    }

    /**
     * Get a security role
     *
     * @param roleName the role name
     * @return the security role or null if not found
     */
    public SecurityRoleMetaData getSecurityRole(String roleName) {
        JBossAssemblyDescriptorMetaData assemblyDescriptor = getJBossMetaDataWithCheck().getAssemblyDescriptor();
        if (assemblyDescriptor == null)
            return null;
        else
            return assemblyDescriptor.getSecurityRole(roleName);
    }

    /**
     * Get a security role's principals
     *
     * @param roleName the role name
     * @return the principals or null if not found
     */
    public Set<String> getSecurityRolePrincipals(String roleName) {
        JBossAssemblyDescriptorMetaData assemblyDescriptor = getJBossMetaDataWithCheck().getAssemblyDescriptor();
        if (assemblyDescriptor == null)
            return null;
        else
            return assemblyDescriptor.getSecurityRolePrincipals(roleName);
    }

    /**
     * Get the Principal versus roles map stored in the security roles
     *
     * @return
     */
    public Map<String, Set<String>> getSecurityRolesPrincipalVersusRolesMap() {
        JBossAssemblyDescriptorMetaData assemblyDescriptor = getJBossMetaDataWithCheck().getAssemblyDescriptor();
        if (assemblyDescriptor == null)
            return null;

        SecurityRolesMetaData securityRolesMetaData = assemblyDescriptor.getSecurityRoles();
        return securityRolesMetaData != null ? securityRolesMetaData.getPrincipalVersusRolesMap() : null;
    }

    /**
     * Get the methods permissions
     *
     * @return the method permissions or null for no result
     */
    public MethodPermissionsMetaData getMethodPermissions() {
        JBossAssemblyDescriptorMetaData assemblyDescriptor = getAssemblyDescriptor();
        if (assemblyDescriptor == null)
            return null;
        return assemblyDescriptor.getMethodPermissionsByEjbName(getEjbName());
    }

    /**
     * A somewhat tedious method that builds a Set<Principal> of the roles
     * that have been assigned permission to execute the indicated method. The
     * work performed is tedious because of the wildcard style of declaring
     * method permission allowed in the ejb-jar.xml descriptor. This method is
     * called by the Container.getMethodPermissions() when it fails to find the
     * prebuilt set of method roles in its cache.
     *
     * @param methodName    the method name
     * @param params        the parameters
     * @param interfaceType the interface type
     * @return The Set<String> for the application domain roles that caller principal's are to be validated against.
     */
    public Set<String> getMethodPermissions(String methodName, Class<?>[] params, MethodInterfaceType interfaceType) {
        Set<String> result = null;

        JBossMetaData jbossMetaData = getJBossMetaDataWithCheck();

        // First check the excluded method list as this takes priority over
        // all other assignments
        ExcludeListMetaData excluded = getExcludeList();
        if (excluded != null && excluded.matches(methodName, params, interfaceType)) {
            // No one is allowed to execute this method so add a role that
            // fails to equate to any Principal or Principal name and return.
            // We don't return null to differentiate between an explicit
            // assignment of no access and no assignment information.
            if (result == null)
                result = new HashSet<String>();
            result.add(SecurityRoleNames.NOBODY_PRINCIPAL);
            return result;
        }

        // Check the permissioned methods list
        MethodPermissionsMetaData permissions = getMethodPermissions();
        if (permissions != null) {
            for (MethodPermissionMetaData permission : permissions) {
                if (permission.isNotChecked(methodName, params, interfaceType)) {
                    if (result == null)
                        result = new HashSet<String>();
                    result.clear();
                    result.add(SecurityRoleNames.ANYBODY_PRINCIPAL);
                    break;
                } else if (permission.matches(methodName, params, interfaceType)) {
                    Set<String> roles = permission.getRoles();
                    if (roles != null) {
                        if (result == null)
                            result = new HashSet<String>();
                        for (String roleName : roles) {
                            // Get any extra principal names assigned to the role
                            Set<String> principals = getSecurityRolePrincipals(roleName);
                            if (principals != null) {
                                for (String principal : principals) {
                                    result.add(principal);
                                }
                            }
                            // Also add the role name itself
                            result.add(roleName);
                        }
                    }
                }
            }
        }

        if (jbossMetaData.isExcludeMissingMethods() == false && result == null) {
            result = new HashSet<String>();
            result.add(SecurityRoleNames.ANYBODY_PRINCIPAL);
        }

        if (result == null)
            result = Collections.emptySet();
        return result;
    }

    /**
     * Check to see if there was a method-permission or exclude-list statement
     * for the given method.
     *
     * @param methodName    - the method name
     * @param params        - the method parameter signature
     * @param interfaceType - the method interface type
     * @return true if a matching method permission exists, false if no match
     */
    public boolean hasMethodPermissions(String methodName, Class<?>[] params, MethodInterfaceType interfaceType) {
        // First check the excluded method list as this takes priority over
        // all other assignments
        ExcludeListMetaData excluded = getExcludeList();
        if (excluded != null && excluded.matches(methodName, params, interfaceType))
            return true;

        // Check the permissioned methods list
        MethodPermissionsMetaData permissions = getMethodPermissions();
        if (permissions != null) {
            for (MethodPermissionMetaData permission : permissions) {
                if (permission.matches(methodName, params, interfaceType))
                    return true;
            }
        }

        // No match
        return false;
    }

    /**
     * Get the iorSecurityConfig.
     *
     * @return the iorSecurityConfig.
     */
    public IORSecurityConfigMetaData getIorSecurityConfig() {
        return iorSecurityConfig;
    }

    /**
     * Set the iorSecurityConfig.
     *
     * @param iorSecurityConfig the iorSecurityConfig.
     * @throws IllegalArgumentException for a null iorSecurityConfig
     */
    public void setIorSecurityConfig(IORSecurityConfigMetaData iorSecurityConfig) {
        if (iorSecurityConfig == null)
            throw new IllegalArgumentException("Null iorSecurityConfig");
        this.iorSecurityConfig = iorSecurityConfig;
    }

    /**
     * Get the ignoreDependency.
     *
     * @return the ignoreDependency.
     */
    public IgnoreDependencyMetaData getIgnoreDependency() {
        return ignoreDependency;
    }

    /**
     * Set the ignoreDependency.
     *
     * @param ignoreDependency the ignoreDependency.
     * @throws IllegalArgumentException for a null ignoreDependency
     */
    public void setIgnoreDependency(IgnoreDependencyMetaData ignoreDependency) {
        if (ignoreDependency == null)
            throw new IllegalArgumentException("Null ignoreDependency");
        this.ignoreDependency = ignoreDependency;
    }

    /**
     * Get the methodAttributes.
     *
     * @return the methodAttributes.
     */
    public MethodAttributesMetaData getMethodAttributes() {
        return methodAttributes;
    }

    /**
     * Set the methodAttributes.
     *
     * @param methodAttributes the methodAttributes.
     * @throws IllegalArgumentException for a null methodAttributes
     */
    public void setMethodAttributes(MethodAttributesMetaData methodAttributes) {
        if (methodAttributes == null)
            throw new IllegalArgumentException("Null methodAttributes");
        this.methodAttributes = methodAttributes;
    }

    /**
     * Is this method a read-only method
     *
     * @param methodName the method name
     * @return true for read only
     */
    public boolean isMethodReadOnly(String methodName) {
        if (methodAttributes == null)
            return false;
        return methodAttributes.isMethodReadOnly(methodName);
    }

    /**
     * Is this method a read-only method
     *
     * @param method the method
     * @return true for read only
     */
    public boolean isMethodReadOnly(Method method) {
        if (method == null)
            return false;
        return isMethodReadOnly(method.getName());
    }

    /**
     * Get the transaction timeout for the method
     *
     * @param methodName the method name
     * @return the transaction timeout
     */
    public int getMethodTransactionTimeout(String methodName) {
        if (methodAttributes == null)
            return 0;
        return methodAttributes.getMethodTransactionTimeout(methodName);
    }

    /**
     * Get the transaction timeout for the method
     *
     * @param method the method
     * @return the transaction timeout
     */
    public int getMethodTransactionTimeout(Method method) {
        if (method == null)
            return 0;
        return getMethodTransactionTimeout(method.getName());
    }

    /**
     * Get the securityIdentity.
     *
     * @return the securityIdentity.
     */
    public SecurityIdentityMetaData getSecurityIdentity() {
        return securityIdentity;
    }

    /**
     * Set the securityIdentity.
     *
     * @param securityIdentity the securityIdentity.
     * @throws IllegalArgumentException for a null securityIdentity
     */
    public void setSecurityIdentity(SecurityIdentityMetaData securityIdentity) {
        if (securityIdentity == null)
            throw new IllegalArgumentException("Null securityIdentity");
        this.securityIdentity = securityIdentity;
    }

    public void merge(JBossEnterpriseBeanMetaData override, EnterpriseBeanMetaData original) {
        this.merge(override, original, "jboss.xml", "ejb-jar.xml", true);
    }

    public void merge(JBossEnterpriseBeanMetaData override, EnterpriseBeanMetaData original,
                      String overrideFile, String overridenFile, boolean mustOverride) {
        NamedMetaDataWithDescriptionGroupMerger.merge(this, override, (NamedMetaData) original);
        if (override != null && override.getEjbClass() != null)
            setEjbClass(override.getEjbClass());
        else if (original != null && original.getEjbClass() != null)
            setEjbClass(original.getEjbClass());
        if (override != null && override.getName() != null)
            setName(override.getName());
        else if (original != null && original.getName() != null)
            setName(original.getName());
        if (override != null && override.getMappedName() != null)
            setMappedName(override.getMappedName());
        else if (original != null && original.getMappedName() != null)
            setMappedName(original.getMappedName());

        // transactionType
        if (override != null && override.getTransactionType() != null)
            transactionType = override.getTransactionType();
        else if (original != null && original.getTransactionType() != null)
            transactionType = original.getTransactionType();

        if (override != null) {
            if (override.getAopDomainName() != null)
                setAopDomainName(override.getAopDomainName());
            if (override.getConfigurationName() != null)
                setConfigurationName(override.getConfigurationName());
            if (override.getAnnotations() != null)
                setAnnotations(override.getAnnotations());
            if (override.getDepends() != null)
                setDepends(override.getDepends());
            setExceptionOnRollback(override.isExceptionOnRollback());
            if (override.getIgnoreDependency() != null)
                setIgnoreDependency(override.getIgnoreDependency());
            if (override.getInvokerBindings() != null)
                setInvokerBindings(override.getInvokerBindings());
            if (override.getIorSecurityConfig() != null)
                setIorSecurityConfig(override.getIorSecurityConfig());
            if (override.getSecurityProxy() != null)
                setSecurityProxy(override.getSecurityProxy());
        }

        if (this.jndiEnvironmentRefsGroup == null)
            jndiEnvironmentRefsGroup = new JBossEnvironmentRefsGroupMetaData();
        Environment env = original != null ? original.getJndiEnvironmentRefsGroup() : null;
        JBossEnvironmentRefsGroupMetaData jenv = null;
        ResourceManagersMetaData resourceMgrs = this.getJBossMetaData().getResourceManagers();
        if (override != null) {
            ResourceManagersMetaData resourceMgrsOverride = override.getJBossMetaData().getResourceManagers();
            if (resourceMgrsOverride != null)
                resourceMgrs = resourceMgrsOverride;
            jenv = (JBossEnvironmentRefsGroupMetaData) override.getJndiEnvironmentRefsGroup();
        }
        JBossEnvironmentRefsGroupMetaDataMerger.merge(jndiEnvironmentRefsGroup, jenv, env, resourceMgrs, overridenFile, overrideFile, mustOverride);

        // Fixup the invoker binding references on ejb refs
        InvokerBindingsMetaData invokerBindings = getInvokerBindings();
        if (invokerBindings != null && invokerBindings.isEmpty() == false) {
            for (InvokerBindingMetaData invokerBinding : invokerBindings) {
                if (invokerBinding.getEjbRefs() != null) {
                    for (InvokerBindingMetaData.EjbRef ejbRef : invokerBinding.getEjbRefs()) {
                        EJBReferenceMetaData targetEjbRef = jndiEnvironmentRefsGroup.getEjbReferenceByName(ejbRef.getEjbRefName());
                        if (targetEjbRef == null)
                            throw new IllegalStateException("ejb-ref " + ejbRef.getEjbRefName() + " found on invoker " + invokerBinding.getName() + " but it does not exist for ejb: " + getName());
                        targetEjbRef.addInvokerBinding(invokerBinding.getName(), ejbRef.getJndiName());
                    }
                }
            }
        }

        // Fixup the security identity
        SecurityIdentityMetaData jbossSecurityIdentity = null;
        if (override != null && override.getSecurityIdentity() != null)
            jbossSecurityIdentity = override.getSecurityIdentity();
        SecurityIdentityMetaData originalSecurityIdentity = null;
        if (original != null)
            originalSecurityIdentity = original.getSecurityIdentity();
        if (jbossSecurityIdentity != null || originalSecurityIdentity != null) {
            if (securityIdentity == null)
                securityIdentity = new SecurityIdentityMetaData();
            securityIdentity.merge(jbossSecurityIdentity, originalSecurityIdentity);
        }
    }

    public void merge(JBossEnterpriseBeanMetaData override, JBossEnterpriseBeanMetaData original) {
        NamedMetaDataWithDescriptionGroupMerger.merge(this, override, original);

        AnnotationsMetaData originalAnnotations = null;
        InvokerBindingsMetaData originalInvokers = null;
        JBossEnvironmentRefsGroupMetaData originalEnv = null;
        MethodAttributesMetaData originalMethodAttrs = null;
        IgnoreDependencyMetaData originalIgnoreDependency = null;
        PoolConfigMetaData originalPool = null;
        JndiRefsMetaData originalJndiRefs = null;
        SecurityIdentityMetaData originalSecId = null;
        if (original != null) {
            if (original.aopDomainName != null)
                aopDomainName = original.aopDomainName;
            if (original.configurationName != null)
                configurationName = original.configurationName;
            if (original.containerName != null)
                containerName = original.containerName;
            if (original.ejbClass != null)
                ejbClass = original.ejbClass;
            if (original.generatedContainerName != null)
                generatedContainerName = original.generatedContainerName;
            if (original.jndiBindingPolicy != null)
                jndiBindingPolicy = original.jndiBindingPolicy;
            if (original.localJndiName != null)
                localJndiName = original.localJndiName;
            if (original.mappedName != null)
                mappedName = original.mappedName;
            if (original.securityDomain != null)
                securityDomain = original.securityDomain;
            if (original.securityProxy != null)
                securityProxy = original.securityProxy;
            if (original.transactionType != null)
                transactionType = original.transactionType;

            if (original.depends != null) {
                if (depends == null)
                    depends = new HashSet<String>();
                depends.addAll(original.depends);
            }

            // boolean wrapper should be used to differentiate between default and not set values
            timerPersistence = original.timerPersistence;
            exceptionOnRollback = original.exceptionOnRollback;

            originalAnnotations = original.annotations;
            originalInvokers = original.invokers;
            originalEnv = original.jndiEnvironmentRefsGroup;
            originalMethodAttrs = original.methodAttributes;
            originalIgnoreDependency = original.ignoreDependency;
            originalPool = original.poolConfig;
            originalJndiRefs = original.jndiRefs;
            originalSecId = original.securityIdentity;

            // not merged currently but overriden
            if (original.iorSecurityConfig != null)
                iorSecurityConfig = original.iorSecurityConfig;
        }

        AnnotationsMetaData overrideAnnotations = null;
        InvokerBindingsMetaData overrideInvokers = null;
        JBossEnvironmentRefsGroupMetaData overrideEnv = null;
        MethodAttributesMetaData overrideMethodAttrs = null;
        IgnoreDependencyMetaData overrideIgnoreDependency = null;
        PoolConfigMetaData overridePool = null;
        JndiRefsMetaData overrideJndiRefs = null;
        SecurityIdentityMetaData overrideSecId = null;
        if (override != null) {
            if (override.aopDomainName != null)
                aopDomainName = override.aopDomainName;
            if (override.configurationName != null)
                configurationName = override.configurationName;
            if (override.containerName != null)
                containerName = override.containerName;
            if (override.ejbClass != null)
                ejbClass = override.ejbClass;
            if (override.generatedContainerName != null)
                generatedContainerName = override.generatedContainerName;
            if (override.jndiBindingPolicy != null)
                jndiBindingPolicy = override.jndiBindingPolicy;
            if (override.localJndiName != null)
                localJndiName = override.localJndiName;
            if (override.mappedName != null)
                mappedName = override.mappedName;
            if (override.securityDomain != null)
                securityDomain = override.securityDomain;
            if (override.securityProxy != null)
                securityProxy = override.securityProxy;
            if (override.transactionType != null)
                transactionType = override.transactionType;

            if (override.depends != null) {
                if (depends == null)
                    depends = new HashSet<String>();
                depends.addAll(override.depends);
            }

            timerPersistence = override.timerPersistence;
            exceptionOnRollback = override.exceptionOnRollback;

            overrideAnnotations = override.annotations;
            overrideInvokers = override.invokers;
            overrideEnv = override.jndiEnvironmentRefsGroup;
            overrideMethodAttrs = override.methodAttributes;
            overrideIgnoreDependency = override.ignoreDependency;
            overridePool = override.poolConfig;
            overrideJndiRefs = override.jndiRefs;
            overrideSecId = override.securityIdentity;

            if (override.iorSecurityConfig != null)
                iorSecurityConfig = override.iorSecurityConfig;
        }

        if (originalAnnotations != null || overrideAnnotations != null) {
            if (annotations == null)
                annotations = new AnnotationsMetaData();
            AnnotationsMetaDataMerger.merge(annotations, overrideAnnotations, originalAnnotations);
        }

        if (originalInvokers != null || overrideInvokers != null) {
            if (invokers == null)
                invokers = new InvokerBindingsMetaData();
            invokers.merge(overrideInvokers, originalInvokers);
        }

        if (originalEnv != null || overrideEnv != null) {
            if (jndiEnvironmentRefsGroup == null)
                jndiEnvironmentRefsGroup = new JBossEnvironmentRefsGroupMetaData();
            JBossEnvironmentRefsGroupMetaDataMerger.merge(jndiEnvironmentRefsGroup, overrideEnv, originalEnv, getJBossMetaData().getResourceManagers());
        }

        if (originalMethodAttrs != null || overrideMethodAttrs != null) {
            if (methodAttributes == null)
                methodAttributes = new MethodAttributesMetaData();
            methodAttributes.merge(overrideMethodAttrs, originalMethodAttrs);
        }

        if (originalIgnoreDependency != null || overrideIgnoreDependency != null) {
            if (ignoreDependency == null)
                ignoreDependency = new IgnoreDependencyMetaData();
            IgnoreDependencyMetaDataMerger.merge(ignoreDependency, overrideIgnoreDependency, originalIgnoreDependency);
        }

        if (originalPool != null || overridePool != null) {
            if (poolConfig == null)
                poolConfig = new PoolConfigMetaData();
            poolConfig.merge(overridePool, originalPool);
        }

        if (originalJndiRefs != null || overrideJndiRefs != null) {
            if (jndiRefs == null)
                jndiRefs = new JndiRefsMetaData();
            JndiRefsMetaDataMerger.merge(jndiRefs, overrideJndiRefs, originalJndiRefs);
        }

        if (originalSecId != null || overrideSecId != null) {
            if (securityIdentity == null)
                securityIdentity = new SecurityIdentityMetaData();
            securityIdentity.merge(overrideSecId, originalSecId);
        }

        // Fixup the invoker binding references on ejb refs
        InvokerBindingsMetaData invokerBindings = getInvokerBindings();
        if (invokerBindings != null && invokerBindings.isEmpty() == false) {
            for (InvokerBindingMetaData invokerBinding : invokerBindings) {
                if (invokerBinding.getEjbRefs() != null) {
                    for (InvokerBindingMetaData.EjbRef ejbRef : invokerBinding.getEjbRefs()) {
                        EJBReferenceMetaData targetEjbRef = jndiEnvironmentRefsGroup.getEjbReferenceByName(ejbRef.getEjbRefName());
                        if (targetEjbRef == null)
                            throw new IllegalStateException("ejb-ref " + ejbRef.getEjbRefName() + " found on invoker " + invokerBinding.getName() + " but it does not exist for ejb: " + getName());
                        targetEjbRef.addInvokerBinding(invokerBinding.getName(), ejbRef.getJndiName());
                    }
                }
            }
        }

        // Fixup the security identity
        SecurityIdentityMetaData jbossSecurityIdentity = null;
        if (override != null && override.getSecurityIdentity() != null)
            jbossSecurityIdentity = override.getSecurityIdentity();
        SecurityIdentityMetaData originalSecurityIdentity = null;
        if (original != null)
            originalSecurityIdentity = original.getSecurityIdentity();
        if (jbossSecurityIdentity != null || originalSecurityIdentity != null) {
            if (securityIdentity == null)
                securityIdentity = new SecurityIdentityMetaData();
            securityIdentity.merge(jbossSecurityIdentity, originalSecurityIdentity);
        }

        if (original instanceof JBossGenericBeanMetaData)
            merge((JBossGenericBeanMetaData) original);
        if (override instanceof JBossGenericBeanMetaData)
            merge((JBossGenericBeanMetaData) override);
    }

    protected void merge(JBossGenericBeanMetaData generic) {
    }

    public void checkValid() {
    }

    /**
     * Get the assembly descriptor
     *
     * @return the ejbJarMetaData.
     */
    protected JBossAssemblyDescriptorMetaData getAssemblyDescriptor() {
        JBossMetaData ejbJar = getEjbJarMetaData();
        if (ejbJar == null)
            return null;
        return ejbJar.getAssemblyDescriptor();
    }

    public String getJndiBindingPolicy() {
        return jndiBindingPolicy;
    }

    public void setJndiBindingPolicy(String jndiBindingPolicy) {
        this.jndiBindingPolicy = jndiBindingPolicy;
    }

    /**
     * @see org.jboss.metadata.javaee.spec.Environment#getDataSources()
     */
    @Override
    public DataSourcesMetaData getDataSources() {
        if (jndiEnvironmentRefsGroup != null)
            return jndiEnvironmentRefsGroup.getDataSources();
        return null;
    }

    /**
     * @see org.jboss.metadata.javaee.spec.Environment#getDataSourceByName(java.lang.String)
     */
    @Override
    public DataSourceMetaData getDataSourceByName(String name) {
        return AbstractMappedMetaData.getByName(name, getDataSources());
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
}
