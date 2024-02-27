/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.AdministeredObjectMetaData;
import org.jboss.metadata.merge.javaee.support.NamedMetaDataMerger;

/**
 *
 * @author Eduardo Martins
 *
 */

public class AdministeredObjectMetaDataMerger {

    public static AdministeredObjectMetaData merge(AdministeredObjectMetaData dest, AdministeredObjectMetaData original) {
        AdministeredObjectMetaData merged = new AdministeredObjectMetaData();
        merge(merged, dest, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(AdministeredObjectMetaData dest, AdministeredObjectMetaData override, AdministeredObjectMetaData original) {

        NamedMetaDataMerger.merge(dest, override, original);

        if (override != null && override.getInterfaceName() != null)
            dest.setInterfaceName(override.getInterfaceName());
        else if (original != null && original.getInterfaceName() != null)
            dest.setInterfaceName(original.getInterfaceName());

        if (override != null && override.getClassName() != null)
            dest.setClassName(override.getClassName());
        else if (original != null && original.getClassName() != null)
            dest.setClassName(original.getClassName());

        if (override != null && override.getResourceAdapter() != null)
            dest.setResourceAdapter(override.getResourceAdapter());
        else if (original != null && original.getResourceAdapter() != null)
            dest.setResourceAdapter(original.getResourceAdapter());

        if (override != null && override.getProperties() != null)
            dest.setProperties(override.getProperties());
        else if (original != null && original.getProperties() != null)
            dest.setProperties(original.getProperties());

    }

}
