/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.support;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import org.jboss.metadata.annotation.AbstractAnnotationImpl;

/**
 * NamedAnnotationMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class NamedAnnotationMetaData extends AbstractAnnotationImpl implements Serializable, MappableMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 2705312164569803721L;

    /**
     * The name
     */
    private String name;

    /**
     * Create a new NamedMetaData.
     *
     * @param annotationType the annotation type
     */
    public NamedAnnotationMetaData(Class<? extends Annotation> annotationType) {
        super(annotationType);
    }

    @Override
    public String getKey() {
        return getName();
    }

    /**
     * Get the name.
     *
     * @return the name.
     */
    public String name() {
        return name;
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
        NamedAnnotationMetaData other = (NamedAnnotationMetaData) obj;
        String otherName = other.getName();
        return name.equals(otherName);
    }

    @Override
    public int hashCode() {
        String name = getName();
        return name.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + "{" + getName() + "}";
    }
}
