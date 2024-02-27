/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.jboss.ejb3;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class AbstractEJBBoundMetaData extends NamedMetaDataWithDescriptions {
    /**
     * Get the ejbName.
     *
     * @return the ejbName.
     */
    public String getEjbName() {
        return getName();
    }

    /**
     * Set the ejbName.
     *
     * @param ejbName the ejbName.
     * @throws IllegalArgumentException for a null ejbName
     */
    public void setEjbName(String ejbName) {
        setName(ejbName);
    }
}
