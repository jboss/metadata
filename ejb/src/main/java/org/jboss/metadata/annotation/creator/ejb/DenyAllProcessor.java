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
package org.jboss.metadata.annotation.creator.ejb;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collection;

import javax.annotation.security.DenyAll;

import org.jboss.annotation.javaee.Descriptions;
import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.EjbProcessorUtils;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.spec.ExcludeListMetaData;
import org.jboss.metadata.ejb.spec.MethodMetaData;
import org.jboss.metadata.ejb.spec.MethodsMetaData;

/**
 * @DenyAll processor
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision: 76002 $
 */
public class DenyAllProcessor
   extends AbstractFinderUser
   implements Processor<ExcludeListMetaData, Method>
{
   private static final Logger log = Logger.getLogger(DenyAllProcessor.class);
   
   public DenyAllProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public void process(ExcludeListMetaData metaData, Method method)
   {
      DenyAll deny = finder.getAnnotation(method, DenyAll.class);
      if(deny == null)
         return;

      MethodsMetaData methods = metaData.getMethods();
      if(methods == null)
      {
         methods = new MethodsMetaData();
         metaData.setMethods(methods);
      }
      String ejbName = EjbNameThreadLocal.ejbName.get();
      if(ejbName == null)
         ejbName = "*";
      MethodMetaData mmd = EjbProcessorUtils.createMethod(ejbName, method);
      Descriptions descriptions = ProcessorUtils.getDescription("@DenyAll for: "+method);
      mmd.setDescriptions(descriptions);
      log.trace("add " + mmd);
      methods.add(mmd);
   }
   
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(DenyAll.class);
   }

}
