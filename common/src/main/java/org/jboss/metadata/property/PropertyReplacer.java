/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.property;

/**
 * Replace any properties found within the provided text.
 *
 * @see org.jboss.metadata.property.PropertyReplacers
 *
 * @author John Bailey
 */
public interface PropertyReplacer {
    /**
     * Replace any properties found within the text provided.
     *
     * @param text Text to replace properties within
     * @return The text with properties replaced
     */
    String replaceProperties(String text);
}
