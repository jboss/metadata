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
package org.jboss.metadata.annotation.creator.web;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import javax.servlet.annotation.MultipartConfig;

import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Creator;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.web.spec.AnnotationMetaData;
import org.jboss.metadata.web.spec.AnnotationsMetaData;
import org.jboss.metadata.web.spec.MultipartConfigMetaData;

/**
 * Processor for servlet @MultipartConfig
 * @author Remy Maucherat
 * @version $Revision: 67218 $
 */
public class MultipartConfigProcessor extends AbstractFinderUser
   implements Processor<AnnotationsMetaData, Class<?>>, Creator<Class<?>, MultipartConfigMetaData>
{
   public MultipartConfigProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public void process(AnnotationsMetaData metaData, Class<?> type)
   {
      MultipartConfig annotation = finder.getAnnotation(type, MultipartConfig.class);
      if(annotation == null)
         return;

      MultipartConfigMetaData multipartConfig = create(type);
      AnnotationMetaData annotationMD = metaData.get(type.getName());
      if (annotationMD == null)
      {
         annotationMD = new AnnotationMetaData();
         annotationMD.setClassName(type.getName());
         metaData.add(annotationMD);
      }
      annotationMD.setMultipartConfig(multipartConfig);
   }
   
   public MultipartConfigMetaData create(Class<?> element)
   {
      MultipartConfig multipartConfig = finder.getAnnotation(element, MultipartConfig.class);
      if(multipartConfig == null)
         return null;

      MultipartConfigMetaData metaData = new MultipartConfigMetaData();
      if (!"".equals(multipartConfig.location()))
         metaData.setLocation(multipartConfig.location());
      if (multipartConfig.maxFileSize() != -1L)
         metaData.setMaxFileSize(multipartConfig.maxFileSize());
      if (multipartConfig.maxRequestSize() != -1L)
         metaData.setMaxRequestSize(multipartConfig.maxRequestSize());
      if (multipartConfig.fileSizeThreshold() != 0)
         metaData.setFileSizeThreshold(multipartConfig.fileSizeThreshold());
      return metaData;
   }

   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(MultipartConfig.class);
   }
   
}
