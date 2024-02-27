/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * RemoveMethodMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class RemoveMethodMetaData extends IdMetaDataImpl {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1830841714074832930L;

    /**
     * The ratainIfExeption default value
     */
    private static final boolean retainIfExceptionDefault = false;

    /**
     * The bean method
     */
    private NamedMethodMetaData beanMethod;

    /**
     * Retain if exception
     */
    private Boolean retainIfException = null;

    /**
     * Create a new RemoveMethodMetaData.
     */
    public RemoveMethodMetaData() {
        // For serialization
    }

    /**
     * Get the beanMethod.
     *
     * @return the beanMethod.
     */
    public NamedMethodMetaData getBeanMethod() {
        return beanMethod;
    }

    /**
     * Set the beanMethod.
     *
     * @param beanMethod the beanMethod.
     * @throws IllegalArgumentException for a null beanMethod
     */
    public void setBeanMethod(NamedMethodMetaData beanMethod) {
        if (beanMethod == null)
            throw new IllegalArgumentException("Null beanMethod");
        this.beanMethod = beanMethod;
    }

    /**
     * Get the retainIfException.
     *
     * @return the retainIfException.
     */
    public Boolean getRetainIfException() {
        return retainIfException;
    }

    /**
     * Set the retainIfException.
     *
     * @param retainIfException the retainIfException.
     */
    public void setRetainIfException(Boolean retainIfException) {
        this.retainIfException = retainIfException;
    }

    public boolean equals(Object o, boolean checkRetainIfException) {
        if (o == this)
            return true;

        if (!(o instanceof RemoveMethodMetaData))
            return false;

        RemoveMethodMetaData other = (RemoveMethodMetaData) o;
        if (this.beanMethod == null
                || other.beanMethod == null
                || !this.beanMethod.equals(other.beanMethod))
            return false;

        if (checkRetainIfException
                && this.retainIfException != other.retainIfException)
            return false;

        return true;
    }


    /**
     * merge the retainIfException
     *
     * @param override
     * @param original
     */
    public void mergeRetainifException(RemoveMethodMetaData override, RemoveMethodMetaData original) {
        // JBMETA-98 - merge retainIfException
        if (original != null && override != null) {
            if (override.retainIfException == null) {
                this.retainIfException = original.retainIfException;
            } else {
                this.retainIfException = override.retainIfException;
            }
            return;
        }

        if (original != null) {
            this.retainIfException = original.retainIfException;
        }
        if (override != null) {
            this.retainIfException = override.retainIfException;
        }
    }
}
