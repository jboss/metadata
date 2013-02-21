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
package org.jboss.metadata.annotation.finder;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * The default annotation processor finder will look for annotations
 * directly on the annotated element.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 65638 $
 */
public class DefaultAnnotationFinder<E extends AnnotatedElement> implements AnnotationFinder<E> {
    public <T extends Annotation> T getAnnotation(E element, Class<T> annotationType) {
        return element.getAnnotation(annotationType);
    }

    public Annotation[] getAnnotations(E element) {
        return element.getAnnotations();
    }

    public Annotation[] getDeclaredAnnotations(E element) {
        return element.getDeclaredAnnotations();
    }

    public boolean isAnnotationPresent(E element, Class<? extends Annotation> annotationType) {
        return element.isAnnotationPresent(annotationType);
    }
}
