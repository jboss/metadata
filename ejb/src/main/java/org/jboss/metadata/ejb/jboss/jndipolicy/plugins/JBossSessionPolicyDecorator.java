/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
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
import java.util.List;
import java.util.Set;

import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagementType;

import org.jboss.logging.Logger;
import org.jboss.metadata.ejb.jboss.CacheConfigMetaData;
import org.jboss.metadata.ejb.jboss.ClusterConfigMetaData;
import org.jboss.metadata.ejb.jboss.ContainerConfigurationMetaData;
import org.jboss.metadata.ejb.jboss.IORSecurityConfigMetaData;
import org.jboss.metadata.ejb.jboss.InvokerBindingMetaData;
import org.jboss.metadata.ejb.jboss.InvokerBindingsMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossServiceBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.LocalBindingMetaData;
import org.jboss.metadata.ejb.jboss.MethodAttributesMetaData;
import org.jboss.metadata.ejb.jboss.PoolConfigMetaData;
import org.jboss.metadata.ejb.jboss.RemoteBindingMetaData;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.EjbDeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.ResolveableJndiNameJbossSessionBeanMetadata;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces.KnownInterfaceType;
import org.jboss.metadata.ejb.spec.AroundInvokesMetaData;
import org.jboss.metadata.ejb.spec.BusinessLocalsMetaData;
import org.jboss.metadata.ejb.spec.BusinessRemotesMetaData;
import org.jboss.metadata.ejb.spec.ContainerTransactionsMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.ExcludeListMetaData;
import org.jboss.metadata.ejb.spec.InitMethodsMetaData;
import org.jboss.metadata.ejb.spec.MethodInterfaceType;
import org.jboss.metadata.ejb.spec.MethodPermissionsMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.ejb.spec.RemoveMethodsMetaData;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.ejb.spec.SessionType;
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
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImpl;
import org.jboss.metadata.javaee.support.NamedMetaData;
import org.jboss.util.NotImplementedException;

