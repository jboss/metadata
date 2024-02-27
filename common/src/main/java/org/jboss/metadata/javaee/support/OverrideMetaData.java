/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.support;

/**
 * OverrideMetaData.
 *
 * @param <T> the overriden metadata type
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public interface OverrideMetaData<T> {
    /**
     * Get the overriden metadata
     *
     * @return the overridden metadata
     */
    T getOverridenMetaData();

    /**
     * Set the overriden metadata
     *
     * @param data the overridden metadata
     * @throws IllegalArgumentException for null data
     */
    void setOverridenMetaData(T data);
}
