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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * InterceptorBindingMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="interceptor-bindingType", propOrder={"descriptions", "ejbName", "interceptorClasses", "interceptorOrder",
      "excludeDefaultInterceptors", "excludeClassInterceptors", "method"})
public class InterceptorBindingMetaData extends NamedMetaDataWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 3265594088830429912L;
   
   /** The interceptor classes */
   private InterceptorClassesMetaData interceptorClasses;

   /** The interceptor order */
   private InterceptorOrderMetaData interceptorOrder;
   
   /** Exclude the default interceptors */
   private boolean excludeDefaultInterceptors = false; 
   
   /** Exclude the class interceptors */
   private boolean excludeClassInterceptors = false;
   
   /** The method */
   private NamedMethodMetaData method;
   
   /**
    * Create a new MethodMetaData.
    */
   public InterceptorBindingMetaData()
   {
      // For serialization
   }
   
   /**
    * Get the ejbName.
    * 
    * @return the ejbName.
    */
   public String getEjbName()
   {
      return getName();
   }

   /**
    * Set the ejbName.
    * 
    * @param ejbName the ejbName.
    * @throws IllegalArgumentException for a null ejbName
    */
   public void setEjbName(String ejbName)
   {
      setName(ejbName);
   }

   /**
    * Get the excludeClassInterceptors.
    * 
    * @return the excludeClassInterceptors.
    */
   public boolean isExcludeClassInterceptors()
   {
      return excludeClassInterceptors;
   }

   /**
    * Set the excludeClassInterceptors.
    * 
    * @param excludeClassInterceptors the excludeClassInterceptors.
    */
   public void setExcludeClassInterceptors(boolean excludeClassInterceptors)
   {
      this.excludeClassInterceptors = excludeClassInterceptors;
   }

   /**
    * Get the excludeDefaultInterceptors.
    * 
    * @return the excludeDefaultInterceptors.
    */
   public boolean isExcludeDefaultInterceptors()
   {
      return excludeDefaultInterceptors;
   }

   /**
    * Set the excludeDefaultInterceptors.
    * 
    * @param excludeDefaultInterceptors the excludeDefaultInterceptors.
    */
   public void setExcludeDefaultInterceptors(boolean excludeDefaultInterceptors)
   {
      this.excludeDefaultInterceptors = excludeDefaultInterceptors;
   }

   /**
    * Get the interceptorClasses.
    * 
    * @return the interceptorClasses.
    */
   public InterceptorClassesMetaData getInterceptorClasses()
   {
      return interceptorClasses;
   }

   /**
    * Set the interceptorClasses.
    * 
    * @param interceptorClasses the interceptorClasses.
    * @throws IllegalArgumentException for a null interceptorClasses
    */
   @XmlElement(name="interceptor-class")
   public void setInterceptorClasses(InterceptorClassesMetaData interceptorClasses)
   {
      if (interceptorClasses == null)
         throw new IllegalArgumentException("Null interceptorClasses");
      
      assert interceptorOrder == null : "Can't have both interceptorClasses and interceptorOrder";
      
      this.interceptorClasses = interceptorClasses;
   }

   /**
    * Is this binding a total ordering or a list of interceptor classes.
    * @return   true if it is a total ordering
    */
   @XmlTransient
   public boolean isTotalOrdering()
   {
      return interceptorOrder != null;
   }
   
   /**
    * Get the interceptorOrder.
    * 
    * @return the interceptorOrder.
    */
   public InterceptorOrderMetaData getInterceptorOrder()
   {
      return interceptorOrder;
   }

   /**
    * Set the interceptorOrder.
    * 
    * @param interceptorOrder the interceptorOrder.
    * @throws IllegalArgumentException for a null interceptorOrder
    */
   public void setInterceptorOrder(InterceptorOrderMetaData interceptorOrder)
   {
      if (interceptorOrder == null)
         throw new IllegalArgumentException("Null interceptorOrder");
      
      assert interceptorClasses == null : "Can't have both interceptorOrder and interceptorClasses";
      
      this.interceptorOrder = interceptorOrder;
   }

   /**
    * Get the method.
    * 
    * @return the method.
    */
   public NamedMethodMetaData getMethod()
   {
      return method;
   }

   /**
    * Set the method.
    * 
    * @param method the method.
    * @throws IllegalArgumentException for a null method
    */
   public void setMethod(NamedMethodMetaData method)
   {
      if (method == null)
         throw new IllegalArgumentException("Null method");
      this.method = method;
   }
}
