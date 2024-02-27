/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.jboss;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * AnnotationPropertyMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class AnnotationPropertyMetaData extends NamedMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -8547813151720473321L;

    /**
     * The property value
     */
    private String propertyValue;

    /**
     * Get the propertyName.
     *
     * @return the propertyName.
     */
    public String getPropertyName() {
        return getName();
    }

    /**
     * Set the propertyName.
     *
     * @param propertyName the propertyName.
     * @throws IllegalArgumentException for a null propertyName
     */
    public void setPropertyName(String propertyName) {
        setName(propertyName);
    }

    /**
     * Get the propertyValue.
     *
     * @return the propertyValue.
     */
    public String getPropertyValue() {
        return propertyValue;
    }

    /**
     * Set the propertyValue.
     *
     * @param propertyValue the propertyValue.
     * @throws IllegalArgumentException for a null propertyValue
     */
    public void setPropertyValue(String propertyValue) {
        if (propertyValue == null)
            throw new IllegalArgumentException("Null propertyValue");
        this.propertyValue = propertyValue;
    }
}
