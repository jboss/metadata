/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * The data-source metadata
 *
 * @author Remy Maucherat
 * @version $Revision: 81768 $
 */

public class DataSourceMetaData extends NamedMetaDataWithDescriptions {
    private static final long serialVersionUID = 1;

    public static final int DEFAULT_PORT_NUMBER = -1;
    public static final int DEFAULT_LOGIN_TIMEOUT = 0;
    public static final boolean DEFAULT_TRANSACTIONAL = true;
    public static final int DEFAULT_INITIAL_POOL_SIZE = -1;
    public static final int DEFAULT_MAX_POOL_SIZE = -1;
    public static final int DEFAULT_MIN_POOL_SIZE = -1;
    public static final int DEFAULT_MAX_IDLE_TIME = -1;
    public static final int DEFAULT_MAX_STATEMENTS = -1;

    private String className;
    private String serverName;
    private int portNumber = DEFAULT_PORT_NUMBER;
    private String databaseName;
    private String url;
    private String user;
    private String password;
    private PropertiesMetaData properties;
    private int loginTimeout = DEFAULT_LOGIN_TIMEOUT;
    private boolean transactional = DEFAULT_TRANSACTIONAL;
    private IsolationLevelType isolationLevel;
    private int initialPoolSize = DEFAULT_INITIAL_POOL_SIZE;
    private int maxPoolSize = DEFAULT_MAX_POOL_SIZE;
    private int minPoolSize = DEFAULT_MIN_POOL_SIZE;
    private int maxIdleTime = DEFAULT_MAX_IDLE_TIME;
    private int maxStatements = DEFAULT_MAX_STATEMENTS;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PropertiesMetaData getProperties() {
        return properties;
    }

    public void setProperties(PropertiesMetaData properties) {
        this.properties = properties;
    }

    public int getLoginTimeout() {
        return loginTimeout;
    }

    public void setLoginTimeout(int loginTimeout) {
        this.loginTimeout = loginTimeout;
    }

    public boolean isTransactional() {
        return transactional;
    }

    public boolean getTransactional() {
        return transactional;
    }

    public void setTransactional(boolean transactional) {
        this.transactional = transactional;
    }

    public IsolationLevelType getIsolationLevel() {
        return isolationLevel;
    }

    public void setIsolationLevel(IsolationLevelType isolationLevel) {
        this.isolationLevel = isolationLevel;
    }

    public int getInitialPoolSize() {
        return initialPoolSize;
    }

    public void setInitialPoolSize(int initialPoolSize) {
        this.initialPoolSize = initialPoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public int getMaxStatements() {
        return maxStatements;
    }

    public void setMaxStatements(int maxStatements) {
        this.maxStatements = maxStatements;
    }
}
