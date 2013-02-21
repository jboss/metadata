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
