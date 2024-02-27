/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.support;

import org.jboss.metadata.javaee.support.NamedMetaData;

/**
 * NamedMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class NamedMetaDataMerger extends IdMetaDataImplMerger {

    /**
     * Merge override + original into this
     *
     * @param override data which overrides original
     * @param original the original data
     * @throws IllegalArgumentException for a null merged or original
     * @throws IllegalStateException    if neither have a name
     */
    public static void merge(NamedMetaData dest, NamedMetaData override, NamedMetaData original) {
        IdMetaDataImplMerger.merge(dest, override, original);
        if (override != null && override.getName() != null)
            dest.setName(override.getName());
        else if (original != null && original.getName() != null)
            dest.setName(original.getName());
        else
            throw new IllegalStateException("Neither the override metadata " + override + " or the original " + original
                    + " have a name.");
    }
}
