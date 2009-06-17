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

import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;

/**
 * JbossEnterpriseBeanJndiNameResolver
 * 
 * Utility class to resolve target JNDI Names for Enterprise 
 * Bean Metadata instances
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class JbossEnterpriseBeanJndiNameResolver
{

   /**
    * Resolves the JNDI Name for the specified target interface on the
    * specified metadata
    * 
    * @param md
    * @param iface
    * @return
    */
   public static String resolveJndiName(JBossEnterpriseBeanMetaData md, String iface)
   {
      // Ensure the metadata is able to resolve JNDI names
      ResolveableJndiNameJbossEnterpriseBeanMetadata rmd = ensureResolvable(md);

      // Resolve
      String resolved = rmd.determineResolvedJndiName(iface);

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
   protected static ResolveableJndiNameJbossEnterpriseBeanMetadata ensureResolvable(JBossEnterpriseBeanMetaData md)
   {
      // Check that castable to a JNDI Resolveable type
      boolean resolveable = md instanceof ResolveableJndiNameJbossEnterpriseBeanMetadata;

      // If resolveable, cast and return
      if (resolveable)
      {
         return (ResolveableJndiNameJbossEnterpriseBeanMetadata) md;
      }

      // Throw error (by assertion if enabled) because this is not castable
      String errorMessage = "Specified instance of " + md.getClass().getName() + " is not resolvable, required type "
            + ResolveableJndiNameJbossEnterpriseBeanMetadata.class.getSimpleName() + " must be implemented";
      assert resolveable : errorMessage;
      throw new IllegalArgumentException(errorMessage);

   }

}
