/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import java.util.List;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * Models per servlet or per servlet type HTTP constraint
 *
 * @author Remy Maucherat
 * @version $Revision: 81768 $
 */
public abstract class HttpConstraintMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;

    private EmptyRoleSemanticType emptyRoleSemantic;
    private TransportGuaranteeType transportGuarantee;
    private List<String> rolesAllowed;

    public EmptyRoleSemanticType getEmptyRoleSemantic() {
        return emptyRoleSemantic;
    }

    public void setEmptyRoleSemantic(EmptyRoleSemanticType emptyRoleSemantic) {
        this.emptyRoleSemantic = emptyRoleSemantic;
    }

    public TransportGuaranteeType getTransportGuarantee() {
        return transportGuarantee;
    }

    public void setTransportGuarantee(TransportGuaranteeType transportGuarantee) {
        this.transportGuarantee = transportGuarantee;
    }

    public List<String> getRolesAllowed() {
        return rolesAllowed;
    }

    public void setRolesAllowed(List<String> rolesAllowed) {
        this.rolesAllowed = rolesAllowed;
    }

}
