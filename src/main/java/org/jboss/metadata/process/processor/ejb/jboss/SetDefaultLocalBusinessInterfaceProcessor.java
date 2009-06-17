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

import javax.ejb.EJBLocalObject;

import org.jboss.logging.Logger;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.spec.BusinessLocalsMetaData;
import org.jboss.metadata.lang.ClassHelper;
import org.jboss.metadata.process.ProcessingException;
import org.jboss.metadata.process.processor.JBossMetaDataProcessor;

/**
 * SetDefaultLocalBusinessInterfaceProcessor
 * 
 * Processor to set the default local business interface
 * on EJBs with only one interface implemented, and not 
 * explicitly marked as either @Local or @Remote
 * 
 * EJB3 Core Spec 4.6.6
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class SetDefaultLocalBusinessInterfaceProcessor<T extends JBossMetaData> implements JBossMetaDataProcessor<T>
{

   // --------------------------------------------------------------------------------||
   // Class Members ------------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   private static final Logger log = Logger.getLogger(SetDefaultLocalBusinessInterfaceProcessor.class);

   // --------------------------------------------------------------------------------||
   // Instance Members ---------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   private ClassLoader cl;

   // --------------------------------------------------------------------------------||
   // Constructor --------------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   /**
    * Sole Constructor
    * 
    * @param cl The ClassLoader to use in Processing
    */
   public SetDefaultLocalBusinessInterfaceProcessor(final ClassLoader cl)
   {
      assert cl != null : "Specified ClassLoader was null";
      this.setCl(cl);
   }

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

         // Only applies to Session beans
         if (!ejb.isSession())
         {
            continue;
         }

         // Cast
         JBossSessionBeanMetaData smd = (JBossSessionBeanMetaData) ejb;

         // Load the EJB Implementation Class
         String ejbImplementationClassName = smd.getEjbClass();
         assert ejbImplementationClassName != null && ejbImplementationClassName.trim().length() > 0 : "EJB Implementation Class for EJB "
               + smd.getName() + " was not specified";
         ClassLoader cl = this.getCl();
         Class<?> ejbImplementationClass = null;
         try
         {
            ejbImplementationClass = Class.forName(ejbImplementationClassName, false, cl);
         }
         catch (ClassNotFoundException e)
         {
            throw new ProcessingException("Could not load EJB Implementation Class " + ejbImplementationClassName
                  + " from the specified ClassLoader: " + cl);
         }

         // If there are already local business interfaces specified
         if (smd.getBusinessLocals() != null && smd.getBusinessLocals().size() > 0)
            continue;

         // If there are already remote business interfaces specified
         if (smd.getBusinessRemotes() != null && smd.getBusinessRemotes().size() > 0)
            continue;

         // Get the a single interface
         Class<?> businessInterface = ClassHelper.extractInterface(ejbImplementationClass.getInterfaces());
         if (businessInterface == null)
            continue;

         // A business interface must not extend EJBLocalObject
         if (EJBLocalObject.class.isAssignableFrom(businessInterface))
            throw new IllegalStateException(
                  "EJB 3.0 Core Specification Violation (4.6.6): The session bean’s business interface "
                        + businessInterface + " must not extend the javax.ejb.EJBLocalObject interface.");

         // Add this businessInterface as the local business interface
         if (smd.getBusinessLocals() == null)
            smd.setBusinessLocals(new BusinessLocalsMetaData());

         // Finally add local business interface
         smd.getBusinessLocals().add(businessInterface.getName());
      }

      // Return
      return metadata;

   }

   // --------------------------------------------------------------------------------||
   // Accessors / Mutators -----------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   public ClassLoader getCl()
   {
      return cl;
   }

   protected void setCl(ClassLoader cl)
   {
      this.cl = cl;
   }

}
