/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.jboss;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * JndiRefsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class JndiRefsMetaData extends AbstractMappedMetaData<JndiRefMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 7447964711034672079L;

    /**
     * Create a new JndiRefsMetaData.
     */
    public JndiRefsMetaData() {
        super("jndi ref name");
    }
}
