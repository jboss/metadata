/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.common.ejb;

import org.jboss.metadata.javaee.support.MappedMetaDataWithDescriptions;

/**
 * ResourceManagersMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ResourceManagersMetaData extends MappedMetaDataWithDescriptions<ResourceManagerMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -4930331173512895612L;

    /**
     * Create a new ResourceManagersMetaData.
     */
    public ResourceManagersMetaData() {
        super("res-name for resource manager");
    }
}
