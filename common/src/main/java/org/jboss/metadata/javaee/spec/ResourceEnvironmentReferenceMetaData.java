/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.ResourceInjectionMetaDataWithDescriptions;

/**
 * ResourceEnvironmentReferenceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ResourceEnvironmentReferenceMetaData extends ResourceInjectionMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -3906197284118629544L;

    /**
     * The type
     */
    private String type;

    /**
     * Create a new ResourceEnvironmentReferenceMetaData.
     */
    public ResourceEnvironmentReferenceMetaData() {
        // For serialization
    }

    /**
     * Get the resourceEnvRefName.
     *
     * @return the resourceEnvRefName.
     */
    public String getResourceEnvRefName() {
        return getName();
    }

    /**
     * Set the resourceEnvRefName.
     *
     * @param resourceEnvRefName the resourceEnvRefName.
     * @throws IllegalArgumentException for a null resourceEnvRefName
     */
    // @JBossXmlNsPrefix(prefix="jee")
    public void setResourceEnvRefName(String resourceEnvRefName) {
        setName(resourceEnvRefName);
    }

    /**
     * Get the type.
     *
     * @return the type.
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type.
     *
     * @param type the type.
     * @throws IllegalArgumentException for a null type
     */
    // @JBossXmlNsPrefix(prefix="jee")
    public void setType(String type) {
        if (type == null)
            throw new IllegalArgumentException("Null type");
        this.type = type;
    }
}
