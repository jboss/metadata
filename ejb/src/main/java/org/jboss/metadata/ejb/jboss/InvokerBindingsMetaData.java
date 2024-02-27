/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import org.jboss.metadata.javaee.support.MappedMetaDataWithDescriptions;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplWithDescriptionsMerger;

/**
 * InvokerBindingsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class InvokerBindingsMetaData extends MappedMetaDataWithDescriptions<InvokerBindingMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 7637011120262076039L;

    /**
     * Create a new InvokerBindingsMetaData.
     */
    public InvokerBindingsMetaData() {
        super("invoker-proxy-binding-name for invoker");
    }

    public void merge(InvokerBindingsMetaData override, InvokerBindingsMetaData original) {
        IdMetaDataImplWithDescriptionsMerger.merge(this, override, original);
        if (original != null) {
            for (InvokerBindingMetaData property : original)
                add(property);
        }
        if (override != null) {
            for (InvokerBindingMetaData property : override)
                add(property);
        }
    }
}
