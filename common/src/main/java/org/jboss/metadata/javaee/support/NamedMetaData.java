/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.support;

/**
 * NamedMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class NamedMetaData extends IdMetaDataImpl implements MappableMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -2918254376676527511L;

    /**
     * The name
     */
    private String name;

    @Override
    public String getKey() {
        return getName();
    }

    /**
     * Get the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name.
     *
     * @param name the name.
     * @throws IllegalArgumentException for a null name
     */
    public void setName(String name) {
        if (name == null)
            throw new IllegalArgumentException("Null name");
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() == getClass() == false)
            return false;
        String name = getName();
        NamedMetaData other = (NamedMetaData) obj;
        String otherName = other.getName();
        return name.equals(otherName);
    }

    @Override
    public int hashCode() {
        String name = getName();
        if (name == null)
            return 0;
        return name.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + "{" + getName() + "}";
    }
}
