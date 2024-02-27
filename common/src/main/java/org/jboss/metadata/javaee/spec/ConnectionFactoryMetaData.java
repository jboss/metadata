/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
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
