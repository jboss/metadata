/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import java.io.Serializable;

import org.jboss.annotation.javaee.Description;
import org.jboss.annotation.javaee.Descriptions;
import org.jboss.annotation.javaee.DisplayName;
import org.jboss.annotation.javaee.DisplayNames;
import org.jboss.annotation.javaee.Icons;

/**
 * DescriptionGroupMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class DescriptionGroupMetaData implements Serializable {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1337095770028220349L;

    /**
     * The descriptions
     */
    private Descriptions descriptions;

    /**
     * The display names
     */
    private DisplayNames displayNames;

    /**
     * The icons
     */
    private Icons icons;

    /**
     * Create a new DescriptionGroupMetaData.
     */
    public DescriptionGroupMetaData() {
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
    // @JBossXmlNsPrefix(prefix="jee")
    public void setDescriptions(Descriptions descriptions) {
        if (descriptions == null)
            throw new IllegalArgumentException("Null descriptions");
        this.descriptions = descriptions;
    }

    public String getDescription() {
        String description = getDescription(Description.DEFAULT_LANGUAGE);
        return description;
    }

    public String getDescription(String lang) {
        String description = null;
        if (descriptions != null) {
            for (Description d : descriptions.value()) {
                if (d.language().equals(lang))
                    description = d.value();
            }
        }
        return description;
    }

    /**
     * Get the displayNames.
     *
     * @return the displayNames.
     */
    public DisplayNames getDisplayNames() {
        return displayNames;
    }

    /**
     * Set the displayNames.
     *
     * @param displayNames the displayNames.
     * @throws IllegalArgumentException for a null displayNames
     */
    public void setDisplayNames(DisplayNames displayNames) {
        if (displayNames == null)
            throw new IllegalArgumentException("Null displayNames");
        this.displayNames = displayNames;
    }

    public String getDisplayName() {
        String displayName = getDisplayName(Description.DEFAULT_LANGUAGE);
        return displayName;
    }

    public String getDisplayName(String lang) {
        String displayName = null;
        if (this.displayNames != null) {
            for (DisplayName d : displayNames.value()) {
                if (d.language().equals(lang))
                    displayName = d.value();
            }
        }
        return displayName;
    }

    /**
     * Get the icons.
     *
     * @return the icons.
     */
    public Icons getIcons() {
        return icons;
    }

    /**
     * Set the icons.
     *
     * @param icons the icons.
     * @throws IllegalArgumentException for a null icons
     */
    public void setIcons(Icons icons) {
        if (icons == null)
            throw new IllegalArgumentException("Null icons");
        this.icons = icons;
    }
}
