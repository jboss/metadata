/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplWithDescriptionsMerger;

/**
 * ActivationConfigMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ActivationConfigMetaData extends IdMetaDataImplWithDescriptions implements MergeableMetaData<ActivationConfigMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -9138498601978922673L;

    /**
     * The activation config properties
     */
    private ActivationConfigPropertiesMetaData activationConfigProperties;

    /**
     * Create a new CMPFieldMetaData.
     */
    public ActivationConfigMetaData() {
        // For serialization
    }

    /**
     * Get the activationConfigProperties.
     *
     * @return the activationConfigProperties.
     */
    public ActivationConfigPropertiesMetaData getActivationConfigProperties() {
        return activationConfigProperties;
    }

    /**
     * Set the activationConfigProperties.
     *
     * @param activationConfigProperties the activationConfigProperties.
     * @throws IllegalArgumentException for a null activationConfigProperties
     */
    public void setActivationConfigProperties(ActivationConfigPropertiesMetaData activationConfigProperties) {
        if (activationConfigProperties == null)
            throw new IllegalArgumentException("Null activationConfigProperties");
        this.activationConfigProperties = activationConfigProperties;
    }

    public void merge(ActivationConfigMetaData override, ActivationConfigMetaData original) {
        IdMetaDataImplWithDescriptionsMerger.merge(this, override, original);
        ActivationConfigPropertiesMetaData propertyOverride = null;
        if (override != null)
            propertyOverride = override.getActivationConfigProperties();
        ActivationConfigPropertiesMetaData propertyOriginal = null;
        if (original != null)
            propertyOriginal = original.getActivationConfigProperties();
        if (propertyOverride == null || propertyOverride.isEmpty()) {
            if (propertyOriginal != null)
                activationConfigProperties = propertyOriginal;
            return;
        }
        if (propertyOriginal == null || propertyOriginal.isEmpty()) {
            if (propertyOverride != null)
                activationConfigProperties = propertyOverride;
            return;
        }
        activationConfigProperties = new ActivationConfigPropertiesMetaData();
        activationConfigProperties.merge(propertyOverride, propertyOriginal);
    }
}
