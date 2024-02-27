/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 *
 * @author emmartins
 */
public class ManagedExecutorMetaData extends NamedMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 2129990191883873784L;

    /**
     *
     */
    private String contextServiceRef;

    /**
     *
     */
    private Integer hungTaskThreshold;

    /**
     *
     */
    private Integer maxAsync;

    /**
     *
     */
    private PropertiesMetaData properties;

    /**
     *
     */
    public ManagedExecutorMetaData() {
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
     * @return the value of maxAsync
     */
    public Integer getMaxAsync() {
        return maxAsync;
    }

    /**
     *
     * @param maxAsync the maxAsync to set
     */
    public void setMaxAsync(Integer maxAsync) {
        this.maxAsync = maxAsync;
    }

    /**
     *
     * @return the value of hungTaskThreshold
     */
    public Integer getHungTaskThreshold() {
        return hungTaskThreshold;
    }

    /**
     *
     * @param hungTaskThreshold the hungTaskThreshold to set
     */
    public void setHungTaskThreshold(Integer hungTaskThreshold) {
        this.hungTaskThreshold = hungTaskThreshold;
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
