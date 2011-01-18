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
package org.jboss.metadata.ejb.spec;

import java.util.ArrayList;

import org.jboss.metadata.merge.MergeUtil;

/**
 * ContainerTransactionsMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ContainerTransactionsMetaData extends ArrayList<ContainerTransactionMetaData>
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -1360496515258292681L;

   /**
    * Create a new ContainerTransactionsMetaData.
    */
   public ContainerTransactionsMetaData()
   {
      // For serialization
   }

   /**
    * Get the container transactions for an ejb
    * 
    * @param ejbName the ejb name
    * @return the container transactions or null for no result
    * @throws IllegalArgumentException for a null ejb name
    */
   public ContainerTransactionsMetaData getContainerTransactionsByEjbName(String ejbName)
   {
      if (ejbName == null)
         throw new IllegalArgumentException("Null ejbName");

      if (isEmpty())
         return null;
      
      ContainerTransactionsMetaData result = null;
      for (ContainerTransactionMetaData transaction : this)
      {
         ContainerTransactionMetaData ejbTransaction = transaction.getContainerTransactionsByEjbName(ejbName);
         if (ejbTransaction != null)
         {
            if (result == null)
               result = new ContainerTransactionsMetaData();
            result.add(ejbTransaction);
         }
      }
      return result;
   }
   
   public void merge(ContainerTransactionsMetaData override, ContainerTransactionsMetaData original)
   {
      MergeUtil.merge(this, override, original);
   }
}
