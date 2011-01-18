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

import java.util.Collections;
import java.util.Set;

import org.jboss.metadata.ejb.spec.MethodPermissionMetaData;

/**
 * PermissionMethodMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@Deprecated
class PermissionMethodMetaData extends MethodMetaData<MethodPermissionMetaData>
{
   /**
    * Create a new PermissionMethodMetaData.
    * 
    * @param delegate the delegatre
    * @param methodDelegate the method delegate
    * @throws IllegalArgumentException for a null delegate or methodDelegate
    */
   public PermissionMethodMetaData(MethodPermissionMetaData delegate, org.jboss.metadata.ejb.spec.MethodMetaData methodDelegate)
   {
      super(delegate, methodDelegate);
   }

   public Set<String> getRoles()
   {
      Set<String> result = getDelegate().getRoles();
      if (result == null)
         result = Collections.emptySet();
      return result;
   }

   public boolean isUnchecked()
   {
      return getDelegate().isNotChecked();
   }
}
