/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.parser.util;

/**
 * A versioned object.
 * @author Paul Ferraro
 */
public interface Versioned<V extends Version<V>> {

    V getVersion();

    /**
     * Indicates whether this version is at least as recent as the specified version.
     * @param version a version
     * @return true, if this version is at least as recent as the specified version, false otherwise.
     */
    default boolean since(V version) {
        return this.getVersion().compareTo(version) >= 0;
    }
}
