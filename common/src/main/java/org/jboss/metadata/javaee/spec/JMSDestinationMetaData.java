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

    public static final String QUEUE_INTERFACE_NAME = "javax.jms.Queue";
    public static final String TOPIC_INTERFACE_NAME = "javax.jms.Topic";

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
        if(!interfaceName.equals(QUEUE_INTERFACE_NAME) && !interfaceName.equals(TOPIC_INTERFACE_NAME)) {
            throw new IllegalArgumentException("Unsupported interfaceName");
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
