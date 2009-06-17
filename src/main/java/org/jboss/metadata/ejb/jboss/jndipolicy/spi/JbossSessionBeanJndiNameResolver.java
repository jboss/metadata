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
package org.jboss.metadata.ejb.jboss.jndipolicy.spi;

import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;

/**
 * JbossSessionBeanJndiNameResolver
 * 
 * Utility class to resolve target JNDI Names for Session 
 * Bean Metadata instances
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class JbossSessionBeanJndiNameResolver extends JbossEnterpriseBeanJndiNameResolver
{
   /**
    * Returns the resolved JNDI target to which the
    * EJB2.x Remote Home interface is to be bound
    * 
    * @return
    */
   public static String resolveRemoteHomeJndiName(JBossSessionBeanMetaData md)
   {
      // Ensure Resolveable
      ResolveableJndiNameJbossSessionBeanMetadata rmd = ensureResolvable(md);

      // Resolve
      String resolved = rmd.determineResolvedRemoteHomeJndiName();

      // Return
      return resolved;
   }

   /**
    * Returns the resolved JNDI target to which the
    * EJB2.x Local Home interface is to be bound
    * 
    * @return
    */
   public static String resolveLocalHomeJndiName(JBossSessionBeanMetaData md)
   {
      // Ensure Resolveable
      ResolveableJndiNameJbossSessionBeanMetadata rmd = ensureResolvable(md);

      // Resolve
      String resolved = rmd.determineResolvedLocalHomeJndiName();

      // Return
      return resolved;
   }

   /**
    * Returns the resolved JNDI target to which the
    * default EJB3.x Remote Business interfaces are to be bound
    * 
    * @return
    */
   public static String resolveRemoteBusinessDefaultJndiName(JBossSessionBeanMetaData md)
   {
      // Ensure Resolveable
      ResolveableJndiNameJbossSessionBeanMetadata rmd = ensureResolvable(md);

      // Resolve
      String resolved = rmd.determineResolvedRemoteBusinessDefaultJndiName();

      // Return
      return resolved;
   }

   /**
    * Returns the resolved JNDI target to which the
    * default EJB3.x Local Business interfaces are to be bound
    * 
    * @return
    */
   public static String resolveLocalBusinessDefaultJndiName(JBossSessionBeanMetaData md)
   {
      // Ensure Resolveable
      ResolveableJndiNameJbossSessionBeanMetadata rmd = ensureResolvable(md);

      // Resolve
      String resolved = rmd.determineResolvedLocalBusinessDefaultJndiName();

      // Return
      return resolved;
   }

   /**
    * Ensures that the specified Metadata is able to resolve JNDI Names and
    * returns the casted type if so.  Otherwise a runtime error will be raised
    * (by assertion failure if enabled or IllegalArgumentException if not)
    * 
    * @param md
    * @return
    */
   protected static ResolveableJndiNameJbossSessionBeanMetadata ensureResolvable(JBossSessionBeanMetaData md)
   {
      // Check that castable to a JNDI Resolveable type
      boolean resolveable = md instanceof ResolveableJndiNameJbossSessionBeanMetadata;

      // If resolveable, cast and return
      if (resolveable)
      {
         return (ResolveableJndiNameJbossSessionBeanMetadata) md;
      }

      // Throw error (by assertion if enabled) because this is not castable
      String errorMessage = "Specified instance of " + md.getClass().getName() + " is not resolvable, required type "
            + ResolveableJndiNameJbossSessionBeanMetadata.class.getSimpleName() + " must be implemented";
      assert resolveable : errorMessage;
      throw new IllegalArgumentException(errorMessage);

   }
}
