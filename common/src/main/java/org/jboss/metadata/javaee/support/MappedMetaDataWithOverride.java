/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.support;

/**
 * MappedMetaDataWithOverride.
 *
 * @param <C> the overriden component type
 * @param <T> the mapped type
 * @param <O> the overriden type
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public interface MappedMetaDataWithOverride<C extends MappableMetaData, T extends MappableMetaDataWithOverride<C>, O extends MappedMetaData<C>>
        extends MappedMetaData<T>, OverrideMetaData<O> {
    /**
     * Create an override metadata
     *
     * @param data the overridden metadata
     * @return the override metadata
     */
    T createOverride(C data);

    /**
     * Create an original metadata
     *
     * @param data the overridden metadata
     * @return the original
     */
    C createOriginal(T data);
}
