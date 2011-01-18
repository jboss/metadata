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

import javax.ejb.TransactionAttribute;

import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.spec.ContainerTransactionMetaData;
import org.jboss.metadata.ejb.spec.EnterpriseBeanMetaData;

/**
 * Process transaction attribute annotations on classes.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 67165 $
 */
public class TransactionAttributeClassProcessor<T extends EnterpriseBeanMetaData> extends AbstractTransactionAttributeProcessor<Class<?>, T>
   implements Processor<T, Class<?>>
{
   private static final Logger log = Logger.getLogger(TransactionAttributeClassProcessor.class);
   
   public TransactionAttributeClassProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   @Override
   protected ContainerTransactionMetaData createContainerTransaction(String ejbName, TransactionAttribute annotation, Class<?> cls)
   {
      ContainerTransactionMetaData containerTransaction = new ContainerTransactionMetaData();
      log.info(containerTransaction);
      containerTransaction.setMethods(createMethods(ejbName, null));
      containerTransaction.setTransAttribute(createTransAttributeType(annotation));
      return containerTransaction;
   }
}
