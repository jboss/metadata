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
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;
import org.jboss.metadata.ejb.spec.AccessTimeoutMetaData;
import org.jboss.metadata.ejb.spec.ConcurrentMethodMetaData;
import org.jboss.metadata.ejb.spec.ConcurrentMethodsMetaData;
import org.jboss.metadata.ejb.spec.MethodParametersMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;

import javax.ejb.AccessTimeout;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Processes method level {@link AccessTimeout} annotation and creates metadata out of it
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class AccessTimeoutMethodProcessor extends AbstractFinderUser
      implements
         Processor<JBossSessionBean31MetaData, Method>
{

   /**
    * @param finder
    */
   public AccessTimeoutMethodProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);

   }

   /**
    * @see org.jboss.metadata.annotation.creator.Processor#getAnnotationTypes()
    */
   @Override
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(AccessTimeout.class);
   }

   /**
    * @see org.jboss.metadata.annotation.creator.Processor#process(java.lang.Object, java.lang.reflect.AnnotatedElement)
    */
   @Override
   public void process(JBossSessionBean31MetaData metaData, Method method)
   {
      AccessTimeout accessTimeout = this.finder.getAnnotation(method, AccessTimeout.class);

      if (accessTimeout == null)
      {
         return;
      }
      
      ConcurrentMethodsMetaData concurrentMethods = metaData.getConcurrentMethods();
      if(concurrentMethods == null)
      {
         concurrentMethods = new ConcurrentMethodsMetaData();
         metaData.setConcurrentMethods(concurrentMethods);
      }

      // create an AccessTimeoutMetaData
      AccessTimeoutMetaData accessTimeoutMetaData = new AccessTimeoutMetaData();
      accessTimeoutMetaData.setTimeout(accessTimeout.value());
      // set access timeout unit
      if (accessTimeout.unit() != null)
      {
         accessTimeoutMetaData.setUnit(accessTimeout.unit());
      }
      // this access timeout has to be applied to concurrency metadata applicable
      // for the method being processed. So let's create/get the concurrency metadata

      // create a named method metadata for this method
      NamedMethodMetaData namedMethod = new NamedMethodMetaData();
      namedMethod.setName(method.getName());
      MethodParametersMetaData methodParamsMetaData = new MethodParametersMetaData();
      for (Class<?> parameterType : method.getParameterTypes())
      {
         methodParamsMetaData.add(parameterType.getName());
      }
      namedMethod.setMethodParams(methodParamsMetaData);
      // get the concurrent method for this named method
      ConcurrentMethodMetaData concurrentMethod = metaData.getConcurrentMethods().find(namedMethod);
      if (concurrentMethod == null)
      {
         concurrentMethod = new ConcurrentMethodMetaData();
         // set the named method in the concurrent method metadata
         concurrentMethod.setMethod(namedMethod);
         metaData.getConcurrentMethods().add(concurrentMethod);
      }
      // set the access timeout metadata on the concurrent method metadata
      concurrentMethod.setAccessTimeout(accessTimeoutMetaData);

   }

}
