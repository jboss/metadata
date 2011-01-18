/*
 * JBoss, Home of Professional Open Source
 * Copyright (c) 2010, JBoss Inc., and individual contributors as indicated
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

import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;
import org.jboss.metadata.ejb.spec.MethodParametersMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;

import javax.ejb.AfterBegin;
import javax.ejb.AfterCompletion;
import javax.ejb.BeforeCompletion;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class SessionSynchronizationProcessor extends AbstractFinderUser
   implements Processor<JBossSessionBean31MetaData, Method>
{
   private static Collection<Class<? extends Annotation>> ANNOTATION_TYPES = Arrays.asList(AfterBegin.class, AfterCompletion.class, BeforeCompletion.class);
   
   public SessionSynchronizationProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   @Override
   public void process(JBossSessionBean31MetaData metaData, Method method)
   {
      AfterBegin afterBegin = finder.getAnnotation(method, AfterBegin.class);
      if(afterBegin != null)
      {
         if(method.getParameterTypes().length != 0)
            throw new IllegalArgumentException("EJB 3.1 FR 4.9.4 The @AfterBegin method " + method + " must take 0 arguments on " + metaData);
         metaData.setAfterBeginMethod(method(method));
      }

      AfterCompletion afterCompletion = finder.getAnnotation(method, AfterCompletion.class);
      if(afterCompletion != null)
      {
         if(method.getParameterTypes().length != 1)
            throw new IllegalArgumentException("EJB 3.1 FR 4.9.4 The @AfterCompletion method " + method + " must take a single argument (of type boolean) on " + metaData);
         if(!method.getParameterTypes()[0].equals(Boolean.TYPE))
            throw new IllegalArgumentException("EJB 3.1 FR 4.9.4 The @AfterCompletion method " + method + " must take a single argument of type boolean on " + metaData);
         metaData.setAfterCompletionMethod(method(method));
      }

      BeforeCompletion beforeCompletion = finder.getAnnotation(method, BeforeCompletion.class);
      if(beforeCompletion != null)
      {
         if(method.getParameterTypes().length != 0)
            throw new IllegalArgumentException("EJB 3.1 FR 4.9.4 The @BeforeCompletion method " + method + " must take 0 arguments on " + metaData);
         metaData.setBeforeCompletionMethod(method(method));
      }
   }

   @Override
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ANNOTATION_TYPES;
   }

   private static NamedMethodMetaData method(Method method)
   {
      NamedMethodMetaData metaData = new NamedMethodMetaData();
      metaData.setMethodName(method.getName());
      if(method.getParameterTypes().length > 0)
      {
         MethodParametersMetaData methodParams = new MethodParametersMetaData();
         for(Class<?> parameterType : method.getParameterTypes())
         {
            methodParams.add(parameterType.getName());
         }
         metaData.setMethodParams(methodParams);
      }
      return metaData;
   }
}
