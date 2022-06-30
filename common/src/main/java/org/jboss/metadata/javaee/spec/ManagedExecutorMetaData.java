/*
 * JBoss, Home of Professional Open Source
 * Copyright 2022, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
