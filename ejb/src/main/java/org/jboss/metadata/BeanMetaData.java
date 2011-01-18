/*
* JBoss, Home of Professional Open Source
* Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.TransactionAttributeType;

import org.jboss.invocation.InvocationType;
import org.jboss.metadata.ejb.jboss.ContainerConfigurationMetaData;
import org.jboss.metadata.ejb.jboss.IORSecurityConfigMetaData;
import org.jboss.metadata.ejb.jboss.InvokerBindingMetaData;
import org.jboss.metadata.ejb.jboss.InvokerBindingsMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.spec.ContainerTransactionsMetaData;
import org.jboss.metadata.ejb.spec.ExcludeListMetaData;
import org.jboss.metadata.ejb.spec.MethodInterfaceType;
import org.jboss.metadata.ejb.spec.MethodPermissionsMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferencesMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.metadata.spi.MetaData;

/**
 * A common meta data class for the entity, message-driven and session beans.
 *
 * @author <a href="mailto:sebastien.alborini@m4x.org">Sebastien Alborini</a>
 * @author <a href="mailto:peter.antman@tim.se">Peter Antman</a>
 * @author <a href="mailto:docodan@mvcsoft.com">Daniel OConnor</a>
 * @author <a href="mailto:Scott.Stark@jboss.org">Scott Stark</a>
 * @author <a href="mailto:osh@sparre.dk">Ole Husgaard</a>
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @author <a href="mailto:criege@riege.com">Christian Riege</a>
 * @author <a href="mailto:Thomas.Diesler@jboss.org">Thomas Diesler</a>
 * @author <a href="mailto:dimitris@jboss.org">Dimitris Andreadis</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 * @version $Revision: 45953 $
 */
@Deprecated
public abstract class BeanMetaData extends OldMetaData<JBossEnterpriseBeanMetaData>
{
   /** Session type */
   public static final char SESSION_TYPE = 'S';

   /** Entity type */
   public static final char ENTITY_TYPE = 'E';

   /** MDB type */
   public static final char MDB_TYPE = 'M';
   
   /** The local invoker proxy binding name */
   public static final String LOCAL_INVOKER_PROXY_BINDING = "LOCAL";
   
   /** The transaction type cache */
   private ConcurrentHashMap<Method, Byte> methodTx = new ConcurrentHashMap<Method, Byte>();
   
   /** application metadata as a field rather than a local variable to hold plugin data (e.g. for cmp)*/
   private ApplicationMetaData applicationMetaData;
   
   /**
    * Create a new BeanMetaData.
    *
    * @param delegate the delegate
    * @return the metadata
    * @throws IllegalArgumentException for a null delegate or an unknown delegate
    */
   public static BeanMetaData create(ApplicationMetaData app, JBossEnterpriseBeanMetaData delegate)
   {
      if (delegate == null)
         throw new IllegalArgumentException("Null delegate");
      if (delegate.isSession())
         return new SessionMetaData(app, delegate);
      if (delegate.isMessageDriven())
         return new MessageDrivenMetaData(app, delegate);
      if (delegate.isEntity())
         return new EntityMetaData(app, delegate);
      throw new IllegalArgumentException("Unknown delegate: " + delegate);
   }
   
   /**
    * Create a new BeanMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   protected BeanMetaData(ApplicationMetaData app, JBossEnterpriseBeanMetaData delegate)
   {
      super(delegate);
      this.applicationMetaData = app;
   }
   
   /**
    * Create a new BeanMetaData.
    *
    * @param metaData the delegate metadata
    * @throws IllegalArgumentException for a null metaData
    * @throws IllegalStateException if the metadata doesn't have an {@link JBossEnterpriseBeanMetaData}
    */
   protected BeanMetaData(MetaData metaData)
   {
      super(metaData, JBossEnterpriseBeanMetaData.class);
   }

   /**
    * Whether this is a session bean
    * 
    * @return true when a session bean
    */
   public boolean isSession()
   {
      return getDelegate().isSession();
   }

   /**
    * Whether this is a message driven bean
    * 
    * @return true when a message driven bean
    */
   public boolean isMessageDriven()
   {
      return getDelegate().isMessageDriven();
   }

   /**
    * Whether this is an entity bean
    * 
    * @return true when an entity bean
    */
   public boolean isEntity()
   {
      return getDelegate().isEntity();
   }

