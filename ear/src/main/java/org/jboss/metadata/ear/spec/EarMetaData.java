/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.jboss.metadata.ear.spec;

import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;

/**
 * Common javaee application metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 75470 $
 */
public class EarMetaData extends IdMetaDataImplWithDescriptionGroup {
    private static final long serialVersionUID = 1;
    private String dtdPublicId;
    private String dtdSystemId;
    private String version;
    /**
     * The application modules
     */
    private ModulesMetaData modules;
    /**
     * The security roles
     */
    private SecurityRolesMetaData securityRoles;

    /**
     * Callback for the DTD information
     *
     * @param root
     * @param publicId
     * @param systemId
     */
    public void setDTD(String root, String publicId, String systemId) {
        this.dtdPublicId = publicId;
        this.dtdSystemId = systemId;
        // Set the version based on
        if (dtdPublicId != null && dtdPublicId.contains("1.3"))
            setVersion("1.3");
        else if (dtdSystemId != null && dtdSystemId.contains("1.3"))
            setVersion("1.3");
        else if (dtdSystemId != null && dtdSystemId.contains("1.2"))
            setVersion("1.2");
    }

    /**
     * Get the DTD public id if one was seen
     *
     * @return the value of the web.xml dtd public id
     */
    public String getDtdPublicId() {
        return dtdPublicId;
    }

    /**
     * Get the DTD system id if one was seen
     *
     * @return the value of the web.xml dtd system id
     */
    public String getDtdSystemId() {
        return dtdSystemId;
    }

    /**
     * Is this a javaee 6 version application
     *
     * @return true if this is a javaee 6 version application
     */
    public boolean isEE6() {
        return false;
    }

    /**
     * Is this a javaee 5 version application
     *
     * @return true if this is a javaee 5 version application
     */
    public boolean isEE5() {
        return false;
    }

    /**
     * Is this a javaee 1.4 version application
     *
     * @return true if this is a javaee 1.4 version application
     */
    public boolean isEE14() {
        return false;
    }

    /**
     * Is this a javaee 1.3 version application
     *
     * @return true if this is a javaee 1.3 version application
     */
    public boolean isEE13() {
        return false;
    }

    /**
     * Get the version.
     *
     * @return the version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set the version.
     *
     * @param version the version.
     * @throws IllegalArgumentException for a null version
     */
    public void setVersion(String version) {
        if (version == null)
            throw new IllegalArgumentException("Null version");
        this.version = version;
    }

    /**
     * Get the application module information
     *
     * @return the list of application modules
     */
    public ModulesMetaData getModules() {
        return modules;
    }

    /**
     * Set the application module information
     *
     * @param modules - the list of application modules
     */
    public void setModules(ModulesMetaData modules) {
        this.modules = modules;
    }

    /**
     * Get the security roles
     *
     * @return securityRoles
     */
    public SecurityRolesMetaData getSecurityRoles() {
        return securityRoles;
    }

    /**
     * Set the security roles
     *
     * @param securityRoles
     */
    public void setSecurityRoles(SecurityRolesMetaData securityRoles) {
        this.securityRoles = securityRoles;
    }
}
