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

import javax.annotation.Resource;
import javax.annotation.Resources;

import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.RemoteEnvironmentRefsGroupMetaData;

/**
 * If the annotation is applied to a class, the annotation declares a resource
 * that the application will look up at runtime. Even though this annotation is
 * not marked Inherited, if used all superclasses MUST be examined to discover
 * all uses of this annotation. All such annotation instances specify resources
 * that are needed by the application. Note that this annotation may appear on
 * private fields and methods of the superclasses. Injection of the declared
 * resources needs to happen in these cases as well, even if a method with such
 * an annotation is overridden by a subclass.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision: 76002 $
 */
public class ResourcesClassProcessor
   extends ResourceClassProcessor
   implements Processor<RemoteEnvironmentRefsGroupMetaData, Class>
{
   /**
    * @param finder
    */
   public ResourcesClassProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   @Override
   public void process(RemoteEnvironmentRefsGroupMetaData metaData, Class type)
   {
      Resources annotation = finder.getAnnotation(type, Resources.class);
      if(annotation == null)
         return;

      Resource[] resources = annotation.value();
      if(resources != null)
      {
         for(Resource res : resources)
            super.process(metaData, type, res);
      }
   }

   @Override
   protected String getName(Class element)
   {
      String name = element.getSimpleName();
      return name;
   }
   @Override
   protected String getType(Class element)
   {
      return element.getName();
   }
   @Override
   protected String getDeclaringClass(Class element)
   {
      return element.getName();
   }
   
   @Override
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(Resources.class);
   }

}
