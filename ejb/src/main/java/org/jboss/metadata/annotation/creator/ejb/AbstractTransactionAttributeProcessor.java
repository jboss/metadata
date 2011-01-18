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

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.EjbProcessorUtils;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.common.ejb.IEjbJarMetaData;
import org.jboss.metadata.ejb.spec.AssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.spec.ContainerTransactionMetaData;
import org.jboss.metadata.ejb.spec.ContainerTransactionsMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.MethodsMetaData;

/**
 * E defines the actual annotated element we're working for.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 76002 $
 */
public abstract class AbstractTransactionAttributeProcessor<E extends AnnotatedElement, T extends EnterpriseBeanMetaData> extends AbstractFinderUser
{
   protected AbstractTransactionAttributeProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }
   
   protected abstract ContainerTransactionMetaData createContainerTransaction(String ejbName, TransactionAttribute annotation, E element);
   
   protected MethodsMetaData createMethods(String ejbName, Method method)
   {
      MethodsMetaData methods = new MethodsMetaData();
      methods.add(EjbProcessorUtils.createMethod(ejbName, method));
      return methods;
   }
   
   protected TransactionAttributeType createTransAttributeType(TransactionAttribute annotation)
   {
      return annotation.value();
   }

   public void process(T bean, E element)
   {
      TransactionAttribute annotation = finder.getAnnotation(element, TransactionAttribute.class);
      if(annotation == null)
         return;
      
      IEjbJarMetaData ejbJarMetaData = bean.getEjbJarMetaData();
      
      if(ejbJarMetaData.getAssemblyDescriptor() == null)
         ejbJarMetaData.setAssemblyDescriptor(new AssemblyDescriptorMetaData());
      if(ejbJarMetaData.getAssemblyDescriptor().getContainerTransactions() == null)
         ejbJarMetaData.getAssemblyDescriptor().setContainerTransactions(new ContainerTransactionsMetaData());
      
      ContainerTransactionMetaData transaction = createContainerTransaction(bean.getEjbName(), annotation, element);
      ejbJarMetaData.getAssemblyDescriptor().getContainerTransactions().add(transaction);
   }
   
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(TransactionAttribute.class);
   }
}
