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
import java.util.Collection;

import javax.ejb.ApplicationException;

import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Creator;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossAssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.spec.ApplicationExceptionMetaData;
import org.jboss.metadata.ejb.spec.ApplicationExceptionsMetaData;

/**
 * Process an application exception annotation.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 67165 $
 */
public class ApplicationExceptionProcessor extends AbstractFinderUser implements Creator<Class<?>, ApplicationExceptionMetaData>, Processor<JBossMetaData, Class<?>>
{
   public ApplicationExceptionProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public ApplicationExceptionMetaData create(Class<?> element)
   {
      ApplicationException annotation = finder.getAnnotation(element, ApplicationException.class);
      if(annotation == null)
         return null;
      
      if(!Exception.class.isAssignableFrom(element))
         throw new IllegalArgumentException("ApplicationException is only valid on an Exception");
      
      ApplicationExceptionMetaData metaData = new ApplicationExceptionMetaData();
      metaData.setExceptionClass(element.getName());
      metaData.setRollback(annotation.rollback());
      
      return metaData;
   }

   public void process(JBossMetaData ejbJar, Class<?> type)
   {
      ApplicationExceptionMetaData applicationException = create(type);
      if(applicationException == null)
         return;
      
      if(ejbJar.getAssemblyDescriptor() == null)
         ejbJar.setAssemblyDescriptor(new JBossAssemblyDescriptorMetaData());
      if(ejbJar.getAssemblyDescriptor().getApplicationExceptions() == null)
         ejbJar.getAssemblyDescriptor().setApplicationExceptions(new ApplicationExceptionsMetaData());
      
      ejbJar.getAssemblyDescriptor().getApplicationExceptions().add(applicationException);
   }
   
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(ApplicationException.class);
   }
}
