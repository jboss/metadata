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
package org.jboss.metadata.annotation.creator.ejb;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.Collections;

import javax.ejb.EJBLocalObject;
import javax.ejb.Remote;

import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.spec.BusinessLocalsMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.lang.ClassHelper;

/**
 * Process the implicit local business interface (4.6.6)
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class ImplicitLocalProcessor extends AbstractFinderUser implements Processor<SessionBeanMetaData, Class<?>>
{

   public ImplicitLocalProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   public void process(SessionBeanMetaData metaData, Class<?> type)
   {
      
      // If there are already local business interfaces specified
      if(metaData.getBusinessLocals() != null && metaData.getBusinessLocals().size() > 0)
         return;
      
      // If there are already remote business interfaces specified
      if(metaData.getBusinessRemotes() != null && metaData.getBusinessRemotes().size() > 0)
         return;
      
      // Don't check super class
      if(metaData.getEjbName() != null && !metaData.getEjbClass().equals(type.getName()))
         return;
      
      // Get the a single interface
      Class<?> businessInterface = ClassHelper.extractInterface(type.getInterfaces());
      if(businessInterface == null)
         return;
      
      // Check if the interface is a remote one      
      Remote remote = finder.getAnnotation(businessInterface, Remote.class);
      if(remote != null)
         return;
      
      // A business interface must not extend EJBLocalObject
      if(EJBLocalObject.class.isAssignableFrom(businessInterface))
         throw new IllegalStateException("EJB 3.0 Core Specification Violation (4.6.6): The session bean’s business interface "+ businessInterface + " must not extend the javax.ejb.EJBLocalObject interface.");
      
      // Add this businessInterface as the local business interface
      if(metaData.getBusinessLocals() == null)
         metaData.setBusinessLocals(new BusinessLocalsMetaData());
      
      // Finally add local business interface
      metaData.getBusinessLocals().add(businessInterface.getName());
   }
   
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      // does not process any annotation
      return Collections.EMPTY_SET;
   }
}

