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

/**
 * RemoveMethodsMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class RemoveMethodsMetaData extends ArrayList<RemoveMethodMetaData>
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -7679945562287708913L;

   /**
    * Create a new RemoveMethodsMetaData.
    */
   public RemoveMethodsMetaData()
   {
      // For serialization
   }
   
   public void merge(RemoveMethodsMetaData override, RemoveMethodsMetaData original)
   {
      if(override != null)
      {
         if(original != null)
         {
            this.addAll(original);
            for(RemoveMethodMetaData overrideMethod : override)
            {
               RemoveMethodMetaData merged = findRemoveMethod(overrideMethod);
               if(merged != null)
               {
                  if(overrideMethod.getId() != null)
                     merged.setId(overrideMethod.getId());
                  else if(merged.getId() != null)
                     merged.setId(merged.getId());
                  
                  merged.setBeanMethod(overrideMethod.getBeanMethod());
                  // JBMETA-98 - merge retainIfException
                  merged.mergeRetainifException(overrideMethod, merged);
               }
               else
               {
                  this.add(overrideMethod);
               }
            }
         }
         else
         {
            addAll(override);
         }
      }
      else if(original != null)
      {
         addAll(original);
      }
   }
   
   private RemoveMethodMetaData findRemoveMethod(RemoveMethodMetaData removeMethod)
   {
      if(removeMethod == null) return null;
      
      for(RemoveMethodMetaData removeMethod2 : this)
      {
         if(removeMethod.equals(removeMethod2, false))
         {
            return removeMethod2;
         }
      }
      return null;
   }
}
