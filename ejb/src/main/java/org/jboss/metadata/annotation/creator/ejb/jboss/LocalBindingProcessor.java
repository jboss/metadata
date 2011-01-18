/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
import java.util.ArrayList;
import java.util.Collection;

import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.LocalBindingMetaData;

/**
 * The @LocalBinding processor.
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class LocalBindingProcessor
      extends AbstractFinderUser
      implements Processor<JBossSessionBeanMetaData, Class<?>>
{

   protected LocalBindingProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public void process(JBossSessionBeanMetaData metaData, Class<?> type)
   {
      LocalBinding annotation = finder.getAnnotation(type, LocalBinding.class);
      if(annotation == null)
         return;
      
      process(metaData, type, annotation);
   }
   
   protected void process(JBossSessionBeanMetaData metaData, Class<?> type, LocalBinding annotation)
   {
      if(metaData.getLocalBindings() == null)
         metaData.setLocalBindings(new ArrayList<LocalBindingMetaData>());
         
      LocalBindingMetaData localBinding = createLocalBindingMetaData(type, annotation);
      if(localBinding != null)
         metaData.getLocalBindings().add(localBinding);
   }
   
   protected LocalBindingMetaData createLocalBindingMetaData(Class<?> type, LocalBinding annotation)
   {
      if(annotation.jndiBinding().length() == 0)
         return null;

      LocalBindingMetaData metaData = new LocalBindingMetaData();
      // set JndiName
      metaData.setJndiName(annotation.jndiBinding());
      return metaData;
   }

   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(LocalBinding.class);
   }
}
