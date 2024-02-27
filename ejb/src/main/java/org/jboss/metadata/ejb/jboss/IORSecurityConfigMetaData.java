/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * IORSecurityConfigMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class IORSecurityConfigMetaData extends IdMetaDataImplWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -4681258049609548746L;

    /**
     * The transport config
     */
    private IORTransportConfigMetaData transportConfig;

    /**
     * The as context
     */
    private IORASContextMetaData asContext;

    /**
     * The as context
     */
    private IORSASContextMetaData sasContext;

    /**
     * Get the transportConfig.
     *
     * @return the transportConfig.
     */
    public IORTransportConfigMetaData getTransportConfig() {
        return transportConfig;
    }

    /**
     * Set the transportConfig.
     *
     * @param transportConfig the transportConfig.
     * @throws IllegalArgumentException for a null transportConfig
     */
    public void setTransportConfig(IORTransportConfigMetaData transportConfig) {
        if (transportConfig == null)
            throw new IllegalArgumentException("Null transportConfig");
        this.transportConfig = transportConfig;
    }

    /**
     * Get the asContext.
     *
     * @return the asContext.
     */
    public IORASContextMetaData getAsContext() {
        return asContext;
    }

    /**
     * Set the asContext.
     *
     * @param asContext the asContext.
     * @throws IllegalArgumentException for a null asContext
     */
    public void setAsContext(IORASContextMetaData asContext) {
        if (asContext == null)
            throw new IllegalArgumentException("Null asContext");
        this.asContext = asContext;
    }

    /**
     * Get the sasContext.
     *
     * @return the sasContext.
     */
    public IORSASContextMetaData getSasContext() {
        return sasContext;
    }

    /**
     * Set the sasContext.
     *
     * @param sasContext the sasContext.
     * @throws IllegalArgumentException for a null sasContext
     */
    public void setSasContext(IORSASContextMetaData sasContext) {
        if (sasContext == null)
            throw new IllegalArgumentException("Null sasContext");
        this.sasContext = sasContext;
    }
}
