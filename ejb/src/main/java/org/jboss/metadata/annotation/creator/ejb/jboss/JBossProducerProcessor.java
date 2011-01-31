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
package org.jboss.metadata.annotation.creator.ejb.jboss;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collection;

import org.jboss.ejb3.annotation.Producer;
import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossConsumerBeanMetaData;
import org.jboss.metadata.ejb.jboss.ProducerMetaData;

/**
 * The @Producer processor.
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class JBossProducerProcessor
   extends AbstractFinderUser
   implements Processor<JBossConsumerBeanMetaData, Class<?>>
{

   protected JBossProducerProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public void process(JBossConsumerBeanMetaData metaData, Class<?> type)
   {
      Producer annotation = finder.getAnnotation(type, Producer.class);
      if(annotation == null)
         return;
      
      process(metaData, annotation, type);
   }
   
   protected void process(JBossConsumerBeanMetaData metaData, Producer annotation, Class<?> type)
   {
      ProducerMetaData producer = new ProducerMetaData();
      if(annotation.connectionFactory().length() > 1)
         producer.setConnectionFactory(annotation.connectionFactory());
      
      if(annotation.producer() != Producer.class)
         producer.setClassName(annotation.producer().getName());
      else if(type != null && type.isInterface())
         producer.setClassName(type.getName());
      
      // TODO
      // annotation.transacted();
      // annotation.acknowledgeMode()
      
      if(metaData.getProducers() == null)
         metaData.setProducers(new ArrayList<ProducerMetaData>());
      
      metaData.getProducers().add(producer);
   }

   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(Producer.class);
   }
}

