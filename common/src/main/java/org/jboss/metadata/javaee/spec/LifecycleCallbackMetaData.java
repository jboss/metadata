/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import java.io.Serializable;

/**
 * LifecycleCallbackMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
public class LifecycleCallbackMetaData implements Serializable {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 6453746684927606565L;

    /**
     * The class
     */
    private String className;

    /**
     * The method name
     */
    private String methodName;

    /**
     * Create a new LifecycleCallbackMetaData.
     */
    public LifecycleCallbackMetaData() {
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

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if (obj instanceof LifecycleCallbackMetaData) {
            LifecycleCallbackMetaData lcmd = (LifecycleCallbackMetaData) obj;
            if (className == lcmd.className || (className != null && className.equals(lcmd.className))) {
                equals = methodName == lcmd.methodName || methodName != null && methodName.equals(lcmd.methodName);
            }
        }
        return equals;
    }

    @Override
    public int hashCode() {
        int hashCode = className != null ? className.hashCode() : 0;
        hashCode += methodName != null ? methodName.hashCode() : 0;
        return hashCode;
    }

}
