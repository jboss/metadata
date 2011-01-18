/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors as indicated
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

import javax.interceptor.AroundInvoke;

import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Creator;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokeMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokesMetaData;

/**
 * Process an AroundInvoke annotation.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 67314 $
 */
public class AroundInvokeProcessor extends AbstractFinderUser
   implements Creator<Method, AroundInvokeMetaData>,
   Processor<JBossSessionBeanMetaData, Method>
{
   public AroundInvokeProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }
   
   public AroundInvokeMetaData create(Method method)
   {
      AroundInvoke init = finder.getAnnotation(method, AroundInvoke.class);
      if(init == null)
         return null;
      
      AroundInvokeMetaData metaData = new AroundInvokeMetaData();
      String className = method.getDeclaringClass().getName();
      metaData.setClassName(className);
      metaData.setMethodName(method.getName());
      return metaData;
   }
   
   public void process(JBossSessionBeanMetaData bean, Method method)
   {
      AroundInvokeMetaData metaData = create(method);
      if(metaData == null)
         return;

      AroundInvokesMetaData invokes = bean.getAroundInvokes();
      if(invokes == null)
      {
         invokes = new AroundInvokesMetaData();
         bean.setAroundInvokes(invokes);
      }
      invokes.add(metaData);
   }
   
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(AroundInvoke.class);
   }
}
