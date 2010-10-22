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
package org.jboss.metadata.annotation.creator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import javax.persistence.PersistenceUnit;
import javax.persistence.PersistenceUnits;

import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferencesMetaData;

/**
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class PersistenceUnitsClassProcessor extends PersistenceUnitClassProcessor
   implements Processor<PersistenceUnitReferencesMetaData, Class<?>>
{

   public PersistenceUnitsClassProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }
   
   @Override
   public void process(PersistenceUnitReferencesMetaData refs, Class<?> element)
   {
      PersistenceUnits annotation = finder.getAnnotation(element, PersistenceUnits.class);
      if(annotation == null)
         return;
      
      for(PersistenceUnit unit : annotation.value())
      {
         process(refs, element, unit);
      }
    }
   
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(PersistenceUnits.class);
   }
}

