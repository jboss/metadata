/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

/**
 * Represents an <pool-config> element of the jboss.xml deployment descriptor
 *
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version <tt>$Revision: 80371 $</tt>
 */
public class PoolConfigMetaData {
    private String value = null;
    private Integer maxSize = null;
    private Integer timeout = null;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        sb.append("[");
        sb.append("poolClass=").append(value);
        sb.append(", maxSize=").append(maxSize);
        sb.append(", timeout=").append(timeout);
        sb.append("]");
        return sb.toString();
    }

    public void merge(PoolConfigMetaData override, PoolConfigMetaData original) {
        if (original != null) {
            if (original.maxSize != null)
                maxSize = original.maxSize;
            if (original.timeout != null)
                timeout = original.timeout;
            if (original.value != null)
                value = original.value;
        }

        if (override != null) {
            if (override.maxSize != null)
                maxSize = override.maxSize;
            if (override.timeout != null)
                timeout = override.timeout;
            if (override.value != null)
                value = override.value;
        }
    }
}
