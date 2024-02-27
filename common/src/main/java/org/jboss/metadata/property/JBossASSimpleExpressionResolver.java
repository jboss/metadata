/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.property;

import java.io.File;

/**
 * Base class for {@link org.jboss.metadata.property.SimpleExpressionResolver} implementations that
 * handle traditional JBoss AS style expressions, with syntax {@code key[,key]*[:defaultValue]}.
 * This class handles:
 * <ol>
 *     <li>any {@code defaultValue} if all {@code key} values are unresolvable</li>
 *     <li>separation of multiple comma-delimited {@code key} values</li>
 *     <li>the special cases where {@code key} is the string "/" or ":", returning
 *      {@link File#separator} and {@link File#pathSeparator}, respectively.
 *     </li>
 * </ol>
 *
 * @author Brian Stansberry (c) 2014 Red Hat Inc.
 */
public abstract class JBossASSimpleExpressionResolver implements SimpleExpressionResolver {

    private static final int INITIAL = 0;
    private static final int DEFAULT = 1;

    /**
     * Parses the given expression, extracting any key if present and returning the resolved
     * value, if available, or, if not, returning any default value that is present. See the class javadoc
     * for full details.
     *
     * @param expressionContent the expression
     * @return the resolved value, or null if the value cannot be resolved
     */
    @Override
    public ResolutionResult resolveExpressionContent(String expressionContent) {
        final int len = expressionContent.length();
        int state = INITIAL;
        int nameStart = 0;
        for (int i = 0; i < len; i = expressionContent.offsetByCodePoints(i, 1)) {
            final int ch = expressionContent.codePointAt(i);
            switch (state) {
                case INITIAL: {
                    switch (ch) {
                        case ':':
                            if (nameStart == i) {
                                // not a default delimiter; same as default case
                                continue;
                            }
                            // else fall into the logic for 'end of key to resolve cases' ","
                        case ',': {
                            final String key = expressionContent.substring(nameStart, i).trim();
                            final String val = resolveExpressionKey(key);

                            if (val != null) {
                                return new ResolutionResult(val, false);
                            } else if (ch == ',') {
                                nameStart = i + 1;
                                continue;
                            } else {
                                // we can assert ch == ':';
                                nameStart = i + 1;
                                state = DEFAULT;
                                continue;
                            }
                        }
                        default: {
                            continue;
                        }
                    }
                    // not reachable
                }
                case DEFAULT: {
                    continue;
                }
                default:
                    // unreachable unless someone alters the class
                    throw new IllegalStateException();
            }
        }
        switch (state) {
            case INITIAL : {
                final String key = expressionContent.substring(nameStart).trim();
                String resolved = resolveExpressionKey(key);
                return resolved == null ? null : new ResolutionResult(resolved, false);
            }
            case DEFAULT: {
                return new ResolutionResult(expressionContent.substring(nameStart), true);
            }
            default:
                // unreachable unless someone alters the class
                throw new IllegalStateException();
        }
    }

    /**
     * Resolve a value with the given key.
     *
     * @param key the key. Will not be {@code null}
     * @return the resolved value, or {@code null} if no value corresponds to {@code key}
     */
    protected abstract String resolveKey(String key);

    private String resolveExpressionKey(String key) {
        if ("/".equals(key)) {
            return File.separator;
        } else if (":".equals(key)) {
            return File.pathSeparator;
        }
        return resolveKey(key);
    }
}
