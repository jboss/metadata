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
 * IdMetaDataImpl.
 *
 * @param <T> the overridden metadata
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class IdMetaDataImplWithOverride<T> extends IdMetaDataImpl implements OverrideMetaData<T> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1178687564812348455L;

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
