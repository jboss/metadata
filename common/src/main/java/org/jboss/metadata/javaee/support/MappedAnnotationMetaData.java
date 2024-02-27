/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.support;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.jboss.metadata.annotation.AbstractAnnotationImpl;

/**
 * MappedAnnotationMetaData.
 *
 * @param <T> the metadata type
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class MappedAnnotationMetaData<T extends MappableMetaData> extends AbstractAnnotationImpl implements
        Collection<T>, Serializable {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -3359670727781266839L;

    /**
     * The metaDatas
     */
    private Map<String, T> map = new NonNullLinkedHashMap<String, T>();

    /**
     * Create a new MappedAnnotationMetaData
     *
     * @param annotationType the annotation type
     */
    public MappedAnnotationMetaData(Class<? extends Annotation> annotationType) {
        super(annotationType);
    }

    /**
     * Get the metadata for the given key
     *
     * @param key the key
     * @return the key
     * @throws IllegalArgumentException for a null key
     */
    public T get(String key) {
        if (key == null)
            throw new IllegalArgumentException("Null key");
        return map.get(key);
    }

    @Override
    public boolean add(T o) {
        if (o == null)
            throw new IllegalArgumentException("Null object");
        String key = o.getKey();
        if (key == null)
            throw new IllegalArgumentException("Null key");

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
    public <T> T[] toArray(T[] a) {
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
