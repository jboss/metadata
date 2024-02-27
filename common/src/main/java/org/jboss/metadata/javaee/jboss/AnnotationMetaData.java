/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
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
