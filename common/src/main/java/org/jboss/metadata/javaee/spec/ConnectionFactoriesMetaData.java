/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 *
 * @author Eduardo Martins
 *
 */
public class ConnectionFactoriesMetaData extends AbstractMappedMetaData<ConnectionFactoryMetaData> {

    private static final long serialVersionUID = 1;

    public ConnectionFactoriesMetaData() {
        super("connection factories");
    }
}
