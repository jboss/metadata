/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.annotation;

import java.lang.annotation.Annotation;

/**
 * AbstractAnnotationImpl.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AbstractAnnotationImpl implements Annotation {
    /**
     * The annotation type
     */
    private Class<? extends Annotation> annotationType;

    /**
     * Create a new AbstractAnnotationImpl.
     * <p/>
     * <p/>
     * This constructor guesses the annotationType.
     */
    @SuppressWarnings("unchecked")
    public AbstractAnnotationImpl() {
        for (Class clazz : getClass().getInterfaces()) {
            if (clazz.equals(Annotation.class) == false && (clazz.isAnnotation())) {
                annotationType = clazz;
                return;
            }
        }
    }

    /**
     * Create a new AbstractAnnotationImpl.
     *
     * @param annotationType the annotation type
     */
    public AbstractAnnotationImpl(Class<? extends Annotation> annotationType) {
        this.annotationType = annotationType;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return annotationType;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;
        if (object == null || object instanceof Annotation == false)
            return false;

        Annotation other = (Annotation) object;
        return annotationType.equals(other.annotationType());
    }

    @Override
    public int hashCode() {
        return annotationType.hashCode();
    }
}
