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
import java.util.Collection;

import org.jboss.metadata.annotation.creator.AbstractCreator;
import org.jboss.metadata.annotation.creator.Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.spec.InterceptorsMetaData;

/**
 * An implementation of {@link Creator} which is responsible for creating 
 * {@link InterceptorsMetaData} from annotated classes
 *<p>
 *  This {@link InterceptorMetaDataCreator} passes the classes through a 
 *  series of annotation processors to create the {@link InterceptorsMetaData}
 *</p>
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class InterceptorMetaDataCreator extends AbstractCreator<InterceptorsMetaData>
      implements
         Creator<Collection<Class<?>>, InterceptorsMetaData>
{

   /**
    * {@inheritDoc}
    * @param finder
    */
   public InterceptorMetaDataCreator(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);

      // add a InterceptorComponentProcessor which is responsible for processing
      // (with the help of type/method/field annotaiton processors) annotations
      // on the interceptor classes
      this.addProcessor(new InterceptorComponentProcessor<InterceptorsMetaData>(finder));
   }

   /**
    * Returns {@link InterceptorsMetaData} after processing the <code>interceptorClasses</code>
    * 
    * @param interceptorClasses The interceptor classes which are to be processed for
    * annotations
    * @see org.jboss.metadata.annotation.creator.AbstractCreator#create(java.util.Collection)
    */
   @Override
   public InterceptorsMetaData create(Collection<Class<?>> interceptorClasses)
   {
      InterceptorsMetaData interceptorsMetaData = new InterceptorsMetaData();
      if (interceptorClasses == null)
      {
         return interceptorsMetaData;
      }
      // process the interceptor classes for annotations
      this.processMetaData(interceptorClasses, interceptorsMetaData);

      // return the metadata populated after processing the interceptor classes
      return interceptorsMetaData;
   }

   /**
    * @see org.jboss.metadata.annotation.creator.AbstractCreator#validateClass(java.lang.Class)
    */
   @Override
   protected boolean validateClass(Class<?> clazz)
   {
      // TODO: Hmm, what really is this method? Not much info available about this method
      return true;
   }

}
