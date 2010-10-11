/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jboss.metadata.javaee.support.MergeableMappedMetaData;
import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * The data-source metadata
 *
 * @author Remy Maucherat
 * @version $Revision: 81768 $
 */
@XmlType(name = "data-sourceType", propOrder = { "descriptions", "name", "className", "serverName", "portNumber",
        "databaseName", "url", "user", "password", "properties", "loginTimeout", "transactional", "isolationLevel",
        "initialPoolSize", "maxPoolSize", "minPoolSize", "maxIdleTime", "maxStatements" })
public class DataSourceMetaData extends NamedMetaDataWithDescriptions implements MergeableMappedMetaData<DataSourceMetaData> {
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

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
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

    @XmlElement(name = "property")
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

    @Override
    public DataSourceMetaData merge(DataSourceMetaData original) {
        DataSourceMetaData merged = new DataSourceMetaData();
        merged.merge(this, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public void merge(DataSourceMetaData override, DataSourceMetaData original) {
        super.merge(override, original);
        if (override != null && override.getClassName() != null)
            setClassName(override.getClassName());
        else if (original != null && original.getClassName() != null)
            setClassName(original.getClassName());
        if (override != null && override.databaseName != null)
            setDatabaseName(override.databaseName);
        else if (original != null && original.databaseName != null)
            setDatabaseName(original.databaseName);
        if (override != null && override.initialPoolSize != DEFAULT_INITIAL_POOL_SIZE)
            setInitialPoolSize(override.initialPoolSize);
        else if (original != null && original.initialPoolSize != DEFAULT_INITIAL_POOL_SIZE)
            setInitialPoolSize(original.initialPoolSize);
        if (override != null && override.isolationLevel != null)
            setIsolationLevel(override.isolationLevel);
        else if (original != null && original.isolationLevel != null)
            setIsolationLevel(original.isolationLevel);
        if (override != null && override.loginTimeout != DEFAULT_LOGIN_TIMEOUT)
            setLoginTimeout(override.loginTimeout);
        else if (original != null && original.loginTimeout != DEFAULT_LOGIN_TIMEOUT)
            setLoginTimeout(original.loginTimeout);
        if (override != null && override.maxIdleTime != DEFAULT_MAX_IDLE_TIME)
            setMaxIdleTime(override.maxIdleTime);
        else if (original != null && original.maxIdleTime != DEFAULT_MAX_IDLE_TIME)
            setMaxIdleTime(original.maxIdleTime);
        if (override != null && override.maxPoolSize != DEFAULT_MAX_POOL_SIZE)
            setMaxPoolSize(override.maxPoolSize);
        else if (original != null && original.maxPoolSize != DEFAULT_MAX_POOL_SIZE)
            setMaxPoolSize(original.maxPoolSize);
        if (override != null && override.maxStatements != DEFAULT_MAX_STATEMENTS)
            setMaxStatements(override.maxStatements);
        else if (original != null && original.maxStatements != DEFAULT_MAX_STATEMENTS)
            setMaxStatements(original.maxStatements);
        if (override != null && override.minPoolSize != DEFAULT_MIN_POOL_SIZE)
            setMinPoolSize(override.minPoolSize);
        else if (original != null && original.minPoolSize != DEFAULT_MIN_POOL_SIZE)
            setMinPoolSize(original.minPoolSize);
        if (override != null && override.password != null)
            setPassword(override.password);
        else if (original != null && original.password != null)
            setPassword(original.password);
        if (override != null && override.portNumber != DEFAULT_PORT_NUMBER)
            setPortNumber(override.portNumber);
        else if (original != null && original.portNumber != DEFAULT_PORT_NUMBER)
            setPortNumber(original.portNumber);
        if (override != null && override.properties != null)
            setProperties(override.properties);
        else if (original != null && original.properties != null)
            setProperties(original.properties);
        if (override != null && override.serverName != null)
            setServerName(override.serverName);
        else if (original != null && original.serverName != null)
            setServerName(original.serverName);
        if (override != null && override.transactional != DEFAULT_TRANSACTIONAL)
            setTransactional(override.transactional);
        else if (original != null && original.transactional != DEFAULT_TRANSACTIONAL)
            setTransactional(original.transactional);
        if (override != null && override.url != null)
            setUrl(override.url);
        else if (original != null && original.url != null)
            setUrl(original.url);
        if (override != null && override.user != null)
            setUser(override.user);
        else if (original != null && original.user != null)
            setUser(original.user);
    }

}
