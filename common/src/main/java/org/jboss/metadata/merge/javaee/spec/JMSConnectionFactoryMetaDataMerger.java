/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.JMSConnectionFactoryMetaData;
import org.jboss.metadata.merge.javaee.support.NamedMetaDataMerger;

/**
 *
 * @author Eduardo Martins
 *
 */
public class JMSConnectionFactoryMetaDataMerger {

    public static JMSConnectionFactoryMetaData merge(JMSConnectionFactoryMetaData dest, JMSConnectionFactoryMetaData original) {
        JMSConnectionFactoryMetaData merged = new JMSConnectionFactoryMetaData();
        merge(merged, dest, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(JMSConnectionFactoryMetaData dest, JMSConnectionFactoryMetaData override, JMSConnectionFactoryMetaData original) {

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

        if (override != null && override.getUser() != null)
            dest.setUser(override.getUser());
        else if (original != null && original.getUser() != null)
            dest.setUser(original.getUser());

        if (override != null && override.getPassword() != null)
            dest.setPassword(override.getPassword());
        else if (original != null && original.getPassword() != null)
            dest.setPassword(original.getPassword());

        if (override != null && override.getClientId() != null)
            dest.setClientId(override.getClientId());
        else if (original != null && original.getClientId() != null)
            dest.setClientId(original.getClientId());

        if (override != null && override.getProperties() != null)
            dest.setProperties(override.getProperties());
        else if (original != null && original.getProperties() != null)
            dest.setProperties(original.getProperties());

        if (override != null && override.getTransactional() != JMSConnectionFactoryMetaData.DEFAULT_TRANSACTIONAL)
            dest.setTransactional(override.getTransactional());
        else if (original != null && original.getTransactional() != JMSConnectionFactoryMetaData.DEFAULT_TRANSACTIONAL)
            dest.setTransactional(original.getTransactional());

        if (override != null && override.getMaxPoolSize() != JMSConnectionFactoryMetaData.DEFAULT_MAX_POOL_SIZE)
            dest.setMaxPoolSize(override.getMaxPoolSize());
        else if (original != null && original.getMaxPoolSize() != JMSConnectionFactoryMetaData.DEFAULT_MAX_POOL_SIZE)
            dest.setMaxPoolSize(original.getMaxPoolSize());

        if (override != null && override.getMinPoolSize() != JMSConnectionFactoryMetaData.DEFAULT_MIN_POOL_SIZE)
            dest.setMinPoolSize(override.getMinPoolSize());
        else if (original != null && original.getMinPoolSize() != JMSConnectionFactoryMetaData.DEFAULT_MIN_POOL_SIZE)
            dest.setMinPoolSize(original.getMinPoolSize());

    }

}
