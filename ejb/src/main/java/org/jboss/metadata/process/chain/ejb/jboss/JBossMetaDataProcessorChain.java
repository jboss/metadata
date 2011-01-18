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
package org.jboss.metadata.process.chain.ejb.jboss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.logging.Logger;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.process.ProcessingException;
import org.jboss.metadata.process.chain.ProcessorChain;
import org.jboss.metadata.process.processor.JBossMetaDataProcessor;

/**
 * JBossMetaDataProcessorChain
 * 
 * A Processor Chain for JBossMetaData
 *
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 * @version $Revision: $
 */
public class JBossMetaDataProcessorChain<T extends JBossMetaData> implements ProcessorChain<T>
{

   // --------------------------------------------------------------------------------||
   // Class Members ------------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   private static final Logger log = Logger.getLogger(JBossMetaDataProcessorChain.class);

   // --------------------------------------------------------------------------------||
   // Instance Members ---------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   /**
    * The processors to apply to the metadata
    */
   private List<JBossMetaDataProcessor<T>> processors;

   // --------------------------------------------------------------------------------||
   // Constructors -------------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   /**
    * Creates a new chain with empty processor list
    */
   public JBossMetaDataProcessorChain()
   {
      this.setProcessors(new ArrayList<JBossMetaDataProcessor<T>>());
   }

   /** Creates a new chain with the specified Processors
    * 
    */
   public JBossMetaDataProcessorChain(List<JBossMetaDataProcessor<T>> initialProcessors)
   {
      this.setProcessors(initialProcessors);
   }

   // --------------------------------------------------------------------------------||
   // Required Implementations -------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   /* (non-Javadoc)
    * @see org.jboss.metadata.process.chain.ProcessorChain#addProcessor(org.jboss.metadata.process.processor.JBossMetaDataProcessor)
    */
   public void addProcessor(JBossMetaDataProcessor<T> processor)
   {
      assert processor != null : "Specified processor was null";
      this._getProcessors().add(processor);
      log.debug("Added Processor " + processor + " to Chain " + this);

   }

   /* (non-Javadoc)
    * @see org.jboss.metadata.process.chain.ProcessorChain#getProcessors()
    */
   public List<JBossMetaDataProcessor<T>> getProcessors()
   {
      // Return an immutable view; don't publish the List
      List<JBossMetaDataProcessor<T>> processors = this._getProcessors();
      return Collections.unmodifiableList(processors);
   }

   /* (non-Javadoc)
    * @see org.jboss.metadata.process.chain.ProcessorChain#process(org.jboss.metadata.ejb.jboss.JBossMetaData)
    */
   public T process(T metadata) throws ProcessingException
   {
      // For each processor in the chain
      for (JBossMetaDataProcessor<T> processor : this._getProcessors())
      {
         // Process
         log.trace("Processing " + metadata + " on " + processor + "...");
         metadata = processor.process(metadata);
      }

      // Return 
      log.debug(metadata + " has been processed on " + this);
      return metadata;
   }

   // --------------------------------------------------------------------------------||
   // Accessors / Mutators -----------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   protected List<JBossMetaDataProcessor<T>> _getProcessors()
   {
      return this.processors;
   }

   protected void setProcessors(List<JBossMetaDataProcessor<T>> processors)
   {
      this.processors = processors;
   }

}
