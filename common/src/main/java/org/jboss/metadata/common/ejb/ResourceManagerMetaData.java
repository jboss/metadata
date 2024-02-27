/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.common.ejb;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * ResourceManagerMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ResourceManagerMetaData extends NamedMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 244708249262277696L;

    /**
     * The resource class
     */
    private String resClass;

    /**
     * The resource jndi name
     */
    private String resJndiName;

    /**
     * The resource url
     */
    private String resUrl;

    @Override
    public String getName() {
        return super.getName();
    }

    /**
     * Get the resName.
     *
     * @return the resName.
     */
    public String getResName() {
        return getName();
    }

    /**
     * Set the resName.
     *
     * @param resName the resName.
     * @throws IllegalArgumentException for a null resName
     */
    public void setResName(String resName) {
        setName(resName);
    }

    /**
     * Get the resClass.
     *
     * @return the resClass.
     */
    public String getResClass() {
        return resClass;
    }

    /**
     * Set the resClass.
     *
     * @param resClass the resClass.
     * @throws IllegalArgumentException for a null resClass
     */
    public void setResClass(String resClass) {
        if (resClass == null)
            throw new IllegalArgumentException("Null resClass");
        this.resClass = resClass;
    }

    /**
     * Get the resJndiName.
     *
     * @return the resJndiName.
     */
    public String getResJndiName() {
        return resJndiName;
    }

    /**
     * Set the resJndiName.
     *
     * @param resJndiName the resJndiName.
     * @throws IllegalArgumentException for a null resJndiName
     */
    public void setResJndiName(String resJndiName) {
        if (resJndiName == null)
            throw new IllegalArgumentException("Null resJndiName");
        this.resJndiName = resJndiName;
    }

    /**
     * Get the resUrl.
     *
     * @return the resUrl.
     */
    public String getResUrl() {
        return resUrl;
    }

    /**
     * Set the resUrl.
     *
     * @param resUrl the resUrl.
     * @throws IllegalArgumentException for a null resUrl
     */
    public void setResUrl(String resUrl) {
        if (resUrl == null)
            throw new IllegalArgumentException("Null resUrl");
        this.resUrl = resUrl;
    }

    /**
     * Get the resource
     *
     * @return the resource
     */
    public String getResource() {
        if (resJndiName != null)
            return resJndiName;
        else
            return resUrl;
    }
}
