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
package org.jboss.metadata.process.processor.ejb.jboss;

import java.util.List;

import org.jboss.logging.Logger;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.LocalBindingMetaData;
import org.jboss.metadata.process.ProcessingException;
import org.jboss.metadata.process.processor.JBossMetaDataProcessor;

/**
 * SetExplicitLocalJndiNameProcessor
 * 
 * Processor to set the default local JNDI name
 * as specified by @LocalBinding.jndiName
 * upon metadata
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class SetExplicitLocalJndiNameProcessor<T extends JBossMetaData> implements JBossMetaDataProcessor<T>
{

   // --------------------------------------------------------------------------------||
   // Class Members ------------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   private static final Logger log = Logger.getLogger(SetExplicitLocalJndiNameProcessor.class);

   /**
    * Convenience instance
    */
   @SuppressWarnings("unchecked")
   public static final SetExplicitLocalJndiNameProcessor INSTANCE = new SetExplicitLocalJndiNameProcessor();

   // --------------------------------------------------------------------------------||
   // Required Implementations -------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   /* (non-Javadoc)
    * @see org.jboss.metadata.process.processor.JBossMetaDataProcessor#process(org.jboss.metadata.ejb.jboss.JBossMetaData)
    */
   public T process(T metadata) throws ProcessingException
   {
      // Sanity check
      assert metadata != null : "Specified metadata was null";

      // Get EJBs
      JBossEnterpriseBeansMetaData ejbs = metadata.getEnterpriseBeans();

      // For each EJB
      for (JBossEnterpriseBeanMetaData ejb : ejbs)
      {

         // Only applies to SLSB, SFSB, @Service
         if (!ejb.isSession() && !ejb.isService())
         {
            continue;
         }

         // Cast
         JBossSessionBeanMetaData smd = (JBossSessionBeanMetaData) ejb;

         // Get @LocalBindings
         List<LocalBindingMetaData> localBindings = smd.getLocalBindings();

         // If not specified, continue to next bean
         if (localBindings == null || localBindings.size() == 0)
         {
            continue;
         }

         // For now, we just support 1 LocalBinding
         assert localBindings.size() == 1 : "Currently only 1 @LocalBinding is supported for EJB " + smd.getName();

         // Grab the top @LocalBinding
         LocalBindingMetaData localBinding = localBindings.get(0);

         // Get the JNDI Name
         String localJndiName = localBinding.getJndiName();
         assert localJndiName != null && localJndiName.length() > 0 : "@LocalBinding.jndiName must be specified for EJB "
               + smd.getName();

         // Set the local JNDI Name
         smd.setLocalJndiName(localJndiName);

         // Log
         if (log.isTraceEnabled())
         {
            log.trace("Found and set @LocalBinding.jndiName for EJB " + smd.getName() + " to " + localJndiName);
         }
      }

      // Return
      return metadata;

   }

}
