/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import org.jboss.metadata.javaee.support.NamedMetaData;

/**
 * MethodAttributeMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class MethodAttributeMetaData extends NamedMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 8484757755955187189L;

    /**
     * The default methods attributes
     */
    public static final MethodAttributeMetaData DEFAULT;

    static {
        DEFAULT = new MethodAttributeMetaData();
        DEFAULT.setMethodName("*");
    }

    /**
     * Whether the method is read only
     */
    private boolean readOnly;

    /**
     * Whether the method is idempotent
     */
    private boolean idempotent;

    /**
     * The transaction timeout
     */
    private int transactionTimeout;

    /**
     * Get the methodName.
     *
     * @return the methodName.
     */
    public String getMethodName() {
        return getName();
    }

    /**
     * Set the methodName.
     *
     * @param methodName the methodName.
     * @throws IllegalArgumentException for a null methodName
     */
    public void setMethodName(String methodName) {
        setName(methodName);
    }

    /**
     * Get the readOnly.
     *
     * @return the readOnly.
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * Set the readOnly.
     *
     * @param readOnly the readOnly.
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    /**
     * Get the idempotent.
     *
     * @return the idempotent.
     */
    public boolean isIdempotent() {
        return idempotent;
    }

    /**
     * Set the idempotent.
     *
     * @param idempotent the idempotent.
     */
    public void setIdempotent(boolean idempotent) {
        this.idempotent = idempotent;
    }

    /**
     * Get the transactionTimeout.
     *
     * @return the transactionTimeout.
     */
    public int getTransactionTimeout() {
        return transactionTimeout;
    }

    /**
     * Set the transactionTimeout.
     *
     * @param transactionTimeout the transactionTimeout.
     */
    public void setTransactionTimeout(int transactionTimeout) {
        this.transactionTimeout = transactionTimeout;
    }

    /**
     * Whether this matches the method name
     *
     * @param methodName the method name
     * @return true for a match
     */
    public boolean matches(String methodName) {
        if (methodName == null)
            return false;

        int ct, end;

        String pattern = getMethodName();

        end = pattern.length();

        if (end > methodName.length())
            return false;

        for (ct = 0; ct < end; ct++) {
            char c = pattern.charAt(ct);
            if (c == '*')
                return true;
            if (c != methodName.charAt(ct))
                return false;
        }
        return ct == methodName.length();
    }
}
