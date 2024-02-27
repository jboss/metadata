/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.permissions.spec;

public class Permissions100MetaData extends Permissions90MetaData {

    private static final long serialVersionUID = -2338626292552177485L;

    @Override
    public Version getVersion() {
        return Version.PERMISSIONS_10_0;
    }
}
