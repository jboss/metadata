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

import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.RemoteHome;

import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.creator.ejb.AbstractHomeProcessor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;

/**
 * @RemoteHome annotation processor.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 67165 $
 */
public class RemoteHomeProcessor extends AbstractHomeProcessor
   implements Processor<JBossSessionBeanMetaData, Class<?>>
{  
   public RemoteHomeProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }
   
   public void process(JBossSessionBeanMetaData metaData, Class<?> type)
   {
      RemoteHome remote = finder.getAnnotation(type, RemoteHome.class);
      if(remote == null)
         return;

      Class<?> remoteHome = remote.value();
      
      if(!EJBHome.class.isAssignableFrom(remoteHome))
         throw new IllegalStateException("Declared EJB 2.1 Home Interface " + remoteHome.getName() + " does not extend "
               + EJBHome.class.getName() + " as required by EJB 3.0 Core Specification 4.6.8"); 
      
      metaData.setHome(remoteHome.getName());

      // Processing the remote interface
      setRemote(metaData, remoteHome, metaData.isStateless());
   }
   
   private void setRemote(JBossSessionBeanMetaData metaData, Class<?> remoteHome, boolean isStateless)
   {
      Class<?> businessInterface = getCreateReturnSignature(remoteHome, metaData.isStateless());
      if(! EJBObject.class.isAssignableFrom(businessInterface))
         throw new IllegalStateException("EJB 3.0 Core Specification Violation (4.6.7): The session bean’s remote interface "+ businessInterface + " must extend the javax.ejb.EJBObject interface.");
      
      // As the RemoteProcessor allows a @Remote which extends EjbObject it should not fail on the same interface
      if(metaData.getRemote() != null && !metaData.getRemote().equals(businessInterface.getName()))
         throw new IllegalStateException("2.1 bean " + metaData.getEjbName() + " already has a remote interface " + metaData.getRemote() + ", can't add " + businessInterface.getName());
         
      metaData.setRemote(businessInterface.getName());
   }
   
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(RemoteHome.class);
   }
}