   /**
    * Get the ejb name
    * 
    * @return the ejb name
    */
   public String getEjbName()
   {
      return getDelegate().getEjbName();
   }

   /**
    * Get the home class name
    * 
    * @return the home class name
    */
   public abstract String getHome();

   /**
    * Get the remote class name
    * 
    * @return the remote class name
    */
   public abstract String getRemote();

   /**
    * Get the local home class name
    * 
    * @return the local home class name
    */
   public abstract String getLocalHome();

   /**
    * Get the local class name
    * 
    * @return the local class name
    */
   public abstract String getLocal();

   /**
    * Get the service endpoint
    * 
    * @return the service endpoint
    */
   public abstract String getServiceEndpoint();

   /**
    * Get the ejbClass.
    * 
    * @return the ejbClass.
    */
   public String getEjbClass()
   {
      return getDelegate().getEjbClass();
   }

   /**
    * Get the container managed transactions
    * 
    * @return the container managed transactions
    */
   public boolean isContainerManagedTx()
   {
      return getDelegate().isCMT();
   }

   /**
    * Is bean managed transaction
    * 
    * @return true for BMT
    */
   public boolean isBeanManagedTx()
   {
      return getDelegate().isBMT();
   }

   /**
    * Get the ejb references
    * 
    * @return the ejb references
    */
   public Iterator<EjbRefMetaData> getEjbReferences()
   {
      EJBReferencesMetaData ejbRefs = getDelegate().getEjbReferences();
      return new OldMetaDataIterator<EJBReferenceMetaData, EjbRefMetaData>(ejbRefs, EJBReferenceMetaData.class, EjbRefMetaData.class);
   }

   /**
    * Get the ejb local references
    * 
    * @return the local references
    */
   public Iterator<EjbLocalRefMetaData> getEjbLocalReferences()
   {
      EJBLocalReferencesMetaData ejbLocalRefs = getDelegate().getEjbLocalReferences();
      return new OldMetaDataIterator<EJBLocalReferenceMetaData, EjbLocalRefMetaData>(ejbLocalRefs, EJBLocalReferenceMetaData.class, EjbLocalRefMetaData.class);
   }

   /**
    * Get an ejb reference by name
    * 
    * @param name the name
    * @return the reference or null if not found
    * @throws IllegalArgumentException for a null name
    */
   public EjbRefMetaData getEjbRefByName(String name)
   {
      EJBReferenceMetaData result = getDelegate().getEjbReferenceByName(name);
      if (result != null)
         return new EjbRefMetaData(result);
      return null;
   }

   /**
    * Get an ejb local reference by name
    * 
    * @param name the name
    * @return the reference or null if not found
    * @throws IllegalArgumentException for a null name
    */
   public EjbLocalRefMetaData getEjbLocalRefByName(String name)
   {
      EJBLocalReferenceMetaData result = getDelegate().getEjbLocalReferenceByName(name);
      if (result != null)
         return new EjbLocalRefMetaData(result);
      return null;
   }

   /**
    * Get the environment entries
    * 
    * @return the environment entries
    */
   public Iterator<EnvEntryMetaData> getEnvironmentEntries()
   {
      EnvironmentEntriesMetaData result = getDelegate().getEnvironmentEntries();
      return new OldMetaDataIterator<EnvironmentEntryMetaData, EnvEntryMetaData>(result, EnvironmentEntryMetaData.class, EnvEntryMetaData.class);
   }

   /**
    * Get the security role references
    * 
    * @return the security role references
    */
   public abstract Iterator<SecurityRoleRefMetaData> getSecurityRoleReferences();

   /**
    * Get the resource references
    * 
    * @return the resource references
    */
   public Iterator<ResourceRefMetaData> getResourceReferences()
   {
      ResourceReferencesMetaData result = getDelegate().getResourceReferences();
      return new OldMetaDataIterator<ResourceReferenceMetaData, ResourceRefMetaData>(result, ResourceReferenceMetaData.class, ResourceRefMetaData.class);
   }

   /**
    * Get the resource environment references
    * 
    * @return the resource environment references
    */
   public Iterator<ResourceEnvRefMetaData> getResourceEnvReferences()
   {
      ResourceEnvironmentReferencesMetaData result = getDelegate().getResourceEnvironmentReferences();
      return new OldMetaDataIterator<ResourceEnvironmentReferenceMetaData, ResourceEnvRefMetaData>(result, ResourceEnvironmentReferenceMetaData.class, ResourceEnvRefMetaData.class);
   }

