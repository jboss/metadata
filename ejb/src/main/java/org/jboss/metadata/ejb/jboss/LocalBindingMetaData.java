/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * LocalBindingMetaData
 *
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class LocalBindingMetaData extends IdMetaDataImplWithDescriptions {

    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 8742751094401901120L;

    /**
     * The jndi name
     */
    private String jndiName;

    /**
     * Get the jndiName.
     *
     * @return the jndiName.
     */
    public String getJndiName() {
        return jndiName;
    }

    /**
     * Set the jndiName.
     *
     * @param jndiName the jndiName.
     * @throws IllegalArgumentException for a null jndiName
     */
    public void setJndiName(String jndiName) {
        if (jndiName == null)
            throw new IllegalArgumentException("Null jndiName");
        this.jndiName = jndiName;
    }

}
