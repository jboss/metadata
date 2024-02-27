/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import java.util.concurrent.ConcurrentHashMap;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;

public class MethodAttributesMetaData extends AbstractMappedMetaData<MethodAttributeMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 4074287842839442989L;

    /**
     * The cache
     */
    private transient ConcurrentHashMap<String, MethodAttributeMetaData> cache;

    /**
     * Create a new MethodAttributesMetaData.
     */
    public MethodAttributesMetaData() {
        super("method-name for method attributes");
    }

    /**
     * Is this method a read-only method
     *
     * @param methodName the method name
     * @return true for read only
     */
    public boolean isMethodReadOnly(String methodName) {
        MethodAttributeMetaData attribute = getMethodAttribute(methodName);
        return attribute.isReadOnly();
    }

    /**
     * Get the transaction timeout for the method
     *
     * @param methodName the method name
     * @return the transaction timeout
     */
    public int getMethodTransactionTimeout(String methodName) {
        MethodAttributeMetaData attribute = getMethodAttribute(methodName);
        return attribute.getTransactionTimeout();
    }

    /**
     * Get the attributes for the method
     *
     * @param methodName the method name
     * @return the attributes
     */
    public MethodAttributeMetaData getMethodAttribute(String methodName) {
        if (methodName == null)
            return MethodAttributeMetaData.DEFAULT;

        MethodAttributeMetaData result = null;
        if (cache != null) {
            result = cache.get(methodName);
            if (result != null)
                return result;
        }

        for (MethodAttributeMetaData attribute : this) {
            if (attribute.matches(methodName)) {
                result = attribute;
                break;
            }
        }

        if (result == null)
            result = MethodAttributeMetaData.DEFAULT;

        if (cache == null)
            cache = new ConcurrentHashMap<String, MethodAttributeMetaData>();
        cache.put(methodName, result);

        return result;
    }

    public void merge(MethodAttributesMetaData override, MethodAttributesMetaData original) {
        IdMetaDataImplMerger.merge(this, override, original);
        if (original != null) {
            for (MethodAttributeMetaData property : original)
                add(property);
        }
        if (override != null) {
            for (MethodAttributeMetaData property : override)
                add(property);
        }
    }
}
