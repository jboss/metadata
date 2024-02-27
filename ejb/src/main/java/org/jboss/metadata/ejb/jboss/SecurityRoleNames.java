/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

/**
 * Constants for the well known security role/principal names.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 65859 $
 */
public interface SecurityRoleNames {
    /**
     * The principal name representing a principal that matches no one
     */
    String NOBODY_PRINCIPAL = "<NOBODY>";
    /**
     * The principal name representing a principal that matches anyone
     */
    String ANYBODY_PRINCIPAL = "<ANYBODY>";
}
