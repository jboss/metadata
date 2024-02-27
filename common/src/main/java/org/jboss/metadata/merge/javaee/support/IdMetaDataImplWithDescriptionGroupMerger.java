/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.support;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;

/**
 * IdMetaDataImplWithDescriptionGroup.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class IdMetaDataImplWithDescriptionGroupMerger extends IdMetaDataImplMerger {
    public static void merge(IdMetaDataImplWithDescriptionGroup dest, IdMetaDataImplWithDescriptionGroup override, IdMetaDataImplWithDescriptionGroup original) {
        IdMetaDataImplMerger.merge(dest, override, original);
        if (override != null && override.getDescriptionGroup() != null)
            dest.setDescriptionGroup(override.getDescriptionGroup());
        else if (original != null && original.getDescriptionGroup() != null)
            dest.setDescriptionGroup(original.getDescriptionGroup());
    }
}
