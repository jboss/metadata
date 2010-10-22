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

import javax.ejb.EJB;

import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.DescriptionImpl;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 80150 $
 */
public abstract class AbstractEJBProcessor<E extends AnnotatedElement>
   extends AbstractInjectionTargetProcessor<E>
{
   /**
    * @param finder
    */
   public AbstractEJBProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public void process(AnnotatedEJBReferencesMetaData refs, E element)
   {
      EJB annotation = finder.getAnnotation(element, EJB.class);
      if(annotation == null)
         return;

      process(refs, element, annotation);
   }

   protected void process(AnnotatedEJBReferencesMetaData refs, E element, EJB annotation)
   {
      AnnotatedEJBReferenceMetaData ref = createEJB(annotation, element);
      addReference(refs, ref);
   }

   /**
    * Get the ejb name based on the AnnotatedElement
    * @param element
    * @return
    */
   protected abstract String getName(E element);
   /**
    * Get the business interface class based on the AnnotatedElement
    * @param element
    * @return
    */
   protected abstract Class getType(E element);

   protected AnnotatedEJBReferenceMetaData createEJB(EJB annotation, E element)
   {
      AnnotatedEJBReferenceMetaData ref = new AnnotatedEJBReferenceMetaData();
      if(annotation.name().length() > 0)
         ref.setEjbRefName(annotation.name());
      else
         ref.setEjbRefName(getName(element));
      if(annotation.beanInterface() != Object.class)
         ref.setBeanInterface(annotation.beanInterface());
      else
         ref.setBeanInterface(getType(element));
      if(annotation.description().length() > 0)
      {
         DescriptionImpl description = new DescriptionImpl();
         description.setDescription(annotation.description());
         DescriptionsImpl descriptions = new DescriptionsImpl();
         descriptions.add(description);
         ref.setDescriptions(descriptions);
      }
      if(annotation.beanName().length() > 0)
         ref.setLink(annotation.beanName());
      if(annotation.mappedName().length() > 0)
         ref.setMappedName(annotation.mappedName());

      String name = ProcessorUtils.getName(element);
      Set<ResourceInjectionTargetMetaData> injectionTargets = ProcessorUtils.getInjectionTargets(name, element);
      if(injectionTargets != null)
         ref.setInjectionTargets(injectionTargets);

      return ref;
   }
   
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(EJB.class);
   }
}
