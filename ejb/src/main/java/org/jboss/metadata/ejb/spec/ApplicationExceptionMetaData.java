/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
