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
