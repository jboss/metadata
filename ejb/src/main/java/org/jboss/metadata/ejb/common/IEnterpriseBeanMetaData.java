/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.metadata.ejb.common;

import java.lang.reflect.Method;

import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagementType;

import org.jboss.metadata.ejb.spec.ContainerTransactionsMetaData;
import org.jboss.metadata.ejb.spec.ExcludeListMetaData;
import org.jboss.metadata.ejb.spec.MethodInterfaceType;
import org.jboss.metadata.ejb.spec.MethodPermissionsMetaData;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
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
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.metadata.javaee.support.MappableMetaData;

/**
 * Common interface for spec/jboss enterprise bean metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 67466 $
 */
public interface IEnterpriseBeanMetaData<A extends IAssemblyDescriptorMetaData,
        C extends IEnterpriseBeansMetaData<A, C, E, J>,
        E extends IEnterpriseBeanMetaData<A, C, E, J>,
        J extends IEjbJarMetaData<A, C, E, J>>
        extends MappableMetaData {
    String getId();

    DescriptionGroupMetaData getDescriptionGroup();

    void setDescriptionGroup(DescriptionGroupMetaData descriptionGroup);

    J getEjbJarMetaData();

    String getEjbName();

    void setEjbName(String ejbName);

    boolean isSession();

    boolean isMessageDriven();

    boolean isEntity();

    TransactionManagementType getTransactionType();

    boolean isCMT();

    boolean isBMT();

    String getMappedName();

    void setMappedName(String mappedName);

    String getEjbClass();

    void setEjbClass(String ejbClass);

    Environment getJndiEnvironmentRefsGroup();

    void setJndiEnvironmentRefsGroup(Environment jndiEnvironmentRefsGroup);

    SecurityIdentityMetaData getSecurityIdentity();

    EJBLocalReferenceMetaData getEjbLocalReferenceByName(String name);

    EJBLocalReferencesMetaData getEjbLocalReferences();

    EJBReferenceMetaData getEjbReferenceByName(String name);

    EJBReferencesMetaData getEjbReferences();

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

    ;

    ResourceReferenceMetaData getResourceReferenceByName(String name);

    ResourceReferencesMetaData getResourceReferences();

    ;

    ServiceReferenceMetaData getServiceReferenceByName(String name);

    ServiceReferencesMetaData getServiceReferences();

    ;

    MethodPermissionsMetaData getMethodPermissions();

    ;

    ContainerTransactionsMetaData getContainerTransactions();

    ;

    TransactionAttributeType getMethodTransactionType(String methodName, Class<?>[] params, MethodInterfaceType iface);

    TransactionAttributeType getMethodTransactionType(Method m, MethodInterfaceType iface);

    ExcludeListMetaData getExcludeList();

    void setEnterpriseBeansMetaData(C data);

}
