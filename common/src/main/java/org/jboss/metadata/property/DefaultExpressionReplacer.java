/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat, Inc., and individual contributors
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

import java.util.Stack;
import java.util.regex.Pattern;

/**
 * {@link org.jboss.metadata.property.PropertyReplacer} that can deal with nested expressions and
 * with recursively resolution in cases where expression resolves to another expression.
 *
 * @author Brian Stansberry (c) 2014 Red Hat Inc.
 */
public class DefaultExpressionReplacer implements PropertyReplacer {

    /** A {@link java.util.regex.Pattern} that can be used to identify strings that include expression syntax */
    private Pattern EXPRESSION_PATTERN = Pattern.compile(".*\\$\\{.*\\}.*");

    private static final int INITIAL = 0;
    private static final int GOT_DOLLAR = 1;
    private static final int GOT_OPEN_BRACE = 2;

    private final SimpleExpressionResolver resolver;

    DefaultExpressionReplacer(SimpleExpressionResolver resolver) {
        this.resolver = getDefaultCompatibleResolver(resolver);
    }

    @SuppressWarnings("deprecation")
    DefaultExpressionReplacer(final PropertyResolver resolver) {
        SimpleExpressionResolver ser = resolver instanceof SimpleExpressionResolver
                ? (SimpleExpressionResolver) resolver
                : new CompatibilityExpressionResolver(resolver);
        this.resolver = getDefaultCompatibleResolver(ser);
    }

    // DefaultPropertyReplacer used to provide the key1,key2:default semantics, so if the
    // configured resolver doesn't, make sure something does
    private static SimpleExpressionResolver getDefaultCompatibleResolver(SimpleExpressionResolver resolver) {
        if (resolver instanceof JBossASSimpleExpressionResolver
                || (resolver instanceof CompositePropertyResolver
                    && ((CompositePropertyResolver) resolver).hasJBossASExpressionSupport)) {
            return resolver;
        }
        // Create a composite resolver that will include the needed support
        return new CompositePropertyResolver(resolver, new JBossASSimpleExpressionResolver() {
            @Override
            protected String resolveKey(String key) {
                return null;
            }
        });
    }

    @Override
    public String replaceProperties(String text) {
        return resolveExpressionStringRecursively(text, false, true);
    }

    /**
     * Attempt to resolve the given expression string, recursing if resolution of one string produces
     * another expression.
     *
     * @param expressionString the expression string
     * @param ignoreResolutionFailure {@code false} if resolution failures should be ignored and {@code expressionString} returned
     * @param initial {@code true} if this call originated outside this method; {@code false} if it is a recursive call
     *
     * @return a string containing the resolved expression, or {@code expressionString} if
     *         {@code ignoreResolutionFailure} and {@code initial} are both {@code true} and the string could not be resolved.
     */
    private String resolveExpressionStringRecursively(final String expressionString, final boolean ignoreResolutionFailure,
                                                         final boolean initial) {
        ParseAndResolveResult resolved = parseAndResolve(expressionString, ignoreResolutionFailure);
        if (resolved.recursive) {
            // Some part of expressionString resolved into a different expression.
            // So, start over, ignoring failures. Ignore failures because we don't require
            // that expressions must not resolve to something that *looks like* an expression but isn't
            return resolveExpressionStringRecursively(resolved.result, true, false);
        } else if (resolved.modified) {
            // Typical case
            return resolved.result;
        } else if (initial && EXPRESSION_PATTERN.matcher(expressionString).matches()) {
            // We should only get an unmodified expression string back if there was a resolution
            // failure that we ignored.
            assert ignoreResolutionFailure;
            // expressionString came from a node of type expression, so since we did nothing send it back in the same type
            return expressionString;
        } else {
            // The string wasn't really an expression. Two possible cases:
            // 1) if initial == true, it just wasn't an expression
            // 2) if initial == false, we resolved from an expression to a string that looked like an
            // expression but can't be resolved. We don't require that expressions must not resolve to something that
            // *looks like* an expression but isn't, so we'll just treat this as a string
            return expressionString;
        }
    }

