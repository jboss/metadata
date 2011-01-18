/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.ejb.jboss.jndipolicy.plugins;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagementType;

import org.jboss.metadata.ejb.jboss.CacheInvalidationConfigMetaData;
import org.jboss.metadata.ejb.jboss.ClusterConfigMetaData;
import org.jboss.metadata.ejb.jboss.ContainerConfigurationMetaData;
import org.jboss.metadata.ejb.jboss.IORSecurityConfigMetaData;
import org.jboss.metadata.ejb.jboss.InvokerBindingMetaData;
import org.jboss.metadata.ejb.jboss.InvokerBindingsMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.MethodAttributesMetaData;
import org.jboss.metadata.ejb.jboss.PoolConfigMetaData;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.EjbDeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.ResolveableJndiNameJbossEnterpriseBeanMetadata;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces.KnownInterfaceType;
import org.jboss.metadata.ejb.spec.CMPFieldsMetaData;
import org.jboss.metadata.ejb.spec.ContainerTransactionsMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.ExcludeListMetaData;
import org.jboss.metadata.ejb.spec.MethodInterfaceType;
import org.jboss.metadata.ejb.spec.MethodPermissionsMetaData;
import org.jboss.metadata.ejb.spec.PersistenceType;
import org.jboss.metadata.ejb.spec.QueriesMetaData;
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
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImpl;
import org.jboss.metadata.javaee.support.NamedMetaData;

