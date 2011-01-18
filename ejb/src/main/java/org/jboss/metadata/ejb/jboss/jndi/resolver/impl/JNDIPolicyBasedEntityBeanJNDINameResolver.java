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

import org.jboss.logging.Logger;
import org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData;
import org.jboss.metadata.ejb.jboss.jndi.resolver.spi.EntityBeanJNDINameResolver;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.DefaultJNDIBindingPolicyFactory;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces.KnownInterfaceType;

/**
 * JNDIPolicyBasedEntityBeanJNDINameResolver
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class JNDIPolicyBasedEntityBeanJNDINameResolver extends AbstractJNDIPolicyBasedJNDINameResolver
      implements
         EntityBeanJNDINameResolver
{

   /**
    * Logger
    */
   private static Logger logger = Logger.getLogger(JNDIPolicyBasedEntityBeanJNDINameResolver.class);
   
   /**
    * Constructs a resolver which will use the {@link DefaultJndiBindingPolicy} returned by  
    *  {@link DefaultJNDIBindingPolicyFactory#getDefaultJNDIBindingPolicy()}
    */
   public JNDIPolicyBasedEntityBeanJNDINameResolver()
   {
      
   }

   public JNDIPolicyBasedEntityBeanJNDINameResolver(DefaultJndiBindingPolicy jndiBindingPolicy)
   {
      super(jndiBindingPolicy);
   }

   /**
    * @see org.jboss.metadata.ejb.jboss.jndi.resolver.spi.EntityBeanJNDINameResolver#resolveLocalHomeJNDIName(org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData)
    */
   @Override
   public String resolveLocalHomeJNDIName(JBossEntityBeanMetaData metadata)
   {
      // first check if the "local-jndi-name" (== jndi name for the local home interface of EJB2.x entity bean)
      // has been set of metadata.
      if (metadata.getLocalJndiName() != null)
      {
         return metadata.getLocalJndiName();
      }
      // Let the policy generate the actual name
      DefaultJndiBindingPolicy policy = this.getJNDIBindingPolicy(metadata);
      return policy.getJndiName(this.getEjbDeploymentSummary(metadata), metadata.getLocalHome(),
            KnownInterfaceType.LOCAL_HOME);
   }

   /**
    * @see org.jboss.metadata.ejb.jboss.jndi.resolver.spi.EntityBeanJNDINameResolver#resolveRemoteHomeJNDIName(org.jboss.metadata.ejb.jboss.JBossEntityBeanMetaData)
    */
   @Override
   public String resolveRemoteHomeJNDIName(JBossEntityBeanMetaData metadata)
   {
      // first check if the "jndi-name" (== jndi-name for the home interface of EJB2.x entity bean)
      // has been set of metadata.
      if (metadata.getJndiName() != null)
      {
         return metadata.getJndiName();
      }
      // Let the policy generate the actual name
      DefaultJndiBindingPolicy policy = this.getJNDIBindingPolicy(metadata);
      return policy.getJndiName(this.getEjbDeploymentSummary(metadata), metadata.getHome(),
            KnownInterfaceType.REMOTE_HOME);
   }

   @Override
   public String resolveJNDIName(JBossEntityBeanMetaData metadata, String interfaceName)
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

   protected KnownInterfaceType classifyInterface(JBossEntityBeanMetaData metadata, String interfaceName)
   {
      // TODO: Why do we need this? The KnownInterfaces.classifyInterface expects the param 
      // passed to be strings like "home", "remote" etc...
      // TODO: This is here just for backward compatibility of earlier ways where the interfacename could
      // have even been "home", "remote", "local" etc... (see ResolveJndiNameDecoratorUnitTestCase#testResolvedJndiNamesWithKnownIfacesEntity)
      // Once everyone has moved to new resolvers then remove this hack
      KnownInterfaceType ifaceType = KnownInterfaces.classifyInterface(interfaceName);
      if (ifaceType != KnownInterfaceType.UNKNOWN)
      {
         return ifaceType;
      }
      // end of hack

      // Compare interface against the metadata local-home/home 
      if (metadata.getLocalHome() != null && metadata.getLocalHome().equals(interfaceName))
      {
         return KnownInterfaceType.LOCAL_HOME;
      }
      else if (metadata.getHome() != null && metadata.getHome().equals(interfaceName))
      {
         return KnownInterfaceType.REMOTE_HOME;
      }

      // default unknown
      return KnownInterfaceType.UNKNOWN;
   }
}
