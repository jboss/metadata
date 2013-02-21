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
package org.jboss.metadata.javaee.support;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * MappedMetaData.
 *
 * @param <T> the metadata type
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class MappedMetaDataWithDescriptions<T extends MappableMetaData> extends IdMetaDataImplWithDescriptions
        implements MappedMetaData<T> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 5696461894017065337L;

    /**
     * The key name
     */
    private String keyName;

    /**
     * The metaDatas
     */
    private Map<String, T> map = new NonNullLinkedHashMap<String, T>();

    /**
     * Create a new MappedMetaData.
     *
     * @param keyName the key name
     */
    protected MappedMetaDataWithDescriptions(String keyName) {
        if (keyName == null)
            throw new IllegalArgumentException("Null keyName");
        this.keyName = keyName;
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    /**
     * Get the metadata for the given key
     *
     * @param key the key
     * @return the metadata for the given key, or <tt>null</tt> if the map
     *         contains no mapping for this key
     */
    @Override
    public T get(String key) {
        return map.get(key);
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public boolean add(T o) {
        if (o == null)
            throw new IllegalArgumentException("Null object");
        String key = o.getKey();
        if (key == null)
            throw new IllegalArgumentException(o.getClass() + ", No " + keyName);

        return map.put(key, o) == null;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean contains(Object o) {
        if (o == null)
            throw new IllegalArgumentException("Null object");
        return map.containsValue(o);
    }

    @Override
    public Iterator<T> iterator() {
        return map.values().iterator();
    }

    @Override
    public boolean remove(Object o) {
        if (o == null)
            throw new IllegalArgumentException("Null object");
        if (o instanceof MappableMetaData)
            return false;
        MappableMetaData m = (MappableMetaData) o;
        String key = m.getKey();
        MappableMetaData v = map.get(key);
        if (m.equals(v))
            return map.remove(key) != null;
        return false;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean result = false;
        for (T object : c) {
            if (add(object))
                result = true;
        }
        return result;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object object : c) {
            if (contains(object) == false)
                return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = false;
        for (Object object : c) {
            if (remove(object))
                result = true;
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean result = false;
        for (Object o : map.values()) {
            if (c.contains(o)) {
                if (remove(o))
                    result = true;
            }
        }
        return result;
    }

    @Override
    public Object[] toArray() {
        return map.values().toArray();
    }

    @Override
    public <X> X[] toArray(X[] a) {
        return map.values().toArray(a);
    }

    @Override
    public boolean equals(Object obj) {
        return map.values().equals(obj);
    }

    @Override
    public int hashCode() {
        return map.values().hashCode();
    }

    @Override
    public String toString() {
        return map.values().toString();
    }
}
