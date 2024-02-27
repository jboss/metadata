/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * Metadata for javaee:jms-connection-factoryType
 *
 * @author Eduardo Martins
 *
 */
public class JMSConnectionFactoryMetaData extends NamedMetaDataWithDescriptions {

    /**
     *
     */
    private static final long serialVersionUID = -2942637439140956026L;

    public static final boolean DEFAULT_TRANSACTIONAL = true;
    public static final int DEFAULT_MAX_POOL_SIZE = -1;
    public static final int DEFAULT_MIN_POOL_SIZE = -1;

    public static final String CONNECTION_FACTORY_INTERFACE_NAME = "jakarta.jms.ConnectionFactory";
    public static final String QUEUE_CONNECTION_FACTORY_INTERFACE_NAME = "jakarta.jms.QueueConnectionFactory";
    public static final String TOPIC_CONNECTION_FACTORY_INTERFACE_NAME = "jakarta.jms.TopicConnectionFactory";
    public static final String DEFAULT_INTERFACE_NAME = CONNECTION_FACTORY_INTERFACE_NAME;

    /**
     *
     */
    private String className;

    /**
     *
     */
    private String interfaceName = DEFAULT_INTERFACE_NAME;

    /**
     *
     */
    private String resourceAdapter;

    /**
     *
     */
    private String user;

    /**
     *
     */
    private String password;

    /**
     *
     */
    private String clientId;

    /**
    *
    */
    private PropertiesMetaData properties;

    /**
     *
     */
    private boolean transactional = DEFAULT_TRANSACTIONAL;

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
    public String getClassName() {
        return className;
    }

    /**
     *
     * @param className
     */
    public void setClassName(String className) {
        this.className = className;
    }

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
     * @throws IllegalArgumentException if arg is null or unsupported
     */
    public void setInterfaceName(String interfaceName) throws IllegalArgumentException {
        if (interfaceName == null) {
            throw new IllegalArgumentException("Null interfaceName");
        }

        // If this class has been transformed to the jakarta namespace, translate the input
        if (CONNECTION_FACTORY_INTERFACE_NAME.startsWith("jakarta.") && interfaceName.startsWith("javax.")) {
            interfaceName = interfaceName.replaceFirst("javax", "jakarta");
        }

        if(!interfaceName.equals(CONNECTION_FACTORY_INTERFACE_NAME) && !interfaceName.equals(QUEUE_CONNECTION_FACTORY_INTERFACE_NAME) && !interfaceName.equals(TOPIC_CONNECTION_FACTORY_INTERFACE_NAME)) {
            throw new IllegalArgumentException("Unsupported interfaceName " + interfaceName);
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
     */
    public void setResourceAdapter(String resourceAdapter) {
        this.resourceAdapter = resourceAdapter;
    }

    /**
     *
     * @return
     */
    public String getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getClientId() {
        return clientId;
    }

    /**
     *
     * @param clientId
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    /**
     *
     * @return
     */
    public boolean isTransactional() {
        return transactional;
    }

    /**
     *
     * @return
     */
    public boolean getTransactional() {
        return transactional;
    }

    /**
     *
     * @param transactional
     */
    public void setTransactional(boolean transactional) {
        this.transactional = transactional;
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

}
