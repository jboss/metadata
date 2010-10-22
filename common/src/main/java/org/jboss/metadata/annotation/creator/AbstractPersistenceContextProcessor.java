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
package org.jboss.metadata.annotation.creator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.Set;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceProperty;

import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.spec.PropertiesMetaData;
import org.jboss.metadata.javaee.spec.PropertyMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;

/**
 * Base class for @PersistenceContext processors
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision: 76756 $
 */
public abstract class AbstractPersistenceContextProcessor<E extends AnnotatedElement>
   extends AbstractInjectionTargetProcessor<E>
{
   /**
    * @param finder
    */
   public AbstractPersistenceContextProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public void process(PersistenceContextReferencesMetaData refs, E element)
   {
      PersistenceContext annotation = finder.getAnnotation(element, PersistenceContext.class);
      if(annotation == null)
         return;

      process(refs, element, annotation);
   }

   protected void process(PersistenceContextReferencesMetaData refs, E element, PersistenceContext annotation)
   {
      PersistenceContextReferenceMetaData ref = createPC(element, annotation);
      addReference(refs, ref);
   }

   protected PersistenceContextReferenceMetaData createPC(E element, PersistenceContext annotation)
   {
      PersistenceContextReferenceMetaData ref = new PersistenceContextReferenceMetaData();
      String name = annotation.name();
      if(name.length() == 0)
         name = ProcessorUtils.getName(element);
      ref.setPersistenceContextRefName(name);
      if(annotation.unitName().length() > 0)
         ref.setPersistenceUnitName(annotation.unitName());
      PersistenceContextType type = annotation.type();
      ref.setPersistenceContextType(type);

      PersistenceProperty[] properties = annotation.properties();
      if(properties.length > 0 )
      {
         PropertiesMetaData refProperties = new PropertiesMetaData();
         for(PersistenceProperty p : properties)
         {
            PropertyMetaData pmd = new PropertyMetaData();
            pmd.setName(p.name());
            pmd.setValue(p.value());
            refProperties.add(pmd);
         }
         ref.setProperties(refProperties);
      }

      String injectionName = ProcessorUtils.getName(element);
      Set<ResourceInjectionTargetMetaData> injectionTargets = ProcessorUtils.getInjectionTargets(injectionName, element);
      if(injectionTargets != null)
         ref.setInjectionTargets(injectionTargets);

      return ref;
   }
   
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(PersistenceContext.class);
   }
}
