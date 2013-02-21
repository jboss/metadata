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
