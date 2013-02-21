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
