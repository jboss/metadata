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

import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;

/**
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 * @version $Revision$
 */
public class WebServiceRefClassProcessor
   extends AbstractWebServiceRefProcessor<Class<?>>
   implements Processor<ServiceReferencesMetaData, Class<?>>
{

   public WebServiceRefClassProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   @Override
   protected String getName(Class<?> element)
   {
      throw new IllegalStateException("@WebServiceRef annotation on type '"+ element.getName() +"' must define a name.");
   }

   @Override
   protected String getInjectionName(Class<?> element)
   {
      return null;
   }

   @Override
   protected String getType(Class<?> element)
   {
      throw new IllegalStateException("@WebServiceRef annotation on type '"+ element.getName() +"' must define a type.");
   }

   @Override
   protected String getDeclaringClass(Class<?> element)
   {
      return element.getName();
   }

}
