/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;

/**
 * CMPFieldsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class CMPFieldsMetaData extends AbstractMappedMetaData<CMPFieldMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -8076951897867288171L;

    /**
     * Create a new CMPFieldsMetaData.
     */
    public CMPFieldsMetaData() {
        super("cmp field name");
    }

    public void merge(CMPFieldsMetaData override, CMPFieldsMetaData original) {
        IdMetaDataImplMerger.merge(this, override, original);
        if (original != null) {
            for (CMPFieldMetaData property : original)
                add(property);
        }
        if (override != null) {
            for (CMPFieldMetaData property : override)
                add(property);
        }
    }
}
