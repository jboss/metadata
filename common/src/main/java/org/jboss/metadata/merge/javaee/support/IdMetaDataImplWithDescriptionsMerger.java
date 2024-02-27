/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.support;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * IdMetaDataImplWithDescriptions.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class IdMetaDataImplWithDescriptionsMerger extends IdMetaDataImplMerger {
    public static void merge(IdMetaDataImpl dest, IdMetaDataImpl override, IdMetaDataImpl original) {
        IdMetaDataImplMerger.merge(dest, override, original);
        IdMetaDataImplWithDescriptions id0 = (IdMetaDataImplWithDescriptions) override;
        IdMetaDataImplWithDescriptions id1 = (IdMetaDataImplWithDescriptions) original;
        IdMetaDataImplWithDescriptions dest1 = (IdMetaDataImplWithDescriptions) dest;
        if (id0 != null && id0.getDescriptions() != null)
            dest1.setDescriptions(id0.getDescriptions());
        else if (id1 != null && id1.getDescriptions() != null)
            dest1.setDescriptions(id1.getDescriptions());
    }

}
