/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import org.jboss.metadata.javaee.support.MappedMetaDataWithDescriptions;

/**
 * ContainerConfigurationsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ContainerConfigurationsMetaData extends MappedMetaDataWithDescriptions<ContainerConfigurationMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 8621050513072760399L;

    /**
     * Create a new ContainerConfigurationsMetaData.
     */
    public ContainerConfigurationsMetaData() {
        super("container-name for container configuration");
    }

    /**
     * Simply merges all ContainerConfigurationMetaData from extra
     * into this as ContainerConfigurationMetaData does not merge.
     *
     * @param extra - a collection of ContainerConfigurationMetaData
     */
    public void merge(ContainerConfigurationsMetaData extra) {
        if (extra == null)
            return;
        this.addAll(extra);
    }
}
