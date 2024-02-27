/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.property;

import java.util.Properties;

/**
 * Resolves properties using a {@code Properties} object.
 *
 * @author John Bailey
 */
public class PropertiesPropertyResolver extends JBossASSimpleExpressionResolver {
    private final Properties deploymentProperties;

    public PropertiesPropertyResolver(final Properties deploymentProperties) {
        this.deploymentProperties = deploymentProperties;
    }

    @Override
    protected String resolveKey(String key) {
        return deploymentProperties.getProperty(key);
    }
}
