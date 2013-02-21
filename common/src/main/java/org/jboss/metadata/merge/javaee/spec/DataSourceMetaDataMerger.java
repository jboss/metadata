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

import org.jboss.metadata.javaee.spec.DataSourceMetaData;
import org.jboss.metadata.merge.javaee.support.NamedMetaDataMerger;

/**
 * The data-source metadata
 *
 * @author Remy Maucherat
 * @version $Revision: 81768 $
 */

public class DataSourceMetaDataMerger {

    public static DataSourceMetaData merge(DataSourceMetaData dest, DataSourceMetaData original) {
        DataSourceMetaData merged = new DataSourceMetaData();
        merge(merged, dest, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(DataSourceMetaData dest, DataSourceMetaData override, DataSourceMetaData original) {
        NamedMetaDataMerger.merge(dest, override, original);
        if (override != null && override.getClassName() != null)
            dest.setClassName(override.getClassName());
        else if (original != null && original.getClassName() != null)
            dest.setClassName(original.getClassName());
        if (override != null && override.getDatabaseName() != null)
            dest.setDatabaseName(override.getDatabaseName());
        else if (original != null && original.getDatabaseName() != null)
            dest.setDatabaseName(original.getDatabaseName());
        if (override != null && override.getInitialPoolSize() != DataSourceMetaData.DEFAULT_INITIAL_POOL_SIZE)
            dest.setInitialPoolSize(override.getInitialPoolSize());
        else if (original != null && original.getInitialPoolSize() != DataSourceMetaData.DEFAULT_INITIAL_POOL_SIZE)
            dest.setInitialPoolSize(original.getInitialPoolSize());
        if (override != null && override.getIsolationLevel() != null)
            dest.setIsolationLevel(override.getIsolationLevel());
        else if (original != null && original.getIsolationLevel() != null)
            dest.setIsolationLevel(original.getIsolationLevel());
        if (override != null && override.getLoginTimeout() != DataSourceMetaData.DEFAULT_LOGIN_TIMEOUT)
            dest.setLoginTimeout(override.getLoginTimeout());
        else if (original != null && original.getLoginTimeout() != DataSourceMetaData.DEFAULT_LOGIN_TIMEOUT)
            dest.setLoginTimeout(original.getLoginTimeout());
        if (override != null && override.getMaxIdleTime() != DataSourceMetaData.DEFAULT_MAX_IDLE_TIME)
            dest.setMaxIdleTime(override.getMaxIdleTime());
        else if (original != null && original.getMaxIdleTime() != DataSourceMetaData.DEFAULT_MAX_IDLE_TIME)
            dest.setMaxIdleTime(original.getMaxIdleTime());
        if (override != null && override.getMaxPoolSize() != DataSourceMetaData.DEFAULT_MAX_POOL_SIZE)
            dest.setMaxPoolSize(override.getMaxPoolSize());
        else if (original != null && original.getMaxPoolSize() != DataSourceMetaData.DEFAULT_MAX_POOL_SIZE)
            dest.setMaxPoolSize(original.getMaxPoolSize());
        if (override != null && override.getMaxStatements() != DataSourceMetaData.DEFAULT_MAX_STATEMENTS)
            dest.setMaxStatements(override.getMaxStatements());
        else if (original != null && original.getMaxStatements() != DataSourceMetaData.DEFAULT_MAX_STATEMENTS)
            dest.setMaxStatements(original.getMaxStatements());
        if (override != null && override.getMinPoolSize() != DataSourceMetaData.DEFAULT_MIN_POOL_SIZE)
            dest.setMinPoolSize(override.getMinPoolSize());
        else if (original != null && original.getMinPoolSize() != DataSourceMetaData.DEFAULT_MIN_POOL_SIZE)
            dest.setMinPoolSize(original.getMinPoolSize());
        if (override != null && override.getPassword() != null)
            dest.setPassword(override.getPassword());
        else if (original != null && original.getPassword() != null)
            dest.setPassword(original.getPassword());
        if (override != null && override.getPortNumber() != DataSourceMetaData.DEFAULT_PORT_NUMBER)
            dest.setPortNumber(override.getPortNumber());
        else if (original != null && original.getPortNumber() != DataSourceMetaData.DEFAULT_PORT_NUMBER)
            dest.setPortNumber(original.getPortNumber());
        if (override != null && override.getProperties() != null)
            dest.setProperties(override.getProperties());
        else if (original != null && original.getProperties() != null)
            dest.setProperties(original.getProperties());
        if (override != null && override.getServerName() != null)
            dest.setServerName(override.getServerName());
        else if (original != null && original.getServerName() != null)
            dest.setServerName(original.getServerName());
        if (override != null && override.getTransactional() != DataSourceMetaData.DEFAULT_TRANSACTIONAL)
            dest.setTransactional(override.getTransactional());
        else if (original != null && original.getTransactional() != DataSourceMetaData.DEFAULT_TRANSACTIONAL)
            dest.setTransactional(original.getTransactional());
        if (override != null && override.getUrl() != null)
            dest.setUrl(override.getUrl());
        else if (original != null && original.getUrl() != null)
            dest.setUrl(original.getUrl());
        if (override != null && override.getUser() != null)
            dest.setUser(override.getUser());
        else if (original != null && original.getUser() != null)
            dest.setUser(original.getUser());
    }

}
