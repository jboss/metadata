/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

/**
 * The web-fragment spec metadata
 *
 * @author Remy Maucherat
 * @version $Revision: 70996 $
 */
public class WebFragmentMetaData extends WebCommonMetaData {
    private static final long serialVersionUID = 1;

    private OrderingMetaData ordering;
    private boolean metadataComplete;
    private String name;

    public OrderingMetaData getOrdering() {
        return ordering;
    }

    public void setOrdering(OrderingMetaData ordering) {
        this.ordering = ordering;
    }

    public boolean isMetadataComplete() {
        return metadataComplete;
    }

    public void setMetadataComplete(boolean metadataComplete) {
        this.metadataComplete = metadataComplete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
