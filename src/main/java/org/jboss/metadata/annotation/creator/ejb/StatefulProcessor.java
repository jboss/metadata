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
import java.util.Collection;

import javax.ejb.Stateful;

import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.creator.ejb.jboss.AccessTimeoutClassProcessor;
import org.jboss.metadata.annotation.creator.ejb.jboss.AccessTimeoutMethodProcessor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionType;

/**
 * Create the correct meta data for a stateful annotation.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 76002 $
 */
public class StatefulProcessor extends AbstractSessionBeanProcessor
{
   public StatefulProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
      addMethodProcessor(new RemoveProcessor(finder));
      addMethodProcessor(new PostActivateMethodProcessor(finder));
      addMethodProcessor(new PrePassivateMethodProcessor(finder));
      // concurrency management annotation is applicable for stateful bean
      addTypeProcessor(new ConcurrencyManagementProcessor(finder));
      addTypeProcessor(new AccessTimeoutClassProcessor(finder));
      
      addMethodProcessor(new AccessTimeoutMethodProcessor(finder));
   }

   public SessionBeanMetaData create(Class<?> beanClass)
   {
      Stateful annotation = finder.getAnnotation(beanClass, Stateful.class);
      if(annotation == null)
         return null;
      
      SessionBeanMetaData beanMetaData = create(beanClass, annotation);
      beanMetaData.setSessionType(SessionType.Stateful);
      return beanMetaData;
   }
   
   protected SessionBeanMetaData create(Class<?> beanClass, Stateful annotation)
   {
      return create(beanClass, annotation.name(), annotation.mappedName(), annotation.description());
   }
   
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(Stateful.class);
   }
}
