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
package org.jboss.metadata.ejb.jboss;

import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.NamedMetaData;

/**
 * MethodAttributeMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="methodType", propOrder={"methodName", "readOnly", "idempotent", "transactionTimeout"})
public class MethodAttributeMetaData extends NamedMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 8484757755955187189L;

   /** The default methods attributes */
   public static final MethodAttributeMetaData DEFAULT;

   static
   {
      DEFAULT = new MethodAttributeMetaData();
      DEFAULT.setMethodName("*");
   }

   /** Whether the method is read only */
   private boolean readOnly;

   /** Whether the method is idempotent */
   private boolean idempotent;

   /** The transaction timeout */
   private int transactionTimeout;

   /**
    * Get the methodName.
    * 
    * @return the methodName.
    */
   public String getMethodName()
   {
      return getName();
   }

   /**
    * Set the methodName.
    * 
    * @param methodName the methodName.
    * @throws IllegalArgumentException for a null methodName
    */
   public void setMethodName(String methodName)
   {
      setName(methodName);
   }

   /**
    * Get the readOnly.
    * 
    * @return the readOnly.
    */
   public boolean isReadOnly()
   {
      return readOnly;
   }

   /**
    * Set the readOnly.
    * 
    * @param readOnly the readOnly.
    */
   public void setReadOnly(boolean readOnly)
   {
      this.readOnly = readOnly;
   }

   /**
    * Get the idempotent.
    * 
    * @return the idempotent.
    */
   public boolean isIdempotent()
   {
      return idempotent;
   }

   /**
    * Set the idempotent.
    * 
    * @param idempotent the idempotent.
    */
   public void setIdempotent(boolean idempotent)
   {
      this.idempotent = idempotent;
   }

   /**
    * Get the transactionTimeout.
    * 
    * @return the transactionTimeout.
    */
   public int getTransactionTimeout()
   {
      return transactionTimeout;
   }

   /**
    * Set the transactionTimeout.
    * 
    * @param transactionTimeout the transactionTimeout.
    */
   public void setTransactionTimeout(int transactionTimeout)
   {
      this.transactionTimeout = transactionTimeout;
   }

   /**
    * Whether this matches the method name
    * 
    * @param methodName the method name
    * @return true for a match
    */
   public boolean matches(String methodName)
   {
      if (methodName == null)
         return false;
      
      int ct, end;

      String pattern = getMethodName();
      
      end = pattern.length();

      if(end > methodName.length())
          return false;

      for(ct = 0; ct < end; ct ++)
      {
         char c = pattern.charAt(ct);
         if(c == '*')
        return true;
         if(c != methodName.charAt(ct))
        return false;
      }
      return ct == methodName.length();
   }
}
