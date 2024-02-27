/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.NamedMetaData;

/**
 * PropertyMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class PropertyMetaData extends NamedMetaData {

    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -5215106879053864009L;
    /**
     * The value
     */
    private String value;

    /**
     * Create a new PropertyMetaData.
     */
    public PropertyMetaData() {
        // For serialization
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

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("{name=");
        tmp.append(super.getName());
        tmp.append(",value=");
        tmp.append(getValue());
        tmp.append('}');
        return tmp.toString();
    }
}
