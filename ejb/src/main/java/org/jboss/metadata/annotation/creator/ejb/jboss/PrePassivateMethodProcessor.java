/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
import java.lang.reflect.Method;
import java.util.Collection;

import javax.ejb.PrePassivate;

import org.jboss.metadata.annotation.creator.AbstractLifeCycleMethodProcessor;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbackMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;

/**
 * Translate @PrePassivate into LifecycleCallbackMetaData for
 * SessionBeanMetaData
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision: 67165 $
 */
public class PrePassivateMethodProcessor
   extends AbstractLifeCycleMethodProcessor
   implements Processor<JBossSessionBeanMetaData, Method>
{
   /**
    * @param finder
    */
   public PrePassivateMethodProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public void process(JBossSessionBeanMetaData metaData, Method element)
   {
      PrePassivate annotation = finder.getAnnotation(element, PrePassivate.class);
      if(annotation == null)
         return;

      LifecycleCallbackMetaData callback = super.create(element);
      LifecycleCallbacksMetaData preDestroys = metaData.getPrePassivates();
      if(preDestroys == null)
      {
         preDestroys = new LifecycleCallbacksMetaData();
         metaData.setPrePassivates(preDestroys);
      }
      preDestroys.add(callback);
   }
   
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(PrePassivate.class);
   }

}
