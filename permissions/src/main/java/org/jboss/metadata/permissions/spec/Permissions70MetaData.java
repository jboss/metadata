/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.permissions.spec;

import java.util.ArrayList;

/**
 * Metadata for Java EE 7 permissions.xml
 *
 * @author Eduardo Martins
 *
 */
public class Permissions70MetaData extends ArrayList<Permission70MetaData> {

    /**
     *
     */
    private static final long serialVersionUID = 2479654434987744459L;

    public Version getVersion() {
        return Version.PERMISSIONS_7_0;
    }

}
