/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * Metadata for javaee:administered-objectType
 *
 * @author Eduardo Martins
 *
 */
public class AdministeredObjectMetaData extends NamedMetaDataWithDescriptions {

    /**
     *
     */
    private static final long serialVersionUID = -8828730452352230094L;

    /**
     *
     */
    private String interfaceName;

    /**
     *
     */
    private String className;

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
     * @return
     */
    public String getInterfaceName() {
        return interfaceName;
    }

    /**
     *
     * @param interfaceName
     */
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

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
     * @throws IllegalArgumentException if arg is null
     */
    public void setClassName(String className) throws IllegalArgumentException {
        if (className == null) {
            throw new IllegalArgumentException("Null className");
        }
        this.className = className;
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
