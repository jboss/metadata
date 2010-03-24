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
package org.jboss.metadata.annotation.creator.ejb;

import java.lang.reflect.AnnotatedElement;

import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.jboss.AbstractComponentProcessor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.spec.InterceptorMetaData;
import org.jboss.metadata.ejb.spec.InterceptorsMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;

/**
 * An implementation of {@link Processor} which is responsible for
 * processing annotations which are applicable for interceptor classes.
 * This {@link InterceptorComponentProcessor} sets up the {@link InterceptorsMetaData}
 *  during the annotation processing
 *  
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class InterceptorComponentProcessor<MD extends InterceptorsMetaData> extends AbstractComponentProcessor<MD>
      implements
         Processor<MD, Class<?>>
{

   /**
    * @param finder
    */
   public InterceptorComponentProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);

      // TODO: We need similar processors for InterceptorMetaData (the following currently
      // work with JBossSessionBeanMetaData)
      //      this.addMethodProcessor(new AroundInvokeProcessor(finder));
      //
      //      this.addMethodProcessor(new PostActivateMethodProcessor(finder));
      //      this.addMethodProcessor(new PrePassivateMethodProcessor(finder));

   }

   /**
    * Process the passed <code>interceptorClass</code> for annotations and 
    * creates {@link InterceptorMetaData} for that class
    * 
    * @param interceptorClass The interceptor class being processed for annotations
    * @param interceptors {@link InterceptorsMetaData} which will be updated with
    *               appropriate {@link InterceptorMetaData} while processing the interceptor class
    */
   @Override
   public void process(MD interceptors, Class<?> interceptorClass)
   {
      // create an empty interceptor metadata for the interceptor class 
      InterceptorMetaData interceptor = this.createInterceptorMetaData(interceptorClass);
      // add it to the interceptors metadata
      interceptors.add(interceptor);

      // Now, time to process the interceptor class for the EnvironmentRefsGroupMetadata
      // which includes AnnotatedEjbReferences, PersistenceUnit references, @Resource references
      // etc...
      EnvironmentRefsGroupMetaData environmentRefsGroup = interceptor.getJndiEnvironmentRefsGroup();
      if (environmentRefsGroup == null)
      {
         environmentRefsGroup = new EnvironmentRefsGroupMetaData();
         interceptor.setJndiEnvironmentRefsGroup(environmentRefsGroup);
      }
      // process
      super.process(environmentRefsGroup, interceptorClass);

      // process @PersistenceContext
      PersistenceContextReferencesMetaData pcRefs = interceptor.getPersistenceContextRefs();
      if (pcRefs == null)
      {
         pcRefs = new PersistenceContextReferencesMetaData();
         environmentRefsGroup.setPersistenceContextRefs(pcRefs);
      }
      processClass(pcRefs, interceptorClass);
   }

   /**
    * Creates a {@link InterceptorMetaData} for the passed <code>interceptorClass</code>
    * @param interceptorClass
    * @return
    */
   private InterceptorMetaData createInterceptorMetaData(Class<?> interceptorClass)
   {
      InterceptorMetaData interceptor = new InterceptorMetaData();
      interceptor.setInterceptorClass(interceptorClass.getName());

      return interceptor;
   }
}
