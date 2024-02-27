/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.support;

/**
 * IdMetaDataImplWithDescriptionsWithOverride.
 *
 * @param <T> the overriden type
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class IdMetaDataImplWithDescriptionsWithOverride<T> extends IdMetaDataImplWithDescriptions implements
        OverrideMetaData<T> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 3513592008103433460L;

    /**
     * The overriden metadata
     */
    private T data;

    @Override
    public T getOverridenMetaData() {
        return data;
    }

    /**
     * Get the override metadata
     *
     * @return the data
     * @throws IllegalStateException when not set
     */
    protected T getOverridenMetaDataWithCheck() {
        if (data == null)
            throw new IllegalStateException("Override metadata has not been set " + this);
        return data;
    }

    // @SchemaProperty(ignore=true)
    @Override
    public void setOverridenMetaData(T data) {
        if (data == null)
            throw new IllegalArgumentException("Null data");
        this.data = data;
    }
}
