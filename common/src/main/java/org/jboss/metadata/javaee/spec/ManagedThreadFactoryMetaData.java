/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 *
 * @author emmartins
 */
public class ManagedThreadFactoryMetaData extends NamedMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 2455129592434504220L;

    /**
     *
     */
    private String contextServiceRef;

    /**
     *
     */
    private int priority = Thread.NORM_PRIORITY;

    /**
     *
     */
    private PropertiesMetaData properties;

    /**
     *
     */
    public ManagedThreadFactoryMetaData() {
        // For serialization
    }

    /**
     *
     * @return the value of contextServiceRef.
     */
    public String getContextServiceRef() {
        return contextServiceRef;
    }

    /**
     *
     * @param contextServiceRef the contextServiceRef to set
     */
    public void setContextServiceRef(String contextServiceRef) {
        this.contextServiceRef = contextServiceRef;
    }

    /**
     *
     * @return the value of hungTaskThreshold
     */
    public int getPriority() {
        return priority;
    }

    /**
     *
     * @param priority the hungTaskThreshold to set
     */
    public void setPriority(int priority) {
        if (priority < Thread.MIN_PRIORITY || priority > Thread.MAX_PRIORITY) {
            throw new IllegalArgumentException("priority must be >= Thread.MIN_PRIORITY and <= Thread.MAX_PRIORITY");
        }
        this.priority = priority;
    }

    /**
     *
     * @return the value of properties
     */
    public PropertiesMetaData getProperties() {
        return properties;
    }

    /**
     *
     * @param properties the properties to set
     */
    public void setProperties(PropertiesMetaData properties) {
        this.properties = properties;
    }

}
