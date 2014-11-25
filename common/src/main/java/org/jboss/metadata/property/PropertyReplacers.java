package org.jboss.metadata.property;

/**
 * Provides standard {@link org.jboss.metadata.property.PropertyReplacer} implementations.
 *
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
     * @return the replacer. Will not be {@code null}
     */
    public static PropertyReplacer noop() {
        return NO_OP_REPLACER;
    }

    /**
     * Return a {@code PropertyReplacer} that uses the provided {@code PropertyResolver} to resolve any
     * properties found in the text. The returned replacer searches for strings beginning with the string "${"
     * and ending with the char '}', and passes the value within to the given {@code resolver}. The replacer
     * supports arbitrarily nested expressions, finding the inner-most expressions and resolving those before
     * using the resolved values to compose the outer expressions. The replacer also supports recursive resolution,
     * so if a resolved value is itself in the form of an expression, that expression will in turn be resolved.
     *
     * @param resolver the resolver used for any properties being replaced. Cannot be {@code null}
     * @return the replacer. Will not be {@code null}
     *
     * @deprecated use {@link #resolvingExpressionReplacer(SimpleExpressionResolver)}
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public static PropertyReplacer resolvingReplacer(final PropertyResolver resolver) {
        return new DefaultExpressionReplacer(resolver);
    }

    /**
     * Return a {@code PropertyReplacer} that uses the provided {@code SimpleExpressionResolver} to resolve any
     * expressions found in the text. The returned replacer searches for strings beginning with the string "${"
     * and ending with the char '}', and passes the value within to the given {@code resolver}. The replacer
     * supports arbitrarily nested expressions, finding the inner-most expressions and resolving those before
     * using the resolved values to compose the outer expressions. The replacer also supports recursive resolution,
     * so if a resolved value is itself in the form of an expression, that expression will in turn be resolved.
     *
     * @param resolver The resolver used for any expressions being replaced. Cannot be {@code null}
     * @return the replacer. Will not be {@code null}
     */
    public static PropertyReplacer resolvingExpressionReplacer(final SimpleExpressionResolver resolver) {
        return new DefaultExpressionReplacer(resolver);
    }
}
