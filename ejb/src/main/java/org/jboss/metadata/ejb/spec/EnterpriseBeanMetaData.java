/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.lang.reflect.Method;

import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagementType;

import org.jboss.metadata.javaee.spec.AnnotatedEJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.DataSourceMetaData;
import org.jboss.metadata.javaee.spec.DataSourcesMetaData;
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
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.metadata.javaee.support.IdMetaData;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public interface EnterpriseBeanMetaData extends Environment, IdMetaData {
    DescriptionGroupMetaData getDescriptionGroup();

    /**
     * A shortcut for getEjbJarMetaData().getEjbJarVersion()
     *
     * @return the version of the EJB or null if not yet attached to an EjbJar
     */
    EjbJarVersion getEjbJarVersion();

    EnterpriseBeansMetaData getEnterpriseBeansMetaData();

    /**
     * Get the ejbJarMetaData.
     *
     * @return the ejbJarMetaData.
     */
    EjbJarMetaData getEjbJarMetaData();

    String getName();

    /**
     * Get the assembly descriptor
     *
     * @return the ejbJarMetaData.
     */
    AssemblyDescriptorMetaData getAssemblyDescriptor();

    /**
     * Get the ejbName.
     *
     * @return the ejbName.
     */
    String getEjbName();

    /**
     * Whether this is a session bean
     *
     * @return true when a session bean
     */
    boolean isSession();

    /**
     * Whether this is a message driven bean
     *
     * @return true when a message driven bean
     */
    boolean isMessageDriven();

    /**
     * Whether this is an entity bean
     *
     * @return true when an entity bean
     */
    boolean isEntity();

    /**
     * Get the transactionType.
     *
     * @return the transactionType.
     */
    TransactionManagementType getTransactionType();

    /**
     * Is this container managed transactions
     *
     * @return true when CMT
     */
    boolean isCMT();

    /**
     * Is this bean managed transactions
     *
     * @return true when BMT
     */
    boolean isBMT();

    /**
     * Get the mappedName.
     *
     * @return the mappedName.
     */
    String getMappedName();

    /**
     * Get the ejbClass.
     *
     * @return the ejbClass.
     */
    String getEjbClass();

    /**
     * Get the jndiEnvironmentRefsGroup.
     *
     * @return the jndiEnvironmentRefsGroup.
     */
    Environment getJndiEnvironmentRefsGroup();

    /**
     * Get the securityIdentity.
     *
     * @return the securityIdentity.
     */
    SecurityIdentityMetaData getSecurityIdentity();

    /**
     * Get the securityRoleRefs.
     *
     * @return the securityRoleRefs.
     */
    SecurityRoleRefsMetaData getSecurityRoleRefs();

    EJBLocalReferenceMetaData getEjbLocalReferenceByName(String name);

    EJBLocalReferencesMetaData getEjbLocalReferences();

    EJBReferenceMetaData getEjbReferenceByName(String name);

    EJBReferencesMetaData getEjbReferences();

    AnnotatedEJBReferencesMetaData getAnnotatedEjbReferences();

    EnvironmentEntriesMetaData getEnvironmentEntries();

    EnvironmentEntryMetaData getEnvironmentEntryByName(String name);

    MessageDestinationReferenceMetaData getMessageDestinationReferenceByName(String name);

    MessageDestinationReferencesMetaData getMessageDestinationReferences();

    PersistenceContextReferenceMetaData getPersistenceContextReferenceByName(String name);

    PersistenceContextReferencesMetaData getPersistenceContextRefs();

    PersistenceUnitReferenceMetaData getPersistenceUnitReferenceByName(String name);

    PersistenceUnitReferencesMetaData getPersistenceUnitRefs();

    LifecycleCallbacksMetaData getPostConstructs();

    LifecycleCallbacksMetaData getPreDestroys();

    ResourceEnvironmentReferenceMetaData getResourceEnvironmentReferenceByName(String name);

    ResourceEnvironmentReferencesMetaData getResourceEnvironmentReferences();

    ResourceReferenceMetaData getResourceReferenceByName(String name);

    ResourceReferencesMetaData getResourceReferences();

    ServiceReferenceMetaData getServiceReferenceByName(String name);

    ServiceReferencesMetaData getServiceReferences();

    /**
     * Get the methods permissions
     *
     * @return the method permissions or null for no result
     */
    MethodPermissionsMetaData getMethodPermissions();

    /**
     * Get the container transactions
     *
     * @return the container transactions or null for no result
     */
    ContainerTransactionsMetaData getContainerTransactions();

    /**
     * Get the method transaction type
     *
     * @param methodName the method name
     * @param params     the parameters
     * @param iface      the interface type
     * @return the method transaction type
     */
    TransactionAttributeType getMethodTransactionType(String methodName, Class<?>[] params, MethodInterfaceType iface);

    /**
     * Get the transaction type
     *
     * @param m     the method
     * @param iface the interface type
     * @return the transaction type
     */
    TransactionAttributeType getMethodTransactionType(Method m, MethodInterfaceType iface);

    /**
     * Get the exclude list
     *
     * @return the exclude list or null for no result
     */
    ExcludeListMetaData getExcludeList();

    /**
     * @see org.jboss.metadata.javaee.spec.Environment#getDataSources()
     */
    DataSourcesMetaData getDataSources();

    /**
     * @see org.jboss.metadata.javaee.spec.Environment#getDataSourceByName(java.lang.String)
     */
    DataSourceMetaData getDataSourceByName(String name);
}
