/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.support;

import org.jboss.metadata.javaee.support.NamedMetaData;
import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptionGroup;

/**
 * NamedMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class NamedMetaDataWithDescriptionGroupMerger extends NamedMetaDataMerger {
    public static void merge(NamedMetaData dest, NamedMetaData override, NamedMetaData original) {
        NamedMetaDataMerger.merge(dest, override, original);
        NamedMetaDataWithDescriptionGroup n0 = (NamedMetaDataWithDescriptionGroup) override;
        NamedMetaDataWithDescriptionGroup n1 = (NamedMetaDataWithDescriptionGroup) original;
        NamedMetaDataWithDescriptionGroup dest1 = (NamedMetaDataWithDescriptionGroup) dest;
        if (n0 != null && n0.getDescriptionGroup() != null)
            dest1.setDescriptionGroup(n0.getDescriptionGroup());
        else if (n1 != null && n1.getDescriptionGroup() != null)
            dest1.setDescriptionGroup(n1.getDescriptionGroup());
    }

}
