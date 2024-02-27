/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * PersistenceContextReferencesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class PersistenceContextReferencesMetaData extends AbstractMappedMetaData<PersistenceContextReferenceMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 75353633724235761L;

    /**
     * Create a new PersistenceContextReferencesMetaData.
     */
    public PersistenceContextReferencesMetaData() {
        super("persistence-context-ref-name");
    }
}
