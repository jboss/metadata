/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * PropertiesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class PropertiesMetaData extends AbstractMappedMetaData<PropertyMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -1618585082266053135L;

    /**
     * Create a new PropertiesMetaData.
     */
    public PropertiesMetaData() {
        super("property name");
    }
}
