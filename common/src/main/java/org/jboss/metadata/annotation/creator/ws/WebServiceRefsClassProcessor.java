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

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import javax.xml.ws.WebServiceRef;
import javax.xml.ws.WebServiceRefs;

import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;

/**
 * @WebServiceRefs processor
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class WebServiceRefsClassProcessor 
   extends WebServiceRefClassProcessor
   implements Processor<ServiceReferencesMetaData, Class<?>>
{

   public WebServiceRefsClassProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }
   
   @Override
   public void process(ServiceReferencesMetaData refsMetaData, Class<?> type)
   {
      WebServiceRefs annotation = finder.getAnnotation(type, WebServiceRefs.class);
      if(annotation == null)
         return;
      
      WebServiceRef[] references = annotation.value();
      if(references != null)
      {
         for(WebServiceRef reference : references)
            super.process(refsMetaData, type, reference);
      }
   }

   @Override
   protected String getName(Class<?> element)
   {
      String name = element.getSimpleName();
      return name;
   }

   @Override
   protected String getType(Class<?> element)
   {
      return element.getName();
   }

   @Override
   protected String getDeclaringClass(Class<?> element)
   {
      return element.getName();
   }

   @Override
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(WebServiceRefs.class);
   }
}
