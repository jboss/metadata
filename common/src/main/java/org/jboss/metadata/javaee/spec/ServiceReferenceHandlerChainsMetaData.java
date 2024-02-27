/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import java.util.List;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * ServiceReferenceHandlerChainsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
// @SchemaType(name="service-ref_handler-chainsType", mandatory=false)
public class ServiceReferenceHandlerChainsMetaData
// extends NonNullLinkedHashSet<ServiceReferenceHandlerChainMetaData>
        extends IdMetaDataImpl {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1;
    private List<ServiceReferenceHandlerChainMetaData> handlers;

    /**
     * Create a new ServiceReferenceChainssMetaData.
     */
    public ServiceReferenceHandlerChainsMetaData() {
        // For serialization
    }

    public List<ServiceReferenceHandlerChainMetaData> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<ServiceReferenceHandlerChainMetaData> handlers) {
        this.handlers = handlers;
    }
}
