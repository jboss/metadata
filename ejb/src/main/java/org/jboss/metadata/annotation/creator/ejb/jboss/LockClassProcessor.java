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

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import javax.ejb.Lock;

import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossSessionBean31MetaData;

/**
 * Processes class level {@link Lock} annotation and creates metadata out of it
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class LockClassProcessor extends AbstractFinderUser implements Processor<JBossSessionBean31MetaData, Class<?>>
{

   /**
    * @param finder
    */
   public LockClassProcessor(AnnotationFinder<AnnotatedElement> finder)
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
   public void process(JBossSessionBean31MetaData metaData, Class<?> klass)
   {
      Lock lock = this.finder.getAnnotation(klass, Lock.class);

      if (lock == null)
      {
         return;
      }
      // EJB3.1 spec, section 4.8.5.5 specifies special meaning for @Lock annotation
      // on superclass(es) of the bean. So we have to keep track of which exact class
      // has the @Lock annotation
      metaData.setLockType(klass.getName(), lock.value());
   }

}
