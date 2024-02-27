/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.annotation.javaee.Icon;
import org.jboss.metadata.javaee.support.LanguageMetaData;

/**
 * IconImpl.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class IconImpl extends LanguageMetaData implements Icon {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -8544489136833248714L;

    /**
     * The small icon
     */
    private String smallIcon = "";

    /**
     * The large icon
     */
    private String largeIcon = "";

    /**
     * Create a new IconImpl.
     */
    public IconImpl() {
        super(Icon.class);
    }

    @Override
    public String largeIcon() {
        return getLargeIcon();
    }

    @Override
    public String smallIcon() {
        return getSmallIcon();
    }

    /**
     * Get the largeIcon.
     *
     * @return the largeIcon.
     */
    public String getLargeIcon() {
        return largeIcon;
    }

    /**
     * Set the largeIcon.
     *
     * @param largeIcon the largeIcon.
     * @throws IllegalArgumentException for a null largeIcon
     */
    public void setLargeIcon(String largeIcon) {
        if (largeIcon == null)
            throw new IllegalArgumentException("Null largeIcon");
        this.largeIcon = largeIcon;
    }

    /**
     * Get the smallIcon.
     *
     * @return the smallIcon.
     */
    public String getSmallIcon() {
        return smallIcon;
    }

    /**
     * Set the smallIcon.
     *
     * @param smallIcon the smallIcon.
     * @throws IllegalArgumentException for a null smallIcon
     */
    public void setSmallIcon(String smallIcon) {
        if (smallIcon == null)
            throw new IllegalArgumentException("Null smallIcon");
        this.smallIcon = smallIcon;
    }
}
