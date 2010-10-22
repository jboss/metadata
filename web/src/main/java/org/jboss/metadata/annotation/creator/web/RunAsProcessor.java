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

import java.lang.reflect.AnnotatedElement;

import javax.annotation.security.RunAs;

import org.jboss.metadata.annotation.creator.AbstractRunAsProcessor;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.RunAsMetaData;
import org.jboss.metadata.web.spec.AnnotationMetaData;
import org.jboss.metadata.web.spec.AnnotationsMetaData;

/**
 * Processor for ejb @RunAs
 * @author Scott.Stark@jboss.org
 * @author Remy Maucherat
 * @version $Revision: 67218 $
 */
public class RunAsProcessor extends AbstractRunAsProcessor
   implements Processor<AnnotationsMetaData, Class<?>>
{
   /**
    * @param finder
    */
   public RunAsProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public void process(AnnotationsMetaData metaData, Class<?> type)
   {
      RunAs annotation = finder.getAnnotation(type, RunAs.class);
      if(annotation == null)
         return;

      RunAsMetaData runAs = super.create(type);
      AnnotationMetaData annotationMD = metaData.get(type.getName());
      if (annotationMD == null)
      {
         annotationMD = new AnnotationMetaData();
         annotationMD.setClassName(type.getName());
         metaData.add(annotationMD);
      }
      annotationMD.setRunAs(runAs);
   }
}
