/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.ejb.jboss;

import java.lang.reflect.Method;
import java.util.Set;

import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagementType;

import org.jboss.metadata.ejb.spec.ContainerTransactionsMetaData;
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
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;

/**
 * Create a JBossEntityBeanMetaData from a JBossGenericBeanMetaData for
 * use in merging a JBossGenericBeanMetaData into a JBossEntityBeanMetaData,
 * 
 * @author alex@jboss.org
 * @version $Revision: 68866 $
 */
class JBossEntityGenericWrapper extends JBossEntityBeanMetaData
{
   private static final long serialVersionUID = 1;
   private JBossGenericBeanMetaData delegate;
   JBossEntityGenericWrapper(JBossGenericBeanMetaData delegate)
   {
      this.delegate = delegate;
   }
   @Override
   public Set<String> determineAllDepends()
   {
      return delegate.determineAllDepends();
   }
   @Override
   public String determineConfigurationName()
   {
      return delegate.determineConfigurationName();
   }
   @Override
   public ContainerConfigurationMetaData determineContainerConfiguration()
   {
      return delegate.determineContainerConfiguration();
   }
   @Override
   public InvokerBindingMetaData determineInvokerBinding(String invokerName)
   {
      return delegate.determineInvokerBinding(invokerName);
   }
   @Override
   public InvokerBindingsMetaData determineInvokerBindings()
   {
      return delegate.determineInvokerBindings();
   }
   @Override
   public boolean equals(Object obj)
   {
      return delegate.equals(obj);
   }
   @Override
   public AnnotatedEJBReferencesMetaData getAnnotatedEjbReferences()
   {
      return delegate.getAnnotatedEjbReferences();
   }
   @Override
   public AnnotationsMetaData getAnnotations()
   {
      return delegate.getAnnotations();
   }
   @Override
   public String getAopDomainName()
   {
      return delegate.getAopDomainName();
   }
   @Override
   public String getConfigurationName()
   {
      return delegate.getConfigurationName();
   }
   @Override
   public String getContainerObjectNameJndiName()
   {
      return delegate.getContainerObjectNameJndiName();
   }
   @Override
   public ContainerTransactionsMetaData getContainerTransactions()
   {
      return delegate.getContainerTransactions();
   }
   @Override
   public String getDefaultConfigurationName()
   {
      return delegate.getDefaultConfigurationName();
   }
   @Override
   public Set<String> getDepends()
   {
      return delegate.getDepends();
   }
   @Override
   public DescriptionGroupMetaData getDescriptionGroup()
   {
      return delegate.getDescriptionGroup();
   }
   @Override
   public String getEjbClass()
   {
      return delegate.getEjbClass();
   }
   @Override
   public JBossMetaData getEjbJarMetaData()
   {
      return delegate.getEjbJarMetaData();
   }
   @Override
   public EJBLocalReferenceMetaData getEjbLocalReferenceByName(String name)
   {
      return delegate.getEjbLocalReferenceByName(name);
   }
   @Override
   public EJBLocalReferencesMetaData getEjbLocalReferences()
   {
      return delegate.getEjbLocalReferences();
   }
   @Override
   public String getEjbName()
   {
      return delegate.getEjbName();
   }
   @Override
   public EJBReferenceMetaData getEjbReferenceByName(String name)
   {
      return delegate.getEjbReferenceByName(name);
   }
   @Override
   public EJBReferencesMetaData getEjbReferences()
   {
      return delegate.getEjbReferences();
   }
   @Override
   public JBossEnterpriseBeansMetaData getEnterpriseBeansMetaData()
   {
      return delegate.getEnterpriseBeansMetaData();
   }
   @Override
   public EnvironmentEntriesMetaData getEnvironmentEntries()
   {
      return delegate.getEnvironmentEntries();
   }
   @Override
   public EnvironmentEntryMetaData getEnvironmentEntryByName(String name)
   {
      return delegate.getEnvironmentEntryByName(name);
   }
   @Override
   public ExcludeListMetaData getExcludeList()
   {
      return delegate.getExcludeList();
   }
   @Override
   public String getId()
   {
      return delegate.getId();
   }
   @Override
   public IgnoreDependencyMetaData getIgnoreDependency()
   {
      return delegate.getIgnoreDependency();
   }
   @Override
   public InvokerBindingsMetaData getInvokerBindings()
   {
      return delegate.getInvokerBindings();
   }
   @Override
   public IORSecurityConfigMetaData getIorSecurityConfig()
   {
      return delegate.getIorSecurityConfig();
   }
   @Override
   public JBossMetaData getJBossMetaData()
   {
      return delegate.getJBossMetaData();
   }
   @Override
   public JBossMetaData getJBossMetaDataWithCheck()
   {
      return delegate.getJBossMetaDataWithCheck();
   }
   @Override
   public Environment getJndiEnvironmentRefsGroup()
   {
      return delegate.getJndiEnvironmentRefsGroup();
   }
   @Override
   public String getJndiName()
   {
      return delegate.getJndiName();
   }
   @Override
   public JndiRefsMetaData getJndiRefs()
   {
      return delegate.getJndiRefs();
   }
   @Override
   public String getKey()
   {
      return delegate.getKey();
   }
   @Override
   public String getLocalJndiName()
   {
      return delegate.getLocalJndiName();
   }
   @Override
   public String getMappedName()
   {
      return delegate.getMappedName();
   }
   @Override
   public MessageDestinationReferenceMetaData getMessageDestinationReferenceByName(
         String name)
   {
      return delegate.getMessageDestinationReferenceByName(name);
   }
   @Override
   public MessageDestinationReferencesMetaData getMessageDestinationReferences()
   {
      return delegate.getMessageDestinationReferences();
   }
   @Override
   public MethodAttributesMetaData getMethodAttributes()
   {
      return delegate.getMethodAttributes();
   }
   @Override
   public MethodPermissionsMetaData getMethodPermissions()
   {
      return delegate.getMethodPermissions();
   }
   @Override
   public Set<String> getMethodPermissions(String methodName,
         Class<?>[] params, MethodInterfaceType interfaceType)
   {
      return delegate.getMethodPermissions(methodName, params, interfaceType);
   }
   @Override
   public int getMethodTransactionTimeout(Method method)
   {
      return delegate.getMethodTransactionTimeout(method);
   }
   @Override
   public int getMethodTransactionTimeout(String methodName)
   {
      return delegate.getMethodTransactionTimeout(methodName);
   }
   @Override
   public TransactionAttributeType getMethodTransactionType(Method m,
         MethodInterfaceType iface)
   {
      return delegate.getMethodTransactionType(m, iface);
   }
   @Override
   public TransactionAttributeType getMethodTransactionType(String methodName,
         Class<?>[] params, MethodInterfaceType iface)
   {
      return delegate.getMethodTransactionType(methodName, params, iface);
   }
   @Override
   public String getName()
   {
      return delegate.getName();
   }
   @Override
   public PersistenceContextReferenceMetaData getPersistenceContextReferenceByName(
         String name)
   {
      return delegate.getPersistenceContextReferenceByName(name);
   }
   @Override
   public PersistenceContextReferencesMetaData getPersistenceContextRefs()
   {
      return delegate.getPersistenceContextRefs();
   }
   @Override
   public PersistenceUnitReferenceMetaData getPersistenceUnitReferenceByName(
         String name)
   {
      return delegate.getPersistenceUnitReferenceByName(name);
   }
   @Override
   public PersistenceUnitReferencesMetaData getPersistenceUnitRefs()
   {
      return delegate.getPersistenceUnitRefs();
   }
   @Override
   public PoolConfigMetaData getPoolConfig()
   {
      return delegate.getPoolConfig();
   }
   @Override
   public LifecycleCallbacksMetaData getPostConstructs()
   {
      return delegate.getPostConstructs();
   }
   @Override
   public LifecycleCallbacksMetaData getPreDestroys()
   {
      return delegate.getPreDestroys();
   }
   @Override
   public ResourceEnvironmentReferenceMetaData getResourceEnvironmentReferenceByName(
         String name)
   {
      return delegate.getResourceEnvironmentReferenceByName(name);
   }
   @Override
   public ResourceEnvironmentReferencesMetaData getResourceEnvironmentReferences()
   {
      return delegate.getResourceEnvironmentReferences();
   }
   @Override
   public ResourceReferenceMetaData getResourceReferenceByName(String name)
   {
      return delegate.getResourceReferenceByName(name);
   }
   @Override
   public ResourceReferencesMetaData getResourceReferences()
   {
      return delegate.getResourceReferences();
   }
   @Override
   public String getSecurityDomain()
   {
      return delegate.getSecurityDomain();
   }
   @Override
   public SecurityIdentityMetaData getSecurityIdentity()
   {
      return delegate.getSecurityIdentity();
   }
   @Override
   public String getSecurityProxy()
   {
      return delegate.getSecurityProxy();
   }
   @Override
   public SecurityRoleMetaData getSecurityRole(String roleName)
   {
      return delegate.getSecurityRole(roleName);
   }
   @Override
   public Set<String> getSecurityRolePrincipals(String roleName)
   {
      return delegate.getSecurityRolePrincipals(roleName);
   }
   @Override
   public SecurityRoleRefsMetaData getSecurityRoleRefs()
   {
      return delegate.getSecurityRoleRefs();
   }
   @Override
   public ServiceReferenceMetaData getServiceReferenceByName(String name)
   {
      return delegate.getServiceReferenceByName(name);
   }
   @Override
   public ServiceReferencesMetaData getServiceReferences()
   {
      return delegate.getServiceReferences();
   }
   @Override
   public TransactionManagementType getTransactionType()
   {
      return delegate.getTransactionType();
   }
   @Override
   public int hashCode()
   {
      return delegate.hashCode();
   }
   @Override
   public boolean hasMethodPermissions(String methodName, Class<?>[] params,
         MethodInterfaceType interfaceType)
   {
      return delegate.hasMethodPermissions(methodName, params, interfaceType);
   }
   @Override
   public boolean isBMT()
   {
      return delegate.isBMT();
   }
   @Override
   public boolean isCMT()
   {
      return delegate.isCMT();
   }
   @Override
   public boolean isConsumer()
   {
      return delegate.isConsumer();
   }
   @Override
   public boolean isEntity()
   {
      return delegate.isEntity();
   }
   @Override
   public boolean isExceptionOnRollback()
   {
      return delegate.isExceptionOnRollback();
   }
   @Override
   public boolean isMessageDriven()
   {
      return delegate.isMessageDriven();
   }
   @Override
   public boolean isMethodReadOnly(Method method)
   {
      return delegate.isMethodReadOnly(method);
   }
   @Override
   public boolean isMethodReadOnly(String methodName)
   {
      return delegate.isMethodReadOnly(methodName);
   }
   @Override
   public boolean isService()
   {
      return delegate.isService();
   }
   @Override
   public boolean isSession()
   {
      return delegate.isSession();
   }
   @Override
   public boolean isTimerPersistence()
   {
      return delegate.isTimerPersistence();
   }

}
