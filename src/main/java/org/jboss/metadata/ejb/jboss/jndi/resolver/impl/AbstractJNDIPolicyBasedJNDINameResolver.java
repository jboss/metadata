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
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.jndipolicy.plugins.DefaultJNDIBindingPolicyFactory;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DeploymentSummary;
import org.jboss.metadata.ejb.jboss.jndipolicy.spi.EjbDeploymentSummary;

/**
 * AbstractJNDIPolicyBasedJNDINameResolver
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public abstract class AbstractJNDIPolicyBasedJNDINameResolver
{

   /**
    * Logger
    */
   private static Logger logger = Logger.getLogger(AbstractJNDIPolicyBasedJNDINameResolver.class);

   /**
    * The jndi binding policy which will be used to resolve jndi names,
    * from enterprise bean metadata, if the metadata does not have a jndi binding 
    * policy set, or if {@link #isIgnoreJNDIBindingPolicyOnMetaData()} returns true
    */
   protected DefaultJndiBindingPolicy jndiBindingPolicy;

   /**
    * Whether the jndi binding policy on the metadata should be ignored 
    * while resolving the jndi names. 
    * <ul>
    *   <li>
    *       If set to true, then the binding policy on metadata (if any) will be ignored 
    *       and instead the jndi binding policy which was passed
    *       to the constructor {@link #JNDIPolicyBasedSessionBeanJNDINameResolver(DefaultJndiBindingPolicy)}
    *       will be used, during jndi name resolution.
    *   </li>
    *   <li>
    *       If set to false, then the metadata will be checked to see if the jndi binding policy
    *       has been set. If yes, then the jndi binding policy set on the metadata will be loaded
    *       using the TCCL and that binding policy will be used to resolve the jndi names. If the 
    *       binding policy is not set on the metadata then this resolver will use the jndi binding policy
    *       which was passed to the constructor {@link #JNDIPolicyBasedSessionBeanJNDINameResolver(DefaultJndiBindingPolicy)}
    *   </li> 
    * </ul>
    */
   protected boolean ignoreJNDIBindingPolicyOnMetaData;
   
   /**
    * Constructs a resolver which will use the {@link DefaultJndiBindingPolicy} returned by  
    *  {@link DefaultJNDIBindingPolicyFactory#getDefaultJNDIBindingPolicy()}
    */
   public AbstractJNDIPolicyBasedJNDINameResolver()
   {
      this(DefaultJNDIBindingPolicyFactory.getDefaultJNDIBindingPolicy());
   }

   /**
    * Constructs a {@link AbstractJNDIPolicyBasedJNDINameResolver} 
    * 
    * @param jndibindingPolicy The jndi binding policy which will be used for resolving jndi names 
    *       out of session bean metadata, if the metadata does not have a jndi binding policy set
    *       or if {@link #isIgnoreJNDIBindingPolicyOnMetaData()} returns true
    * @throws IllegalArgumentException If the passed <code>jndibindingPolicy</code> is null
    * @see #getJNDIBindingPolicy(JBossSessionBeanMetaData)       
    */
   public AbstractJNDIPolicyBasedJNDINameResolver(DefaultJndiBindingPolicy jndibindingPolicy)
   {
      if (jndibindingPolicy == null)
      {
         throw new IllegalArgumentException(
               "JNDI binding policy cannot be null, for a JNDI binding policy based jndi-name resolver");
      }
      this.jndiBindingPolicy = jndibindingPolicy;
   }

   /**
   *
   * @return  Returns whether the {@link #ignoreJNDIBindingPolicyOnMetaData} flag is set
   */
   public boolean isIgnoreJNDIBindingPolicyOnMetaData()
   {
      return this.ignoreJNDIBindingPolicyOnMetaData;
   }

   /**
    * Sets the {@link #ignoreJNDIBindingPolicyOnMetaData} flag
    * @param ignoreJNDIBindingPolicyOnMetaData True if the jndi binding policy on the metadata
    *         (if any) has to ignored during resolving jndi-names   
    */
   public void setIgnoreJNDIBindingPolicyOnMetaData(boolean ignoreJNDIBindingPolicyOnMetaData)
   {
      this.ignoreJNDIBindingPolicyOnMetaData = ignoreJNDIBindingPolicyOnMetaData;
   }

   /**
    * Returns the {@link EjbDeploymentSummary} from the metadata
    * 
    * @param metadata Bean metadata
    * @return
    */
   protected EjbDeploymentSummary getEjbDeploymentSummary(JBossEnterpriseBeanMetaData metadata)
   {
      DeploymentSummary dsummary = metadata.getJBossMetaData().getDeploymentSummary();
      if (dsummary == null)
         dsummary = new DeploymentSummary();
      return new EjbDeploymentSummary(metadata, dsummary);
   }

   /**
    * Returns an appropriate {@link DefaultJndiBindingPolicy} to be used for resolving
    * jndi-names. <br/>
    * 
    * This method first checks whether the jndi binding policy on the metadata (if any)
    * is to be ignored ({@link #isIgnoreJNDIBindingPolicyOnMetaData()}). If yes, then
    * this method returns the jndi binding policy which was passed to the constructor 
    * of this instance {@link #JNDIPolicyBasedSessionBeanJNDINameResolver(DefaultJndiBindingPolicy)}.
    * 
    * If {@link #isIgnoreJNDIBindingPolicyOnMetaData()} returns false, then this method checks
    * to see if there is any jndi binding policy set on the metadata. If it finds one then
    * this method uses the context classloader of the current thread ({@link Thread#getContextClassLoader()})
    * to load that binding policy. If successfully loaded, then this method returns the instance
    * of the jndi binding policy set on the metadata. If the binding policy cannot be loaded,
    * or if the jndi binding policy doesn't implement {@link DefaultJndiBindingPolicy} then this method
    * throws a {@link RuntimeException}.
    * 
    * If {@link #isIgnoreJNDIBindingPolicyOnMetaData()} returns false and there is *no* jndi binding 
    * policy set on the metadata, then this method returns the jndi binding policy which was passed to the constructor 
    * of this instance {@link #JNDIPolicyBasedSessionBeanJNDINameResolver(DefaultJndiBindingPolicy)}.
    * 
    * @param metadata Metadata of the bean, for which the jndi-names will be resolved by this resolver
    * @throws RuntimeException If the jndi binding policy on the metadata cannot be loaded in 
    *           the current thread's context classloader or if the jndi binding policy on the metadata
    *           does not implement {@link DefaultJndiBindingPolicy}
    * @return
    */
   protected DefaultJndiBindingPolicy getJNDIBindingPolicy(JBossEnterpriseBeanMetaData metadata)
   {
      if (this.isIgnoreJNDIBindingPolicyOnMetaData())
      {
         return this.jndiBindingPolicy;
      }
      String jndiBindingPolicyClassName = metadata.getJndiBindingPolicy();
      if (jndiBindingPolicyClassName == null || jndiBindingPolicyClassName.trim().isEmpty())
      {
         return this.jndiBindingPolicy;
      }
      // TODO: Privileged block?
      ClassLoader tccl = Thread.currentThread().getContextClassLoader();
      try
      {
         Class<?> jndiBindingPolicyOnMetaDataClass = Class.forName(jndiBindingPolicyClassName, true, tccl);
         // make sure it indeed implements DefaultJndiBindingPolicy
         if (!DefaultJndiBindingPolicy.class.isAssignableFrom(jndiBindingPolicyOnMetaDataClass))
         {
            throw new RuntimeException("JNDI binding class: " + jndiBindingPolicyClassName + " on EJB named "
                  + metadata.getEjbName() + " does not implement " + DefaultJndiBindingPolicy.class);
         }
         // create an instance
         return (DefaultJndiBindingPolicy) jndiBindingPolicyOnMetaDataClass.newInstance();
      }
      catch (ClassNotFoundException cnfe)
      {
         throw new RuntimeException("Could not load jndi binding policy: " + jndiBindingPolicyClassName
               + " from metadata for EJB named " + metadata.getEjbName(), cnfe);
      }
      catch (InstantiationException ine)
      {
         throw new RuntimeException("Could not create an instance of jndi binding policy: "
               + jndiBindingPolicyClassName + " from metadata for EJB named " + metadata.getEjbName(), ine);
      }
      catch (IllegalAccessException iae)
      {
         throw new RuntimeException(
               "IllegalAccessException while trying to create an instance of jndi binding policy: "
                     + jndiBindingPolicyClassName + " from metadata for EJB named " + metadata.getEjbName(), iae);
      }
   }

}
