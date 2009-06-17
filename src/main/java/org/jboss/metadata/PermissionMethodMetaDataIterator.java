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

import org.jboss.metadata.ejb.spec.MethodPermissionMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionsMetaData;
import org.jboss.metadata.ejb.spec.MethodsMetaData;

/**
 * PermissionMethodMetaDataIterator.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@Deprecated
class PermissionMethodMetaDataIterator implements Iterator<MethodMetaData>
{
   /** The delegate */
   private Iterator<MethodPermissionMetaData> delegate;
   
   /** The current method permission */
   private MethodPermissionMetaData permission;
   
   /** The methods */
   private Iterator<org.jboss.metadata.ejb.spec.MethodMetaData> methods;
   
   /**
    * Create a new PermissionMethod iterator
    * 
    * @param permissions the permissions
    */
   public PermissionMethodMetaDataIterator(MethodPermissionsMetaData permissions) 
   {
      if (permissions == null)
         return;
      delegate = permissions.iterator();
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
      PermissionMethodMetaData result = new PermissionMethodMetaData(permission, theMethod);
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
         permission = delegate.next();
         MethodsMetaData theMethods = permission.getMethods();
         if (theMethods != null && theMethods.isEmpty() == false)
         {
            methods = theMethods.iterator();
            break;
         }
      }
   }
}
