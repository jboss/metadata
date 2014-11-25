/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

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
