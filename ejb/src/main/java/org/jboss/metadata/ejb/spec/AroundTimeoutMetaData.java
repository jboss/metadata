/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.io.Serializable;

/**
 * @author Stuart Douglas
 */
public class AroundTimeoutMetaData implements Serializable {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -4782968110296545024L;

    /**
     * The class
     */
    private String className;

    /**
     * The method name
     */
    private String methodName;

    /**
     * Create a new AroundInvokeMetaData.
     */
    public AroundTimeoutMetaData() {
        // For serialization
    }

    /**
     * Get the className.
     *
     * @return the className.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Set the className.
     *
     * @param className the className.
     * @throws IllegalArgumentException for a null className
     */
    public void setClassName(String className) {
        if (className == null)
            throw new IllegalArgumentException("Null className");
        this.className = className;
    }

    /**
     * Get the methodName.
     *
     * @return the methodName.
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Set the methodName.
     *
     * @param methodName the methodName.
     * @throws IllegalArgumentException for a null methodName
     */
    public void setMethodName(String methodName) {
        if (methodName == null)
            throw new IllegalArgumentException("Null methodName");
        this.methodName = methodName;
    }
}
