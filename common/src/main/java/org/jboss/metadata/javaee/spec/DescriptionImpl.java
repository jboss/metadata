/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.annotation.javaee.Description;
import org.jboss.metadata.javaee.support.LanguageMetaData;

/**
 * DescriptionImpl.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class DescriptionImpl extends LanguageMetaData implements Description {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 7060589158592181267L;

    /**
     * The description
     */
    private String description = "";

    /**
     * Create a new DescriptionImpl.
     */
    public DescriptionImpl() {
        super(Description.class);
    }

    @Override
    public String value() {
        return getDescription();
    }

    /**
     * Get the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description.
     *
     * @param description the description.
     * @throws IllegalArgumentException for a null description
     */
    public void setDescription(String description) {
        if (description == null)
            throw new IllegalArgumentException("Null description");
        this.description = description;
    }
}
