/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * Metadata for javaee:jms-destinationType
 *
 * @author Eduardo Martins
 *
 */
public class JMSDestinationMetaData extends NamedMetaDataWithDescriptions {

    /**
     *
     */
    private static final long serialVersionUID = -8828730452352230094L;

    public static final String QUEUE_INTERFACE_NAME = "jakarta.jms.Queue";
    public static final String TOPIC_INTERFACE_NAME = "jakarta.jms.Topic";

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
    private String destinationName;

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
     * @throws IllegalArgumentException if arg is null or unsupported
     */
    public void setInterfaceName(String interfaceName) throws IllegalArgumentException {
        if (interfaceName == null) {
            throw new IllegalArgumentException("Null interfaceName");
        }

        // If this class has been transformed to the jakarta namespace, translate the input
        if (QUEUE_INTERFACE_NAME.startsWith("jakarta.") && interfaceName.startsWith("javax.")) {
            interfaceName = interfaceName.replaceFirst("javax", "jakarta");
        }

        if(!interfaceName.equals(QUEUE_INTERFACE_NAME) && !interfaceName.equals(TOPIC_INTERFACE_NAME)) {
            throw new IllegalArgumentException("Unsupported interfaceName " + interfaceName);
        }
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
     */
    public void setClassName(String className) {
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
     */
    public void setResourceAdapter(String resourceAdapter) {
        this.resourceAdapter = resourceAdapter;
    }

    /**
     *
     * @return
     */
    public String getDestinationName() {
        return destinationName;
    }

    /**
     *
     * @param destinationName
     */
    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
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
