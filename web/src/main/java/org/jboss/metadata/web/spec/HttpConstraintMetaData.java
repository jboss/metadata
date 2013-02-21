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
