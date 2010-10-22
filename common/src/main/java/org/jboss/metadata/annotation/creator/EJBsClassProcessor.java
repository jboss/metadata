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

import javax.ejb.EJB;
import javax.ejb.EJBs;

import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferencesMetaData;

/**
 * Process a collection of EJB references
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision: 76002 $
 */
public class EJBsClassProcessor
   extends EJBClassProcessor
   implements Processor<AnnotatedEJBReferencesMetaData, Class>
{

   /**
    * @param finder
    */
   public EJBsClassProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public void process(AnnotatedEJBReferencesMetaData metaData, Class type)
   {
      EJBs annotation = finder.getAnnotation(type, EJBs.class);
      if(annotation == null)
         return;

      EJB[] ejbs = annotation.value();
      if(ejbs != null)
      {
         for(EJB ejb : ejbs)
            super.process(metaData, type, ejb);
      }
   }

   @Override
   protected String getName(Class element)
   {
      String name = element.getSimpleName();
      return name;
   }
   @Override
   protected Class getType(Class element)
   {
      return element;
   }
   
   @Override
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(EJBs.class);
   }

}
