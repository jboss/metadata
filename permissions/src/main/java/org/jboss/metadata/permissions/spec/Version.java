/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.permissions.spec;

/**
 *
 * @author Eduardo Martins
 *
 */
public enum Version {

    // always first
    UNKNOWN(null),

    PERMISSIONS_7_0("7"),

    PERMISSIONS_9_0("9"),

    PERMISSIONS_10_0("10");

    private final String name;

    Version(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Version fromString(String s) {
        if(s.equals(PERMISSIONS_7_0.name)) {
            return PERMISSIONS_7_0;
        } else if (s.equals(PERMISSIONS_9_0.name)) {
            return PERMISSIONS_9_0;
        } else if (s.equals(PERMISSIONS_10_0.name)) {
            return PERMISSIONS_10_0;
        }
        return UNKNOWN;
    }

}
