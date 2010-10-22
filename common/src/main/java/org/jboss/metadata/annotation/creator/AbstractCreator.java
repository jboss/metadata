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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.metadata.annotation.creator.AbstractProcessor.Scope;
import org.jboss.metadata.annotation.finder.AnnotationFinder;

/**
 * A abstract base creator.
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */ 
public abstract class AbstractCreator<MD> extends AbstractFinderUser
{
   /** The Processors */
   private List<Processor<MD, Class<?>>> processors;
   
   /** The processed annotations */
   private Map<Scope, Set<Class<? extends Annotation>>> processedAnnotations;

   /**
    * The constructor.
    * 
    * @param finder the AnnotationFinder
    */
   protected AbstractCreator(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
      // 
      this.processors = new ArrayList<Processor<MD,Class<?>>>();
      this.processedAnnotations = new HashMap<Scope, Set<Class<? extends Annotation>>>();
   }

   /**
    * Abstract create method of a Creator<Collection<Class<?>, MD>.
    * 
    * @param element the classes
    * @return the metadata
    */
   public abstract MD create(Collection<Class<?>> classes);
   
   /**
    * Process the meta data.
    * 
    * @param classes the classes
    * @param metaData the meta data
    */
   protected <T extends MD> void processMetaData(Collection<Class<?>> classes, T metaData)
   {
      Collection<Class<?>> validatedClasses = validateClasses(classes);
      // Don't create meta data for a empty collection
      if(validatedClasses.isEmpty())
         return;

      // Process the meta data
      for(Class<?> clazz : validatedClasses)
         process(metaData, clazz);
   }
   
   /**
    * Check a class if it needs to be processed.
    * 
    * @param clazz the Class
    * @return if it needs to be further processed
    */
   protected abstract boolean validateClass(Class<?> clazz);

   
   /**
    * Check a set of classes if further processing is needed.
    * 
    * @param classes the classes
    * @return a collection of checked classes
    */
   protected Collection<Class<?>> validateClasses(Collection<Class<?>> classes)
   {
      Collection<Class<?>> validatedClasses = new HashSet<Class<?>>();
      for(Class<?> clazz : classes)
      {
         if(validateClass(clazz))
            validatedClasses.add(clazz);
      }
      return validatedClasses;
   }
   
   
   /**
    * Add a Class<?> processor.
    * 
    * @param processor
    * @throws IllegalArgumentException
    */
   @SuppressWarnings("unchecked")
   public void addProcessor(Processor<MD, Class<?>> processor)
   {
      if(processor == null)
         throw new IllegalArgumentException("null processor.");
      
      // Add annotations
      if(processor instanceof AbstractProcessor)
         addAnnotations((AbstractProcessor) processor);
      else
         addAnnotations(Scope.TYPE, processor.getAnnotationTypes());
      
      this.processors.add(processor);
   }

   /**
    * Process the meta data.
    * 
    * @param metaData the meta data
    * @param type a Class
    * @throws IllegalArgumentException
    */
   protected void process(MD metaData, Class<?> type)
   {
      if(metaData == null)
         throw new IllegalArgumentException("null metadata.");
      if(type == null)
         throw new IllegalArgumentException("null classes;");
      
      for(Processor<MD, Class<?>> processor : processors)
      {
         processor.process(metaData, type);
      }
   }
   
   /**
    * Add the annotations handled by an AbstractProcessor.
    * 
    * @param processor the abstract processor
    */
   @SuppressWarnings("unchecked")
   private void addAnnotations(AbstractProcessor processor)
   {
      addAnnotations(Scope.TYPE, processor.getProcessedAnnotations(Scope.TYPE));
      addAnnotations(Scope.METHOD, processor.getProcessedAnnotations(Scope.METHOD));
      addAnnotations(Scope.FIELD, processor.getProcessedAnnotations(Scope.FIELD));
   }
   
   /**
    * Add annotations to a specific Scope.
    * 
    * @param scope the scope
    * @param annotations a set of annotations
    */
   private void addAnnotations(Scope scope, Collection<Class<?extends Annotation>> annotations)
   {
      if(annotations == null)
         return;
         
     if(scope == null)
        throw new IllegalArgumentException("null scope.");
      
      if(this.processedAnnotations.get(scope) == null)
         this.processedAnnotations.put(scope, new HashSet<Class<? extends Annotation>>());
      
      this.processedAnnotations.get(scope).addAll(annotations);
   }
   
   /**
    * Get annotations for a given Scope.
    * 
    * @param scope the Scope
    * @return the processed annotations
    */
   protected Collection<Class<? extends Annotation>> getAnnotationsForScope(Scope scope)
   {
      return this.processedAnnotations.get(scope);
   }
   
   /**
    * Create a basic annotation context, based on the information of the added processors.
    * 
    * @return the annotation context
    */
   public AnnotationContext getAnnotationContext()
   {
      return new AnnotationContext()
      {
         public Collection<Class<? extends Annotation>> getFieldAnnotations()
         {
            return processedAnnotations.get(Scope.FIELD);
         }

         public Collection<Class<? extends Annotation>> getMethodAnnotations()
         {
            return processedAnnotations.get(Scope.METHOD);
         }

         public Collection<Class<? extends Annotation>> getTypeAnnotations()
         {
            return processedAnnotations.get(Scope.TYPE);
         }
      };
   }
}

