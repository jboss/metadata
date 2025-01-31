/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.jbossweb;

@Deprecated
public class Location {
    /**
     * @deprecated Use {@link Version#getVersion(String)} instead.
     */
    @Deprecated
    public static Version getVersion(String location) {
        return Version.fromLocation(location);
    }
}