    private ParseAndResolveResult parseAndResolve(final String initialValue, boolean lenient) {

        final StringBuilder builder = new StringBuilder();
        final int len = initialValue.length();
        int state = INITIAL;
        int ignoreBraceLevel = 0;
        boolean modified = false;
        Stack<OpenExpression> stack = new Stack<OpenExpression>();
        for (int i = 0; i < len; i = initialValue.offsetByCodePoints(i, 1)) {
            final int ch = initialValue.codePointAt(i);
            switch (state) {
                case INITIAL: {
                    switch (ch) {
                        case '$': {
                            stack = addToStack(stack, i);
                            state = GOT_DOLLAR;
                            continue;
                        }
                        default: {
                            builder.appendCodePoint(ch);
                            continue;
                        }
                    }
                    // not reachable
                }
                case GOT_DOLLAR: {
                    switch (ch) {
                        case '{': {
                            state = GOT_OPEN_BRACE;
                            continue;
                        }
                        default: {
                            // Previous $ was not the start of an expression
                            if (stack.size() == 1) {
                                // not in an outer expression; store to the builder and resume
                                stack.clear(); // looks faster than pop()
                                if (ch != '$') {
                                    // Preceding $ wasn't an escape, so restore it
                                    builder.append('$');
                                } else {
                                    modified = true; // since we discarded the '$'
                                }
                                builder.appendCodePoint(ch);
                                state = INITIAL;
                            } else {
                                // We're in an outer expression, so just discard the top stack element
                                // created when we saw the '$' and resume tracking the outer expression
                                stack.pop();
                                state = GOT_OPEN_BRACE;
                            }
                            continue;
                        }
                    }
                    // not reachable
                }
                case GOT_OPEN_BRACE: {
                    switch (ch) {
                        case '$': {
                            stack.push(new OpenExpression(i));
                            state = GOT_DOLLAR;
                            continue;
                        }
                        case '{': {
                            ignoreBraceLevel++;
                            continue;
                        }
                        case '}': {
                            if (ignoreBraceLevel > 0) {
                                ignoreBraceLevel--;
                                continue;
                            }
                            String toResolve = getStringToResolve(initialValue, stack, i);
                            final SimpleExpressionResolver.ResolutionResult rr = resolver.resolveExpressionContent(toResolve);
                            String resolved = rr == null ? null : rr.getValue();
                            // We only successfully resolved if toResolve != resolved
                            if (resolved != null) {
                                if (EXPRESSION_PATTERN.matcher(resolved).matches()) {
                                    // The resolved value is itself an expression, so
                                    // there will need to be another pass.
                                    // We need to discard any changes made from initialValue
                                    // prior to this expression, because if there were any
                                    // escaped $ sequences in there, we can't lose the escape char
                                    return createRecursiveResult(initialValue, resolved, stack, i);
                                }

                                // Non-recursive case

                                // Update the stack
                                recordResolutionInStack(resolved, stack);

                                if (stack.size() == 0) {
                                    // All expressions so far are resolved; record for output
                                    builder.append(resolved);
                                    state = INITIAL;
                                } else {
                                    // We resolved a nested expression; keep going with the outer one
                                    state = GOT_OPEN_BRACE;
                                }
                                modified = true;
                                continue;
                            } else if (stack.size() > 1) {
                                // We don't fail the overall resolution due to not resolving a nested expression,
                                // as the nested part may be irrelevant to the final resolution.
                                // For example '${bar}' is irrelevant to resolving '${foo:${bar}}'
                                // if system property 'foo' is set.

                                // Clean up stack and store toResolve so we don't have to build it again
                                // when we get to the end of the outer expression
                                recordResolutionInStack(toResolve, stack);
                                state = GOT_OPEN_BRACE;
                                continue;
                            } else if (lenient) {
                                // just respond with the initial value
                                return new ParseAndResolveResult(initialValue, false, false);
                            } else {
                                throw new IllegalStateException("Failed to resolve expression: " + toResolve);
                            }
                        }
                        default: {
                            continue;
                        }
                    }
                    // not reachable
                }
                default:
                    // If we reach this, there's a programming error in this class
                    throw new IllegalStateException();
            }
        }

        if (stack != null && stack.size() > 0) {
            if (state == GOT_DOLLAR) {
                stack.pop();
            }

            if (stack.size() > 0) {
                throw new IllegalStateException("Incomplete expression: " + initialValue);
            }

            // Stack was a single item due to GOT_DOLLAR. Need to restore the lost $
            builder.append('$');
        }

        return new ParseAndResolveResult(builder.toString(), modified, false);
    }

