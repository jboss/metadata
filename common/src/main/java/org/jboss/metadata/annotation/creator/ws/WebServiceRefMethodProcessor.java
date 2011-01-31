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
package org.jboss.metadata.annotation.creator.ws;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;

/**
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class WebServiceRefMethodProcessor
   extends AbstractWebServiceRefProcessor<Method>
   implements Processor<ServiceReferencesMetaData, Method>
{

   public WebServiceRefMethodProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   @Override
   protected String getName(Method element)
   {
      String name = element.getName().substring(3);
      if (name.length() > 1)
      {
         name = name.substring(0, 1).toLowerCase() + name.substring(1);
      }
      else
      {
         name = name.toLowerCase();
      }
      // declaringClass / name
      return element.getDeclaringClass().getName() + "/" + name;
   }

   @Override
   protected String getInjectionName(Method element)
   {
      return element.getName();
   }

   @Override
   protected String getType(Method element)
   {
      if(element.getParameterTypes().length != 1)
         throw new IllegalStateException("The method requires one parameter: "+ element);
      
      return element.getParameterTypes()[0].getName();
   }

   @Override
   protected String getDeclaringClass(Method element)
   {
      return element.getDeclaringClass().getName();
   }
}
