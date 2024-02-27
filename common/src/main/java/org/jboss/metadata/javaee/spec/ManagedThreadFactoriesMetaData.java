/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 *
 * @author emmartins
 */
public class ManagedThreadFactoriesMetaData extends AbstractMappedMetaData<ManagedThreadFactoryMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 7373169183167961264L;

    /**
     */
    public ManagedThreadFactoriesMetaData() {
        super("managed thread factory name");
    }
}
