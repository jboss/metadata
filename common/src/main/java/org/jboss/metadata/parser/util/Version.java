/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.parser.util;

/**
 * Encapsulates the version of some metadata.
 * @author Paul Ferraro
 */
public interface Version<V extends Version<V>> extends Comparable<V> {

    /**
     * Returns the major component of this version.
     * @return the major component of this version.
     */
    int major();

    /**
     * Returns the minor component of this version.
     * @return the manor component of this version.
     */
    int minor();

    @Override
    default int compareTo(V version) {
        int result = Integer.compare(this.major(), this.minor());
        return (result == 0) ? Integer.compare(this.minor(), version.minor()) : result;
    }
}
