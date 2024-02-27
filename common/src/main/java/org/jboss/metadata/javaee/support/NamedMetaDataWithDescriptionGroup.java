/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.support;

import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;

/**
 * NamedMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class NamedMetaDataWithDescriptionGroup extends NamedMetaData implements MappableMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -1282107210311451130L;

    /**
     * The description
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
    // @SchemaProperty(noInterceptor=true)
    public void setDescriptionGroup(DescriptionGroupMetaData descriptionGroup) {
        if (descriptionGroup == null)
            throw new IllegalArgumentException("Null descriptionGroup");
        this.descriptionGroup = descriptionGroup;
    }
}
