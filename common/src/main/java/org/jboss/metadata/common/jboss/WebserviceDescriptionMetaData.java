/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.common.jboss;

import org.jboss.metadata.javaee.support.NamedMetaData;

/**
 * WebservicesDescriptionMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */

public class WebserviceDescriptionMetaData extends NamedMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -3783036458417091083L;

    /**
     * The context name
     */
    private String configName;

    /**
     * The context file
     */
    private String configFile;

    /**
     * The wsdl publish location
     */
    private String wsdlPublishLocation;

    @Override
    public String getName() {
        return super.getName();
    }

    /**
     * Get the webservicesDescriptionName.
     *
     * @return the webservicesDescriptionName.
     */
    public String getWebserviceDescriptionName() {
        return getName();
    }

    /**
     * Set the webservicesDescriptionName.
     *
     * @param webservicesDescriptionName the webservicesDescriptionName.
     * @throws IllegalArgumentException for a null webservicesDescriptionName
     */
    public void setWebserviceDescriptionName(String webservicesDescriptionName) {
        setName(webservicesDescriptionName);
    }

    /**
     * Get the configName.
     *
     * @return the configName.
     */
    public String getConfigName() {
        return configName;
    }

    /**
     * Set the configName.
     *
     * @param configName the configName.
     * @throws IllegalArgumentException for a null configName
     */
    public void setConfigName(String configName) {
        if (configName == null)
            throw new IllegalArgumentException("Null configName");
        this.configName = configName;
    }

    /**
     * Get the configFile.
     *
     * @return the configFile.
     */
    public String getConfigFile() {
        return configFile;
    }

    /**
     * Set the configFile.
     *
     * @param configFile the configFile.
     * @throws IllegalArgumentException for a null configFile
     */
    public void setConfigFile(String configFile) {
        if (configFile == null)
            throw new IllegalArgumentException("Null configFile");
        this.configFile = configFile;
    }

    /**
     * Get the wsdlPublishLocation.
     *
     * @return the wsdlPublishLocation.
     */
    public String getWsdlPublishLocation() {
        return wsdlPublishLocation;
    }

    /**
     * Set the wsdlPublishLocation.
     *
     * @param wsdlPublishLocation the wsdlPublishLocation.
     * @throws IllegalArgumentException for a null wsdlPublishLocation
     */
    public void setWsdlPublishLocation(String wsdlPublishLocation) {
        if (wsdlPublishLocation == null)
            throw new IllegalArgumentException("Null wsdlPublishLocation");
        this.wsdlPublishLocation = wsdlPublishLocation;
    }
}
