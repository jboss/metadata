/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.support.NamedMetaData;


/**
 * ActivationConfigPropertyMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ActivationConfigPropertyMetaData extends NamedMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -2551310342222240351L;

    /**
     * The value
     */
    private String value;

    /**
     * Create a new ActivationConfigMetaData.
     */
    public ActivationConfigPropertyMetaData() {
        // For serialization
    }

    /**
     * Get the activationConfigPropertyName.
     *
     * @return the activationConfigPropertyName.
     */
    public String getActivationConfigPropertyName() {
        return getName();
    }

    /**
     * Set the activationConfigPropertyName.
     *
     * @param activationConfigPropertyName the activationConfigPropertyName.
     * @throws IllegalArgumentException for a null activationConfigPropertyName
     */
    public void setActivationConfigPropertyName(String activationConfigPropertyName) {
        setName(activationConfigPropertyName);
    }

    /**
     * Get the value.
     *
     * @return the value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the value.
     *
     * @param value the value.
     * @throws IllegalArgumentException for a null value
     */
    public void setValue(String value) {
        if (value == null)
            throw new IllegalArgumentException("Null value");
        this.value = value;
    }
}
