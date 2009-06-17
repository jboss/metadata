/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors as indicated
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

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.Set;

import javax.xml.ws.WebServiceRef;

import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.creator.AbstractInjectionTargetProcessor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;

/**
 * Base processor for @WebServiceRef annotations.
 * Delegates @HandlerChain to WebServiceHandlerChainProcessor.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision: 67548 $
 */
public abstract class AbstractWebServiceRefProcessor<E extends AnnotatedElement>
   extends AbstractInjectionTargetProcessor<E>
{
   /** The logger. */
   private static Logger log = Logger.getLogger(AbstractWebServiceRefProcessor.class);

   /** The WebServiceHandlerChainProcessor */
   private WebServiceHandlerChainProcessor<E> handlerChainProcessor;

   protected AbstractWebServiceRefProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
      // The handlerChainProcessor only needs to process the class if there is a @WebServiceRef
      handlerChainProcessor = new WebServiceHandlerChainProcessor<E>(finder);
   }
   
   public void process(ServiceReferencesMetaData refs, E element)
   {
      WebServiceRef annotation = finder.getAnnotation(element, WebServiceRef.class);
      if(annotation == null)
         return;
      process(refs, element, annotation);
   }
   
   protected void process(ServiceReferencesMetaData refs, E element, WebServiceRef annotation)
   {
      boolean trace = log.isTraceEnabled();
      String type = annotation.type().getName();
      if(type.equals("java.lang.Object"))
         type = getType(element);
      if(trace)
         log.trace("process: "+annotation+", type="+type);
      
      ServiceReferenceMetaData ref = createServiceRef(annotation, element);
      addReference(refs, ref);
      
      if(trace)
         log.trace("created service-ref: "+ref);
      
      /** Delegate @HandlerChain processing to the handlerChainProcessor */
      handlerChainProcessor.process(ref, element);
   }

   /**
    * Get the resource name based on the AnnotatedElement
    * @param element
    * @return
    */
   protected abstract String getName(E element);
   protected abstract String getInjectionName(E element);
   protected abstract String getType(E element);
   protected abstract String getDeclaringClass(E element);

   protected ServiceReferenceMetaData createServiceRef(WebServiceRef annotation, E element)
   {
      ServiceReferenceMetaData ref = new ServiceReferenceMetaData();
      String name = annotation.name();
      if(name.length() == 0)
         name = getName(element);
      ref.setServiceRefName(name);
      if(annotation.mappedName().length() > 0)
         ref.setMappedName(annotation.mappedName());
      ref.setAnnotatedElement(element);
      if(annotation.wsdlLocation().length() > 0)
         ref.setWsdlFile(annotation.wsdlLocation());
      if(annotation.type() != Object.class)
         ref.setServiceRefType(annotation.type().getName());
      else
         ref.setServiceRefType(getType(element));
      if(annotation.value() != Object.class)
         ref.setServiceInterface(annotation.value().getName());
      
      String injectionName = getInjectionName(element);
      Set<ResourceInjectionTargetMetaData> injectionTargets = ProcessorUtils.getInjectionTargets(injectionName, element);
      if(injectionTargets != null)
         ref.setInjectionTargets(injectionTargets);

      return ref;
   }
   
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(WebServiceRef.class);
   }
}
