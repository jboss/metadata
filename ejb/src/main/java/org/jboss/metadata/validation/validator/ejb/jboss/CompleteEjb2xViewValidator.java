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
package org.jboss.metadata.validation.validator.ejb.jboss;

import org.jboss.logging.Logger;
import org.jboss.metadata.common.spi.ErrorCodes;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.validation.ValidationException;
import org.jboss.metadata.validation.validator.ejb.JBossEjb3SessionBeanValidatorBase;

/**
 * CompleteEjb2xViewValidator
 * 
 * Raises a validation exception if there is an incomplete 
 * EJB2.x View defined by the ejb-jar schema:
 * 
 * "Either both the local-home and the local elements 
 * or both the home and the remote elements must be 
 * specified for the session bean." 
 * 
 * JBMETA-130
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class CompleteEjb2xViewValidator extends JBossEjb3SessionBeanValidatorBase
{
   // --------------------------------------------------------------------------------||
   // Class Members ------------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   private static final Logger log = Logger.getLogger(CompleteEjb2xViewValidator.class);

   /**
    * Convenience instance
    */
   public static CompleteEjb2xViewValidator INSTANCE = new CompleteEjb2xViewValidator();

   // --------------------------------------------------------------------------------||
   // Required Implementations -------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   /**
    * Validate the specified Session bean's metadata
    * 
    * @param smd
    * @throws ValidationException
    */
   protected void validate(JBossSessionBeanMetaData smd) throws ValidationException
   {
      // Initialize
      String errorCode = ErrorCodes.ERROR_CODE_JBMETA130;
      String errorMessageSuffix = "; Incomplete EJB2.x View [" + errorCode + "]";

      // Get component and home interfaces
      String remote = smd.getRemote();
      String home = smd.getHome();
      String local = smd.getLocal();
      String localHome = smd.getLocalHome();

      // If remote, need home
      if ((remote != null && remote.trim().length() > 0) && (home == null || home.trim().length() == 0))
      {
         throw new ValidationException("EJB " + smd.getName() + " has defined EJB2.x remote component interface of "
               + remote + " but has no home; " + errorMessageSuffix);
      }

      // If home, need remote
      if ((home != null && home.trim().length() > 0) && (remote == null || remote.trim().length() == 0))
      {
         throw new ValidationException("EJB " + smd.getName() + " has defined EJB2.x home interface of " + home
               + " but has no remote component interface; " + errorMessageSuffix);
      }

      // If local, need localHome
      if ((local != null && local.trim().length() > 0) && (localHome == null || localHome.trim().length() == 0))
      {
         throw new ValidationException("EJB " + smd.getName() + " has defined EJB2.x local component interface of "
               + local + " but has no localHome; " + errorMessageSuffix);
      }

      // If localHome, need local
      if ((localHome != null && localHome.trim().length() > 0) && (local == null || local.trim().length() == 0))
      {
         throw new ValidationException("EJB " + smd.getName() + " has defined EJB2.x local home interface of "
               + localHome + " but has no local component interface; " + errorMessageSuffix);
      }

      // Log OK
      log.trace("Passed validation for " + errorCode);

   }

}
