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

    public static final String CONNECTION_FACTORY_INTERFACE_NAME = "javax.jms.ConnectionFactory";
    public static final String QUEUE_CONNECTION_FACTORY_INTERFACE_NAME = "javax.jms.QueueConnectionFactory";
    public static final String TOPIC_CONNECTION_FACTORY_INTERFACE_NAME = "javax.jms.TopicConnectionFactory";
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
        if(!interfaceName.equals(CONNECTION_FACTORY_INTERFACE_NAME) && !interfaceName.equals(QUEUE_CONNECTION_FACTORY_INTERFACE_NAME) && !interfaceName.equals(TOPIC_CONNECTION_FACTORY_INTERFACE_NAME)) {
            throw new IllegalArgumentException("Unsupported interfaceName");
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
