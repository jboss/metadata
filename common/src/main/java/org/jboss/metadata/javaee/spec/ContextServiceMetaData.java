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

import java.util.Set;

/**
 *
 * @author emmartins
 */
public class ContextServiceMetaData extends NamedMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 6886722064122852797L;

    /**
     *
     */
    private Set<String> cleared;
    /**
     *
     */
    private Set<String> propagated;
    /**
     *
     */
    private PropertiesMetaData properties;
    /**
     *
     */
    private Set<String> unchanged;

    /**
     *
     */
    public ContextServiceMetaData() {
        // For serialization
    }

    /**
     *
     * @return cleared, may be null
     */
    public Set<String> getCleared() {
        return cleared;
    }

    /**
     *
     * @param cleared
     */
    public void setCleared(Set<String> cleared) {
        this.cleared = cleared;
    }

    /**
     *
     * @return propagated, may be null
     */
    public Set<String> getPropagated() {
        return propagated;
    }

    /**
     *
     * @param propagated
     */
    public void setPropagated(Set<String> propagated) {
        this.propagated = propagated;
    }

    /**
     *
     * @return properties, may be null
     */
    public PropertiesMetaData getProperties() {
        return properties;
    }

    /**
     *
     * @param properties
     */
    public void setProperties(PropertiesMetaData properties) {
        this.properties = properties;
    }

    /**
     *
     * @return unchanged, may be null
     */
    public Set<String> getUnchanged() {
        return unchanged;
    }

    /**
     *
     * @param unchanged
     */
    public void setUnchanged(Set<String> unchanged) {
        this.unchanged = unchanged;
    }
}
