/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors as indicated
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

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.Collections;

import org.jboss.metadata.annotation.creator.AbstractCreator;
import org.jboss.metadata.annotation.creator.AnnotationContext;
import org.jboss.metadata.annotation.creator.Creator;
import org.jboss.metadata.annotation.creator.AbstractProcessor.Scope;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.spec.EjbJar30MetaData;
import org.jboss.metadata.ejb.spec.EjbJar3xMetaData;

/**
 * Create the correct meta data for a set of annotated classes.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 79337 $
 */
@Deprecated
public class EjbJar30Creator extends AbstractCreator<EjbJar3xMetaData>
   implements Creator<Collection<Class<?>>, EjbJar30MetaData>
{
   /**
    * Create a new EjbJar30Creator.
    * 
    * @param finder the AnnotationFinder
    */
   public EjbJar30Creator(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
      //
      addProcessor(new StatefulProcessor(finder));
      addProcessor(new StatelessProcessor(finder));
      addProcessor(new MessageDrivenProcessor(finder));
      addProcessor(new ApplicationExceptionProcessor(finder));
   }
   
   /**
    * Create the meta data for a set of annotated classes.
    * 
    * @param classes
    */
   public EjbJar30MetaData create(Collection<Class<?>> classes)
   {
      // Don't create meta data for a empty collection
      if(classes == null || classes.isEmpty())
         return null;
      
      // Create meta data
      EjbJar30MetaData metaData = create();
      
      processMetaData(classes, metaData);
      
      return metaData;
   }
   
   protected EjbJar30MetaData create()
   {
      EjbJar30MetaData metaData = new EjbJar30MetaData();
      metaData.setVersion("3.0");
      return metaData;
   }
   
   protected boolean validateClass(Class<?> clazz)
   {
      // The AnnotationDeployer picks up only classes based on to the AnnotationContext, therefore no further validation 
      return true;
   }
   
   /**
    * Get the annotation context. This overrides the inherited method,
    * as we just need the Type annotations. e.g. @Stateful, @Stateless, @Service
    * 
    * @return the AnnotationContext
    */
   @Override
   public AnnotationContext getAnnotationContext()
   {
      return new AnnotationContext()
      {
         public Collection<Class<? extends Annotation>> getTypeAnnotations()
         {
            return getAnnotationsForScope(Scope.TYPE);
         }
         
         public Collection<Class<? extends Annotation>> getFieldAnnotations() { return Collections.EMPTY_SET; }

         public Collection<Class<? extends Annotation>> getMethodAnnotations() { return Collections.EMPTY_SET; }
         
      };
   }
}