/**
 * JbossEntityPolicyDecorator
 * 
 * Decorate a JBossEntityBeanMetaData with the ability to resolve JNDI Names
 * based on a specified JNDI Binding Policy, so any getter of a JNDI
 * name will never return null.
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class JbossEntityPolicyDecorator extends JBossEntityBeanMetaData
      implements
         ResolveableJndiNameJbossEnterpriseBeanMetadata
{

   // --------------------------------------------------------------------------------||
   // Class Members ------------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   private static final long serialVersionUID = 1L;

   // --------------------------------------------------------------------------------||
   // Instance Members ---------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   /**
    * The Wrapped (Decorated) Metadata
    */
   private JBossEntityBeanMetaData delegate;

   /**
    * The JNDI Policy to apply
    */
   private DefaultJndiBindingPolicy jndiPolicy;

   // --------------------------------------------------------------------------------||
   // Constructor --------------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   public JbossEntityPolicyDecorator(JBossEntityBeanMetaData delegate, DefaultJndiBindingPolicy jndiPolicy)
   {
      // Sanity Checks
      assert delegate != null : "delegate is null";
      assert jndiPolicy != null : this.getClass().getSimpleName() + " requires a specified "
            + DefaultJndiBindingPolicy.class.getSimpleName();

      // Set delegate and JNDI Policy
      this.delegate = delegate;
      this.jndiPolicy = jndiPolicy;
   }

   // --------------------------------------------------------------------------------||
   // Required Implementations -------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   /**
    * Returns the resolved JNDI Name for the specified
    * interface of this metadata.
    * 
    * @param iface
    * @return
    */
   public String determineResolvedJndiName(String iface)
   {
      return jndiPolicy.getJndiName(this.getEjbDeploymentSummary(), iface, this.classifyInterface(iface));
   }
   
   // --------------------------------------------------------------------------------||
   // Internal Helper Methods --------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   protected EjbDeploymentSummary getEjbDeploymentSummary()
   {
      DeploymentSummary dsummary = getJBossMetaData().getDeploymentSummary();
      if(dsummary == null)
         dsummary = new DeploymentSummary();
      return new EjbDeploymentSummary(delegate, dsummary);
   }
   
   protected KnownInterfaceType classifyInterface(String iface)
   {
      KnownInterfaceType ifaceType = KnownInterfaces.classifyInterface(iface);
      if(ifaceType != KnownInterfaceType.UNKNOWN)
         return ifaceType;
      
      // Need to compare iface against the metadata local-home/home & business locals/remotes
      // Figure out the interface type from the metadata
      if (delegate.getLocalHome() != null && delegate.getLocalHome().equals(iface))
         return KnownInterfaceType.LOCAL_HOME;
      else if (delegate.getHome() != null && delegate.getHome().equals(iface))
         return KnownInterfaceType.REMOTE_HOME;
      
      return KnownInterfaceType.UNKNOWN;
   }

   /*
    * 
    * All Delegation Below This Marker -----------------------------------------------||
    * 
    */

   @Override
   public void checkValid()
   {
      delegate.checkValid();
   }

   @Override
   public IdMetaDataImpl clone()
   {
      return delegate.clone();
   }

   @Override
   public DefaultJndiBindingPolicy createPolicy(ClassLoader loader,
         Class<? extends DefaultJndiBindingPolicy> defaultPolicyClass) throws Exception
   {
      return delegate.createPolicy(loader, defaultPolicyClass);
   }

   @Override
   public Set<String> determineAllDepends()
   {
      return delegate.determineAllDepends();
   }

   @Override
   public CacheInvalidationConfigMetaData determineCacheInvalidationConfig()
   {
      return delegate.determineCacheInvalidationConfig();
   }

   @Override
   public ClusterConfigMetaData determineClusterConfig()
   {
      return delegate.determineClusterConfig();
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
   public String determineContainerName()
   {
      return delegate.determineContainerName();
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
   public String determineJndiName()
   {
      return delegate.determineJndiName();
   }

   @Override
   public boolean equals(Object obj)
   {
      return delegate.equals(obj);
   }

   @Override
   public String getAbstractSchemaName()
   {
      return delegate.getAbstractSchemaName();
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
   public CacheInvalidationConfigMetaData getCacheInvalidationConfig()
   {
      return delegate.getCacheInvalidationConfig();
   }

   @Override
   public ClusterConfigMetaData getClusterConfig()
   {
      return delegate.getClusterConfig();
   }

   @Override
   public CMPFieldsMetaData getCmpFields()
   {
      return delegate.getCmpFields();
   }

   @Override
   public String getCmpVersion()
   {
      return delegate.getCmpVersion();
   }

   @Override
   public String getConfigurationName()
   {
      return delegate.getConfigurationName();
   }

   @Override
   public String getContainerName()
   {
      return delegate.getContainerName();
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
   public String getDefaultInvokerName()
   {
      return delegate.getDefaultInvokerName();
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
   public String getGeneratedContainerName()
   {
      return delegate.getGeneratedContainerName();
   }

   @Override
   public String getHome()
   {
      return delegate.getHome();
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
   public String getJndiBindingPolicy()
   {
      return delegate.getJndiBindingPolicy();
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
   public String getLocal()
   {
      return delegate.getLocal();
   }

   @Override
   public String getLocalHome()
   {
      return delegate.getLocalHome();
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
   public MessageDestinationReferenceMetaData getMessageDestinationReferenceByName(String name)
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
   public Set<String> getMethodPermissions(String methodName, Class<?>[] params, MethodInterfaceType interfaceType)
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
   public TransactionAttributeType getMethodTransactionType(Method m, MethodInterfaceType iface)
   {
      return delegate.getMethodTransactionType(m, iface);
   }

   @Override
   public TransactionAttributeType getMethodTransactionType(String methodName, Class<?>[] params,
         MethodInterfaceType iface)
   {
      return delegate.getMethodTransactionType(methodName, params, iface);
   }

   @Override
   public String getName()
   {
      return delegate.getName();
   }

   @Override
   public PersistenceContextReferenceMetaData getPersistenceContextReferenceByName(String name)
   {
      return delegate.getPersistenceContextReferenceByName(name);
   }

   @Override
   public PersistenceContextReferencesMetaData getPersistenceContextRefs()
   {
      return delegate.getPersistenceContextRefs();
   }

   @Override
   public PersistenceType getPersistenceType()
   {
      return delegate.getPersistenceType();
   }

   @Override
   public PersistenceUnitReferenceMetaData getPersistenceUnitReferenceByName(String name)
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
   public String getPrimKeyClass()
   {
      return delegate.getPrimKeyClass();
   }

   @Override
   public String getPrimKeyField()
   {
      return delegate.getPrimKeyField();
   }

   @Override
   public QueriesMetaData getQueries()
   {
      return delegate.getQueries();
   }

   @Override
   public String getRemote()
   {
      return delegate.getRemote();
   }

   @Override
   public ResourceEnvironmentReferenceMetaData getResourceEnvironmentReferenceByName(String name)
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
   public Map<String, Set<String>> getSecurityRolesPrincipalVersusRolesMap()
   {
      return delegate.getSecurityRolesPrincipalVersusRolesMap();
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
   public boolean hasMethodPermissions(String methodName, Class<?>[] params, MethodInterfaceType interfaceType)
   {
      return delegate.hasMethodPermissions(methodName, params, interfaceType);
   }

   @Override
   public boolean isBMP()
   {
      return delegate.isBMP();
   }

   @Override
   public boolean isBMT()
   {
      return delegate.isBMT();
   }

   @Override
   public boolean isCacheInvalidation()
   {
      return delegate.isCacheInvalidation();
   }

   @Override
   public boolean isCallByValue()
   {
      return delegate.isCallByValue();
   }

   @Override
   public boolean isClustered()
   {
      return delegate.isClustered();
   }

   @Override
   public boolean isCMP()
   {
      return delegate.isCMP();
   }

   @Override
   public boolean isCMP1x()
   {
      return delegate.isCMP1x();
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
   public boolean isGeneric()
   {
      return delegate.isGeneric();
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
   public boolean isReadOnly()
   {
      return delegate.isReadOnly();
   }

   @Override
   public boolean isReentrant()
   {
      return delegate.isReentrant();
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

   @Override
   public void merge(IdMetaData override, IdMetaData original)
   {
      delegate.merge(override, original);
   }

   @Override
   public void merge(IdMetaDataImpl override, IdMetaDataImpl original)
   {
      delegate.merge(override, original);
   }

   @Override
   public void merge(JBossEnterpriseBeanMetaData override, EnterpriseBeanMetaData original, String overridenFile,
         String overrideFile, boolean mustOverride)
   {
      delegate.merge(override, original, overridenFile, overrideFile, mustOverride);
   }

   @Override
   public void merge(JBossEnterpriseBeanMetaData override, EnterpriseBeanMetaData original)
   {
      delegate.merge(override, original);
   }

   @Override
   public void merge(JBossEnterpriseBeanMetaData overrideEjb, JBossEnterpriseBeanMetaData originalEjb)
   {
      delegate.merge(overrideEjb, originalEjb);
   }

   @Override
   public void merge(NamedMetaData override, NamedMetaData original)
   {
      delegate.merge(override, original);
   }

   @Override
   public JBossEnterpriseBeanMetaData newBean()
   {
      return delegate.newBean();
   }

   @Override
   public void setAbstractSchemaName(String abstractSchemaName)
   {
      delegate.setAbstractSchemaName(abstractSchemaName);
   }

   @Override
   public void setAnnotations(AnnotationsMetaData annotations)
   {
      delegate.setAnnotations(annotations);
   }

   @Override
   public void setAopDomainName(String aopDomainName)
   {
      delegate.setAopDomainName(aopDomainName);
   }

   @Override
   public void setCacheInvalidation(boolean cacheInvalidation)
   {
      delegate.setCacheInvalidation(cacheInvalidation);
   }

   @Override
   public void setCacheInvalidationConfig(CacheInvalidationConfigMetaData cacheInvalidationConfig)
   {
      delegate.setCacheInvalidationConfig(cacheInvalidationConfig);
   }

   @Override
   public void setCallByValue(boolean callByValue)
   {
      delegate.setCallByValue(callByValue);
   }

   @Override
   public void setClusterConfig(ClusterConfigMetaData clusterConfig)
   {
      delegate.setClusterConfig(clusterConfig);
   }

   @Override
   public void setClustered(boolean clustered)
   {
      delegate.setClustered(clustered);
   }

   @Override
   public void setCmpFields(CMPFieldsMetaData cmpFields)
   {
      delegate.setCmpFields(cmpFields);
   }

   @Override
   public void setCmpVersion(String cmpVersion)
   {
      delegate.setCmpVersion(cmpVersion);
   }

   @Override
   public void setConfigurationName(String configurationName)
   {
      delegate.setConfigurationName(configurationName);
   }

   @Override
   public void setContainerName(String containerName)
   {
      delegate.setContainerName(containerName);
   }

   @Override
   public void setDepends(Set<String> depends)
   {
      delegate.setDepends(depends);
   }

   @Override
   public void setDescriptionGroup(DescriptionGroupMetaData descriptionGroup)
   {
      delegate.setDescriptionGroup(descriptionGroup);
   }

   @Override
   public void setEjbClass(String ejbClass)
   {
      delegate.setEjbClass(ejbClass);
   }

   @Override
   public void setEjbName(String ejbName)
   {
      delegate.setEjbName(ejbName);
   }

   @Override
   public void setEnterpriseBeansMetaData(JBossEnterpriseBeansMetaData enterpriseBeansMetaData)
   {
      delegate.setEnterpriseBeansMetaData(enterpriseBeansMetaData);
   }

   @Override
   public void setExceptionOnRollback(boolean exceptionOnRollback)
   {
      delegate.setExceptionOnRollback(exceptionOnRollback);
   }

   @Override
   public void setGeneratedContainerName(String containerName)
   {
      delegate.setGeneratedContainerName(containerName);
   }

   @Override
   public void setHome(String home)
   {
      delegate.setHome(home);
   }

   @Override
   public void setId(String id)
   {
      delegate.setId(id);
   }

   @Override
   public void setIgnoreDependency(IgnoreDependencyMetaData ignoreDependency)
   {
      delegate.setIgnoreDependency(ignoreDependency);
   }

   @Override
   public void setInvokerBindings(InvokerBindingsMetaData invokers)
   {
      delegate.setInvokerBindings(invokers);
   }

   @Override
   public void setIorSecurityConfig(IORSecurityConfigMetaData iorSecurityConfig)
   {
      delegate.setIorSecurityConfig(iorSecurityConfig);
   }

   @Override
   public void setJndiBindingPolicy(String jndiBindingPolicy)
   {
      delegate.setJndiBindingPolicy(jndiBindingPolicy);
   }

   @Override
   public void setJndiEnvironmentRefsGroup(Environment env)
   {
      delegate.setJndiEnvironmentRefsGroup(env);
   }

   @Override
   public void setJndiName(String jndiName)
   {
      delegate.setJndiName(jndiName);
   }

   @Override
   public void setJndiRefs(JndiRefsMetaData jndiRefs)
   {
      delegate.setJndiRefs(jndiRefs);
   }

   @Override
   public void setLocal(String local)
   {
      delegate.setLocal(local);
   }

   @Override
   public void setLocalHome(String localHome)
   {
      delegate.setLocalHome(localHome);
   }

   @Override
   public void setLocalJndiName(String localJndiName)
   {
      delegate.setLocalJndiName(localJndiName);
   }

   @Override
   public void setMappedName(String mappedName)
   {
      delegate.setMappedName(mappedName);
   }

   @Override
   public void setMethodAttributes(MethodAttributesMetaData methodAttributes)
   {
      delegate.setMethodAttributes(methodAttributes);
   }

   @Override
   public void setName(String name)
   {
      delegate.setName(name);
   }

   @Override
   public void setPersistenceType(PersistenceType persistenceType)
   {
      delegate.setPersistenceType(persistenceType);
   }

   @Override
   public void setPoolConfig(PoolConfigMetaData poolConfig)
   {
      delegate.setPoolConfig(poolConfig);
   }

   @Override
   public void setPrimKeyClass(String primKeyClass)
   {
      delegate.setPrimKeyClass(primKeyClass);
   }

   @Override
   public void setPrimKeyField(String primKeyField)
   {
      delegate.setPrimKeyField(primKeyField);
   }

   @Override
   public void setQueries(QueriesMetaData queries)
   {
      delegate.setQueries(queries);
   }

   @Override
   public void setReadOnly(boolean readOnly)
   {
      delegate.setReadOnly(readOnly);
   }

   @Override
   public void setReentrant(boolean reentrant)
   {
      delegate.setReentrant(reentrant);
   }

   @Override
   public void setRemote(String remote)
   {
      delegate.setRemote(remote);
   }

   @Override
   public void setSecurityDomain(String securityDomain)
   {
      delegate.setSecurityDomain(securityDomain);
   }

   @Override
   public void setSecurityIdentity(SecurityIdentityMetaData securityIdentity)
   {
      delegate.setSecurityIdentity(securityIdentity);
   }

   @Override
   public void setSecurityProxy(String securityProxy)
   {
      delegate.setSecurityProxy(securityProxy);
   }

   @Override
   public void setSecurityRoleRefs(SecurityRoleRefsMetaData securityRoleRefs)
   {
      delegate.setSecurityRoleRefs(securityRoleRefs);
   }

   @Override
   public void setTimerPersistence(boolean timerPersistence)
   {
      delegate.setTimerPersistence(timerPersistence);
   }

   @Override
   public void setTransactionType(TransactionManagementType transactionType)
   {
      delegate.setTransactionType(transactionType);
   }

   @Override
   public String toString()
   {
      return delegate.toString();
   }

}
