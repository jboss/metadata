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
package org.jboss.metadata.annotation.creator.ejb;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collection;

import javax.ejb.Remove;

import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Creator;
import org.jboss.metadata.annotation.creator.EjbProcessorUtils;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.spec.MethodParametersMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.ejb.spec.RemoveMethodMetaData;
import org.jboss.metadata.ejb.spec.RemoveMethodsMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionType;

/**
 * Process @Remove annotation.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 76002 $
 */
public class RemoveProcessor extends AbstractFinderUser
   implements Creator<Method, RemoveMethodMetaData>,
   Processor<SessionBeanMetaData, Method>
{
   public RemoveProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }
   
   public RemoveMethodMetaData create(Method method)
   {
      Remove remove = finder.getAnnotation(method, Remove.class);
      if(remove == null)
         return null;
      
      RemoveMethodMetaData metaData = new RemoveMethodMetaData();
      NamedMethodMetaData beanMethod = new NamedMethodMetaData();
      beanMethod.setMethodName(method.getName());
      metaData.setBeanMethod(beanMethod);
      metaData.setRetainIfException(remove.retainIfException());

      MethodParametersMetaData methodParams = EjbProcessorUtils.getMethodParameters(method);
      beanMethod.setMethodParams(methodParams);
      
      return metaData;
   }
   
   public void process(SessionBeanMetaData bean, Method method)
   {
      RemoveMethodMetaData removeMethod = create(method);
      if(removeMethod == null)
         return;
      
      if(bean.getSessionType() != SessionType.Stateful)
         throw new IllegalArgumentException("Remove annotation is only valid on a stateful bean");
      
      if(bean.getRemoveMethods() == null)
         bean.setRemoveMethods(new RemoveMethodsMetaData());
      
      bean.getRemoveMethods().add(removeMethod);
   }
   
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(Remove.class);
   }
}
