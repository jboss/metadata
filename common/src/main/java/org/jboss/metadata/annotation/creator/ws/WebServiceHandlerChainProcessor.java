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

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import javax.jws.HandlerChain;

import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.ServiceReferenceHandlerChainsMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.xb.binding.JBossXBException;
import org.jboss.xb.binding.Unmarshaller;
import org.jboss.xb.binding.UnmarshallerFactory;
import org.jboss.xb.binding.sunday.unmarshalling.DefaultSchemaResolver;

/**
 * @HandlerChain processor
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision $
 */
public class WebServiceHandlerChainProcessor<E extends AnnotatedElement>
      extends AbstractFinderUser
      implements Processor<ServiceReferenceMetaData, E>
{
   
   /** The logger. */
   private static Logger log = Logger.getLogger(WebServiceHandlerChainProcessor.class);

   /** The default schema resolver. */
   private static final DefaultSchemaResolver resolver;

   static
   {
      resolver = new DefaultSchemaResolver();
      resolver.addClassBinding("http://java.sun.com/xml/ns/javaee", ServiceReferenceHandlerChainsWrapper.class);
   }

   public WebServiceHandlerChainProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public void process(ServiceReferenceMetaData refs, E type)
   {
      HandlerChain annotation = finder.getAnnotation(type, HandlerChain.class);
      if (annotation == null)
         return;

      if (annotation.file() != null && annotation.file().length() > 1)
      {
         if(log.isTraceEnabled())
            log.trace("processing HandlerChain for element: " + type);
         process(refs, type, annotation);
      }
   }

   protected void process(ServiceReferenceMetaData refs, E element, HandlerChain annotation)
   {
      String fileName = annotation.file();
      ServiceReferenceHandlerChainsMetaData handlerChains = null;
      try
      {
         // get inputStream for the file
         InputStream in = getResourceInputStream(element, fileName);
         
         // unmarshal
         handlerChains = unmarshall(in);
      }
      catch (Exception e)
      {
         throw new IllegalStateException("Could not process file: "+ fileName, e);
      }
      
      if (handlerChains == null)
         return;

      // Merge if there are already handlerChains 
      if (refs.getHandlerChains() != null)
      {
         ServiceReferenceHandlerChainsMetaData merged = new ServiceReferenceHandlerChainsMetaData();
         merged.merge(handlerChains, refs.getHandlerChains());
         refs.setHandlerChains(merged);
      }
      else
      {
         refs.setHandlerChains(handlerChains);
      }
   }

   /**
    * Unmarshal the handler-chains xml file.
    * 
    * @param in the input stream of the file
    * @return ServiceReferenceHandlerChainsMetaData
    * @throws JBossXBException
    */
   protected ServiceReferenceHandlerChainsMetaData unmarshall(InputStream in) throws JBossXBException
   {
      if(in == null)
         throw new IllegalArgumentException("InputStream may not be null.");
      
      Unmarshaller unmarshaller = UnmarshallerFactory.newInstance().newUnmarshaller();
      return (ServiceReferenceHandlerChainsMetaData) unmarshaller.unmarshal(in, resolver);
   }
   
   /**
    * Returns the InputStream of a specified file.
    * 
    * 1. An absolute java.net.URL in externalForm (ex: http://myhandlers.foo.com/handlerfile1.xml).
    * 2. A relative path from the source file or class file (ex: bar/handlerfile1.xml). 
    * 
    * @param type the declaring class
    * @param file the specified filename
    * @return the InputStream of the file or throws an IllegalStateException if the file was not found
    * @throws MalformedURLException, IOException, IllegalStateException
    */
   protected InputStream getResourceInputStream(E type, String file) throws MalformedURLException, IOException, IllegalStateException
   {
      // If the filename is a url
      if(file.startsWith("http://") )
      {
         URL url = new URL(file);
         if(url != null)
            return url.openStream();
      }
      
      String fileName = file;
      // Resolve file name
      Class<?> declaredClass = resolveDeclaringClass(type);
      InputStream in = declaredClass.getResourceAsStream(fileName);
      if (in != null)
         return in;
      
      // If it's not an absolute fileName resolve the name manually (package / fileName) 
      if (!file.startsWith("/"))
      {
         String baseName = declaredClass.getName();
         int index = baseName.lastIndexOf('.');
         if (index != -1)
         {
            fileName = baseName.substring(0, index).replace('.', '/') + "/" + file;
         }
      }
      else
      {
         fileName = file.substring(1);
      }

      in = declaredClass.getResourceAsStream(fileName);
      if (in != null)
         return in;

      throw new IllegalStateException("could not find file: " + file);
   }

   /**
    * Resolves the declaring class of an AnnotatedElement.
    * 
    * @param element
    * @return the declaring class
    */
   private Class<?> resolveDeclaringClass(E element)
   {
      if(element == null)
         throw new IllegalArgumentException("element may not be null.");
      
      if(element instanceof Class)
         return (Class<?>) element;
      else if(element instanceof Method)
         return ((Method) element).getDeclaringClass();
      else
         return ((Field) element).getDeclaringClass();
   }
   
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(HandlerChain.class);
   }
   
}