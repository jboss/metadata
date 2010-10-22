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
import java.util.Set;

import javax.persistence.PersistenceUnit;

import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;

/**
 * @PersistenceUnit processor
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public abstract class AbstractPersistenceUnitProcessor<E extends AnnotatedElement>
   extends AbstractInjectionTargetProcessor<E>
{

   protected AbstractPersistenceUnitProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }
   
   public void process(PersistenceUnitReferencesMetaData metaData, E element)
   {
      PersistenceUnit annotation = finder.getAnnotation(element, PersistenceUnit.class);
      if(annotation == null)
         return;
      
      process(metaData, element, annotation);
   }
   
   protected void process(PersistenceUnitReferencesMetaData refs, E element, PersistenceUnit annotation)
   {
      PersistenceUnitReferenceMetaData ref = createPU(element, annotation);
      addReference(refs, ref);
   }
   
   protected abstract String getName(E element);
   protected abstract String getInjectionName(E element);
   
   protected PersistenceUnitReferenceMetaData createPU(E element, PersistenceUnit annotation)
   {
      PersistenceUnitReferenceMetaData ref = new PersistenceUnitReferenceMetaData();
      String name = annotation.name();
      if(name.length() == 0)
         name = getName(element);
      ref.setPersistenceUnitRefName(name);
      if(annotation.unitName().length() != 0)
         ref.setPersistenceUnitName(annotation.unitName());
      
      String injectionName = getInjectionName(element);
      Set<ResourceInjectionTargetMetaData> injectionTargets = ProcessorUtils.getInjectionTargets(injectionName, element);
      if(injectionTargets != null)
         ref.setInjectionTargets(injectionTargets);

      return ref;
   }
   
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(PersistenceUnit.class);
   }
}

