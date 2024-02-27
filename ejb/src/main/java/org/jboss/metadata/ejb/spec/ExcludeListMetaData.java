/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplWithDescriptionsMerger;

/**
 * ExcludeListMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ExcludeListMetaData extends IdMetaDataImplWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -7505132782235878508L;

    /**
     * The methods
     */
    private MethodsMetaData methods;

    /**
     * Create a new ExcludeListMetaData.
     */
    public ExcludeListMetaData() {
        // For serialization
    }

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

    /**
     * Whether this matches
     *
     * @param methodName    the method name
     * @param params        the parameters
     * @param interfaceType the interface type
     * @return true when it matches
     */
    public boolean matches(String methodName, Class[] params, MethodInterfaceType interfaceType) {
        if (methods == null)
            return false;
        return methods.matches(methodName, params, interfaceType);
    }

    /**
     * Get the exclude list for an ejb
     *
     * @param ejbName the ejb name
     * @return the exclude list or null for no result
     * @throws IllegalArgumentException for a null ejb name
     */
    public ExcludeListMetaData getExcludeListByEjbName(String ejbName) {
        if (ejbName == null)
            throw new IllegalArgumentException("Null ejbName");

        if (methods == null)
            return null;

        MethodsMetaData ejbMethods = methods.getMethodsByEjbName(ejbName);
        if (ejbMethods == null)
            return null;

        ExcludeListMetaData result = clone();
        result.setMethods(ejbMethods);
        return result;
    }

    @Override
    public ExcludeListMetaData clone() {
        return (ExcludeListMetaData) super.clone();
    }

    public void merge(ExcludeListMetaData override, ExcludeListMetaData original) {
        IdMetaDataImplWithDescriptionsMerger.merge(this, override, original);

        // TODO: can't merge myself

        methods = new MethodsMetaData();
        methods.merge(override != null ? override.methods : null, original != null ? original.methods : null);
    }
}
