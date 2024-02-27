/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.property;

/**
 * Converts between {@link org.jboss.metadata.property.PropertyResolver} by delegating the
 * {@link #resolveExpressionContent(String)} call to a provided {@code PropertyResolver}.
 *
 * @author Brian Stansberry (c) 2014 Red Hat Inc.
 */
public class CompatibilityExpressionResolver implements SimpleExpressionResolver {

    private final SimpleExpressionResolver propertyResolver;

    public CompatibilityExpressionResolver(SimpleExpressionResolver propertyResolver) {
        this.propertyResolver = propertyResolver;
    }

    @Override
    public ResolutionResult resolveExpressionContent(String expressionContent) {
        return propertyResolver.resolveExpressionContent(expressionContent);
    }
}
