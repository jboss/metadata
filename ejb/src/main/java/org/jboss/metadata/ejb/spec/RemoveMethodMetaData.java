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

import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * RemoveMethodMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="remove-methodType", propOrder={"beanMethod", "retainIfException"})
public class RemoveMethodMetaData extends IdMetaDataImpl
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 1830841714074832930L;
   
   /** The ratainIfExeption default value */
   private static final boolean retainIfExceptionDefault = false;
   
   /** The bean method */
   private NamedMethodMetaData beanMethod;
   
   /** Retain if exception */
   private Boolean retainIfException = null;
   
   /**
    * Create a new RemoveMethodMetaData.
    */
   public RemoveMethodMetaData()
   {
      // For serialization
   }

   /**
    * Get the beanMethod.
    * 
    * @return the beanMethod.
    */
   public NamedMethodMetaData getBeanMethod()
   {
      return beanMethod;
   }

   /**
    * Set the beanMethod.
    * 
    * @param beanMethod the beanMethod.
    * @throws IllegalArgumentException for a null beanMethod
    */
   public void setBeanMethod(NamedMethodMetaData beanMethod)
   {
      if (beanMethod == null)
         throw new IllegalArgumentException("Null beanMethod");
      this.beanMethod = beanMethod;
   }

   /**
    * Get the retainIfException.
    * 
    * @return the retainIfException.
    */
   public boolean isRetainIfException()
   {
      if(retainIfException == null) return retainIfExceptionDefault;
      return retainIfException.booleanValue();
   }

   /**
    * Set the retainIfException.
    * 
    * @param retainIfException the retainIfException.
    */
   public void setRetainIfException(boolean retainIfException)
   {
      this.retainIfException = new Boolean(retainIfException);
   }
   
   public boolean equals(Object o, boolean checkRetainIfException)
   {
      if(o == this)
         return true;
      
      if(!(o instanceof RemoveMethodMetaData))
         return false;

      RemoveMethodMetaData other = (RemoveMethodMetaData) o;
      if(this.beanMethod == null
            || other.beanMethod == null
            || ! this.beanMethod.equals(other.beanMethod))
         return false;
      
      if(checkRetainIfException
            && this.retainIfException != other.retainIfException)
         return false;
      
      return true;
   }

   
   /**
    * merge the retainIfException
    * 
    * @param override
    * @param original
    */
   public void mergeRetainifException(RemoveMethodMetaData override, RemoveMethodMetaData original)
   {
      // JBMETA-98 - merge retainIfException
      if(original != null && override != null)
      {
         if(override.retainIfException == null)
         {
            this.retainIfException = original.retainIfException;
         }
         else
         {
            this.retainIfException = override.retainIfException;
         }
         return;
      }
      
      if(original != null)
      {
         this.retainIfException = original.retainIfException;
      }
      if(override != null)
      {
         this.retainIfException = override.retainIfException;
      }
   }
}
