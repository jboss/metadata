/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * RelationRoleSourceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class RelationRoleSourceMetaData extends IdMetaDataImplWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -9067826695189947397L;

    /**
     * The ejb name
     */
    private String ejbName;

    /**
     * Create a new RelationRoleSourceMetaData.
     */
    public RelationRoleSourceMetaData() {
        // For serialization
    }

    /**
     * Get the ejbName.
     *
     * @return the ejbName.
     */
    public String getEjbName() {
        return ejbName;
    }

    /**
     * Set the ejbName.
     *
     * @param ejbName the ejbName.
     * @throws IllegalArgumentException for a null ejbName
     */
    public void setEjbName(String ejbName) {
        if (ejbName == null)
            throw new IllegalArgumentException("Null ejbName");
        this.ejbName = ejbName;
    }
}
