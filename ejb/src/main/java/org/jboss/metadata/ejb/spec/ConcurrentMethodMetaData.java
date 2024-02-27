/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.io.Serializable;

import jakarta.ejb.Lock;
import jakarta.ejb.LockType;

/**
 * Metadata for methods which specify {@link Lock} for concurrency management
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public class ConcurrentMethodMetaData implements Serializable {

    private static final long serialVersionUID = 1L;

    private NamedMethodMetaData method;

    private LockType lockType;

    private AccessTimeoutMetaData accessTimeout;

    public void setMethod(NamedMethodMetaData namedMethod) {
        this.method = namedMethod;
    }

    public NamedMethodMetaData getMethod() {
        return this.method;
    }

    public void setLockType(LockType lockType) {
        this.lockType = lockType;
    }

    public LockType getLockType() {
        return this.lockType;
    }

    public void setAccessTimeout(AccessTimeoutMetaData accessTimeout) {
        this.accessTimeout = accessTimeout;
    }

    public AccessTimeoutMetaData getAccessTimeout() {
        return this.accessTimeout;
    }

    /**
     * A {@link ConcurrentMethodMetaData} is equal to another {@link ConcurrentMethodMetaData} if
     * the {@link ConcurrentMethodMetaData#method} of both the {@link ConcurrentMethodMetaData} is equal
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof ConcurrentMethodMetaData))
            return false;

        ConcurrentMethodMetaData anotherConcurrentMethod = (ConcurrentMethodMetaData) obj;
        if (this.method == null) {
            return false;
        }
        return this.method.equals(anotherConcurrentMethod.method);
    }

    @Override
    public int hashCode() {
        int result = method != null ? method.hashCode() : 0;
        result = 31 * result + (lockType != null ? lockType.hashCode() : 0);
        result = 31 * result + (accessTimeout != null ? accessTimeout.hashCode() : 0);
        return result;
    }

    public boolean matches(String methodName, String[] params) {
        return getMethod().matches(methodName, params);
    }
}