   /**
    * Get the message destination references
    * 
    * @return the message destination references
    */
   public Iterator<MessageDestinationRefMetaData> getMessageDestinationReferences()
   {
      MessageDestinationReferencesMetaData result = getDelegate().getMessageDestinationReferences();
      return new OldMetaDataIterator<MessageDestinationReferenceMetaData, MessageDestinationRefMetaData>(result, MessageDestinationReferenceMetaData.class, MessageDestinationRefMetaData.class);
   }

   /**
    * Get the service references
    * 
    * @return the service references
    */
   public Iterator<ServiceReferenceMetaData> getServiceReferences()
   {
      ServiceReferencesMetaData result = getDelegate().getServiceReferences();
      return (result != null ? result.iterator() : new ArrayList<ServiceReferenceMetaData>().iterator());
   }

   /**
    * Get the security identity
    *  
    * @return the security identity or null if none is specified
    */
   public SecurityIdentityMetaData getSecurityIdentityMetaData()
   {
      org.jboss.metadata.ejb.spec.SecurityIdentityMetaData securityIdentity = getDelegate().getSecurityIdentity();
      if (securityIdentity != null)
         return new SecurityIdentityMetaData(securityIdentity);
      return null;
   }

   /**
    * Get the transaction methods 
    * 
    * @return the transaction methods
    */
   public Iterator<MethodMetaData> getTransactionMethods()
   {
      ContainerTransactionsMetaData transactions = getDelegate().getContainerTransactions();
      return new TransactionMethodMetaDataIterator(transactions);
   }

   /**
    * Get the method transaction type
    * 
    * @param methodName the method name
    * @param params the parameters
    * @param iface the interface type
    * @return the method transaction type
    */
   public byte getMethodTransactionType(String methodName, Class[] params, InvocationType iface)
   {
      TransactionAttributeType type = getDelegate().getMethodTransactionType(methodName, params, invocationTypeToMethodInterfaceType(iface));
      return mapTransactionType(type);
   }

   /**
    * Get the transaction type
    * 
    * @param m the method
    * @param iface the interface type
    * @return the transaction type
    */
   public byte getTransactionMethod(Method m, InvocationType iface)
   {
      if (m == null)
         return TX_SUPPORTS;

      Byte b = methodTx.get(m);
      if (b != null) 
         return b;

      TransactionAttributeType type = getDelegate().getMethodTransactionType(m, invocationTypeToMethodInterfaceType(iface));
      byte result = mapTransactionType(type);

      // provide default if method is not found in descriptor
      if (result == TX_UNKNOWN)
         result = TX_REQUIRED;

      methodTx.put(m, result);
      return result;
   }

   /**
    * Get the permission methods
    * 
    * @return  the permission methods
    */
   public Iterator<MethodMetaData> getPermissionMethods()
   {
      MethodPermissionsMetaData methodPermissions = getDelegate().getMethodPermissions();
      return new PermissionMethodMetaDataIterator(methodPermissions);
   }

   public EjbPortComponentMetaData getPortComponent()
   {
      JBossMetaData jmd = getDelegate().getJBossMetaDataWithCheck();
      JBossSessionBeanMetaData jebmd = (JBossSessionBeanMetaData) jmd.getEnterpriseBean(this.getEjbName());
      return new EjbPortComponentMetaData(jebmd.getPortComponent());
   }

   /**
    * Get the excluded methods
    * 
    * @return the excluded methods
    */
   public Iterator<MethodMetaData> getExcludedMethods()
   {
      ExcludeListMetaData excluded = getDelegate().getExcludeList();
      return new ExcludedMethodMetaDataIterator(excluded);
   }

   /**
    * A somewhat tedious method that builds a Set<Principal> of the roles
    * that have been assigned permission to execute the indicated method. The
    * work performed is tedious because of the wildcard style of declaring
    * method permission allowed in the ejb-jar.xml descriptor. This method is
    * called by the Container.getMethodPermissions() when it fails to find the
    * prebuilt set of method roles in its cache.
    *
    * @param methodName the method name
    * @param params the parameters
    * @param iface the interface
    * @return The Set<String> for the application domain roles that
    *         caller principal's are to be validated against.
    */
   public Set<String> getMethodPermissions(String methodName, Class[] params, InvocationType iface)
   {
      return getDelegate().getMethodPermissions(methodName, params, invocationTypeToMethodInterfaceType(iface));
   }
   
