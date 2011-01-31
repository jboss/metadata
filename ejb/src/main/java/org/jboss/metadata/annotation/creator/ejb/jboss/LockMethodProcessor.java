/*
* JBoss, Home of Professional Open Source
* Copyright 2005, JBoss Inc., and individual contributors as indicated
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

import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.EjbProcessorUtils;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;
import org.jboss.metadata.ejb.spec.ConcurrentMethodMetaData;
import org.jboss.metadata.ejb.spec.ConcurrentMethodsMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;

import javax.ejb.Lock;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Processes method level {@link Lock} annotation and creates metadata out of it
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class LockMethodProcessor extends AbstractFinderUser implements Processor<JBossSessionBean31MetaData, Method>
{

   /**
    * @param finder
    */
   public LockMethodProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   /**
    * @see org.jboss.metadata.annotation.creator.Processor#getAnnotationTypes()
    */
   @Override
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(Lock.class);
   }

   /**
    * @see org.jboss.metadata.annotation.creator.Processor#process(java.lang.Object, java.lang.reflect.AnnotatedElement)
    */
   @Override
   public void process(JBossSessionBean31MetaData metaData, Method method)
   {
      Lock lock = this.finder.getAnnotation(method, Lock.class);
      if (lock == null)
      {
         return;
      }

      ConcurrentMethodsMetaData concurrentMethods = metaData.getConcurrentMethods();
      if(concurrentMethods == null)
      {
         concurrentMethods = new ConcurrentMethodsMetaData();
         metaData.setConcurrentMethods(concurrentMethods);
      }
      
      // create a named method metadata for this method
      NamedMethodMetaData namedMethod = new NamedMethodMetaData();
      namedMethod.setName(method.getName());
      namedMethod.setMethodParams(EjbProcessorUtils.getMethodParameters(method));
      // get the concurrent method for this named method
      ConcurrentMethodMetaData concurrentMethod = metaData.getConcurrentMethods().find(namedMethod);
      if (concurrentMethod == null)
      {
         concurrentMethod = new ConcurrentMethodMetaData();
         // set the named method in the concurrent method metadata
         concurrentMethod.setMethod(namedMethod);
         metaData.getConcurrentMethods().add(concurrentMethod);
      }
      // if the lock type has been set on the annotation, then set it in the concurrent method metadata
      if (lock.value() != null)
      {
         concurrentMethod.setLockType(lock.value());
      }
   }

}
