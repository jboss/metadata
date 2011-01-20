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
package org.jboss.metadata.ear.jboss;


import org.jboss.metadata.common.jboss.LoaderRepositoryMetaData;
import org.jboss.metadata.ear.spec.Ear5xMetaData;
import org.jboss.metadata.ear.spec.EarMetaData;
import org.jboss.metadata.ear.spec.ModuleMetaData;
import org.jboss.metadata.ear.spec.ModulesMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;

/**
 * The jboss application metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 78586 $
 */
public class JBossAppMetaData extends IdMetaDataImplWithDescriptionGroup {
    private static final long serialVersionUID = 2;
    private String dtdPublicId;
    private String dtdSystemId;
    /**
     * jboss application version
     */
    private String version;
    /**
     * The default application security domain
     */
    private String securityDomain;
    /**
     * The loader repository
     */
    private LoaderRepositoryMetaData loaderRepository;
    /**
     * The unauthenticated principal
     */
    private String unauthenticatedPrincipal;
    private ModulesMetaData modules;
    /**
     * The security roles
     */
    private SecurityRolesMetaData securityRoles;
    private String libraryDirectory;
    private String jmxName;
    private ModuleOrder moduleOrder = ModuleOrder.IMPLICIT;

    public enum ModuleOrder {
        STRICT, IMPLICIT;
    }

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
        if (dtdPublicId != null && dtdPublicId.contains("3.0"))
            setVersion("3.0");
        else if (dtdPublicId != null && dtdPublicId.contains("3.2"))
            setVersion("3.2");
        else if (dtdPublicId != null && dtdPublicId.contains("4.0"))
            setVersion("4.0");
        else if (dtdPublicId != null && dtdPublicId.contains("4.2"))
            setVersion("4.2");
        else if (dtdPublicId != null && dtdPublicId.contains("5.0"))
            setVersion("5.0");

        if (getVersion() == null) {
            if (dtdSystemId != null && dtdSystemId.contains("jboss-app_3_0.dtd"))
                setVersion("3.0");
            else if (dtdSystemId != null && dtdSystemId.contains("jboss-app_3_2.dtd"))
                setVersion("3.2");
            else if (dtdSystemId != null && dtdSystemId.contains("jboss-app_4_0.dtd"))
                setVersion("4.0");
            else if (dtdSystemId != null && dtdSystemId.contains("jboss-app_4_2.dtd"))
                setVersion("4.2");
            else if (dtdSystemId != null && dtdSystemId.contains("jboss-app_5_0.dtd"))
                setVersion("5.0");
        }
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

    public LoaderRepositoryMetaData getLoaderRepository() {
        return loaderRepository;
    }

    public void setLoaderRepository(LoaderRepositoryMetaData loaderRepository) {
        this.loaderRepository = loaderRepository;
    }

    public String getSecurityDomain() {
        return securityDomain;
    }

    public void setSecurityDomain(String securityDomain) {
        this.securityDomain = securityDomain;
    }

    public SecurityRolesMetaData getSecurityRoles() {
        return securityRoles;
    }

    public void setSecurityRoles(SecurityRolesMetaData securityRoles) {
        this.securityRoles = securityRoles;
    }

    public String getUnauthenticatedPrincipal() {
        return unauthenticatedPrincipal;
    }

    public void setUnauthenticatedPrincipal(String unauthenticatedPrincipal) {
        this.unauthenticatedPrincipal = unauthenticatedPrincipal;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLibraryDirectory() {
        return libraryDirectory;
    }

    public void setLibraryDirectory(String libraryDirectory) {
        this.libraryDirectory = libraryDirectory;
    }

    public String getJmxName() {
        return jmxName;
    }

    public void setJmxName(String jmxName) {
        this.jmxName = jmxName;
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
     * The order to deploy modules of an EARi in.
     * If "strict" deploy in application.xml order.
     * If "implicit" deploy according to the deployment sorter.
     *
     * @param moduleOrder how to order modules
     */
    public void setModuleOrder(String moduleOrder) {
        if ("strict".equalsIgnoreCase(moduleOrder))
            this.moduleOrder = ModuleOrder.STRICT;
    }

    public String getModuleOrder() {
        return moduleOrder.toString();
    }

    public ModuleOrder getModuleOrderEnum() {
        return this.moduleOrder;
    }

    public synchronized ModuleMetaData getModule(String name) {
        return modules.get(name);
    }
}