   /**
    * Check to see if there was a method-permission or exclude-list statement
    * for the given method.
    * 
    * @param methodName - the method name
    * @param params - the method parameter signature
    * @param iface - the method interface type
    * @return true if a matching method permission exists, false if no match
    */
   public boolean hasMethodPermission(String methodName, Class[] params, InvocationType iface)
   {
      return getDelegate().hasMethodPermissions(methodName, params, invocationTypeToMethodInterfaceType(iface));
   }

   /**
    * Get the excludeMissingMethods.
    * 
    * @return the excludeMissingMethods.
    */
   public boolean isExcludeMissingMethods()
   {
      return getDelegate().getJBossMetaDataWithCheck().isExcludeMissingMethods();
   }

   /**
    * Get the application metadata
    * 
    * @return the application metadata
    */
   public ApplicationMetaData getApplicationMetaData()
   {
      if(applicationMetaData == null)
         throw new IllegalStateException("ApplicationMetaData has not been initialized.");
      return applicationMetaData;
   }

   /**
    * Get the invoker bindings
    * 
    * @return an iterator of invoker proxy binding names
    */
   public Iterator<String> getInvokerBindings()
   {
      InvokerBindingsMetaData invokerBindings = getDelegate().determineInvokerBindings();
      return new InvokerBindingsIterator(invokerBindings);
   }

   /**
    * Get an invoker binding
    * 
    * @param invokerName the invoker proxy binding name
    * @return the jndi name
    */
   public String getInvokerBinding(String invokerName)
   {
      InvokerBindingMetaData binding = getDelegate().determineInvokerBinding(invokerName);
      String bindingName = binding.getJndiName();
      if(bindingName == null || bindingName.length() == 0)
      {
         bindingName = isMessageDriven() ? getEjbName() : getJndiName();
      }
      return bindingName;
   }
   
   /**
    * Get the jndi name
    * 
    * @return the jndi name
    */
   public abstract String getJndiName();

   /**
    * Gets the JNDI name under with the local home interface should be bound.
    * The default is local/&lt;ejbName&gt;
    * 
    * @return the local jndi name
    */
   public String getLocalJndiName()
   {
      return getDelegate().determineLocalJndiName();
   }

   /**
    * Gets the container jndi name used in the object name
    * 
    * @return the jndi name suitable for use as the jndi name
    */
   public String getContainerObjectNameJndiName()
   {
      return getHome() != null ? getJndiName() : getLocalJndiName();
   }

   /**
    * Get the configuration name
    * 
    * @return the configuration name
    */
   public String getConfigurationName()
   {
      return getDelegate().determineConfigurationName();
   }

   /**
    * Get the container configuration
    * 
    * @return the configuration
    * @throws IllegalStateException when the configuration could not be determined
    */
   public ConfigurationMetaData getContainerConfiguration()
   {
      ContainerConfigurationMetaData containerConfigurationMetaData = getDelegate().determineContainerConfiguration();
      return new ConfigurationMetaData(containerConfigurationMetaData);
   }

   /**
    * Get the security proxy
    * 
    * @return the security proxy
    */
   public String getSecurityProxy()
   {
      return getDelegate().getSecurityProxy();
   }

   /**
    * Get the ejb timeout identity
    * 
    * @return the ejb timeout identity
    */
   public abstract SecurityIdentityMetaData getEjbTimeoutIdentity();

   /**
    * Get the default configuration name
    * 
    * @return the default configuration name
    */
   public String getDefaultConfigurationName()
   {
      return getDelegate().getDefaultConfigurationName();
   }

   /**
    * Get all the dependencies
    * 
    * @return all the dependencies
    */
   public Collection<String> getDepends()
   {
      return getDelegate().determineAllDepends();
   }

   /**
    * Is this method a read-only method described in jboss.xml?
    * 
    * @param methodName the method name
    * @return true for read only
    */
   public boolean isMethodReadOnly(String methodName)
   {
      return getDelegate().isMethodReadOnly(methodName);
   }

