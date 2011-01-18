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

import org.jboss.logging.Logger;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.process.ProcessingException;
import org.jboss.metadata.process.processor.JBossMetaDataProcessor;
import org.jboss.metadata.validation.chain.ValidatorChain;
import org.jboss.metadata.validation.chain.ejb.jboss.JBossMetaDataValidatorChain;

/**
 * JBossMetaDataValidatorChainProcessor
 * 
 * Processor to send the specified metadata
 * through the default validation chain
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class JBossMetaDataValidatorChainProcessor<T extends JBossMetaData> implements JBossMetaDataProcessor<T>
{

   // --------------------------------------------------------------------------------||
   // Class Members ------------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   private static final Logger log = Logger.getLogger(JBossMetaDataValidatorChainProcessor.class);

   @SuppressWarnings("unchecked")
   public static final JBossMetaDataValidatorChainProcessor<JBossMetaData> INSTANCE = new JBossMetaDataValidatorChainProcessor<JBossMetaData>();

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

      // Set up the default validator chain
      ValidatorChain<T> chain = new JBossMetaDataValidatorChain<T>();

      // Validate
      chain.validate(metadata);

      // Return
      log.debug(metadata + " has been validated by " + chain);
      return metadata;

   }

}