/**
 * JBossSessionPolicyDecorator
 * 
 * Decorate a JBossSessionBeanMetaData with the ability to resolve JNDI Names
 * based on a specified JNDI Binding Policy, so any getter of a JNDI
 * name will never return null.
 * 
 * Note that you should on call getters on this object.
 * 
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class JBossSessionPolicyDecorator<T extends JBossSessionBeanMetaData> extends JBossServiceBeanMetaData implements ResolveableJndiNameJbossSessionBeanMetadata
{
   private static final long serialVersionUID = 1L;
   
   private static final Logger log = Logger.getLogger(JBossSessionPolicyDecorator.class);

   private T delegate;
   
   private DefaultJndiBindingPolicy jndiPolicy;

   @SuppressWarnings("unchecked")
   public JBossSessionPolicyDecorator(T delegate, DefaultJndiBindingPolicy jndiPolicy)
   {
      assert delegate != null : "delegate is null";
      assert jndiPolicy != null : this.getClass().getSimpleName() + " requires a specified "
            + DefaultJndiBindingPolicy.class.getSimpleName();
      
      // Disallow double-wrapping
      if(delegate instanceof JBossSessionPolicyDecorator)
      {
         JBossSessionPolicyDecorator<T> d =(JBossSessionPolicyDecorator<T>)delegate;
         delegate = d.getDelegate();
      }
      
      this.delegate = delegate;
      this.jndiPolicy = jndiPolicy;
   }
   
   protected KnownInterfaceType classifyInterface(String iface)
   {
      assert iface != null : "Specified Interface for classification of type was null";
      
      KnownInterfaceType ifaceType = KnownInterfaces.classifyInterface(iface);
      if(ifaceType != KnownInterfaceType.UNKNOWN)
         return ifaceType;
      
      // Need to compare iface against the metadata local-home/home & business locals/remotes
      // Figure out the interface type from the metadata
      if (delegate.getLocalHome() != null && delegate.getLocalHome().equals(iface))
         return KnownInterfaceType.LOCAL_HOME;
      else if (delegate.getHome() != null && delegate.getHome().equals(iface))
         return KnownInterfaceType.REMOTE_HOME;
      else
      {
         // Check business locals
         BusinessLocalsMetaData locals = delegate.getBusinessLocals();
         if (locals != null)
         {
            for (String local : locals)
            {
               if (local.equals(iface))
               {
                  return KnownInterfaceType.BUSINESS_LOCAL;
               }
            }
         }
         if (ifaceType == KnownInterfaceType.UNKNOWN)
         {
            // Check business remotes
            BusinessRemotesMetaData remotes = delegate.getBusinessRemotes();
            if (remotes != null)
            {
               for (String remote : remotes)
               {
                  if (remote.equals(iface))
                  {
                     return KnownInterfaceType.BUSINESS_REMOTE;
                  }
               }
            }
         }
      }

      // Assume business remote
      return KnownInterfaceType.BUSINESS_REMOTE;
   }
   
   private EjbDeploymentSummary getEjbDeploymentSummary()
   {
      DeploymentSummary dsummary = getJBossMetaData().getDeploymentSummary();
      if(dsummary == null)
         dsummary = new DeploymentSummary();
      return new EjbDeploymentSummary(this.delegate, dsummary);
   }
   
   /**
    * Resolve the JNDI binding policy that's to be used.
    * 
    * @return the JNDI binding policy to be used
    */
   public DefaultJndiBindingPolicy getJndiPolicy()
   {
      return this.jndiPolicy;
   }
   
   public void setJndiPolicy(DefaultJndiBindingPolicy policy)
   {
      this.jndiPolicy = policy;
   }
   
   public T getDelegate()
   {
      return delegate;
   }

   /**
    * Returns the resolved JNDI Name for the specified
    * interface of this metadata, using the current JNDI 
    * Binding Policy
    * 
    * @param iface
    * @return
    */
   public String determineResolvedJndiName(String iface)
   {
      // Initialize
      String resolvedJndiName = null;

      // Classify the interface
      KnownInterfaceType ifaceType = classifyInterface(iface);

      // Take appropriate handling depending upon the interface
      if (ifaceType.equals(KnownInterfaceType.REMOTE_HOME))
      {
         resolvedJndiName = this.determineResolvedRemoteHomeJndiName();
      }
      if (ifaceType.equals(KnownInterfaceType.LOCAL_HOME))
      {
         resolvedJndiName = this.determineResolvedLocalHomeJndiName();
      }
      if (ifaceType.equals(KnownInterfaceType.BUSINESS_REMOTE) || ifaceType.equals(KnownInterfaceType.BUSINESS_LOCAL))
      {
         // Obtain the JNDI Policy
         DefaultJndiBindingPolicy policy = this.getJndiPolicy();
         // Revert to defaults; have the policy generate the actual name
         resolvedJndiName = policy.getJndiName(getEjbDeploymentSummary(), iface, ifaceType);
         log.debug("Resolved JNDI Name for Interface " + iface + " of type " + ifaceType + " is " + resolvedJndiName);
      }

      // Ensure found
      assert resolvedJndiName != null : "The target JNDI Name has not been properly resolved";

      // Return
      return resolvedJndiName;
   }
   
   /**
    * @deprecated For backwards-compat only; remove
    * @see org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData#determineResolvedJndiName(java.lang.String, org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy)
    */
   @Override
   @Deprecated
   public String determineResolvedJndiName(String iface, DefaultJndiBindingPolicy defaultPolicy)
   {
      // Warn
      log
            .warn("Ignoring specified " + DefaultJndiBindingPolicy.class.getName()
                  + " in 'determineResolvedJndiName(String,DefaultJndiBindingPolicy)'"
                  + " JBMETA-68, this call is deprecated");
      
      // Redirect to proper handling
      return this.determineResolvedJndiName(iface);
   }

   @Override
   public String determineJndiName()
   {
      return this.determineResolvedRemoteBusinessDefaultJndiName();
   }

   @Override
   public String determineLocalJndiName()
   {
      return this.determineResolvedLocalBusinessDefaultJndiName();
   }

   /**
    * Returns the resolved JNDI target to which the
    * EJB2.x Remote Home interface is to be bound
    * 
    * @return
    */
   public String determineResolvedRemoteHomeJndiName(){
      String s = super.getHomeJndiName();
      if(s != null)
         return s;
      
      return getJndiPolicy().getJndiName(getEjbDeploymentSummary(), KnownInterfaces.HOME, KnownInterfaceType.REMOTE_HOME);
   }

   /**
    * Returns the resolved JNDI target to which the
    * EJB2.x Local Home interface is to be bound
    * 
    * @return
    */
   public String determineResolvedLocalHomeJndiName(){  
      // Check first for explicitly-defined Local Home JNDI Name
      String localHomeJndiName = this.delegate.getLocalHomeJndiName();
      if (localHomeJndiName != null)
         return localHomeJndiName;

      // Default to JNDI Policy
      return getJndiPolicy().getJndiName(getEjbDeploymentSummary(), KnownInterfaces.LOCAL_HOME,
            KnownInterfaceType.LOCAL_HOME);
   }

   /**
    * Returns the resolved JNDI target to which the
    * default EJB3.x Remote Business interfaces are to be bound
    * 
    * @return
    */
   public String determineResolvedRemoteBusinessDefaultJndiName(){
      
      // Fall back on the mapped name
      return this.getMappedName();
   }

   /**
    * Returns the resolved JNDI target to which the
    * default EJB3.x Local Business interfaces are to be bound
    * 
    * @return
    */
   public String determineResolvedLocalBusinessDefaultJndiName(){
      String s = delegate.getLocalJndiName();
      if (s != null && s.length() > 0)
         return s;
      
      return getJndiPolicy().getJndiName(getEjbDeploymentSummary(), KnownInterfaces.LOCAL, KnownInterfaceType.BUSINESS_LOCAL);
   }
   
   // --------------------------------------------------------------------------------||
   // Overridden Implementations -----------------------------------------------------||
   // --------------------------------------------------------------------------------||
   
   @Override
   public String getMappedName()
   {
      // Obtain remote bindings
      List<RemoteBindingMetaData> bindings = this.delegate.getRemoteBindings();
      
      // If defined, use the first remote binding as a JNDI default
      if (bindings != null && bindings.size() > 0)
      {
         String jndiName = bindings.get(0).getJndiName();
         if (jndiName != null && jndiName.length() > 0)
         {
            return jndiName;
         }
      }
      
      // Try the mapped-name
      String s = delegate.getMappedName();
      if (s != null && s.length() > 0)
         return s;
      
      // Try explicit jndi-name
      s = delegate.getJndiName();
      if (s != null && s.length() > 0)
         return s;
      
      // Delegate out to the policy; not explicitly-defined here
      return getJndiPolicy().getJndiName(getEjbDeploymentSummary(), KnownInterfaces.REMOTE, KnownInterfaceType.BUSINESS_REMOTE);
   }
   
   @Override
   public String getHomeJndiName()
   {
      return this.determineResolvedRemoteHomeJndiName();
   }
   
   @Override
   public String getJndiName()
   {
      return this.determineResolvedRemoteBusinessDefaultJndiName();
   }
   
   @Override
   public String getLocalHomeJndiName()
   {
      return this.determineResolvedLocalHomeJndiName();
   }
   
   @Override
   public String getLocalJndiName()
   {
      return this.determineResolvedLocalBusinessDefaultJndiName();
   }
   
   
   /* Only delegation beyond this point */
   
   @Override
   public void checkValid()
   {
      delegate.checkValid();
   }

   @Override
   public IdMetaDataImpl clone()
   {
      throw new NotImplementedException();
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
   public AroundInvokesMetaData getAroundInvokes()
   {
      return delegate.getAroundInvokes();
   }

   @Override
   public BusinessLocalsMetaData getBusinessLocals()
   {
      return delegate.getBusinessLocals();
   }

   @Override
   public BusinessRemotesMetaData getBusinessRemotes()
   {
      return delegate.getBusinessRemotes();
   }

   @Override
   public CacheConfigMetaData getCacheConfig()
   {
      return delegate.getCacheConfig();
   }

   @Override
   public ClusterConfigMetaData getClusterConfig()
   {
      return delegate.getClusterConfig();
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
   public SecurityIdentityMetaData getEjbTimeoutIdentity()
   {
      return delegate.getEjbTimeoutIdentity();
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
   public InitMethodsMetaData getInitMethods()
   {
      return delegate.getInitMethods();
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
   public PortComponent getPortComponent()
   {
      return delegate.getPortComponent();
   }

   @Override
   public LifecycleCallbacksMetaData getPostActivates()
   {
      return delegate.getPostActivates();
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
   public LifecycleCallbacksMetaData getPrePassivates()
   {
      return delegate.getPrePassivates();
   }

   @Override
   public String getRemote()
   {
      return delegate.getRemote();
   }

   @Override
   public List<RemoteBindingMetaData> getRemoteBindings()
   {
      return delegate.getRemoteBindings();
   }

   @Override
   public RemoveMethodsMetaData getRemoveMethods()
   {
      return delegate.getRemoveMethods();
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
   public String getServiceEndpoint()
   {
      return delegate.getServiceEndpoint();
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
   public SessionType getSessionType()
   {
      return delegate.getSessionType();
   }

   @Override
   public NamedMethodMetaData getTimeoutMethod()
   {
      return delegate.getTimeoutMethod();
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
   public boolean isBMT()
   {
      return delegate.isBMT();
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
   public boolean isCMT()
   {
      return delegate.isCMT();
   }

   @Override
   public Boolean isConcurrent()
   {
      return delegate.isConcurrent();
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
   public boolean isStateful()
   {
      return delegate.isStateful();
   }

   @Override
   public boolean isStateless()
   {
      return delegate.isStateless();
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
   public void merge(NamedMetaData override, NamedMetaData original)
   {
      delegate.merge(override, original);
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
   public void setAroundInvokes(AroundInvokesMetaData aroundInvokes)
   {
      delegate.setAroundInvokes(aroundInvokes);
   }

   @Override
   public void setBusinessLocals(BusinessLocalsMetaData businessLocals)
   {
      delegate.setBusinessLocals(businessLocals);
   }

   @Override
   public void setBusinessRemotes(BusinessRemotesMetaData businessRemotes)
   {
      delegate.setBusinessRemotes(businessRemotes);
   }

   @Override
   public void setCacheConfig(CacheConfigMetaData cacheConfig)
   {
      delegate.setCacheConfig(cacheConfig);
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
   public void setConcurrent(Boolean concurrent)
   {
      delegate.setConcurrent(concurrent);
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
   public void setEjbTimeoutIdentity(SecurityIdentityMetaData ejbTimeoutIdentity)
   {
      delegate.setEjbTimeoutIdentity(ejbTimeoutIdentity);
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
   public void setHomeJndiName(String homeJndiName)
   {
      delegate.setHomeJndiName(homeJndiName);
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
   public void setInitMethods(InitMethodsMetaData initMethods)
   {
      delegate.setInitMethods(initMethods);
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
   public void setLocalHomeJndiName(String localHomeJndiName)
   {
      delegate.setLocalHomeJndiName(localHomeJndiName);
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
   public void setPoolConfig(PoolConfigMetaData poolConfig)
   {
      delegate.setPoolConfig(poolConfig);
   }

   @Override
   public void setPortComponent(PortComponent portComponent)
   {
      delegate.setPortComponent(portComponent);
   }

   @Override
   public void setPostActivates(LifecycleCallbacksMetaData postActivates)
   {
      delegate.setPostActivates(postActivates);
   }

   @Override
   public void setPrePassivates(LifecycleCallbacksMetaData prePassivates)
   {
      delegate.setPrePassivates(prePassivates);
   }

   @Override
   public void setRemote(String remote)
   {
      delegate.setRemote(remote);
   }

   @Override
   public void setRemoteBindings(List<RemoteBindingMetaData> remoteBindings)
   {
      delegate.setRemoteBindings(remoteBindings);
   }

   @Override
   public void setRemoveMethods(RemoveMethodsMetaData removeMethods)
   {
      delegate.setRemoveMethods(removeMethods);
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
   public void setServiceEndpoint(String serviceEndpoint)
   {
      delegate.setServiceEndpoint(serviceEndpoint);
   }

   @Override
   public void setSessionType(SessionType sessionType)
   {
      delegate.setSessionType(sessionType);
   }

   @Override
   public void setTimeoutMethod(NamedMethodMetaData timeoutMethod)
   {
      delegate.setTimeoutMethod(timeoutMethod);
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
   
   /*
    * Unsupported Operations Beyond This Point
    * 
    * JBMETA-165
    * 
    * For these instead use JBossServicePolicyDecorator
    */
   
   private static final String ERROR_MESSAGE_SERVICE_NOT_SUPPORTED = "Instead use " + JBossServicePolicyDecorator.class.getSimpleName();

   @Override
   public String getManagement()
   {
      throw new UnsupportedOperationException(ERROR_MESSAGE_SERVICE_NOT_SUPPORTED);
   }

   @Override
   public String getObjectName()
   {
      throw new UnsupportedOperationException(ERROR_MESSAGE_SERVICE_NOT_SUPPORTED);
   }

   @Override
   public String getXmbean()
   {
      throw new UnsupportedOperationException(ERROR_MESSAGE_SERVICE_NOT_SUPPORTED);
   }

   @Override
   public void merge(JBossEnterpriseBeanMetaData overrideEjb, JBossEnterpriseBeanMetaData originalEjb)
   {
      throw new UnsupportedOperationException(ERROR_MESSAGE_SERVICE_NOT_SUPPORTED);
   }

   @Override
   public void setManagement(String management)
   {
      throw new UnsupportedOperationException(ERROR_MESSAGE_SERVICE_NOT_SUPPORTED);
   }

   @Override
   public void setObjectName(String objectName)
   {
      throw new UnsupportedOperationException(ERROR_MESSAGE_SERVICE_NOT_SUPPORTED);
   }

   @Override
   public void setXmbean(String xmBean)
   {
      throw new UnsupportedOperationException(ERROR_MESSAGE_SERVICE_NOT_SUPPORTED);
   }

   @Override
   public List<LocalBindingMetaData> getLocalBindings()
   {
      return delegate.getLocalBindings();
   }

   @Override
   public void setLocalBindings(List<LocalBindingMetaData> localBindings)
   {
      delegate.setLocalBindings(localBindings);
   }
}
