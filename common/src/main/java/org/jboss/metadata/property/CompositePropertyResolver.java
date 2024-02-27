/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Resolves properties using a series of other {@code PropertyResolver}s. If any are implementations of
 * {@link SimpleExpressionResolver}, then any default resolution they provide
 * will not be returned unless no other resolver provides a non-default resolution.
 * See https://issues.jboss.org/browse/JBMETA-371 for the problem this addresses.
 *
 * @author John Bailey
 */
public class CompositePropertyResolver implements SimpleExpressionResolver {
    private final Collection<SimpleExpressionResolver> expressionResolvers;
    final boolean hasJBossASExpressionSupport;

    public CompositePropertyResolver(SimpleExpressionResolver... resolvers) {
        this.expressionResolvers = new ArrayList<SimpleExpressionResolver>(resolvers.length);
        boolean jbossSupport = false;
        for (SimpleExpressionResolver ser : resolvers) {
            expressionResolvers.add(ser);
            if (!jbossSupport) {
                if (ser instanceof JBossASSimpleExpressionResolver) {
                    jbossSupport = true;
                } else if (ser instanceof CompositePropertyResolver
                        && ((CompositePropertyResolver) ser).hasJBossASExpressionSupport) {
                    jbossSupport = true;
                }
            }
        }
        this.hasJBossASExpressionSupport = jbossSupport;
    }

    @Override
    public ResolutionResult resolveExpressionContent(String expressionContent) {

        ResolutionResult value = null;
        ResolutionResult defaultValue = null;
        if (expressionResolvers != null) {
            for (Iterator<SimpleExpressionResolver> iter = expressionResolvers.iterator(); iter.hasNext() && value == null; ) {
                SimpleExpressionResolver.ResolutionResult rr = iter.next().resolveExpressionContent(expressionContent);
                if (rr != null) {
                    if (!rr.isDefault()) {
                        value = rr;
                    } else if (defaultValue == null) {
                        defaultValue = rr;
                    }
                }
            }
        }
        return value == null ? defaultValue : value;
    }
}
