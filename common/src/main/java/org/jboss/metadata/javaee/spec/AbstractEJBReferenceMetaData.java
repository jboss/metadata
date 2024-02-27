/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.ResourceInjectionMetaDataWithDescriptions;

/**
 * AbstractEJBReferenceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractEJBReferenceMetaData extends ResourceInjectionMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 6067128692691909155L;

    /**
     * The type
     */
    private EJBReferenceType type;

    /**
     * The link
     */
    private String link;

    /**
     * Create a new EJBReferenceMetaData.
     */
    public AbstractEJBReferenceMetaData() {
        // For serialization
    }

    /**
     * Get the ejbRefName.
     *
     * @return the ejbRefName.
     */
    public String getEjbRefName() {
        return getName();
    }

    /**
     * Set the ejbRefName.
     *
     * @param ejbRefName the ejbRefName.
     * @throws IllegalArgumentException for a null ejbRefName
     */
    public void setEjbRefName(String ejbRefName) {
        setName(ejbRefName);
    }

    /**
     * Get the type.
     *
     * @return the type.
     */
    public EJBReferenceType getEjbRefType() {
        return type;
    }

    /**
     * Set the type.
     *
     * @param type the type.
     * @throws IllegalArgumentException for a null type
     */
    public void setEjbRefType(EJBReferenceType type) {
        if (type == null)
            throw new IllegalArgumentException("Null type");
        this.type = type;
    }

    /**
     * Get the type.
     *
     * @return the type.
     */
    public String getType() {
        if (type == null)
            throw new IllegalStateException("Type has not been set: " + this);
        return type.name();
    }

    /**
     * Get the link.
     *
     * @return the link.
     */
    public String getLink() {
        return link;
    }

    /**
     * Set the link.
     *
     * @param link the link.
     * @throws IllegalArgumentException for a null link
     */
    public void setLink(String link) {
        if (link == null)
            throw new IllegalArgumentException("Null link");
        this.link = link;
    }
}
