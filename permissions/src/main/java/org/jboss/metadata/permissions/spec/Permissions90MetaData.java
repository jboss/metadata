/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.permissions.spec;

/**
 * Metadata for Jakarta EE 9 permissions.xml
 *
 * @author Brian Stansberry
 *
 */
public class Permissions90MetaData extends Permissions70MetaData {

    /**
     *
     */
    private static final long serialVersionUID = 2479654434987744459L;

    public Version getVersion() {
        return Version.PERMISSIONS_9_0;
    }
}
