/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.support.NamedMetaData;

/**
 * ApplicationExceptionMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ApplicationExceptionMetaData extends NamedMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -485493144287221056L;

    /**
     * Whether to rollback
     */
    private boolean rollback = false;

    private Boolean inherited;

    /**
     * Create a new ApplicationExceptionMetaData.
     */
    public ApplicationExceptionMetaData() {
        // For serialization
    }

    /**
     * Get the exceptionClass.
     *
     * @return the exceptionClass.
     */
    public String getExceptionClass() {
        return getName();
    }

    /**
     * Set the exceptionClass.
     *
     * @param exceptionClass the exceptionClass.
     * @throws IllegalArgumentException for a null exceptionClass
     */
    public void setExceptionClass(String exceptionClass) {
        setName(exceptionClass);
    }

    /**
     * Get the rollback.
     *
     * @return the rollback.
     */
    public boolean isRollback() {
        return rollback;
    }

    /**
     * Set the rollback.
     *
     * @param rollback the rollback.
     */
    public void setRollback(boolean rollback) {
        this.rollback = rollback;
    }

    /**
     * Returns true if the application-exception is marked as "inherited". Returns false if
     * "inherited" is explicitly marked as false. In case the application-exception doesn't
     * explicitly specify the "inherited" attribute, then this method returns null.
     *
     * @return
     */
    public Boolean isInherited() {
        return this.inherited;
    }

    /**
     * Sets the "inherited" attribute of application-exception
     *
     * @param inherited True if the application-exception is to be marked as "inherited". False otherwise
     */
    public void setInherited(Boolean inherited) {
        this.inherited = inherited;
    }
}
