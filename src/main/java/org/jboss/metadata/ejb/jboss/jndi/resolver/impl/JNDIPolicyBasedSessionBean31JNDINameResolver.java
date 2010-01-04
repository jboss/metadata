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

import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.jndi.resolver.spi.SessionBean31JNDINameResolver;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.EjbDeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.KnownInterfaces.KnownInterfaceType;

/**
 * JNDIPolicyBasedSessionBean31JNDINameResolver
 * 
 * An implementation of {@link SessionBean31JNDINameResolver}, which uses 
 * a {@link DefaultJndiBindingPolicy} for resolving jndi names for EJB3.1
 * session beans 
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class JNDIPolicyBasedSessionBean31JNDINameResolver extends JNDIPolicyBasedSessionBeanJNDINameResolver
      implements
         SessionBean31JNDINameResolver
{

   /**
    * Constructs a resolver based on the <code>jndibindingPolicy</code>
    * 
    * @param jndibindingPolicy The jndi binding policy which will be used for resolving jndi names 
    *       out of session bean metadata, if the metadata does not have a jndi binding policy set
    *       or if {@link #isIgnoreJNDIBindingPolicyOnMetaData()} returns true
    *       
    * @see #JNDIPolicyBasedSessionBean31JNDINameResolver(DefaultJndiBindingPolicy)
    * @throws IllegalArgumentException If the passed <code>jndibindingPolicy</code> is null
    */
   public JNDIPolicyBasedSessionBean31JNDINameResolver(DefaultJndiBindingPolicy jndibindingPolicy)
   {
      super(jndibindingPolicy);
   }

   /**
    * Uses the {@link DefaultJndiBindingPolicy} instance returned by {@link #getJNDIBindingPolicy(JBossSessionBeanMetaData)}
    * to resolve the no-interface view jndi name for the <code>metadata</code>
    * 
    * @see org.jboss.metadata.ejb.jboss.jndi.resolver.spi.SessionBean31JNDINameResolver#resolveNoInterfaceJNDIName(org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData)
    * @see #getJNDIBindingPolicy(JBossSessionBeanMetaData)
    */
   @Override
   public String resolveNoInterfaceJNDIName(JBossSessionBean31MetaData metadata)
   {
      // Get the no-interface view jndi name from the jndi binding policy
      EjbDeploymentSummary ejbDeploymentSummary = this.getEjbDeploymentSummary(metadata);
      DefaultJndiBindingPolicy policy = this.getJNDIBindingPolicy(metadata);
      return policy.getJndiName(ejbDeploymentSummary, KnownInterfaces.NO_INTERFACE, KnownInterfaceType.NO_INTERFACE);
   }

}
