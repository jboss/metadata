/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 *
 * @author emmartins
 */
public class ManagedScheduledExecutorsMetaData extends AbstractMappedMetaData<ManagedScheduledExecutorMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 3048901048702142330L;

    /**
     */
    public ManagedScheduledExecutorsMetaData() {
        super("managed scheduled executor name");
    }
}
