/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
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
