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
package org.jboss.metadata.ejb.jboss.jndi.resolver.impl;

import java.util.List;

import org.jboss.logging.Logger;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.RemoteBindingMetaData;
import org.jboss.metadata.ejb.jboss.jndi.resolver.spi.SessionBeanJNDINameResolver;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.EjbDeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces.KnownInterfaceType;
import org.jboss.metadata.ejb.spec.BusinessLocalsMetaData;
import org.jboss.metadata.ejb.spec.BusinessRemotesMetaData;

/**
 * JNDIPolicyBasedSessionBeanJNDINameResolver
 * 
 * A {@link SessionBeanJNDINameResolver} which uses a {@link DefaultJndiBindingPolicy}
 * for resolving the jndi names.
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class JNDIPolicyBasedSessionBeanJNDINameResolver extends AbstractJNDIPolicyBasedJNDINameResolver
      implements
         SessionBeanJNDINameResolver
{

   /**
    * Logger
    */
   private static Logger logger = Logger.getLogger(JNDIPolicyBasedSessionBeanJNDINameResolver.class);

   /**
    * Constructs a {@link JNDIPolicyBasedSessionBeanJNDINameResolver} 
    * 
    * @param jndibindingPolicy The jndi binding policy which will be used for resolving jndi names 
    *       out of session bean metadata, if the metadata does not have a jndi binding policy set
    *       or if {@link #isIgnoreJNDIBindingPolicyOnMetaData()} returns true
    * @throws IllegalArgumentException If the passed <code>jndibindingPolicy</code> is null
    * @see #getJNDIBindingPolicy(JBossSessionBeanMetaData)       
    */
   public JNDIPolicyBasedSessionBeanJNDINameResolver(DefaultJndiBindingPolicy jndibindingPolicy)
   {
      super(jndibindingPolicy);
   }

   /**
    * Uses the {@link DefaultJndiBindingPolicy} instance returned by {@link #getJNDIBindingPolicy(JBossSessionBeanMetaData)}
    * to resolve the local home jndi name for the <code>metadata</code>
    * 
    * @see org.jboss.metadata.ejb.jboss.jndi.resolver.spi.SessionBeanJNDINameResolver#resolveLocalHomeJNDIName(org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData)
    * @see #getJNDIBindingPolicy(JBossSessionBeanMetaData)
    */
   @Override
   public String resolveLocalHomeJNDIName(JBossSessionBeanMetaData metadata)
   {
      // Check first for explicitly-defined Local Home JNDI Name
      String localHomeJndiName = metadata.getLocalHomeJndiName();
      if (localHomeJndiName != null)
         return localHomeJndiName;

      // Get the local home jndi name from the jndi binding policy
      EjbDeploymentSummary ejbDeploymentSummary = this.getEjbDeploymentSummary(metadata);
      DefaultJndiBindingPolicy policy = this.getJNDIBindingPolicy(metadata);
      return policy.getJndiName(ejbDeploymentSummary, KnownInterfaces.LOCAL_HOME, KnownInterfaceType.LOCAL_HOME);
   }

   /**
    * Uses the {@link DefaultJndiBindingPolicy} instance returned by {@link #getJNDIBindingPolicy(JBossSessionBeanMetaData)}
    * to resolve the remote business default jndi name for the <code>metadata</code>
    * 
    * @see org.jboss.metadata.ejb.jboss.jndi.resolver.spi.SessionBeanJNDINameResolver#resolveRemoteBusinessDefaultJNDIName(org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData)
    * @see #getJNDIBindingPolicy(JBossSessionBeanMetaData)
    */
   @Override
   public String resolveRemoteBusinessDefaultJNDIName(JBossSessionBeanMetaData metadata)
   {
      // Obtain remote bindings
      List<RemoteBindingMetaData> bindings = metadata.getRemoteBindings();

      // If defined, use the first remote binding as a JNDI default
      if (bindings != null && bindings.size() > 0)
      {
         String remoteBindingJndiName = bindings.get(0).getJndiName();
         if (remoteBindingJndiName != null && remoteBindingJndiName.length() > 0)
         {
            return remoteBindingJndiName;
         }
      }

      // Try the mapped-name
      String mappedName = metadata.getMappedName();
      if (mappedName != null && mappedName.length() > 0)
         return mappedName;

      // Try explicit jndi-name
      String jndiName = metadata.getJndiName();
      if (jndiName != null && jndiName.length() > 0)
         return jndiName;

      // Not explicitly defined, so delegate out to the policy
      EjbDeploymentSummary ejbDeploymentSummary = this.getEjbDeploymentSummary(metadata);
      DefaultJndiBindingPolicy policy = this.getJNDIBindingPolicy(metadata);
      return policy.getJndiName(ejbDeploymentSummary, KnownInterfaces.REMOTE, KnownInterfaceType.BUSINESS_REMOTE);
   }

   /**
    * Uses the {@link DefaultJndiBindingPolicy} instance returned by {@link #getJNDIBindingPolicy(JBossSessionBeanMetaData)}
    * to resolve the remote home jndi name for the <code>metadata</code>
    * 
    * @see org.jboss.metadata.ejb.jboss.jndi.resolver.spi.SessionBeanJNDINameResolver#resolveRemoteHomeJNDIName(org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData)
    * @see #getJNDIBindingPolicy(JBossSessionBeanMetaData)
    */
   @Override
   public String resolveRemoteHomeJNDIName(JBossSessionBeanMetaData metadata)
   {
      // Check first for explicitly-defined Remote Home JNDI Name
      String remoteHomeJndiName = metadata.getHomeJndiName();
      if (remoteHomeJndiName != null)
         return remoteHomeJndiName;

      // Get the remote home jndi name from the jndi binding policy
      EjbDeploymentSummary ejbDeploymentSummary = this.getEjbDeploymentSummary(metadata);
      DefaultJndiBindingPolicy policy = this.getJNDIBindingPolicy(metadata);
      return policy.getJndiName(ejbDeploymentSummary, KnownInterfaces.HOME, KnownInterfaceType.REMOTE_HOME);

   }

   /**
    * Uses the {@link DefaultJndiBindingPolicy} instance returned by {@link #getJNDIBindingPolicy(JBossSessionBeanMetaData)}
    * to resolve the local default business jndi name for the <code>metadata</code>
    * 
    * @see org.jboss.metadata.ejb.jboss.jndi.resolver.spi.SessionBeanJNDINameResolver#resolveLocalBusinessDefaultJNDIName(org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData)
    * @see #getJNDIBindingPolicy(JBossSessionBeanMetaData)
    */
   @Override
   public String resolveLocalBusinessDefaultJNDIName(JBossSessionBeanMetaData metadata)
   {
      // Check first for explicitly-defined local JNDI Name
      String localJndiName = metadata.getLocalJndiName();
      if (localJndiName != null && localJndiName.length() > 0)
         return localJndiName;

      // Get the local jndi name from the jndi binding policy
      EjbDeploymentSummary ejbDeploymentSummary = this.getEjbDeploymentSummary(metadata);
      DefaultJndiBindingPolicy policy = this.getJNDIBindingPolicy(metadata);
      return policy.getJndiName(ejbDeploymentSummary, KnownInterfaces.LOCAL, KnownInterfaceType.BUSINESS_LOCAL);
   }

   /**
    * Uses the {@link DefaultJndiBindingPolicy} instance returned by {@link #getJNDIBindingPolicy(JBossSessionBeanMetaData)}
    * to resolve the jndi name for the <code>metadata</code> and the <code>interfaceName</code> combination
    * 
    * @see org.jboss.metadata.ejb.jboss.jndi.resolver.spi.EnterpriseBeanJNDINameResolver#resolveJNDIName(org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData, java.lang.String)
    * @see #getJNDIBindingPolicy(JBossSessionBeanMetaData)
    */
   @Override
   public String resolveJNDIName(JBossSessionBeanMetaData metadata, String interfaceName)
   {
      String resolvedJndiName = null;

      // Classify the interface
      KnownInterfaceType ifaceType = classifyInterface(metadata, interfaceName);

      // Take appropriate handling depending upon the interface
      if (ifaceType.equals(KnownInterfaceType.REMOTE_HOME))
      {
         resolvedJndiName = this.resolveRemoteHomeJNDIName(metadata);
      }
      else if (ifaceType.equals(KnownInterfaceType.LOCAL_HOME))
      {
         resolvedJndiName = this.resolveLocalHomeJNDIName(metadata);
      }
      else 
      {
         // Let the policy generate the actual name
         DefaultJndiBindingPolicy policy = this.getJNDIBindingPolicy(metadata);
         resolvedJndiName = policy.getJndiName(this.getEjbDeploymentSummary(metadata), interfaceName, ifaceType);
      }

      logger.debug("Resolved JNDI Name for Interface " + interfaceName + " of type " + ifaceType + " is "
            + resolvedJndiName);

      // Return
      return resolvedJndiName;
   }

   
   /**
    * Classifies the fully qualified <code>interfaceName</code> into a {@link KnownInterfaceType}
    * based on the bean <code>metadata</code>.
    * 
    * @param metadata The bean metadata
    * @param interfaceName Fully qualified interface name
    * @return Returns a {@link KnownInterfaceType}
    * @throws NullPointerException If <code>metadata</code> is null
    */
   protected KnownInterfaceType classifyInterface(JBossSessionBeanMetaData metadata, String interfaceName)
   {

      // TODO: Why do we need this? The KnownInterfaces.classifyInterface expects the param 
      // passed to be strings like "home", "remote" etc...
      // TODO: This is here just for backward compatibility of earlier ways where the interfacename could
      // have even been "home", "remote", "local" etc... (see Default2xNamingStrategyTestCase#assertLocalHome)
      // Once everyone has moved to new resolvers then remove this hack
      KnownInterfaceType ifaceType = KnownInterfaces.classifyInterface(interfaceName);
      if (ifaceType != KnownInterfaceType.UNKNOWN)
      {
         return ifaceType;
      }
      // end of hack

      // Compare interface against the metadata local-home/home & business locals/remotes
      // Figure out the interface type from the metadata
      if (metadata.getLocalHome() != null && metadata.getLocalHome().equals(interfaceName))
         return KnownInterfaceType.LOCAL_HOME;
      else if (metadata.getHome() != null && metadata.getHome().equals(interfaceName))
         return KnownInterfaceType.REMOTE_HOME;
      else
      {
         // Check business locals
         BusinessLocalsMetaData locals = metadata.getBusinessLocals();
         if (locals != null)
         {
            for (String local : locals)
            {
               if (local.equals(interfaceName))
               {
                  return KnownInterfaceType.BUSINESS_LOCAL;
               }
            }
         }
         if (ifaceType == KnownInterfaceType.UNKNOWN)
         {
            // Check business remotes
            BusinessRemotesMetaData remotes = metadata.getBusinessRemotes();
            if (remotes != null)
            {
               for (String remote : remotes)
               {
                  if (remote.equals(interfaceName))
                  {
                     return KnownInterfaceType.BUSINESS_REMOTE;
                  }
               }
            }
         }
      }

      // Assume business remote
      return KnownInterfaceType.UNKNOWN;
   }

}
