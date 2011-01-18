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
import java.util.Collection;

import javax.ejb.ActivationConfigProperty;

import org.jboss.ejb3.annotation.Consumer;
import org.jboss.metadata.annotation.creator.Creator;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossConsumerBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertiesMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertyMetaData;

/**
 * The @Consumer processor.
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class JBossConsumerProcessor
   extends AbstractEnterpriseBeanProcessor<JBossConsumerBeanMetaData>
   implements Creator<Class<?>, JBossConsumerBeanMetaData>, Processor<JBossMetaData, Class<?>>
{

   protected JBossConsumerProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
      // @Producer(s)
      addTypeProcessor(new JBossProducerProcessor(finder));
      addTypeProcessor(new JBossProducersProcessor(finder));
   }

   @Override
   public JBossConsumerBeanMetaData create(Class<?> beanClass)
   {
      Consumer annotation = finder.getAnnotation(beanClass, Consumer.class);
      if(annotation == null)
         return null;
      
      JBossConsumerBeanMetaData metaData = new JBossConsumerBeanMetaData();
      metaData.setEjbClass(beanClass.getName());
      if(annotation.name().length() > 0)
         metaData.setEjbName(annotation.name());
      else
         metaData.setEjbName(beanClass.getSimpleName());
      
      ActivationConfigProperty[] props = annotation.activationConfig();
      ActivationConfigMetaData config = new ActivationConfigMetaData();
      ActivationConfigPropertiesMetaData configProps = new ActivationConfigPropertiesMetaData();
      config.setActivationConfigProperties(configProps);
      for(ActivationConfigProperty prop : props)
      {
         ActivationConfigPropertyMetaData acp = new ActivationConfigPropertyMetaData();
         acp.setActivationConfigPropertyName(prop.propertyName());
         acp.setValue(prop.propertyValue());
         configProps.add(acp);
      }
      metaData.setActivationConfig(config);
      
      return metaData;
   }

   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(Consumer.class);
   }
}

