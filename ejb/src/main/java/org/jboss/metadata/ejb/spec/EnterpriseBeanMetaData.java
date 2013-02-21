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
package org.jboss.metadata.ejb.spec;

import java.lang.reflect.Method;

import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagementType;

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
