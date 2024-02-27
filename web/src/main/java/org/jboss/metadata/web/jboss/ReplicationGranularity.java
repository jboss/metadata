/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.jboss;

@Deprecated
public enum ReplicationGranularity {
    SESSION, ATTRIBUTE;

    public static ReplicationGranularity fromString(String granularity) {
        return (granularity == null ? SESSION : Enum.valueOf(ReplicationGranularity.class, granularity));
    }
}
