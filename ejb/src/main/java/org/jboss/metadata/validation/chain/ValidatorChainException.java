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
package org.jboss.metadata.validation.chain;

import java.util.Collection;

import org.jboss.metadata.validation.ValidationException;

/**
 * ValidatorChainException
 * 
 * Represents an error in validation due to any N 
 * single ValidationExceptions
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class ValidatorChainException extends ValidationException
{
   // --------------------------------------------------------------------------------||
   // Class Members ------------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   private static final long serialVersionUID = 1L;

   // --------------------------------------------------------------------------------||
   // Instance Members ---------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   private ValidationException[] causes;

   // --------------------------------------------------------------------------------||
   // Constructors -------------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   public ValidatorChainException(ValidationException[] validationExceptions)
   {
      this.causes = validationExceptions;
   }

   public ValidatorChainException(Collection<ValidationException> validationExceptions)
   {
      this(validationExceptions.toArray(new ValidationException[]
      {}));
   }

   // --------------------------------------------------------------------------------||
   // Overridden Implementations -----------------------------------------------------||
   // --------------------------------------------------------------------------------||

   @Override
   public String getMessage()
   {
      // Initialize
      StringBuffer sb = new StringBuffer("Validation has failed due to the following ");
      sb.append(ValidationException.class.getSimpleName());
      sb.append("(s) raised:\n");

      /*
       * Report each underlying exception
       */
      for (ValidationException ve : this.causes)
      {
         sb.append("* ");
         sb.append(ve.getMessage());
         sb.append("\n");
      }

      // Return
      return sb.toString();
   }
}
