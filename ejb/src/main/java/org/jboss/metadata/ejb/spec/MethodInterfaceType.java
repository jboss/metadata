/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

/**
 * MethodInterfaceType.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public enum MethodInterfaceType {
    /**
     * Home
     */
    Home,

    /**
     * Remote
     */
    Remote,

    /**
     * LocalHome
     */
    LocalHome,

    /**
     * Local
     */
    Local,

    /**
     * ServiceEndpoint
     */
    ServiceEndpoint,

    /**
     * Timer
     */
    Timer,

    /**
     * MessageEndpoint
     */
    MessageEndpoint,

    /**
     * Used by internal implementation to indicate a direct call to the Jakarta Enterprise Beans.
     * Not specified by the spec.
     */
    Bean
}
