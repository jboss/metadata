/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * IORASContextMetaData.
 * <p/>
 * TODO LAST enums
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class IORASContextMetaData extends IdMetaDataImplWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -4611413076087109396L;

    /**
     * Username and password
     */
    public static final String AUTH_METHOD_USERNAME_PASSWORD = "USERNAME_PASSWORD";

    /**
     * None
     */
    public static final String AUTH_METHOD_NONE = "NONE";

    /**
     * The authorization method
     */
    private String authMethod = AUTH_METHOD_USERNAME_PASSWORD;

    /**
     * The realm
     */
    private String realm = "Default";

    /**
     * Whether it is required
     */
    private boolean required = false;

    /**
     * Get the authMethod.
     *
     * @return the authMethod.
     */
    public String getAuthMethod() {
        return authMethod;
    }

    /**
     * Set the authMethod.
     *
     * @param authMethod the authMethod.
     * @throws IllegalArgumentException for a null authMethod
     */
    public void setAuthMethod(String authMethod) {
        if (authMethod == null)
            throw new IllegalArgumentException("Null authMethod");
        if (AUTH_METHOD_NONE.equalsIgnoreCase(authMethod))
            this.authMethod = AUTH_METHOD_NONE;
        else if (AUTH_METHOD_USERNAME_PASSWORD.equalsIgnoreCase(authMethod))
            this.authMethod = AUTH_METHOD_USERNAME_PASSWORD;
        else
            throw new IllegalArgumentException("Unknown ascontext authMethod: " + authMethod);
    }

    /**
     * Get the realm.
     *
     * @return the realm.
     */
    public String getRealm() {
        return realm;
    }

    /**
     * Set the realm.
     *
     * @param realm the realm.
     * @throws IllegalArgumentException for a null realm
     */
    public void setRealm(String realm) {
        if (realm == null)
            throw new IllegalArgumentException("Null realm");
        this.realm = realm;
    }

    /**
     * Get the required.
     *
     * @return the required.
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Set the required.
     *
     * @param required the required.
     */
    public void setRequired(boolean required) {
        this.required = required;
    }
}
