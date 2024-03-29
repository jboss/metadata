/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import java.util.Collections;
import java.util.List;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * The web app security-constraints
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 81768 $
 */
public class SecurityConstraintMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;

    private String displayName;
    private WebResourceCollectionsMetaData resourceCollections;
    private AuthConstraintMetaData authConstraint;
    private UserDataConstraintMetaData userDataConstraint;

    public AuthConstraintMetaData getAuthConstraint() {
        return authConstraint;
    }

    public void setAuthConstraint(AuthConstraintMetaData authConstraint) {
        this.authConstraint = authConstraint;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public WebResourceCollectionsMetaData getResourceCollections() {
        return resourceCollections;
    }

    public void setResourceCollections(WebResourceCollectionsMetaData resourceCollections) {
        this.resourceCollections = resourceCollections;
    }

    public UserDataConstraintMetaData getUserDataConstraint() {
        return userDataConstraint;
    }

    public void setUserDataConstraint(UserDataConstraintMetaData userDataConstraint) {
        this.userDataConstraint = userDataConstraint;
    }

    /**
     * The unchecked flag is set when there is no
     * security-constraint/auth-constraint
     *
     * @return true if there is no auth-constraint
     */
    public boolean isUnchecked() {
        return authConstraint == null;
    }

    /**
     * The excluded flag is set when there is an empty
     * security-constraint/auth-constraint element
     *
     * @return true if there is an empty auth-constraint
     */
    public boolean isExcluded() {
        boolean isExcluded = authConstraint != null && authConstraint.getRoleNames() == null;
        return isExcluded;
    }

    /**
     * Accessor for the security-constraint/auth-constraint/role-name(s)
     *
     * @return A possibly empty set of constraint role names. Use isUnchecked
     *         and isExcluded to check for no or an emtpy auth-constraint
     */
    public List<String> getRoleNames() {
        List<String> roleNames = Collections.emptyList();
        if (authConstraint != null && authConstraint.getRoleNames() != null)
            roleNames = authConstraint.getRoleNames();
        return roleNames;
    }

    /**
     * Accessor for the UserDataConstraint.TransportGuarantee
     *
     * @return UserDataConstraint.TransportGuarantee
     */
    public TransportGuaranteeType getTransportGuarantee() {
        TransportGuaranteeType type = TransportGuaranteeType.NONE;
        if (userDataConstraint != null)
            type = userDataConstraint.getTransportGuarantee();
        return type;
    }
}
