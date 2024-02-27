/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.support;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * NonNullLinkedhashMap
 *
 * @param <K> the key type
 * @param <V> the value type
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class NonNullLinkedHashMap<K, V> extends LinkedHashMap<K, V> implements Serializable {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -5239003812587497285L;

    /**
     * Create a new NonNullLinkedHashMap.
     */
    public NonNullLinkedHashMap() {
        // For serialization
    }

    @Override
    public V put(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException("Null key");
        if (value == null)
            throw new IllegalArgumentException("Null value");
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (m == null)
            throw new IllegalArgumentException("Null map");
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            if (entry.getKey() == null)
                throw new IllegalArgumentException("Map contains a null key: " + m);
            if (entry.getValue() == null)
                throw new IllegalArgumentException("Map contains a null value: " + m);
        }
        super.putAll(m);
    }
}
