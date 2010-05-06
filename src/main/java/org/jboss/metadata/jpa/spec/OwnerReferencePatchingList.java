/*
* JBoss, Home of Professional Open Source
* Copyright 2010, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata.jpa.spec;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * List of elements, where the Object holding the list (or logically owning it), is
 * passed to each List element (as they are added to the list).
 *
 * The owner of the list is specified via the OwnerReferencePatchingList.setOwner(Object).
 *
 * The owner of the list is passed to each element via reflection.  OwnerReferencePatchingList.add(T t) will
 * invoke the t.setOwner(Object) method (passing the owner).  A IllegalArgumentException is thrown if that doesn't work.
 * 
 * @author <a href="mailto:smarlow@redhat.com">Scott Marlow</a>
 */
public class OwnerReferencePatchingList<T> implements List<T>
{
   private List<T> list = new ArrayList();
   private Object owner;

   protected void setOwner(Object owner) {
      if (owner == null)
         throw new IllegalArgumentException("Null owner");
      this.owner = owner;
   }

   public boolean add(T t)
   {
      Method setOwner = null;
      try {
         setOwner = t.getClass().getMethod("setOwner",Object.class);
      } catch (NoSuchMethodException e) {
         throw new IllegalArgumentException("cannot add type " + t.getClass().getName() + ", must have setOwner(Object) method", e);
      }
      try {
         setOwner.invoke(t, owner );
      } catch (IllegalAccessException e) {
         throw new IllegalArgumentException("cannot add type " + t.getClass().getName() + ", couldn't call setOwner(Object) method", e);
      } catch (InvocationTargetException e) {
         throw new IllegalArgumentException("cannot add type " + t.getClass().getName() + ", could not call setOwner(Object) method", e);
      }
      return list.add(t);
   }

   public boolean addAll(Collection<? extends T> c)
   {
      //return list.addAll(c);
      throw new UnsupportedOperationException("addAll(Collection) is not implemented");
   }

   public boolean addAll(int index, Collection<? extends T> c)
   {
      // return list.addAll(index, c);
      throw new UnsupportedOperationException("addAll(int, Collection) is not implemented");
   }

   public int size()
   {
      return list.size();
   }

   public boolean isEmpty()
   {
      return list.isEmpty();
   }

   public boolean contains(Object o)
   {
      return list.contains(o);
   }

   public Iterator<T> iterator()
   {
      return list.iterator();
   }

   public Object[] toArray()
   {
      return list.toArray();
   }

   public <T> T[] toArray(T[] a)
   {
      return list.toArray(a);
   }

   public boolean remove(Object o)
   {
      return list.remove(o);
   }

   public boolean containsAll(Collection<?> c)
   {
      return list.containsAll(c);
   }

   public boolean removeAll(Collection<?> c)
   {
      return list.removeAll(c);
   }

   public boolean retainAll(Collection<?> c)
   {
      return list.retainAll(c);
   }

   public void clear()
   {
      list.clear();
   }

   public boolean equals(Object o)
   {
      return list.equals(o);
   }

   public int hashCode()
   {
      return list.hashCode();
   }

   public T get(int index)
   {
      return list.get(index);
   }

   public T set(int index, T element)
   {
      return list.set(index, element);
   }

   public void add(int index, T element)
   {
      list.add(index, element);
   }

   public T remove(int index)
   {
      return list.remove(index);
   }

   public int indexOf(Object o)
   {
      return list.indexOf(o);
   }

   public int lastIndexOf(Object o)
   {
      return list.lastIndexOf(o);
   }

   public ListIterator<T> listIterator()
   {
      return list.listIterator();
   }

   public ListIterator<T> listIterator(int index)
   {
      return list.listIterator(index);
   }

   public List<T> subList(int fromIndex, int toIndex)
   {
      return list.subList(fromIndex, toIndex);
   }
}
