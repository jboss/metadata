package org.jboss.metadata.property;

/**
 * Replace any properties found within the provided text.
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
    String replaceProperties(final String text);
}
