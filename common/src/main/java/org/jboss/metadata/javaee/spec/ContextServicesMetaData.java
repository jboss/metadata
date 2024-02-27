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
public class ContextServicesMetaData extends AbstractMappedMetaData<ContextServiceMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 6163806016818910806L;

    /**
     */
    public ContextServicesMetaData() {
        super("context service name");
    }
}
