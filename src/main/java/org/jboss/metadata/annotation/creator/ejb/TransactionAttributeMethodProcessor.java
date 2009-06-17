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
import java.lang.reflect.Method;

import javax.ejb.TransactionAttribute;

import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.spec.ContainerTransactionMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;

/**
 * Process transaction attribute annotations on methods.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 67544 $
 */
public class TransactionAttributeMethodProcessor<T extends EnterpriseBeanMetaData>
   extends AbstractTransactionAttributeProcessor<Method, T>
   implements Processor<T, Method>
{
   private static final Logger log = Logger.getLogger(TransactionAttributeMethodProcessor.class);
   
   public TransactionAttributeMethodProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   @Override
   protected ContainerTransactionMetaData createContainerTransaction(String ejbName, TransactionAttribute annotation, Method method)
   {
      ContainerTransactionMetaData containerTransaction = new ContainerTransactionMetaData();
      containerTransaction.setMethods(createMethods(ejbName, method));
      containerTransaction.setTransAttribute(createTransAttributeType(annotation));
      return containerTransaction;
   }
}
