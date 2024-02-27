/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.support;

import org.jboss.annotation.javaee.Descriptions;

/**
 * IdMetaDataImplWithDescriptions.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class IdMetaDataImplWithDescriptions extends IdMetaDataImpl implements WithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 8444982686386775186L;

    /**
     * The descriptions
     */
    private Descriptions descriptions;

    /**
     * Create a new IdMetaDataImplWithDescriptions.
     */
    public IdMetaDataImplWithDescriptions() {
        // For serialization
    }

    /**
     * Get the descriptions.
     *
     * @return the descriptions.
     */
    public Descriptions getDescriptions() {
        return descriptions;
    }

    /**
     * Set the descriptions.
     *
     * @param descriptions the descriptions.
     * @throws IllegalArgumentException for a null descriptions
     */
    public void setDescriptions(Descriptions descriptions) {
        if (descriptions == null)
            throw new IllegalArgumentException("Null descriptions");
        this.descriptions = descriptions;
    }
}
