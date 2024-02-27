/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.support;

/**
 * NamedMetaData.
 *
 * @param <T> the overriden type
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class NamedMetaDataWithDescriptionGroupWithOverride<T extends MappableMetaData> extends
        NamedMetaDataWithDescriptionGroup implements MappableMetaDataWithOverride<T> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 281861015522919956L;

    /**
     * The overriden metadata
     */
    private T data;

    /**
     * Make sure there is an overriden metadata set. If there is none, then
     * create one from the given class.
     *
     * @param cls the class of the overriden metadata
     * @return an instance of overriden metadata, never null
     */
    protected T ensureOverride(Class<? extends T> cls) {
        try {
            if (data == null)
                data = cls.newInstance();
            return data;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T getOverridenMetaData() {
        return data;
    }

    /**
     * Get the overriden metadata
     *
     * @return the overriden metadata
     * @throws IllegalStateException if there is no overriden metadata
     */
    public T getOverridenMetaDataWithCheck() {
        if (data == null)
            throw new IllegalStateException("No overriden metadata");
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
