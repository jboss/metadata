/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.jboss;

import org.jboss.metadata.javaee.support.ResourceInjectionMetaDataWithDescriptions;

/**
 * JndiRefMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class JndiRefMetaData extends ResourceInjectionMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 6572104852686049731L;

    /**
     * Get the jndiRefName.
     *
     * @return the jndiRefName.
     */
    public String getJndiRefName() {
        return getName();
    }

    /**
     * Set the jndiRefName.
     *
     * @param jndiRefName the jndiRefName.
     * @throws IllegalArgumentException for a null jndiRefName
     */
    public void setJndiRefName(String jndiRefName) {
        setName(jndiRefName);
    }
}
