/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * InterceptorBindingMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class InterceptorBindingMetaData extends NamedMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 3265594088830429912L;

    /**
     * The interceptor classes
     */
    private InterceptorClassesMetaData interceptorClasses;

    /**
     * The interceptor order
     */
    private InterceptorOrderMetaData interceptorOrder;

    /**
     * Exclude the default interceptors
     */
    private boolean excludeDefaultInterceptors = false;

    /**
     * Exclude the class interceptors
     */
    private boolean excludeClassInterceptors = false;

    /**
     * The method
     */
    private NamedMethodMetaData method;

    /**
     * Create a new MethodMetaData.
     */
    public InterceptorBindingMetaData() {
        // For serialization
    }

    /**
     * Get the ejbName.
     *
     * @return the ejbName.
     */
    public String getEjbName() {
        return getName();
    }

    /**
     * Set the ejbName.
     *
     * @param ejbName the ejbName.
     * @throws IllegalArgumentException for a null ejbName
     */
    public void setEjbName(String ejbName) {
        setName(ejbName);
    }

    /**
     * Get the excludeClassInterceptors.
     *
     * @return the excludeClassInterceptors.
     */
    public boolean isExcludeClassInterceptors() {
        return excludeClassInterceptors;
    }

    /**
     * Set the excludeClassInterceptors.
     *
     * @param excludeClassInterceptors the excludeClassInterceptors.
     */
    public void setExcludeClassInterceptors(boolean excludeClassInterceptors) {
        this.excludeClassInterceptors = excludeClassInterceptors;
    }

    /**
     * Get the excludeDefaultInterceptors.
     *
     * @return the excludeDefaultInterceptors.
     */
    public boolean isExcludeDefaultInterceptors() {
        return excludeDefaultInterceptors;
    }

    /**
     * Set the excludeDefaultInterceptors.
     *
     * @param excludeDefaultInterceptors the excludeDefaultInterceptors.
     */
    public void setExcludeDefaultInterceptors(boolean excludeDefaultInterceptors) {
        this.excludeDefaultInterceptors = excludeDefaultInterceptors;
    }

    /**
     * Get the interceptorClasses.
     *
     * @return the interceptorClasses.
     */
    public InterceptorClassesMetaData getInterceptorClasses() {
        return interceptorClasses;
    }

    /**
     * Set the interceptorClasses.
     *
     * @param interceptorClasses the interceptorClasses.
     * @throws IllegalArgumentException for a null interceptorClasses
     */
    public void setInterceptorClasses(InterceptorClassesMetaData interceptorClasses) {
        if (interceptorClasses == null)
            throw new IllegalArgumentException("Null interceptorClasses");

        assert interceptorOrder == null : "Can't have both interceptorClasses and interceptorOrder";

        this.interceptorClasses = interceptorClasses;
    }

    /**
     * Is this binding a total ordering or a list of interceptor classes.
     *
     * @return true if it is a total ordering
     */
    public boolean isTotalOrdering() {
        return interceptorOrder != null;
    }

    /**
     * Get the interceptorOrder.
     *
     * @return the interceptorOrder.
     */
    public InterceptorOrderMetaData getInterceptorOrder() {
        return interceptorOrder;
    }

    /**
     * Set the interceptorOrder.
     *
     * @param interceptorOrder the interceptorOrder.
     * @throws IllegalArgumentException for a null interceptorOrder
     */
    public void setInterceptorOrder(InterceptorOrderMetaData interceptorOrder) {
        if (interceptorOrder == null)
            throw new IllegalArgumentException("Null interceptorOrder");

        assert interceptorClasses == null : "Can't have both interceptorOrder and interceptorClasses";

        this.interceptorOrder = interceptorOrder;
    }

    /**
     * Get the method.
     *
     * @return the method.
     */
    public NamedMethodMetaData getMethod() {
        return method;
    }

    /**
     * Set the method.
     *
     * @param method the method.
     * @throws IllegalArgumentException for a null method
     */
    public void setMethod(NamedMethodMetaData method) {
        if (method == null)
            throw new IllegalArgumentException("Null method");
        this.method = method;
    }
}
