/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.jboss.metadata.ejb.spec.ContainerTransactionMetaData;
import org.jboss.metadata.ejb.spec.ContainerTransactionsMetaData;
import org.jboss.metadata.ejb.spec.MethodsMetaData;

/**
 * TransactionMethodMetaDataIterator.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@Deprecated
class TransactionMethodMetaDataIterator implements Iterator<MethodMetaData>
{
   /** The delegate */
   private Iterator<ContainerTransactionMetaData> delegate;
   
   /** The current container transaction */
   private ContainerTransactionMetaData transaction;
   
   /** The methods */
   private Iterator<org.jboss.metadata.ejb.spec.MethodMetaData> methods;
   
   /**
    * Create a new TransactionMethod iterator
    * 
    * @param transactions the transactions
    */
   public TransactionMethodMetaDataIterator(ContainerTransactionsMetaData transactions) 
   {
      if (transactions == null)
         return;
      delegate = transactions.iterator();
      bump();
   }
   
   public boolean hasNext()
   {
      if (delegate == null || methods == null)
         return false;
      return methods.hasNext();
   }

   public MethodMetaData next()
   {
      if (delegate == null || methods == null)
         throw new NoSuchElementException("No next");
      
      org.jboss.metadata.ejb.spec.MethodMetaData theMethod = methods.next();
      TransactionMethodMetaData result = new TransactionMethodMetaData(transaction, theMethod);
      if (methods.hasNext() == false)
         bump();
      return result;
   }

   public void remove()
   {
      throw new UnsupportedOperationException("remove");
   }
   
   /**
    * Bump the iterators onto the next element
    */
   private void bump()
   {
      while (delegate.hasNext())
      {
         transaction = delegate.next();
         MethodsMetaData theMethods = transaction.getMethods();
         if (theMethods != null && theMethods.isEmpty() == false)
         {
            methods = theMethods.iterator();
            break;
         }
      }
   }
}
