/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * SecurityRoleRefsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
public class SecurityRoleRefsMetaData extends AbstractMappedMetaData<SecurityRoleRefMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1;

    /**
     * Create a new SecurityRoleRefsMetaData.
     */
    public SecurityRoleRefsMetaData() {
        super("role name for security role ref");
    }
}
