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
package org.jboss.metadata.javaee.jboss;

import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * AnnotationMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class AnnotationMetaData extends NamedMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -8547813151720473321L;

    /**
     * The impl class
     */
    private String annotationImplementationClass;

    /**
     * The resource injection target
     */
    private ResourceInjectionTargetMetaData injectionTarget;

    /**
     * The annotation properties
     */
    private AnnotationPropertiesMetaData properties;

    /**
     * Get the annotationClass.
     *
     * @return the annotationClass.
     */
    public String getAnnotationClass() {
        return getName();
    }

    /**
     * Set the annotationClass.
     *
     * @param annotationClass the annotationClass.
     * @throws IllegalArgumentException for a null annotationClass
     */
    public void setAnnotationClass(String annotationClass) {
        super.setName(annotationClass);
    }

    /**
     * Get the annotationImplementationClass.
     *
     * @return the annotationImplementationClass.
     */
    public String getAnnotationImplementationClass() {
        return annotationImplementationClass;
    }

    /**
     * Set the annotationImplementationClass.
     *
     * @param annotationImplementationClass the annotationImplementationClass.
     * @throws IllegalArgumentException for a null annotationImplementationClass
     */
    public void setAnnotationImplementationClass(String annotationImplementationClass) {
        if (annotationImplementationClass == null)
            throw new IllegalArgumentException("Null annotationImplementationClass");
        this.annotationImplementationClass = annotationImplementationClass;
    }

    /**
     * Get the injectionTarget.
     *
     * @return the injectionTarget.
     */
    public ResourceInjectionTargetMetaData getInjectionTarget() {
        return injectionTarget;
    }

    /**
     * Set the injectionTarget.
     *
     * @param injectionTarget the injectionTarget.
     * @throws IllegalArgumentException for a null injectionTarget
     */
    public void setInjectionTarget(ResourceInjectionTargetMetaData injectionTarget) {
        if (injectionTarget == null)
            throw new IllegalArgumentException("Null injectionTarget");
        this.injectionTarget = injectionTarget;
    }

    /**
     * Get the properties.
     *
     * @return the properties.
     */
    public AnnotationPropertiesMetaData getProperties() {
        return properties;
    }

    /**
     * Set the properties.
     *
     * @param properties the properties.
     * @throws IllegalArgumentException for a null properties
     */
    public void setProperties(AnnotationPropertiesMetaData properties) {
        if (properties == null)
            throw new IllegalArgumentException("Null properties");
        this.properties = properties;
    }
}
