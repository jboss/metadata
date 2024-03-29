/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.property;

/**
 * Resolves the value of a simple expression, where a simple expression is defined as the content
 * between the strings "${" and "}", with no other simple expressions nested inside.
 *
 * @see JBossASSimpleExpressionResolver
 *
 * @author Brian Stansberry (c) 2014 Red Hat Inc.
 */
public interface SimpleExpressionResolver {

    /** The result of {@link #resolveExpressionContent(String) resolving some expression content}.*/
    class ResolutionResult {
        private final String value;
        private final boolean isDefault;

        /**
         * Creates a new ResolutionResult.
         *
         * @param value the resolved value. Cannot be {@code null}.
         * @param isDefault  {@code true} if {@code value} was a default value encoded in the expression content;
         *                   {@code false} otherwise
         */
        public ResolutionResult(String value, boolean isDefault) {
            assert value != null;
            this.value = value;
            this.isDefault = isDefault;
        }

        /**
         * Gets the resolved value;
         * @return the value. Will not be {@code null}
         */
        public String getValue() {
            return value;
        }

        /**
         * Gets whether the resolved value represents some sort of default value that was embedded in
         * the expression content; e.g. {@code defaultValue} in a traditional JBoss AS style expression with syntax
         * {@code propertyName[,propertyName]*[:defaultValue]}.
         *
         * @return {@code true} if the value is a default value.
         */
        public boolean isDefault() {
            return isDefault;
        }
    }

    /**
     * Resolve the given expression content. The following guarantees are made with regard to the content:
     * <ol>
     *     <li>The content is the entire string that was included between a leading "${" and a trailing "}".</li>
     *     <li>The content itself does not include the leading "${" and trailing "}".</li>
     *     <li>The content itself does not include any other "${" and trailing "}", i.e. there is no nested expression.</li>
     * </ol>
     *
     * @param expressionContent the string that was between the leading "${"and trailing "}" in an expression
     * @return the result of the resolution, or {@code null} if the expression content could not be resolved.
     */
    ResolutionResult resolveExpressionContent(String expressionContent);
}
