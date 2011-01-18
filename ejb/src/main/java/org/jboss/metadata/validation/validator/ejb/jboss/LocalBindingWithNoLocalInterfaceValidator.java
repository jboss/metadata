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

import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.metadata.common.spi.ErrorCodes;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.validation.ValidationException;
import org.jboss.metadata.validation.validator.ejb.JBossEjb3SessionBeanValidatorBase;

/**
 * Validate if the Bean has local-binding, but no local business interfaces [JBMETA-117].
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class LocalBindingWithNoLocalInterfaceValidator extends JBossEjb3SessionBeanValidatorBase
{
   /** The error code */
   String errorCode = ErrorCodes.ERROR_CODE_JBMETA117;
   
   /** The instance. */
   public static final LocalBindingWithNoLocalInterfaceValidator INSTANCE = new LocalBindingWithNoLocalInterfaceValidator(); 
   
   @Override
   protected void validate(JBossSessionBeanMetaData smd) throws ValidationException
   {
      // If this bean has a locaBinding but no local business interfaces defined
      if(smd.getLocalBindings() != null && ! smd.getLocalBindings().isEmpty())
      {
         if(smd.getBusinessLocals() == null || smd.getBusinessLocals().isEmpty())
         {
            // Fail 
            throw new ValidationException("A @" + LocalBinding.class.getSimpleName() + " was defined on EJB "
                  + smd.getName() + ", but this bean has no local business interfaces defined. [" + errorCode + "]");
         }
      }
   }
}
