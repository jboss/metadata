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
