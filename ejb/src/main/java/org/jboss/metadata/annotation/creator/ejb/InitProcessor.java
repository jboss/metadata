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

import javax.ejb.Init;

import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Creator;
import org.jboss.metadata.annotation.creator.EjbProcessorUtils;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.spec.InitMethodMetaData;
import org.jboss.metadata.ejb.spec.InitMethodsMetaData;
import org.jboss.metadata.ejb.spec.MethodParametersMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionType;

/**
 * Process an init annotation.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 76002 $
 */
public class InitProcessor extends AbstractFinderUser implements Creator<Method, InitMethodMetaData>, Processor<SessionBeanMetaData, Method>
{
   public InitProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }
   
   public InitMethodMetaData create(Method method)
   {
      Init init = finder.getAnnotation(method, Init.class);
      if(init == null)
         return null;
      
      InitMethodMetaData metaData = new InitMethodMetaData();
      NamedMethodMetaData beanMethod = new NamedMethodMetaData();
      metaData.setBeanMethod(beanMethod);
      NamedMethodMetaData createMethod = new NamedMethodMetaData();
      metaData.setCreateMethod(createMethod);
      
      String alternativeName = init.value().length() > 0 ? init.value() : method.getName();
      
      // Is the init declared on the home interface?
      if(method.getDeclaringClass().isInterface())
      {
         beanMethod.setMethodName(alternativeName);
         createMethod.setMethodName(method.getName());
      }
      else
      {
         beanMethod.setMethodName(method.getName());
         createMethod.setMethodName(alternativeName);
      }
      
      MethodParametersMetaData methodParams = EjbProcessorUtils.getMethodParameters(method);
      beanMethod.setMethodParams(methodParams);
      createMethod.setMethodParams(methodParams);
      
      return metaData;
   }
   
   public void process(SessionBeanMetaData bean, Method method)
   {
      InitMethodMetaData initMethod = create(method);
      if(initMethod == null)
         return;
      
      if(bean.getSessionType() != SessionType.Stateful)
      {
         throw new IllegalArgumentException("Bean with name: " + bean.getEjbName() + " , ejb-class: "
               + bean.getEjbClass() + " is not stateful. Init annotation is only valid on a stateful bean.");
      }
      
      if(bean.getInitMethods() == null)
         bean.setInitMethods(new InitMethodsMetaData());
      
      bean.getInitMethods().add(initMethod);
   }
   
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(Init.class);
   }
}
