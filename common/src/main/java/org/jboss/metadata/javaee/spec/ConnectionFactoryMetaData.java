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
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * Metadata for javaee:connection-factory-resourceType
 *
 * @author Eduardo Martins
 *
 */
public class ConnectionFactoryMetaData extends NamedMetaDataWithDescriptions {

    /**
     *
     */
    private static final long serialVersionUID = -2942637439140956026L;

    public static final TransactionSupportType DEFAULT_TRANSACTION_SUPPORT = TransactionSupportType.NoTransaction;
    public static final int DEFAULT_MAX_POOL_SIZE = -1;
    public static final int DEFAULT_MIN_POOL_SIZE = -1;

    /**
     *
     */
    private String interfaceName;

    /**
     *
     */
    private String resourceAdapter;

    /**
     *
     */
    private PropertiesMetaData properties;

    /**
     *
     */
    private TransactionSupportType transactionSupport = DEFAULT_TRANSACTION_SUPPORT;

    /**
     *
     */
    private int maxPoolSize = DEFAULT_MAX_POOL_SIZE;

    /**
     *
     */
    private int minPoolSize = DEFAULT_MIN_POOL_SIZE;

    /**
     *
     * @return
     */
    public String getInterfaceName() {
        return interfaceName;
    }

    /**
     *
     * @param interfaceName
     * @throws IllegalArgumentException if arg is null
     */
    public void setInterfaceName(String interfaceName) throws IllegalArgumentException {
        if (interfaceName == null) {
            throw new IllegalArgumentException("Null interfaceName");
        }
        this.interfaceName = interfaceName;
    }

    /**
     *
     * @return
     */
    public String getResourceAdapter() {
        return resourceAdapter;
    }

    /**
     *
     * @param resourceAdapter
     * @throws IllegalArgumentException if arg is null
     */
    public void setResourceAdapter(String resourceAdapter) throws IllegalArgumentException {
        if (resourceAdapter == null) {
            throw new IllegalArgumentException("Null resourceAdapter");
        }
        this.resourceAdapter = resourceAdapter;
    }

    /**
     *
     * @return
     */
    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    /**
     *
     * @param maxPoolSize
     */
    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    /**
     *
     * @return
     */
    public int getMinPoolSize() {
        return minPoolSize;
    }

    /**
     *
     * @param minPoolSize
     */
    public void setMinPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    /**
     *
     * @return
     */
    public TransactionSupportType getTransactionSupport() {
        return transactionSupport;
    }

    /**
     *
     * @param transactionSupport
     */
    public void setTransactionSupport(TransactionSupportType transactionSupport) {
        this.transactionSupport = transactionSupport;
    }

    /**
     *
     * @return
     */
    public PropertiesMetaData getProperties() {
        return properties;
    }

    /**
     *
     * @param properties
     */
    public void setProperties(PropertiesMetaData properties) {
        this.properties = properties;
    }

}
