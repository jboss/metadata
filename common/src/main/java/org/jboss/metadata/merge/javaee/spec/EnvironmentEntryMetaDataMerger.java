/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.merge.javaee.support.ResourceInjectionMetaDataWithDescriptionsMerger;

/**
 * EnvironmentEntryMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class EnvironmentEntryMetaDataMerger extends ResourceInjectionMetaDataWithDescriptionsMerger {
    public static void merge(EnvironmentEntryMetaData dest, EnvironmentEntryMetaData override, EnvironmentEntryMetaData original) {
        ResourceInjectionMetaDataWithDescriptionsMerger.merge(dest, override, original);
        if (override != null && override.getType() != null)
            dest.setType(override.getType());
        else if (original != null && original.getType() != null)
            dest.setType(original.getType());
        if (override != null && override.getValue() != null)
            dest.setValue(override.getValue());
        else if (original != null && original.getValue() != null)
            dest.setValue(original.getValue());
    }

}
