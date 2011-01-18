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

import org.jboss.metadata.spi.MetaData;

/**
 * Parse the activation-config-property element used in message driven bean.
 * It is a name/value pair
 * 
 * @author <a href="mailto:adrian@jboss.com">Adrian Brock</a>.
 * @version $Revision: 37459 $
 */
@Deprecated
public class ActivationConfigPropertyMetaData extends OldMetaData<org.jboss.metadata.ejb.spec.ActivationConfigPropertyMetaData>
{
   /**
    * Create a new ActivationConfigPropertyMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public ActivationConfigPropertyMetaData(org.jboss.metadata.ejb.spec.ActivationConfigPropertyMetaData delegate)
   {
      super(delegate);
   }

   /**
    * Create a new ActivationConfigPropertyMetaData.
    * 
    * @param metaData the delegate metadata
    * @throws IllegalArgumentException for a null metaData
    * @throws IllegalStateException if the metadata doesn't have an {@link org.jboss.metadata.ejb.spec.ActivationConfigPropertyMetaData}
    */
   protected ActivationConfigPropertyMetaData(MetaData metaData)
   {
      super(metaData, org.jboss.metadata.ejb.spec.ActivationConfigPropertyMetaData.class);
   }

   /**
    * Retrieve the property name
    * 
    * @return the name
    */
   public String getName()
   {
      return getDelegate().getName();
   }

   /**
    * Set the name
    *
    * @param name the name
    * @throws UnsupportedOperationException always
    */
   public void setName(String name)
   {
      throw new UnsupportedOperationException("setName");
   }
   
   /**
    * Retrieve the property value
    * 
    * @return the value
    */
   public String getValue()
   {
      return getDelegate().getValue();
   }

   /**
    * Set the value
    *
    * @param value the value
    * @throws UnsupportedOperationException always
    */
   public void setValue(String value)
   {
      throw new UnsupportedOperationException("setValue");
   }
   
   @Override
   public String toString()
   {
      return "ActivationConfigProperty(" + getName() + "=" + getValue() + ")";
   }
}
