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
package org.jboss.metadata.annotation.creator.ws;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 72354 $
 */
public class WebServiceRefFieldProcessor
   extends AbstractWebServiceRefProcessor<Field>
   implements Processor<ServiceReferencesMetaData, Field>
{

   /**
    * @param finder
    */
   public WebServiceRefFieldProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   @Override
   protected String getName(Field element)
   {
      String name = element.getName();
      // declaringClass / name
      return element.getDeclaringClass().getName() + "/" + name;
   }
   @Override
   protected String getInjectionName(Field element)
   {
      return element.getName();
   }
   @Override
   protected String getType(Field element)
   {
      return element.getType().getName();
   }
   @Override
   protected String getDeclaringClass(Field element)
   {
      return element.getDeclaringClass().getName();
   }

}
