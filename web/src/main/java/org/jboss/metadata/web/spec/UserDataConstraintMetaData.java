/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 87568 $
 */
public class UserDataConstraintMetaData extends IdMetaDataImplWithDescriptions {
    private static final long serialVersionUID = 1;

    private TransportGuaranteeType transportGuarantee;

    public TransportGuaranteeType getTransportGuarantee() {
        return transportGuarantee;
    }

    public void setTransportGuarantee(TransportGuaranteeType transportGuarantee) {
        this.transportGuarantee = transportGuarantee;
    }
}
