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

import java.util.List;

import org.jboss.ejb3.annotation.RemoteBinding;
import org.jboss.logging.Logger;
import org.jboss.metadata.common.spi.ErrorCodes;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.RemoteBindingMetaData;
import org.jboss.metadata.ejb.spec.BusinessRemotesMetaData;
import org.jboss.metadata.validation.ValidationException;
import org.jboss.metadata.validation.validator.ejb.JBossEjb3SessionBeanValidatorBase;

/**
 * RemoteBindingsWithNoRemoteBusinessInterfaceValidator
 * 
 * Raises a validation exception if there are @RemoteBindings
 * defined on any EJB Session Bean with no remote business
 * interface
 * 
 * JBMETA-117
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class RemoteBindingsWithNoRemoteBusinessInterfaceValidator extends JBossEjb3SessionBeanValidatorBase
{
   // --------------------------------------------------------------------------------||
   // Class Members ------------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   private static final Logger log = Logger.getLogger(RemoteBindingsWithNoRemoteBusinessInterfaceValidator.class);

   /**
    * Convenience instance
    */
   public static RemoteBindingsWithNoRemoteBusinessInterfaceValidator INSTANCE = new RemoteBindingsWithNoRemoteBusinessInterfaceValidator();

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
      String errorCode = ErrorCodes.ERROR_CODE_JBMETA117;

      /*
       * JBMETA-117
       * 
       * No @RemoteBindings are allowed if there are no business remote interfaces
       */

      // Get all business remotes
      BusinessRemotesMetaData businessRemotes = smd.getBusinessRemotes();

      // Get all @RemoteBindings
      List<RemoteBindingMetaData> remoteBindings = smd.getRemoteBindings();

      // If there are @RemoteBindings
      if (remoteBindings != null && remoteBindings.size() > 0)
      {
         // If there aren't any business remote interfaces
         if (businessRemotes == null || businessRemotes.size() == 0)
         {
            // Fail 
            throw new ValidationException("An @" + RemoteBinding.class.getSimpleName() + " was defined on EJB "
                  + smd.getName() + ", but this bean has no remote business interfaces defined. [" + errorCode + "]");
         }
      }

      // Log OK
      log.trace("Passed validation for " + errorCode);

   }

}
