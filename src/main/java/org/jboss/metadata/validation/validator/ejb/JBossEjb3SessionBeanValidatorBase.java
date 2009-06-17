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
package org.jboss.metadata.validation.validator.ejb;

import org.jboss.logging.Logger;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeansMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.validation.ValidationException;
import org.jboss.metadata.validation.validator.Validator;

/**
 * JBossSessionBeanValidatorBase
 * 
 * Provides common functions and contract to validate all
 * Session beans within full metadata
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public abstract class JBossEjb3SessionBeanValidatorBase implements Validator
{

   // --------------------------------------------------------------------------------||
   // Class Members ------------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   private static final Logger log = Logger.getLogger(JBossEjb3SessionBeanValidatorBase.class);

   // --------------------------------------------------------------------------------||
   // Required Implementations -------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   /* (non-Javadoc)
    * @see org.jboss.metadata.validation.validator.Validator#validate(org.jboss.metadata.ejb.jboss.JBossMetaData)
    */
   public void validate(JBossMetaData md) throws ValidationException
   {
      // Only applies to EJB3
      if (!md.isEJB3x())
      {
         return;
      }

      // Get all EJBs
      JBossEnterpriseBeansMetaData beans = md.getEnterpriseBeans();

      // For each EJB
      for (JBossEnterpriseBeanMetaData bean : beans)
      {

         // Only applies to Session Beans
         if (!bean.isSession())
         {
            continue;
         }

         // Cast
         JBossSessionBeanMetaData smd = (JBossSessionBeanMetaData) bean;

         // Validate 
         log.debug("Performing validation on Session EJB:  + " + smd.getName());
         this.validate(smd);

      }
   }

   // --------------------------------------------------------------------------------||
   // Contracts ----------------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   /**
    * Validate the specified Session bean's metadata
    * 
    * @param smd
    * @throws ValidationException
    */
   protected abstract void validate(JBossSessionBeanMetaData smd) throws ValidationException;

}
