/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
