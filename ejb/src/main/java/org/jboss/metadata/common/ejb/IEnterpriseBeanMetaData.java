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
package org.jboss.metadata.common.ejb;

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

import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagementType;
import java.lang.reflect.Method;

/**
 * Common interface for spec/jboss enterprise bean metadata
 * @author Scott.Stark@jboss.org
 * @version $Revision: 67466 $
 */
public interface IEnterpriseBeanMetaData<A extends IAssemblyDescriptorMetaData, 
   C extends IEnterpriseBeansMetaData<A, C, E, J>, 
   E extends IEnterpriseBeanMetaData<A, C, E, J>, 
   J extends IEjbJarMetaData<A, C, E, J>> 
   extends MappableMetaData
{
   public String getId();
   public DescriptionGroupMetaData getDescriptionGroup();
   public void setDescriptionGroup(DescriptionGroupMetaData descriptionGroup);
   public J getEjbJarMetaData();
   public String getEjbName();
   public void setEjbName(String ejbName);
   public boolean isSession();
   public boolean isMessageDriven();
   public boolean isEntity();
   public TransactionManagementType getTransactionType();
   public boolean isCMT();
   public boolean isBMT();
   public String getMappedName();
   public void setMappedName(String mappedName);
   public String getEjbClass();
   public void setEjbClass(String ejbClass);
   public Environment getJndiEnvironmentRefsGroup();
   public void setJndiEnvironmentRefsGroup(Environment jndiEnvironmentRefsGroup);
   public SecurityIdentityMetaData getSecurityIdentity();
   public EJBLocalReferenceMetaData getEjbLocalReferenceByName(String name);
   public EJBLocalReferencesMetaData getEjbLocalReferences();
   public EJBReferenceMetaData getEjbReferenceByName(String name);
   public EJBReferencesMetaData getEjbReferences();
   public EnvironmentEntriesMetaData getEnvironmentEntries();
   public EnvironmentEntryMetaData getEnvironmentEntryByName(String name);
   public MessageDestinationReferenceMetaData getMessageDestinationReferenceByName(String name);
   public MessageDestinationReferencesMetaData getMessageDestinationReferences();
   public PersistenceContextReferenceMetaData getPersistenceContextReferenceByName(String name);
   public PersistenceContextReferencesMetaData getPersistenceContextRefs();
   public PersistenceUnitReferenceMetaData getPersistenceUnitReferenceByName(String name);
   public PersistenceUnitReferencesMetaData getPersistenceUnitRefs();
   public LifecycleCallbacksMetaData getPostConstructs();
   public LifecycleCallbacksMetaData getPreDestroys();
   public ResourceEnvironmentReferenceMetaData getResourceEnvironmentReferenceByName(String name);
   public ResourceEnvironmentReferencesMetaData getResourceEnvironmentReferences();;
   public ResourceReferenceMetaData getResourceReferenceByName(String name);
   public ResourceReferencesMetaData getResourceReferences();;
   public ServiceReferenceMetaData getServiceReferenceByName(String name);
   public ServiceReferencesMetaData getServiceReferences();;
   public MethodPermissionsMetaData getMethodPermissions();;
   public ContainerTransactionsMetaData getContainerTransactions();;
   public TransactionAttributeType getMethodTransactionType(String methodName, Class<?>[] params, MethodInterfaceType iface);
   public TransactionAttributeType getMethodTransactionType(Method m, MethodInterfaceType iface);
   public ExcludeListMetaData getExcludeList();
   public void setEnterpriseBeansMetaData(C data);

}
