/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.property;

/**
 * Attempt to resolve properties using system and environment properties.
 *
 * @author John Bailey
 */
public class SystemPropertyResolver extends JBossASSimpleExpressionResolver {
    public static final SystemPropertyResolver INSTANCE = new SystemPropertyResolver();

    private SystemPropertyResolver() {
    }

    @Override
    protected String resolveKey(String key) {
        // First check for system property, then env variable
        String val = System.getProperty(key);
        if (val == null && key.startsWith("env.")) {
            val = System.getenv(key.substring(4));
        }
        return val;
    }
}
