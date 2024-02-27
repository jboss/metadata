/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.jboss.ejb3;

import org.jboss.metadata.ejb.spec.MethodsMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class AbstractMethodsBoundMetaData extends IdMetaDataImplWithDescriptions {
    /**
     * The methods
     */
    private MethodsMetaData methods;

    /**
     * Get the methods.
     *
     * @return the methods.
     */
    public MethodsMetaData getMethods() {
        return methods;
    }

    /**
     * Set the methods.
     *
     * @param methods the methods.
     * @throws IllegalArgumentException for a null methods
     */
    public void setMethods(MethodsMetaData methods) {
        if (methods == null)
            throw new IllegalArgumentException("Null methods");
        this.methods = methods;
    }
}
