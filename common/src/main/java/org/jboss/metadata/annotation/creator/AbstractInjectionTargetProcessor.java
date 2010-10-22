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

import java.lang.reflect.AnnotatedElement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.ResourceInjectionMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * The AbstractInjectionTargetProcessor.
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public abstract class AbstractInjectionTargetProcessor<E extends AnnotatedElement> extends AbstractFinderUser
{

   protected AbstractInjectionTargetProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   /**
    * Add a ResourceInjectionMetaData to a AbstractMappedMetaData, where the 
    * InjectionTargets are merged if a reference already exists with the same name.
    * 
    * @param <T extends ResourceInjectionMetaData>
    * @param refs the mapped meta data
    * @param ref the reference
    */
   protected <T extends ResourceInjectionMetaData> void addReference(AbstractMappedMetaData<T> refs, T ref)
   {
      T existingRef = refs.get(ref.getKey());
      if (existingRef == null)
      {
         refs.add(ref);
      }
      else
      {
         Set<ResourceInjectionTargetMetaData> injectionTargets = new HashSet<ResourceInjectionTargetMetaData>();
         if (existingRef.getInjectionTargets() != null && existingRef.getInjectionTargets().size() > 0)
         {
            for (ResourceInjectionTargetMetaData target : existingRef.getInjectionTargets())
               injectionTargets.add(target);
         }
         if (ref.getInjectionTargets() != null && ref.getInjectionTargets().size() > 0)
         {
            for (ResourceInjectionTargetMetaData target : ref.getInjectionTargets())
               injectionTargets.add(target);
         }
         if (injectionTargets.size() > 0)
            existingRef.setInjectionTargets(Collections.unmodifiableSet(injectionTargets));
      }
   }
}
