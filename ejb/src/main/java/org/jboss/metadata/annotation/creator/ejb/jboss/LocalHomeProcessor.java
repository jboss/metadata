/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors as indicated
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
package org.jboss.metadata.annotation.creator.ejb.jboss;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.LocalHome;

import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.creator.ejb.AbstractHomeProcessor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;

/**
 * Comment
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision: 67165 $
 */
public class LocalHomeProcessor extends AbstractHomeProcessor implements Processor<JBossSessionBeanMetaData, Class<?>>
{
   public LocalHomeProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public void process(JBossSessionBeanMetaData metaData, Class<?> type)
   {
      LocalHome annotation = finder.getAnnotation(type, LocalHome.class);
      if(annotation == null)
         return;

      Class<?> localHome = annotation.value();
      if(!EJBLocalHome.class.isAssignableFrom(localHome)) 
         throw new IllegalStateException("Declared EJB 2.1 Home Interface " + localHome.getName() + " does not extend "
               + EJBLocalHome.class.getName() + " as required by EJB 3.0 Core Specification 4.6.10"); 
      
      metaData.setLocalHome(localHome.getName());
      
      // Processing the local interface
      setLocal(metaData, localHome, metaData.isStateless());
   }
   
   private void setLocal(JBossSessionBeanMetaData metaData, Class<?> localHome, boolean isStateless)
   {
      Class<?> businessInterface = getCreateReturnSignature(localHome, metaData.isStateless());
      if(! EJBLocalObject.class.isAssignableFrom(businessInterface))
         throw new IllegalStateException("EJB 3.0 Core Specification Violation (4.6.9): The session bean’s local interface "+ businessInterface + " must extend the javax.ejb.EJBLocalObject interface.");

      // As the LocalProcessor allows a @Local which extends EjbHomeObject it should not fail on the same interface
      if(metaData.getLocal() != null  && !metaData.getLocal().equals(businessInterface.getName()))
         throw new IllegalStateException("2.1 bean " + metaData.getEjbName() + " already has a local interface " + metaData.getLocal() + ", can't add " + businessInterface.getName());
      
      metaData.setLocal(businessInterface.getName());
   }
   
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(LocalHome.class);
   }
}
