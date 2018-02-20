/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jboss.metadata.ejb.common.IAssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.common.IEjbJarMetaData;
import org.jboss.metadata.ejb.common.IEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.common.IEnterpriseBeansMetaData;
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 66420 $
 */
public abstract class EnterpriseBeansMap<A extends IAssemblyDescriptorMetaData,
        C extends EnterpriseBeansMap<A, C, E, J>,
        E extends IEnterpriseBeanMetaData<A, C, E, J>,
        J extends IEjbJarMetaData<A, C, E, J>>
        extends IdMetaDataImpl
        implements IdMetaData, Collection<E>, IEnterpriseBeansMetaData<A, C, E, J> {
    private static final long serialVersionUID = 1;
    private Map<String, E> map = new HashMap<String, E>();

    public EnterpriseBeansMap() {
    }

    public boolean add(E o) {
        o.setEnterpriseBeansMetaData((C) this);
        String key = o.getKey();
        if (key == null)
            throw new IllegalStateException("Null name for bean: " + o);
        return map.put(o.getKey(), o) == null;
    }

    public boolean addAll(Collection<? extends E> c) {
        for (E t : c)
            add(t);
        return true;
    }

    public void clear() {
        map.clear();
    }

    public boolean contains(Object o) {
        E t = (E) o;
        return map.containsKey(t.getKey());
    }

    public boolean containsAll(Collection<?> c) {
        boolean containsAll = true;
        for (Object o : c) {
            E t = (E) o;
            containsAll &= map.containsKey(t.getKey());
        }
        return containsAll;
    }

    public E get(String ejbName) {
        return map.get(ejbName);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Iterator<E> iterator() {
        return map.values().iterator();
    }

    public boolean remove(Object o) {
        E t = (E) o;
        return map.remove(t.getKey()) != null;
    }

    public boolean removeAll(Collection<?> c) {
        boolean removeAll = true;
        for (Object o : c) {
            E t = (E) o;
            removeAll &= map.remove(t.getKey()) != null;
        }
        return removeAll;
    }

    public boolean retainAll(Collection<?> c) {
        HashMap<String, E> newmap = new HashMap<String, E>();
        for (Object o : c) {
            E t = (E) o;
            newmap.put(t.getKey(), t);
        }
        map = newmap;
        return true;
    }

    public int size() {
        return map.size();
    }

    public Object[] toArray() {
        return map.values().toArray();
    }

    public <T> T[] toArray(T[] a) {
        return map.values().toArray(a);
    }
}
