/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.util.Set;

import org.jboss.metadata.javaee.spec.EmptyMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * MethodPermissionMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class MethodPermissionMetaData extends IdMetaDataImplWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -985973586611090108L;

    /**
     * Whether it is unchecked
     */
    private EmptyMetaData unchecked;

    /**
     * The roles
     */
    private Set<String> roles;

    /**
     * The methods
     */
    private MethodsMetaData methods;

    /**
     * Create a new MethodPermissionMetaData.
     */
    public MethodPermissionMetaData() {
        // For serialization
    }

    /**
     * Get the unchecked.
     *
     * @return the unchecked.
     */
    public boolean isNotChecked() {
        return unchecked != null;
    }

    /**
     * Get the unchecked.
     *
     * @return the unchecked.
     */
    public EmptyMetaData getUnchecked() {
        return unchecked;
    }

    /**
     * Set the unchecked.
     *
     * @param unchecked the unchecked.
     */
    public void setUnchecked(EmptyMetaData unchecked) {
        this.unchecked = unchecked;
    }

    /**
     * Get the roles.
     *
     * @return the roles.
     */
    public Set<String> getRoles() {
        return roles;
    }

    /**
     * Set the roles.
     *
     * @param roles the roles.
     */
    public void setRoles(Set<String> roles) {
        this.roles = roles;
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
     * Get the method permissions for an ejb
     *
     * @param ejbName the ejb name
     * @return the method permission or null for no result
     * @throws IllegalArgumentException for a null ejb name
     */
    public MethodPermissionMetaData getMethodPermissionByEjbName(String ejbName) {
        if (ejbName == null)
            throw new IllegalArgumentException("Null ejbName");

        if (methods == null)
            return null;

        MethodsMetaData ejbMethods = methods.getMethodsByEjbName(ejbName);
        if (ejbMethods == null)
            return null;

        MethodPermissionMetaData result = clone();
        result.setMethods(ejbMethods);
        return result;
    }

    @Override
    public MethodPermissionMetaData clone() {
        return (MethodPermissionMetaData) super.clone();
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
     * Whether this is not checked
     *
     * @param methodName    the method name
     * @param params        the parameters
     * @param interfaceType the interface type
     * @return true when it is not checked and it matches matches
     */
    public boolean isNotChecked(String methodName, Class[] params, MethodInterfaceType interfaceType) {
        if (isNotChecked() == false)
            return false;
        return matches(methodName, params, interfaceType);
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("MethodPermissionMetaData(id=");
        tmp.append(getId());
        if (isNotChecked()) {
            tmp.append(",unchecked=true");
        } else {
            tmp.append(",roles=");
            tmp.append(this.roles);
        }
        tmp.append(",methods=");
        tmp.append(this.methods);
        tmp.append(')');
        return tmp.toString();
    }
}
