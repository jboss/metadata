/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ear.spec;

import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;

/**
 * Common javaee application metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 75470 $
 */
public class EarMetaData extends IdMetaDataImplWithDescriptionGroup {
    private static final long serialVersionUID = 2;

    private final EarVersion earVersion;

    private String libraryDirectory;

    private String applicationName;
    private boolean initializeInOrder;
    private EarEnvironmentRefsGroupMetaData earEnv;

    /**
     * The application modules
     */
    private ModulesMetaData modules;
    /**
     * The security roles
     */
    private SecurityRolesMetaData securityRoles;

    public EarMetaData(EarVersion earVersion) {
        this.earVersion = earVersion;
    }

    public String getLibraryDirectory() {
        return libraryDirectory;
    }

    public void setLibraryDirectory(String libraryDirectory) {
        this.libraryDirectory = libraryDirectory;
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

    public EarVersion getEarVersion() {
        return earVersion;
    }


    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    /**
     * If initialize-in-order is true, modules must be initialized
     * in the order they're listed in this deployment descriptor,
     * with the exception of application client modules, which can
     * be initialized in any order.
     * If initialize-in-order is not set or set to false, the order
     * of initialization is unspecified and may be product-dependent.
     */
    public boolean getInitializeInOrder() {
        return initializeInOrder;
    }

    public void setInitializeInOrder(boolean initializeInOrder) {
        this.initializeInOrder = initializeInOrder;
    }

    /**
     * Get the jndiEnvironmentRefsGroup.
     *
     * @return the jndiEnvironmentRefsGroup.
     */
    public EarEnvironmentRefsGroupMetaData getEarEnvironmentRefsGroup() {
        return earEnv;
    }

    // just for XML binding, to expose the type of the model group
    public void setEarEnvironmentRefsGroup(EarEnvironmentRefsGroupMetaData env) {
        this.earEnv = env;
    }
}
