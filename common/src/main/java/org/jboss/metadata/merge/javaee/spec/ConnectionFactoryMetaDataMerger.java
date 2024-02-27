/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.ConnectionFactoryMetaData;
import org.jboss.metadata.merge.javaee.support.NamedMetaDataMerger;

/**
 *
 * @author Eduardo Martins
 *
 */

public class ConnectionFactoryMetaDataMerger {

    public static ConnectionFactoryMetaData merge(ConnectionFactoryMetaData dest, ConnectionFactoryMetaData original) {
        ConnectionFactoryMetaData merged = new ConnectionFactoryMetaData();
        merge(merged, dest, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(ConnectionFactoryMetaData dest, ConnectionFactoryMetaData override, ConnectionFactoryMetaData original) {

        NamedMetaDataMerger.merge(dest, override, original);

        if (override != null && override.getInterfaceName() != null)
            dest.setInterfaceName(override.getInterfaceName());
        else if (original != null && original.getInterfaceName() != null)
            dest.setInterfaceName(original.getInterfaceName());

        if (override != null && override.getResourceAdapter() != null)
            dest.setResourceAdapter(override.getResourceAdapter());
        else if (original != null && original.getResourceAdapter() != null)
            dest.setResourceAdapter(original.getResourceAdapter());

        if (override != null && override.getProperties() != null)
            dest.setProperties(override.getProperties());
        else if (original != null && original.getProperties() != null)
            dest.setProperties(original.getProperties());

        if (override != null && override.getTransactionSupport() != null)
            dest.setTransactionSupport(override.getTransactionSupport());
        else if (original != null && original.getTransactionSupport() != null)
            dest.setTransactionSupport(original.getTransactionSupport());

        if (override != null && override.getMaxPoolSize() != ConnectionFactoryMetaData.DEFAULT_MAX_POOL_SIZE)
            dest.setMaxPoolSize(override.getMaxPoolSize());
        else if (original != null && original.getMaxPoolSize() != ConnectionFactoryMetaData.DEFAULT_MAX_POOL_SIZE)
            dest.setMaxPoolSize(original.getMaxPoolSize());

        if (override != null && override.getMinPoolSize() != ConnectionFactoryMetaData.DEFAULT_MIN_POOL_SIZE)
            dest.setMinPoolSize(override.getMinPoolSize());
        else if (original != null && original.getMinPoolSize() != ConnectionFactoryMetaData.DEFAULT_MIN_POOL_SIZE)
            dest.setMinPoolSize(original.getMinPoolSize());

    }

}