   /**
    * Is this method a read-only method described in jboss.xml?
    * 
    * @param method the method
    * @return true for read only
    */
   public boolean isMethodReadOnly(Method method)
   {
      return getDelegate().isMethodReadOnly(method);
   }

   /**
    * Get the transaction timeout for the method
    * 
    * @param methodName the method name
    * @return the transaction timeout
    */
   public int getTransactionTimeout(String methodName)
   {
      return getDelegate().getMethodTransactionTimeout(methodName);
   }

   /**
    * Get the transaction timeout for the method
    * 
    * @param method the method
    * @return the transaction timeout
    */
   public int getTransactionTimeout(Method method)
   {
      return getDelegate().getMethodTransactionTimeout(method);
   }

   /**
    * Get whether it is clustered.
    * 
    * @return true if clustered.
    */
   public abstract boolean isClustered();

   /**
    * Get the callByValue.
    * 
    * @return the callByValue.
    */
   public abstract boolean isCallByValue();

   /**
    * Get the cluster config
    * 
    * @return the cluster config
    */
   public abstract ClusterConfigMetaData getClusterConfigMetaData();

   /**
    * Get the IOR Security config metadata
    * 
    * @return the metadata
    */
   public IorSecurityConfigMetaData getIorSecurityConfigMetaData()
   {
      IORSecurityConfigMetaData config = getDelegate().getIorSecurityConfig();
      if (config == null)
         return null;
      return new IorSecurityConfigMetaData(config); 
   }

   /**
    * Get the exceptionRollback.
    * 
    * @return the exceptionRollback.
    */
   public boolean getExceptionRollback()
   {
      return getDelegate().isExceptionOnRollback();
   }

   /**
    * Get the timerPersistence.
    * 
    * @return the timerPersistence.
    */
   public boolean getTimerPersistence()
   {
      return getDelegate().isTimerPersistence();
   }

   /**
    * Get the port component
    * 
    * @return the port component
    * TODO webservice PortComponent
   public EjbPortComponentMetaData getPortComponent()
   {
      return portComponent;
   }
    */

   /**
    * Get the method interface
    * 
    * @param type the invocation type
    * @return the method interface
    */
   static MethodInterfaceType invocationTypeToMethodInterfaceType(InvocationType type)
   {
      if (type == null)
         return null;
      if (type == InvocationType.REMOTE)
         return MethodInterfaceType.Remote;
      if (type == InvocationType.LOCAL)
         return MethodInterfaceType.Local;
      if (type == InvocationType.HOME)
         return MethodInterfaceType.Home;
      if (type == InvocationType.LOCALHOME)
         return MethodInterfaceType.LocalHome;
      if (type == InvocationType.SERVICE_ENDPOINT)
         return MethodInterfaceType.ServiceEndpoint;
      throw new IllegalArgumentException("Unexpected invocation type: " + type);
   }

   /**
    * Get the transaction type byte
    * 
    * @param type the transaction type
    * @return the byte
    */
   static byte mapTransactionType(TransactionAttributeType type)
   {
      if (type == null)
         return TX_UNKNOWN;
      switch (type)
      {
         case NOT_SUPPORTED: 
            return TX_NOT_SUPPORTED;
         case SUPPORTS:
            return TX_SUPPORTS;
         case REQUIRES_NEW: 
            return TX_REQUIRES_NEW;
         case MANDATORY: 
            return TX_MANDATORY;
         case NEVER: 
            return TX_NEVER;
         default:
            return TX_REQUIRED;
      }
   }

   /**
    * Add a permission method
    * 
    * @param method the method
    * @throws UnsupportedOperationException always
    */
   public void addPermissionMethod(MethodMetaData method)
   {
      throw new UnsupportedOperationException("addPermissionMethod");
   }

   /**
    * Add an excluded method
    * 
    * @param method the method
    * @throws UnsupportedOperationException always
    */
   public void addExcludedMethod(MethodMetaData method)
   {
      throw new UnsupportedOperationException("addExcludedMethod");
   }

   /**
    * Add a transaction method
    * 
    * @param method the method
    * @throws UnsupportedOperationException always
    */
   public void addTransactionMethod(MethodMetaData method)
   {
      throw new UnsupportedOperationException("addTransactionMethod");
   }
}