    private static Stack<OpenExpression> addToStack(Stack<OpenExpression> stack, int startIndex) {
        Stack<OpenExpression> result = stack == null ? new Stack<OpenExpression>() : stack;
        result.push(new OpenExpression(startIndex));
        return result;
    }

    private static String getStringToResolve(String initialValue, Stack<OpenExpression> stack, int expressionEndIndex) {
        int stackSize = stack.size();

        int expressionElement = -1;
        OpenExpression firstUnresolved = null;
        for (int i = stackSize - 1; i >= 0; i--) {
            OpenExpression oe = stack.get(i);
            if (oe.resolvedValue == null) {
                expressionElement = i;
                firstUnresolved = oe;
                break;
            }
        }

        assert expressionElement > -1;

        // Now we know how long this expression is
        firstUnresolved.endIndex = expressionEndIndex;

        if (expressionElement == stackSize - 1) {
            // Simple case; no already resolved nested stuff to patch in
            // Just strip off leading ${ and trailing }
            return initialValue.substring(firstUnresolved.startIndex + 2, expressionEndIndex);
        } else {
            // Compose the new expression from the original and resolved nested elements
            StringBuilder sb = new StringBuilder();
            int nextStart = firstUnresolved.startIndex + 2; // skip ${
            for (int i = expressionElement + 1; i < stackSize; i++) {
                OpenExpression oe = stack.get(i);
                sb.append(initialValue.substring(nextStart, oe.startIndex));
                sb.append(oe.resolvedValue);
                nextStart = oe.endIndex + 1;
            }
            if (nextStart < expressionEndIndex) {
                sb.append(initialValue.substring(nextStart, expressionEndIndex)); // drop trailing }
            }
            return sb.toString();
        }
    }

    private static ParseAndResolveResult createRecursiveResult(String initialValue, String val,
                                                          Stack<OpenExpression> stack, int expressionEndIndex) {
        int initialLength = initialValue.length();

        int expressionIndex = -1;
        while (expressionIndex == -1) {
            OpenExpression oe = stack.pop();
            if (oe.resolvedValue == null) {
                expressionIndex = oe.startIndex;
            }
        }
        String result;
        if (expressionIndex == 0 && expressionEndIndex == initialLength -1) {
            // basic case
            result = val;
        } else if (expressionIndex == 0) {
            result = val + initialValue.substring(expressionEndIndex + 1);
        } else {
            StringBuilder sb = new StringBuilder(initialValue.substring(0, expressionIndex));
            sb.append(val);
            if (expressionEndIndex < initialLength - 1) {
                sb.append(initialValue.substring(expressionEndIndex + 1));
            }
            result = sb.toString();

        }
        return new ParseAndResolveResult(result, true, true);
    }

    private static void recordResolutionInStack(String val, Stack<OpenExpression> stack) {

        for (int i = stack.size() -1; i >= 0; i--) {
            OpenExpression oe = i == 0 ? stack.pop() : stack.peek();
            if (oe.resolvedValue == null) {
                oe.resolvedValue = val;
                break;
            } else {
                assert i > 0;
                // Don't need the nested data any more
                stack.pop();
            }
        }
    }

    private static class ParseAndResolveResult {
        private final String result;
        private final boolean modified;
        private final boolean recursive;

        private ParseAndResolveResult(String result, boolean modified, boolean recursive) {
            this.result = result;
            this.modified = modified;
            this.recursive = recursive;
        }
    }

    private static class OpenExpression {
        private final int startIndex;
        private int endIndex = -1;
        private String resolvedValue;

        private OpenExpression(int startIndex) {
            this.startIndex = startIndex;
        }
    }
}
