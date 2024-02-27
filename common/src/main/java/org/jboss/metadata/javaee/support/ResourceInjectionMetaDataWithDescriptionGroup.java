/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.support;

import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionMetaData;

/**
 * ResourceInjectionMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class ResourceInjectionMetaDataWithDescriptionGroup extends ResourceInjectionMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 5085818160192282098L;
    /**
     * The description group
     */
    private DescriptionGroupMetaData descriptionGroup;

    /**
     * Get the descriptionGroup.
     *
     * @return the descriptionGroup.
     */
    public DescriptionGroupMetaData getDescriptionGroup() {
        return descriptionGroup;
    }

    /**
     * Set the descriptionGroup.
     *
     * @param descriptionGroup the descriptionGroup.
     * @throws IllegalArgumentException for a null descriptionGroup
     */
    public void setDescriptionGroup(DescriptionGroupMetaData descriptionGroup) {
        if (descriptionGroup == null)
            throw new IllegalArgumentException("Null descriptionGroup");
        this.descriptionGroup = descriptionGroup;
    }
}
