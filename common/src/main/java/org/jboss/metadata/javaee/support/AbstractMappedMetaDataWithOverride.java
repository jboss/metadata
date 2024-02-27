/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.support;

/**
 * MappedMetaData.
 *
 * @param <C> the overriden component type
 * @param <T> the mapped type
 * @param <O> the overriden type
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractMappedMetaDataWithOverride<C extends MappableMetaData, T extends MappableMetaDataWithOverride<C>, O extends MappedMetaData<C>>
        extends AbstractMappedMetaData<T> implements MappedMetaDataWithOverride<C, T, O> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1646142944205417776L;

    /**
     * The overriden metadata
     */
    private O data;

    /**
     * Create a new MappedMetaData.
     *
     * @param keyName the key name
     */
    protected AbstractMappedMetaDataWithOverride(String keyName) {
        super(keyName);
    }

    @Override
    public O getOverridenMetaData() {
        return data;
    }

    // @SchemaProperty(ignore=true)
    @Override
    public void setOverridenMetaData(O data) {
        if (data == null)
            throw new IllegalArgumentException("Null data");
        this.data = data;
    }

    @Override
    public C createOriginal(T data) {
        throw new UnsupportedOperationException("Create originals from overrides is not supported for " + getClass().getName());
    }
}
