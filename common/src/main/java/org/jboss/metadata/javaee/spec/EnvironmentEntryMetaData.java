/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.ResourceInjectionMetaDataWithDescriptions;

/**
 * EnvironmentEntryMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class EnvironmentEntryMetaData extends ResourceInjectionMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -4919146521508624575L;

    /**
     * The type
     */
    private String type;

    /**
     * The value
     */
    private String value;

    /**
     * Create a new EnvironmentEntryMetaData.
     */
    public EnvironmentEntryMetaData() {
        // For serialization
    }

    /**
     * Get the envEntryName.
     *
     * @return the envEntryName.
     */
    public String getEnvEntryName() {
        return getName();
    }

    /**
     * Set the envEntryName.
     *
     * @param envEntryName the envEntryName.
     * @throws IllegalArgumentException for a null envEntryName
     */
    public void setEnvEntryName(String envEntryName) {
        setName(envEntryName);
    }

    /**
     * Get the type.
     *
     * @return the type.
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type.
     *
     * @param type the type.
     * @throws IllegalArgumentException for a null type
     */
    public void setType(String type) {
        if (type == null)
            throw new IllegalArgumentException("Null type");
        this.type = type;
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
