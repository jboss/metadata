/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * IORSASContextMetaData.
 * <p/>
 * TODO LAST enums
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class IORSASContextMetaData extends IdMetaDataImplWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -5219684887396127182L;

    /**
     * No caller propagation
     */
    public static final String CALLER_PROPAGATION_NONE = "NONE";

    /**
     * Caller propagation supported
     */
    public static final String CALLER_PROPAGATION_SUPPORTED = "SUPPORTED";

    /**
     * The caller propagation
     */
    private String callerPropagation = CALLER_PROPAGATION_NONE;

    /**
     * Get the callerPropagation.
     *
     * @return the callerPropagation.
     */
    public String getCallerPropagation() {
        return callerPropagation;
    }

    /**
     * Set the callerPropagation.
     *
     * @param callerPropagation the callerPropagation.
     * @throws IllegalArgumentException for a null callerPropagation
     */
    public void setCallerPropagation(String callerPropagation) {
        if (callerPropagation == null)
            throw new IllegalArgumentException("Null callerPropagation");
        if (CALLER_PROPAGATION_NONE.equalsIgnoreCase(callerPropagation))
            this.callerPropagation = CALLER_PROPAGATION_NONE;
        else if (CALLER_PROPAGATION_SUPPORTED.equalsIgnoreCase(callerPropagation))
            this.callerPropagation = CALLER_PROPAGATION_SUPPORTED;
        else
            throw new IllegalArgumentException("Unknown sascontext callerPropagtion: " + callerPropagation);
    }
}
