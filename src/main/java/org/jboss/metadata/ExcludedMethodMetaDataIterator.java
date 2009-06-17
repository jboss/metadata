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

import org.jboss.metadata.ejb.spec.ExcludeListMetaData;
import org.jboss.metadata.ejb.spec.MethodsMetaData;

/**
 * ExcludedMethodMetaDataIterator.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@Deprecated
class ExcludedMethodMetaDataIterator implements Iterator<MethodMetaData>
{
   /** The exclude list */
   private ExcludeListMetaData excludeList;
   
   /** The delegate */
   private Iterator<org.jboss.metadata.ejb.spec.MethodMetaData> delegate;
   
   /**
    * Create a new ExcludedMethod iterator
    * 
    * @param excluded the excluded methods
    */
   public ExcludedMethodMetaDataIterator(ExcludeListMetaData excluded) 
   {
      if (excluded == null)
         return;
      this.excludeList = excluded;
      MethodsMetaData methods = excluded.getMethods();
      if (methods == null)
         return;
      delegate = methods.iterator();
   }
   
   public boolean hasNext()
   {
      if (delegate == null)
         return false;
      return delegate.hasNext();
   }

   public MethodMetaData next()
   {
      if (delegate == null)
         throw new NoSuchElementException("No next");
      
      org.jboss.metadata.ejb.spec.MethodMetaData theMethod = delegate.next();
      return new ExcludedMethodMetaData(excludeList, theMethod);
   }

   public void remove()
   {
      throw new UnsupportedOperationException("remove");
   }
}
