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

import org.jboss.annotation.javaee.Description;
import org.jboss.metadata.annotation.AbstractAnnotationImpl;

/**
 * LanguageMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class LanguageMetaData extends AbstractAnnotationImpl implements Serializable, MappableMetaData, IdMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 64867085990650156L;

    /**
     * The id
     */
    private String id;

    /**
     * The language
     */
    private String language = Description.DEFAULT_LANGUAGE;

    /**
     * Create a new LanguageMetaData.
     *
     * @param annotationType the annotation type
     */
    public LanguageMetaData(Class<? extends Annotation> annotationType) {
        super(annotationType);
    }

    public String language() {
        return getLanguage();
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
    public String getKey() {
        return getLanguage();
    }

    /**
     * Get the language.
     *
     * @return the language.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Set the Langauge.
     *
     * @param language the language.
     * @throws IllegalArgumentException for a null name
     */
    public void setLanguage(String language) {
        if (language == null)
            throw new IllegalArgumentException("Null language");
        this.language = language;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() == getClass() == false)
            return false;
        String name = getLanguage();
        LanguageMetaData other = (LanguageMetaData) obj;
        String otherName = other.getLanguage();
        return name.equals(otherName);
    }

    @Override
    public int hashCode() {
        String name = getLanguage();
        return name.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + "{" + getLanguage() + "}";
    }
}
