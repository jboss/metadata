/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import org.jboss.metadata.common.jboss.WebserviceDescriptionsMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImpl;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;

/**
 * WebservicesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class WebservicesMetaData extends IdMetaDataImpl {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -3222358198684762084L;

    /**
     * The context root
     */
    private String contextRoot;

    /**
     * The webservice descriptions
     */
    private WebserviceDescriptionsMetaData webserviceDescriptions;

    /**
     * Get the contextRoot.
     *
     * @return the contextRoot.
     */
    public String getContextRoot() {
        return contextRoot;
    }

    /**
     * Set the contextRoot.
     *
     * @param contextRoot the contextRoot.
     * @throws IllegalArgumentException for a null contextRoot
     */
    public void setContextRoot(String contextRoot) {
        if (contextRoot == null)
            throw new IllegalArgumentException("Null contextRoot");
        this.contextRoot = contextRoot;
    }

    /**
     * Get the webserviceDescriptions.
     *
     * @return the webserviceDescriptions.
     */
    public WebserviceDescriptionsMetaData getWebserviceDescriptions() {
        return webserviceDescriptions;
    }

    /**
     * Set the webserviceDescriptions.
     *
     * @param webserviceDescriptions the webserviceDescriptions.
     * @throws IllegalArgumentException for a null webserviceDescriptions
     */
    public void setWebserviceDescriptions(WebserviceDescriptionsMetaData webserviceDescriptions) {
        if (webserviceDescriptions == null)
            throw new IllegalArgumentException("Null webserviceDescriptions");
        this.webserviceDescriptions = webserviceDescriptions;
    }

    public void merge(WebservicesMetaData original) {
        if (original != null) {
            contextRoot = original.contextRoot;
            webserviceDescriptions = original.webserviceDescriptions;
        }
    }

    public void merge(WebservicesMetaData override, WebservicesMetaData original) {
        IdMetaDataImplMerger.merge(this, override, original);
        if (override != null) {
            merge(override);
        } else if (override == null && original != null) {
            merge(original);
        }
    }
}
