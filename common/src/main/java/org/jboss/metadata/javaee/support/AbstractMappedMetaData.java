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
import java.util.Collections;
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
public abstract class AbstractMappedMetaData<T extends MappableMetaData> extends IdMetaDataImpl implements MappedMetaData<T> {
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
    private Map<String, T> map;

    /**
     * Get By Name
     *
     * @param <T>  the type
     * @param name the name
     * @param map  the map
     * @return the result
     */
    public static <T extends MappableMetaData> T getByName(String name, MappedMetaData<T> map) {
        if (name == null)
            throw new IllegalArgumentException("Null name");
        if (map == null)
            return null;
        return map.get(name);
    }

    /**
     * Create a new MappedMetaData.
     *
     * @param keyName the key name
     */
    protected AbstractMappedMetaData(String keyName) {
        if (keyName == null)
            throw new IllegalArgumentException("Null keyName");
        this.keyName = keyName;
    }

    @Override
    public boolean containsKey(String key) {
        boolean containsKey = false;
        if (map != null)
            containsKey = map.containsKey(key);
        return containsKey;
    }

    /**
     * Get the key set
     *
     * @return the key set
     */
    @Override
    public Set<String> keySet() {
        if (map == null)
            return Collections.emptySet();
        return Collections.unmodifiableSet(map.keySet());
    }

    /**
     * Get the metadata for the given key
     *
     * @param key the key
     * @return the key
     * @throws IllegalArgumentException for a null key
     */
    @Override
    public T get(String key) {
        if (key == null)
            throw new IllegalArgumentException(keyName + " (/key) is null");
        if (map == null)
            return null;
        return map.get(key);
    }

    @Override
    public boolean add(T o) {
        if (o == null)
            throw new IllegalArgumentException("Null object");
        String key = o.getKey();
        if (key == null)
            throw new IllegalArgumentException(keyName + " (/key) is null");

        if (map == null)
            map = new NonNullLinkedHashMap<String, T>();
        T result = map.put(key, o);
        if (result != null)
            removeNotification(result);
        addNotification(o);
        return true;
    }

    @Override
    public void clear() {
        if (map != null)
            for (T t : map.values())
                removeNotification(t);
        map = null;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null)
            throw new IllegalArgumentException("Null object");
        if (map == null)
            return false;
        return map.containsValue(o);
    }

    @Override
    public Iterator<T> iterator() {
        if (map == null) {
            Collection<T> result = Collections.emptyList();
            return result.iterator();
        }
        return Collections.unmodifiableCollection(map.values()).iterator();
    }

    @Override
    public boolean remove(Object o) {
        if (o == null)
            throw new IllegalArgumentException("Null object");
        if (o instanceof MappableMetaData)
            return false;

        if (map == null)
            return false;

        MappableMetaData m = (MappableMetaData) o;
        String key = m.getKey();
        MappableMetaData v = map.get(key);
        if (m.equals(v)) {
            T result = map.remove(key);
            if (result != null) {
                removeNotification(result);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        if (map == null)
            return 0;
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
        if (map == null)
            return c.isEmpty();

        for (Object object : c) {
            if (contains(object) == false)
                return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        if (map == null)
            return true;
        return size() == 0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (map == null)
            return false;

        boolean result = false;
        for (Object object : c) {
            if (remove(object))
                result = true;
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (map == null)
            return false;

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
        if (map == null)
            return new Object[0];
        return map.values().toArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <X> X[] toArray(X[] a) {
        if (map == null)
            return (X[]) new Object[0];
        return map.values().toArray(a);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj instanceof Collection == false)
            return false;
        Collection<?> other = (Collection<?>) obj;

        if (size() != other.size())
            return false;

        if (map == null)
            return true;

        return other.containsAll(map.values());
    }

    @Override
    public int hashCode() {
        if (map == null)
            return 0;
        return map.values().hashCode();
    }

    @Override
    public String toString() {
        if (map == null)
            return "[]";
        return map.values().toString();
    }

    /**
     * Notification that something was added
     *
     * @param added the thing added
     */
    protected void addNotification(T added) {
        // Nothing
    }

    /**
     * Notification that something was removed
     *
     * @param removed the thing removed
     */
    protected void removeNotification(T removed) {
        // Nothing
    }
}
