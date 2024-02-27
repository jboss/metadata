/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.javaee.jboss;

/**
 * Information about an EE module's name as discussed in EE 6 Section EE.8.1.1.
 *
 * @author Brian Stansberry
 * @version $Revision$
 */
public interface NamedModule {
    /**
     * Gets the name of the module as configured in its deployment descriptor.
     *
     * @return the name, or <code>null</code> if no module name was configured
     *         in the deployment descriptor
     */
    String getModuleName();

    /**
     * Sets the name of the module as configured in its deployment descriptor.
     * <p/>
     * param moduleName the name as configured in the deployment descriptor
     */
    void setModuleName(String moduleName);
}
