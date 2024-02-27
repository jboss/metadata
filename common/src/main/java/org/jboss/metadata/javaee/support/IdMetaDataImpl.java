/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.support;

// HACK import org.jboss.util.UnreachableStatementException;

/**
 * IdMetaDataImpl.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class IdMetaDataImpl implements IdMetaData, Cloneable {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -2952233733011178332L;

    /**
     * The id
     */
    String id;

    /**
     * Create a new IdMetaDataImpl.
     */
    public IdMetaDataImpl() {
        // For serialization
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        if (id == null)
            throw new IllegalArgumentException("Null id");
        this.id = id;
    }

    @Override
    public IdMetaDataImpl clone() {
        try {
            return (IdMetaDataImpl) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final IdMetaDataImpl other = (IdMetaDataImpl) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
