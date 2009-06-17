/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
import javax.ejb.MessageDriven;

import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossMessageDrivenBeanMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertiesMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertyMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;


/**
 * A MessageDrivenProcessor.
 * 
 * @author Scott.Stark@jboss.org
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class MessageDrivenProcessor extends AbstractEnterpriseBeanProcessor<JBossMessageDrivenBeanMetaData>
{

   protected MessageDrivenProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
      addTypeProcessor(new JBossResourceAdapterProcessor(finder));
      addMethodProcessor(new TimeoutProcessor(finder));
   }

   @Override
   protected JBossMessageDrivenBeanMetaData create(Class<?> beanClass)
   {
      MessageDriven annotation = finder.getAnnotation(beanClass, MessageDriven.class);
      if(annotation == null)
         return null;

      JBossMessageDrivenBeanMetaData metaData = new JBossMessageDrivenBeanMetaData();
      metaData.setEjbClass(beanClass.getName());
      if(annotation.name().length() > 0)
         metaData.setEjbName(annotation.name());
      else
         metaData.setEjbName(beanClass.getSimpleName());
      if(annotation.description().length() > 0)
      {
         DescriptionGroupMetaData dg = ProcessorUtils.getDescriptionGroup(annotation.description());
         metaData.setDescriptionGroup(dg);
      }
      if(annotation.mappedName().length() > 0)
         metaData.setMappedName(annotation.mappedName());
      if(annotation.messageListenerInterface() != Object.class)
         metaData.setMessagingType(annotation.messageListenerInterface().getName());
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
      return ProcessorUtils.createAnnotationSet(MessageDriven.class);
   }
}
