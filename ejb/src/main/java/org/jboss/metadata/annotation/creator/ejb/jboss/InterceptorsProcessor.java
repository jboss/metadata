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

import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.ExcludeDefaultInterceptors;
import javax.interceptor.Interceptors;

import org.jboss.annotation.javaee.Descriptions;
import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.EjbProcessorUtils;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.spec.InterceptorBindingMetaData;
import org.jboss.metadata.ejb.spec.InterceptorBindingsMetaData;
import org.jboss.metadata.ejb.spec.InterceptorClassesMetaData;
import org.jboss.metadata.ejb.spec.MethodParametersMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;

/**
 * @Interceptors processor
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision: 67323 $
 */
public class InterceptorsProcessor<T extends AnnotatedElement>
   extends AbstractFinderUser
   implements Processor<InterceptorBindingsMetaData, T>
{
   public InterceptorsProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public void process(InterceptorBindingsMetaData metaData, T type)
   {
      Interceptors interceptors = finder.getAnnotation(type, Interceptors.class);
      if(interceptors == null)
         return;
      ExcludeClassInterceptors excludeClass = finder.getAnnotation(type, ExcludeClassInterceptors.class);
      ExcludeDefaultInterceptors excludeDefaults = finder.getAnnotation(type, ExcludeDefaultInterceptors.class);

      Method method = null;
      if(type instanceof Method)
         method = (Method) type;

      String ejbName = EjbNameThreadLocal.ejbName.get();
      InterceptorBindingMetaData interceptor = new InterceptorBindingMetaData();
      interceptor.setEjbName(ejbName);
      interceptor.setExcludeClassInterceptors(excludeClass != null);
      interceptor.setExcludeDefaultInterceptors(excludeDefaults != null);
      if(method != null)
      {
         NamedMethodMetaData namedMethod = new NamedMethodMetaData();
         namedMethod.setMethodName(method.getName());
         MethodParametersMetaData methodParams = EjbProcessorUtils.getMethodParameters(method);
         namedMethod.setMethodParams(methodParams);
         interceptor.setMethod(namedMethod);
      }
      InterceptorClassesMetaData classes = new InterceptorClassesMetaData();
      for(Class c : interceptors.value())
      {
         classes.add(c.getName());
      }
      interceptor.setInterceptorClasses(classes);

      Descriptions descriptions = ProcessorUtils.getDescription("@Interceptors for: "+type);
      interceptor.setDescriptions(descriptions);
      metaData.add(interceptor);
   }

   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(Interceptors.class);
   }
}
