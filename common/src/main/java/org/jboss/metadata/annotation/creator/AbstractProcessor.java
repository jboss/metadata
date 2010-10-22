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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.finder.AnnotationFinder;

/**
 * A abstract base processor.
 * 
 * @author Scott.Stark@jboss.org
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public abstract class AbstractProcessor<MD> extends AbstractFinderUser
{

   /** Map<Processor metdata type class, List<Processor for classes>> */
   private Map<Class<?>, List<Processor<Object, Class<?>>>> typeProcessors;
   
   /** Map<Processor metdata type class, List<Processor for fields>> */
   private Map<Class<?>, List<Processor<Object, Field>>> fieldProcessors;
   
   /** Map<Processor metdata type class, List<Processor for methods>> */
   private Map<Class<?>, List<Processor<Object, Method>>> methodProcessors;
   
   /** The map of processed Annotations */
   private Map<Scope, Set<Class<? extends Annotation>>> processedAnnotations;
   
   /** The processed types. */
   private Set<Class<?>> processedTypes = new HashSet<Class<?>>();
   
   /** The logger */
   private final static Logger log = Logger.getLogger(AbstractProcessor.class);

   /**
    * The constructor.
    * 
    * @param finder the AnnotationFinder
    */
   public AbstractProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
      //
      typeProcessors = new HashMap<Class<?>, List<Processor<Object,Class<?>>>>();
      fieldProcessors = new HashMap<Class<?>, List<Processor<Object,Field>>>();
      methodProcessors = new HashMap<Class<?>, List<Processor<Object,Method>>>();
      processedAnnotations = new HashMap<Scope, Set<Class<? extends Annotation>>>();
   }

   /**
    * Add a Field processor.
    * 
    * @param processor Processor<Object, Field>
    */
   public void addFieldProcessor(Processor processor)
   {
      Class<?> processorType = getProcessorMetaDataType(processor, Scope.FIELD);
      if(log.isTraceEnabled())
         log.trace("addFieldProcessor: " + processor + ", for type: " + processorType);
      List<Processor<Object, Field>> processors = fieldProcessors.get(processorType);
      if(processors == null)
      {
         processors = new ArrayList<Processor<Object, Field>>();
         fieldProcessors.put(processorType, processors);
      }
      processors.add(processor);
   }

   /**
    * Add a method processor.
    * 
    * @param processor Processor<Object, Method>>
    */
   public void addMethodProcessor(Processor processor)
   {
      Class<?> processorType = getProcessorMetaDataType(processor, Scope.METHOD);
      if(log.isTraceEnabled())
         log.trace("addMethodProcessor: " + processor + ", for type: " + processorType);
      List<Processor<Object, Method>> processors = methodProcessors.get(processorType);
      if(processors == null)
      {
         processors = new ArrayList<Processor<Object, Method>>();
         methodProcessors.put(processorType, processors);
      }
      processors.add(processor);
   }
   
   /**
    * Add a type processor.
    * 
    * @param processor Processor<Object, Class<?>>
    */
   public void addTypeProcessor(Processor processor)
   {
      Class<?> processorType = getProcessorMetaDataType(processor, Scope.TYPE);
      if(log.isTraceEnabled())
         log.trace("addTypeProcessor: " + processor + ", for type: " + processorType);
      List<Processor<Object, Class<?>>> processors = typeProcessors.get(processorType);
      if(processors == null)
      {
         processors = new ArrayList<Processor<Object, Class<?>>>();
         typeProcessors.put(processorType, processors);
      }
      processors.add(processor);
   }
   
   /**
    * Process type for component meta data related annotations
    * 
    * @param metaData
    * @param type
    */
   public void process(MD metaData, Class<?> type)
   {
      processClass(metaData, type);
   }

   /**
    * Process a the cls annotations at the type, method and field levels
    * into the argument metaData. Only processors registered for the metaData
    * type will be run.
    * 
    * @param <T>
    * @param metaData
    * @param cls
    */
   protected <T> void processClass(T metaData, Class<?> cls)
   {
      Class<?> type = metaData.getClass();
      boolean trace = log.isTraceEnabled();

      // See if we process this type
      while(type != Object.class)
      {
         if(processedTypes.contains(type))
         {
            int processorCount = processClass(metaData, cls, type);
            if(trace)
               log.trace("Found "+processorCount+" processors for type: "+type);
         }
         // Also process each superClass of the meta data
         type = type.getSuperclass();
      }
      
      // Also process the interfaces of the meta data class 
      for(Class<?> iface : metaData.getClass().getInterfaces())
      {
         if(processedTypes.contains(iface));
         {
            processClass(metaData, cls, iface);
         }
      }
   }
   
   /**
    * Process the class and superClasses, based on their processorType.
    * 
    * @param <T>
    * @param metaData the metadata
    * @param cls the class
    * @param processorType the processed metadata class
    * @return
    */
   protected <T> int processClass(T metaData, Class<?> cls, Class processorType)
   {
      boolean trace = log.isTraceEnabled();
      int processorCount = 0;
      if(trace)
         log.trace("processClass for metaData: " + processorType + ", class: " + cls);

      List<Processor<Object, Class<?>>> tps = typeProcessors.get(processorType);
      if(tps != null)
      {
         processorCount += tps.size();
         if(trace)
            log.trace("typeProcessors(" + tps.size() + ") for metaData: "+tps);
         // Process class
         for(Processor<Object, Class<?>> processor : tps)
         {
            processor.process(metaData, cls);
         }
         // Process interfaces
         for(Class<?> intf : cls.getInterfaces())
         {
            for(Processor<Object, Class<?>> processor : tps)
            {
               processor.process(metaData, intf);
            }
         }
      }
      
      List<Processor<Object, Field>> fps = fieldProcessors.get(processorType);
      if(fps != null)
      {
         processorCount += fps.size();
         if(trace)
            log.trace("fieldProcessors(" + fps.size() + ") for metaData: " + fps);
         Field[] fields = {};
         try
         {
            fields = cls.getDeclaredFields();
         }
         catch(Throwable e)
         {
            log.debug("Failed to get DeclaredFields for: " + cls, e);
         }
         // Process fields
         for(Field field : fields)
         {
            for(Processor<Object, Field> processor : fps)
            {
               processor.process(metaData, field);
            }
         }
      }

      List<Processor<Object, Method>> mps = methodProcessors.get(processorType);
      if(mps != null)
      {
         processorCount += mps.size();
         if(trace)
            log.trace("methodProcessors(" + mps.size() + ") for metaData: " + mps);
         Method[] methods = {};
         try
         {
            methods = cls.getDeclaredMethods();
         }
         catch(Throwable e)
         {
            log.debug("Failed to get DeclaredMethods for: " + cls, e);            
         }
         // Process methods
         for(Method method : methods)
         {
            if(trace)
               log.trace("process method " + method);
            for(Processor<Object, Method> processor : mps)
            {
               processor.process(metaData, method);
            }
         }
      }
      
      // Process superclass
      if(cls.getSuperclass() != null && cls.getSuperclass() != Object.class)
         processorCount += processClass(metaData, cls.getSuperclass(), processorType);
      return processorCount;
   }

   /**
    * Determine the Processor<T, ?> T generic processorType class.
    * 
    * @param processor
    * @return The Class for the T parameter type.
    */
   private Class<?> getProcessorMetaDataType(Processor processor, Scope scope)
   {
      // Find the Proccessor<T, ?> interface
      Type[] interfaces = processor.getClass().getGenericInterfaces();
      Type processorType = null;
      for(Type t : interfaces)
      {
         ParameterizedType pt = (ParameterizedType) t;
         Type rawType = pt.getRawType();
         if((rawType instanceof Class) && ((Class<?>)rawType).getName().equals(Processor.class.getName()))
         {
            processorType = t;
            break;
         }
      }
      if(processorType == null)
         throw new IllegalStateException("No generic Processor interface found on: "+processor);

      // Get the type of the T parameter
      ParameterizedType pt = (ParameterizedType) processorType;
      Type t0 = pt.getActualTypeArguments()[0];
      Class<?> t = null;
      if(t0 instanceof Class)
         t = (Class<?>) t0;
      else if(t0 instanceof TypeVariable)
      {
         TypeVariable tv = (TypeVariable) t0;
         t = (Class<?>)tv.getBounds()[0];
      }
      
      // Add processed annotations
      addProcessedAnnotations(scope, processor.getAnnotationTypes());
      
      // Add processor to boundedTypes
      if(! processedTypes.contains(t))
         processedTypes.add(t);
      
      return t;
   }
   
   /**
    * Add processed annotations to a specific scope.
    * 
    * @param scope the Scope
    * @param annotations the processed annotations
    */
   private void addProcessedAnnotations(Scope scope, Collection<Class<? extends Annotation>> annotations)
   {
      if(annotations != null && annotations.size() > 0)
      {
         if(this.processedAnnotations.get(scope) == null)
            this.processedAnnotations.put(scope, new HashSet<Class<? extends Annotation>>());
           
         this.processedAnnotations.get(scope).addAll(annotations);
      }
   }
   
   /**
    * Get annotations for a given scope.
    * 
    * @param scope the Scope
    * @return the processed annotations
    */
   public Collection<Class<? extends Annotation>> getProcessedAnnotations(Scope scope)
   {
      return this.processedAnnotations.get(scope);
   }
   
   /**
    * Get a set of all processed annotations handled by this processor.
    * 
    * @return processed annotations
    */
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      // Merge processed annotations from all scopes 
      Set<Class<? extends Annotation>> set = new HashSet<Class<? extends Annotation>>();
      if(getProcessedAnnotations(Scope.TYPE) != null)
         set.addAll(getProcessedAnnotations(Scope.TYPE));
      if(getProcessedAnnotations(Scope.METHOD) != null)
         set.addAll(getProcessedAnnotations(Scope.METHOD));
      if(getProcessedAnnotations(Scope.FIELD) != null)
         set.addAll(getProcessedAnnotations(Scope.FIELD ));
      return set;
   }
   
   public static enum Scope
   {
      TYPE, METHOD, FIELD
   }

}
