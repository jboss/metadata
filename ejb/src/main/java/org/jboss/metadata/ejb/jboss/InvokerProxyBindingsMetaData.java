/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import org.jboss.metadata.javaee.support.MappedMetaDataWithDescriptions;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplWithDescriptionsMerger;

/**
 * InvokerProxyBindingsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class InvokerProxyBindingsMetaData extends MappedMetaDataWithDescriptions<InvokerProxyBindingMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -6052572082285734800L;

    /**
     * Create a new InvokerProxyBindingsMetaData.
     */
    public InvokerProxyBindingsMetaData() {
        super("invoker-proxy-binding-name for invoker-proxy-binding");
    }

    /**
     * Simply merges all InvokerProxyBindingMetaData from extra
     * into this as InvokerProxyBindingMetaData does not merge.
     *
     * @param extra - a collection of InvokerProxyBindingMetaData
     */
    public void merge(InvokerProxyBindingsMetaData extra) {
        if (extra == null)
            return;
        this.addAll(extra);
    }

    public void merge(InvokerProxyBindingsMetaData override, InvokerProxyBindingsMetaData original) {
        IdMetaDataImplWithDescriptionsMerger.merge(this, override, original);

        // addAll
        merge(override);
        merge(original);
    }
}
