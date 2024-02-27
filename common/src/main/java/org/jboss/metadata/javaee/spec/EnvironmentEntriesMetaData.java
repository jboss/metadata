/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * EnvironmentEntriesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class EnvironmentEntriesMetaData extends AbstractMappedMetaData<EnvironmentEntryMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -7573363440492577599L;

    /**
     * Create a new EnvironmentEntriesMetaData.
     */
    public EnvironmentEntriesMetaData() {
        super("env entry name");
    }
}
