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

import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;
import org.jboss.xb.annotations.JBossXmlChild;

@XmlType(name="method-attributesType")
@JBossXmlChild(name="method", type=MethodAttributeMetaData.class, unbounded=true)
public class MethodAttributesMetaData extends AbstractMappedMetaData<MethodAttributeMetaData>
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 4074287842839442989L;

   /** The cache */
   private transient ConcurrentHashMap<String, MethodAttributeMetaData> cache;
   
   /**
    * Create a new MethodAttributesMetaData.
    */
   public MethodAttributesMetaData()
   {
      super("method-name for method attributes");
   }

   /**
    * Is this method a read-only method
    * 
    * @param methodName the method name
    * @return true for read only
    */
   public boolean isMethodReadOnly(String methodName)
   {
      MethodAttributeMetaData attribute = getMethodAttribute(methodName);
      return attribute.isReadOnly();
   }

   /**
    * Get the transaction timeout for the method
    * 
    * @param methodName the method name
    * @return the transaction timeout
    */
   public int getMethodTransactionTimeout(String methodName)
   {
      MethodAttributeMetaData attribute = getMethodAttribute(methodName);
      return attribute.getTransactionTimeout();
   }

   /**
    * Get the attributes for the method
    * 
    * @param methodName the method name
    * @return the attributes
    */
   public MethodAttributeMetaData getMethodAttribute(String methodName)
   {
      if (methodName == null)
         return MethodAttributeMetaData.DEFAULT;
      
      MethodAttributeMetaData result = null;
      if (cache != null)
      {
         result = cache.get(methodName);
         if (result != null)
            return result;
      }
      
      for (MethodAttributeMetaData attribute : this)
      {
         if (attribute.matches(methodName))
         {
            result = attribute;
            break;
         }
      }
      
      if (result == null)
         result = MethodAttributeMetaData.DEFAULT;
      
      if (cache == null)
         cache = new ConcurrentHashMap<String, MethodAttributeMetaData>();
      cache.put(methodName, result);
      
      return result;
   }
   
   public void merge(MethodAttributesMetaData override, MethodAttributesMetaData original)
   {
      super.merge(override, original);
      if (original != null)
      {
         for (MethodAttributeMetaData property : original)
            add(property);
      }
      if (override != null)
      {
         for (MethodAttributeMetaData property : override)
            add(property);
      }
   }
}
