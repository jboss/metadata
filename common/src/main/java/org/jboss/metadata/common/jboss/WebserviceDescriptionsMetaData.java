/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.common.jboss;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * WebservicesDescriptionsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class WebserviceDescriptionsMetaData extends AbstractMappedMetaData<WebserviceDescriptionMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -1286417060419536332L;

    /**
     * Create a new WebservicesDescriptionsMetaData.
     */
    public WebserviceDescriptionsMetaData() {
        super("webservice-description-name for webservice description");
    }
}
