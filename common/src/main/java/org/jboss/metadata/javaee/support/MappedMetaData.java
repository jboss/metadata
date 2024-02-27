/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.support;

import java.util.Collection;
import java.util.Set;

/**
 * MappedMetaData.
 *
 * @param <T> the mapped type
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public interface MappedMetaData<T extends MappableMetaData> extends IdMetaData, Collection<T> {
    /**
     * Returns <tt>true</tt> if this mapped meta data contains a meta data entry
     * for the specified key.
     *
     * @param key the key of the mappable meta data
     * @return <tt>true</tt> if the key can be found
     */
    boolean containsKey(String key);

    /**
     * Get a value
     *
     * @param key the key
     * @return the value
     */
    T get(String key);

    /**
     * Get the key set
     *
     * @return the key set
     */
    Set<String> keySet();
}
