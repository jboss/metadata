/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * The webservice port-component information.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 80371 $
 */
public class PortComponent extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;

    private String portComponentName;
    private String portComponentURI;
    private String authMethod;
    private String transportGuarantee;
    private boolean secureWSDLAccess;

    public PortComponent() {
    }

    public String getPortComponentName() {
        return portComponentName;
    }

    public void setPortComponentName(String portComponentName) {
        this.portComponentName = portComponentName;
    }

    public String getPortComponentURI() {
        return portComponentURI;
    }

    public void setPortComponentURI(String portComponentURI) {
        this.portComponentURI = portComponentURI;
    }

    public String getURLPattern() {
        String pattern = "/*";
        if (portComponentURI != null)
            pattern = portComponentURI;

        return pattern;
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public String getTransportGuarantee() {
        return transportGuarantee;
    }

    public void setTransportGuarantee(String transportGuarantee) {
        this.transportGuarantee = transportGuarantee;
    }

    public boolean getSecureWSDLAccess() {
        return secureWSDLAccess;
    }

    public void setSecureWSDLAccess(boolean secureWSDLAccess) {
        this.secureWSDLAccess = secureWSDLAccess;
    }
}
