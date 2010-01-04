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
package org.jboss.metadata.ejb.jboss.jndipolicy.plugins;

import org.jboss.metadata.ejb.jboss.jndipolicy.spi.DefaultJndiBindingPolicy;

/**
 * DefaultJNDIBindingPolicyFactory
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class DefaultJNDIBindingPolicyFactory
{

   private static final String DEFAULT_JNDI_BINDING_CLASS_NAME = BasicJndiBindingPolicy.class.getName();

   public static DefaultJndiBindingPolicy getDefaultJNDIBindingPolicy()
   {
      ClassLoader tccl = getTCCL();
      return getDefaultJNDIBindingPolicy(tccl);
   }

   public static DefaultJndiBindingPolicy getDefaultJNDIBindingPolicy(ClassLoader cl)
   {
      return getJNDIBindingPolicy(DEFAULT_JNDI_BINDING_CLASS_NAME, cl);
   }

   public static DefaultJndiBindingPolicy getJNDIBindingPolicy(String jndiBindingPolicyClassName)
   {
      ClassLoader tccl = getTCCL();
      return getJNDIBindingPolicy(jndiBindingPolicyClassName, tccl);

   }

   public static DefaultJndiBindingPolicy getJNDIBindingPolicy(String jndiBindingPolicyClassName, ClassLoader cl)
   {
      if (cl == null)
      {
         throw new IllegalArgumentException("Classloader cannot be null while creating a jndi binding policy: " + jndiBindingPolicyClassName);
      }
      if (jndiBindingPolicyClassName == null || jndiBindingPolicyClassName.trim().isEmpty())
      {
         throw new IllegalArgumentException("JNDI binding policy classname cannot be null or empty string: " + jndiBindingPolicyClassName);
      }
      try
      {
         Class<?> jndiBindingPolicyOnMetaDataClass = Class.forName(jndiBindingPolicyClassName, true, cl);
         // make sure it indeed implements DefaultJndiBindingPolicy
         if (!DefaultJndiBindingPolicy.class.isAssignableFrom(jndiBindingPolicyOnMetaDataClass))
         {
            throw new RuntimeException("JNDI binding class: " + jndiBindingPolicyClassName + " does not implement "
                  + DefaultJndiBindingPolicy.class);
         }
         // create an instance
         return (DefaultJndiBindingPolicy) jndiBindingPolicyOnMetaDataClass.newInstance();
      }
      catch (ClassNotFoundException cnfe)
      {
         throw new RuntimeException("Could not load jndi binding policy: " + jndiBindingPolicyClassName, cnfe);
      }
      catch (InstantiationException ine)
      {
         throw new RuntimeException("Could not create an instance of jndi binding policy: "
               + jndiBindingPolicyClassName, ine);
      }
      catch (IllegalAccessException iae)
      {
         throw new RuntimeException(
               "IllegalAccessException while trying to create an instance of jndi binding policy: "
                     + jndiBindingPolicyClassName, iae);
      }
   }

   private static ClassLoader getTCCL()
   {
      // TODO: Privileged block?
      ClassLoader tccl = Thread.currentThread().getContextClassLoader();
      return tccl;
   }
}
