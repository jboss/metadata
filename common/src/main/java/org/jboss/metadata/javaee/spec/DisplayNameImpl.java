/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.annotation.javaee.DisplayName;
import org.jboss.metadata.javaee.support.LanguageMetaData;

/**
 * DisplayNameImpl.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class DisplayNameImpl extends LanguageMetaData implements DisplayName {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 8863089892578047773L;

    /**
     * The display name
     */
    private String displayName = "";

    /**
     * Create a new DisplayNameImpl.
     */
    public DisplayNameImpl() {
        super(DisplayName.class);
    }

    @Override
    public String value() {
        return getDisplayName();
    }

    /**
     * Get the displayName.
     *
     * @return the displayName.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Set the displayName.
     *
     * @param displayName the displayName.
     * @throws IllegalArgumentException for a null displayName
     */
    public void setDisplayName(String displayName) {
        if (displayName == null)
            throw new IllegalArgumentException("Null displayName");
        this.displayName = displayName;
    }
}
