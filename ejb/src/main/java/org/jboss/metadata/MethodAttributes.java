/*
* JBoss, Home of Professional Open Source
* Copyright 2005, JBoss Inc., and individual contributors as indicated
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

import org.jboss.metadata.ejb.jboss.MethodAttributeMetaData;

/**
 *
 * Provides meta-data for method-attributes
 * <method-attributes>
 * <method>
 * <method-name>get*</method-name>
 * <read-only>true</read-only>
 * <idempotent>true</idempotent>
 * <transaction-timeout>100</tranaction-timeout>
 * </method>
 * </method-attributes>
 *
 * @author <a href="pete@subx.com">Peter Murray</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 * @version $Revision: 37459 $
 */
@Deprecated
public class MethodAttributes extends OldMetaData<MethodAttributeMetaData>
{
   /** The default methods attributes */
   public static final MethodAttributes kDefaultMethodAttributes = new MethodAttributes(MethodAttributeMetaData.DEFAULT);
   
   /**
    * Create a new MethodAttributesMetaData.
    *
    * @param delegate the delegate
    * @return the metadata
    * @throws IllegalArgumentException for a null delegate or an unknown delegate
    */
   public static MethodAttributes create(org.jboss.metadata.ejb.jboss.MethodAttributeMetaData delegate)
   {
      if (delegate == null)
         throw new IllegalArgumentException("Null delegate");
      return new MethodAttributes(delegate);
   }

   /**
    * Create a new QueryMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public MethodAttributes(org.jboss.metadata.ejb.jboss.MethodAttributeMetaData delegate)
   {
      super(delegate);
   }

   /**
    * Whether this matches the method name
    * 
    * @param methodName the method name
    * @return true for a match
    */
   public boolean patternMatches(String methodName)
   {
      return getDelegate().matches(methodName);
   }

   /**
    * Whether it is read only
    * 
    * @return true for read only
    */
   public boolean isReadOnly()
   {
      return getDelegate().isReadOnly();
   }

   /**
    * Whether this is idempotent
    * 
    * @return true for idempotent
    */
   public boolean isIdempotent()
   {
      return getDelegate().isIdempotent();
   }

   /**
    * Get the transaction timeout
    * 
    * @return the transaction timeout
    */
   public int getTransactionTimeout()
   {
      return getDelegate().getTransactionTimeout();
   }
}