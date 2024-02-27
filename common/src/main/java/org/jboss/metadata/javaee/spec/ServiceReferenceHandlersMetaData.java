/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * ServiceReferenceHandlersMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ServiceReferenceHandlersMetaData extends AbstractMappedMetaData<ServiceReferenceHandlerMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -732816449464795631L;

    /**
     * Create a new ServiceReferencesMetaData.
     */
    public ServiceReferenceHandlersMetaData() {
        super("handler name for service ref handler");
    }
}
