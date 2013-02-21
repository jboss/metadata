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
