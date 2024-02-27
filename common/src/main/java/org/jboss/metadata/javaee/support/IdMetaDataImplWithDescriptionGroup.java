/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.support;

import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;

/**
 * IdMetaDataImplWithDescriptionGroup.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class IdMetaDataImplWithDescriptionGroup extends IdMetaDataImpl {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 3906255525185441267L;

    /**
     * The description group
     */
    private DescriptionGroupMetaData descriptionGroup;

    /**
     * Create a new IdMetaDataImplWithDescriptions.
     */
    public IdMetaDataImplWithDescriptionGroup() {
        // For serialization
    }

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
