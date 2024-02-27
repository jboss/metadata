/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * InitMethodMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class InitMethodMetaData extends IdMetaDataImpl {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1830841714074832930L;

    /**
     * The create method
     */
    private NamedMethodMetaData createMethod;

    /**
     * The bean method
     */
    private NamedMethodMetaData beanMethod;

    /**
     * Create a new InitMethodMetaData.
     */
    public InitMethodMetaData() {
        // For serialization
    }

    /**
     * Get the createMethod.
     *
     * @return the createMethod.
     */
    public NamedMethodMetaData getCreateMethod() {
        return createMethod;
    }

    /**
     * Set the createMethod.
     *
     * @param createMethod the createMethod.
     * @throws IllegalArgumentException for a null createMethod
     */
    public void setCreateMethod(NamedMethodMetaData createMethod) {
        if (createMethod == null)
            throw new IllegalArgumentException("Null createMethod");
        this.createMethod = createMethod;
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
}
