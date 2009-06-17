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
package org.jboss.metadata.validation.chain.ejb.jboss;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jboss.logging.Logger;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.validation.ValidationException;
import org.jboss.metadata.validation.chain.ValidatorChain;
import org.jboss.metadata.validation.chain.ValidatorChainException;
import org.jboss.metadata.validation.validator.Validator;
import org.jboss.metadata.validation.validator.ejb.jboss.CompleteEjb2xViewValidator;
import org.jboss.metadata.validation.validator.ejb.jboss.LocalBindingWithNoLocalInterfaceValidator;
import org.jboss.metadata.validation.validator.ejb.jboss.RemoteBindingsWithNoRemoteBusinessInterfaceValidator;

/**
 * JBossMetaDataValidatorChain
 * 
 * A Validator Chain for JBossMetaData
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class JBossMetaDataValidatorChain<T extends JBossMetaData> implements ValidatorChain<T>
{

   // --------------------------------------------------------------------------------||
   // Class Members ------------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   private static final Logger log = Logger.getLogger(JBossMetaDataValidatorChain.class);

   // --------------------------------------------------------------------------------||
   // Instance Members ---------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   /**
    * The validators to apply to the metadata
    */
   private List<Validator> validators = new ArrayList<Validator>();

   // --------------------------------------------------------------------------------||
   // Constructors -------------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   /**
    * Constructs a new ValidatorChain using the default Validators
    */
   public JBossMetaDataValidatorChain()
   {
      this(getDefaultValidators());
   }

   /**
    * Creates a new ValidatorChain using 
    * the specified Validators
    * 
    * @param metadata
    * @param validators
    */
   public JBossMetaDataValidatorChain(List<Validator> validators)
   {
      // For each specified validator
      assert validators != null : "Specified validators for chain was null";
      for (Validator validator : validators)
      {
         // Add to the chain
         log.trace("Added Validator to " + this + ": " + validator.getClass().getSimpleName());
         this.addValidator(validator);
      }
   }

   // --------------------------------------------------------------------------------||
   // Required Implementations -------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   /* (non-Javadoc)
    * @see org.jboss.metadata.validation.chain.ValidatorChain#validate(org.jboss.metadata.spi.MetaData)
    */
   public void validate(T metadata) throws ValidationException
   {
      // Initialize
      Collection<ValidationException> validationExceptions = new ArrayList<ValidationException>();

      // Obtain all configured validators
      List<Validator> validators = this.getValidators();

      // For each validator
      for (Validator validator : validators)
      {
         // Run validation
         try
         {
            validator.validate(metadata);
         }
         catch (ValidationException ve)
         {
            // Store the problem for reporting later
            validationExceptions.add(ve);
         }
      }

      // Check that we didn't run into any problems
      if (validationExceptions.size() > 0)
      {
         // Throw a new ValidationChainException from the causes
         throw new ValidatorChainException(validationExceptions);
      }

      // We're here, so all's good
      log.debug("Passed Validation on all configured validators for " + metadata);
   }

   /**
    * Obtains all validators in the chain.  Will return
    * an immutable view of the configured validators as to not allow
    * published mutation of the set except as provided 
    * 
    * @return
    */
   public List<Validator> getValidators()
   {
      List<Validator> validators = Collections.unmodifiableList(this._getValidators());
      return validators;
   }

   /**
    * Adds the specified validator to the chain
    * 
    * @param validator
    */
   public void addValidator(Validator validator)
   {
      assert validator != null : "Specified validator was null";
      this._getValidators().add(validator);
   }

   // --------------------------------------------------------------------------------||
   // Functional Methods -------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   /**
    * Returns a Set of validators to use as the default for this chain
    */
   protected static List<Validator> getDefaultValidators()
   {
      // Initialize
      List<Validator> defaultValidators = new ArrayList<Validator>();

      /*
       * Add all default validators
       */

      // JBMETA-117 No @LocalBindings if there's no local business interface
      defaultValidators.add(LocalBindingWithNoLocalInterfaceValidator.INSTANCE);
      // JBMETA-117 No @RemoteBindings if there's no remote business interface
      defaultValidators.add(RemoteBindingsWithNoRemoteBusinessInterfaceValidator.INSTANCE);
      // JBMETA-130 Complete EJB2.x View
      defaultValidators.add(CompleteEjb2xViewValidator.INSTANCE);

      // Return
      return defaultValidators;
   }

   // --------------------------------------------------------------------------------||
   // Accessors / Mutators -----------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   protected List<Validator> _getValidators()
   {
      return validators;
   }

   protected void setValidators(List<Validator> validators)
   {
      this.validators = validators;
   }

}
