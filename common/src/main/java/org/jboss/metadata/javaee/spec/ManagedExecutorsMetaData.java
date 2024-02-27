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
public class ManagedExecutorsMetaData extends AbstractMappedMetaData<ManagedExecutorMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -6198704374773701337L;

    /**
     */
    public ManagedExecutorsMetaData() {
        super("managed executor name");
    }
}
