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
package org.jboss.metadata.annotation.creator.ejb;

import java.lang.reflect.AnnotatedElement;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.jboss.metadata.annotation.creator.Creator;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.spec.EjbJar3xMetaData;
import org.jboss.metadata.ejb.spec.SessionBean31MetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.DescriptionImpl;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;

/**
 * Abstract processor for helping a processor which creates
 * session bean meta data.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 76002 $
 */
public abstract class AbstractSessionBeanProcessor extends AbstractEnterpriseBeanProcessor<SessionBeanMetaData> implements Creator<Class<?>, SessionBeanMetaData>, Processor<EjbJar3xMetaData, Class<?>>
{
  
   protected AbstractSessionBeanProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
      //
      addTypeProcessor(new LocalProcessor(finder));
      addTypeProcessor(new LocalHomeProcessor(finder));
      addTypeProcessor(new RemoteProcessor(finder));
      addTypeProcessor(new RemoteHomeProcessor(finder));
      addTypeProcessor(new ImplicitLocalProcessor(finder));
      addTypeProcessor(new LocalBeanProcessor(finder));

      addMethodProcessor(new InitProcessor(finder));
      addMethodProcessor(new TimeoutProcessor(finder));
      addMethodProcessor(new AroundInvokeProcessor(finder));
   }

   public abstract SessionBeanMetaData create(Class<?> beanClass);
   
   protected SessionBeanMetaData create(Class<?> beanClass, String name, String mappedName, String description)
   {
      SessionBeanMetaData bean = new SessionBean31MetaData();
      bean.setEjbClass(beanClass.getName());
      String ejbName;
      if(name == null || name.length() == 0)
         ejbName = beanClass.getSimpleName();
      else
         ejbName = name;
      bean.setEjbName(ejbName);
      if(mappedName != null && mappedName.length() > 0)
         bean.setMappedName(mappedName);
      if(description != null && description.length() > 0)
      {
         DescriptionGroupMetaData descriptionGroup = new DescriptionGroupMetaData();
         DescriptionsImpl descriptions = new DescriptionsImpl();
         DescriptionImpl descriptionImpl = new DescriptionImpl();
         descriptionImpl.setDescription(description);
         descriptions.add(descriptionImpl);
         descriptionGroup.setDescriptions(descriptions);
         bean.setDescriptionGroup(descriptionGroup);
      }

      TransactionManagement txMgmt = finder.getAnnotation(beanClass, TransactionManagement.class);
      TransactionManagementType txType = TransactionManagementType.CONTAINER;
      if(txMgmt != null)
         txType = txMgmt.value();
      bean.setTransactionType(txType);
      
      return bean;
   }
   
}
