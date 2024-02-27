/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * PersistenceUnitReferencesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class PersistenceUnitReferencesMetaData extends AbstractMappedMetaData<PersistenceUnitReferenceMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 5762149182578142090L;

    /**
     * Create a new PersistenceUnitReferencesMetaData.
     */
    public PersistenceUnitReferencesMetaData() {
        super("persistence-unit-ref-name");
    }
}
