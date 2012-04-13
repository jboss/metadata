package org.jboss.metadata.property;

/**
 * @author John Bailey
 */
public class PropertyReplacers {

    private static final PropertyReplacer NO_OP_REPLACER = new PropertyReplacer() {
        public String replaceProperties(final String text) {
            return text;
        }
    };

    /**
     * Return a replacer that functions as a no-op.
     *
     * @return replacer
     */
    public static PropertyReplacer noop() {
        return NO_OP_REPLACER;
    }

    /**
     * Return a {@code PropertyReplacer} that uses the provided {@code PropertyResolver} to resolver any properties found in the text.
     *
     * @param resolver The resolver used for any properties being replaced
     * @return The replacer
     */
    public static PropertyReplacer resolvingReplacer(final PropertyResolver resolver) {
        return new DefaultPropertyReplacer(resolver);
    }
}
