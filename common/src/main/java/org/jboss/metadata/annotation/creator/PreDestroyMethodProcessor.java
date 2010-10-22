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
package org.jboss.metadata.annotation.creator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collection;

import javax.annotation.PreDestroy;

import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.LifecycleCallbackMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.RemoteEnvironmentRefsGroupMetaData;

/**
 * Translate @PreDestroy into LifecycleCallbackMetaData for
 * EnvironmentRefsGroupMetaData
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision: 76002 $
 */
public class PreDestroyMethodProcessor
   extends AbstractLifeCycleMethodProcessor
   implements Processor<RemoteEnvironmentRefsGroupMetaData, Method>
{
   /**
    * @param finder
    */
   public PreDestroyMethodProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public void process(RemoteEnvironmentRefsGroupMetaData metaData, Method element)
   {
      PreDestroy annotation = finder.getAnnotation(element, PreDestroy.class);
      if(annotation == null)
         return;

      LifecycleCallbackMetaData callback = super.create(element);
      LifecycleCallbacksMetaData preDestroys = metaData.getPreDestroys();
      if(preDestroys == null)
      {
         preDestroys = new LifecycleCallbacksMetaData();
         metaData.setPreDestroys(preDestroys);
      }
      preDestroys.add(callback);
   }

   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(PreDestroy.class);
   }
}
