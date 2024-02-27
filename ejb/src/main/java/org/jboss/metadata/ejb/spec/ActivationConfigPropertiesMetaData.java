/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;

/**
 * ActivationConfigPropertiesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ActivationConfigPropertiesMetaData extends AbstractMappedMetaData<ActivationConfigPropertyMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 8065908912716344436L;

    /**
     * Create a new ActivationConfigPropertiesMetaData.
     */
    public ActivationConfigPropertiesMetaData() {
        super("activation config property name");
    }

    public void merge(ActivationConfigPropertiesMetaData override, ActivationConfigPropertiesMetaData original) {
        IdMetaDataImplMerger.merge(this, override, original);
        if (original != null) {
            for (ActivationConfigPropertyMetaData property : original)
                add(property);
        }
        if (override != null) {
            for (ActivationConfigPropertyMetaData property : override)
                add(property);
        }
    }
}
