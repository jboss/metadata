/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ear.spec;

import org.jboss.metadata.ear.jboss.ServiceModuleMetaData;
import org.jboss.metadata.javaee.support.NamedMetaData;

/**
 * Application module metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */
public class ModuleMetaData extends NamedMetaData {
    private static final long serialVersionUID = 1;
    private AbstractModule module;
    private String altDD;
    private String uniqueName;

    public enum ModuleType {Connector, Client, Ejb, Service, Web}

    public AbstractModule getValue() {
        return module;
    }

    public void setValue(AbstractModule value) {
        this.module = value;
        // Set the mappable name to the module file name
        super.setName(value.getFileName());
    }

    public String getAlternativeDD() {
        return altDD;
    }

    public void setAlternativeDD(String altDD) {
        this.altDD = altDD;
    }

    public String getFileName() {
        String fileName = null;
        if (module != null)
            fileName = module.getFileName();
        return fileName;
    }

    public ModuleType getType() {
        ModuleType type = ModuleType.Client;
        if (module instanceof EjbModuleMetaData)
            type = ModuleType.Ejb;
        else if (module instanceof ConnectorModuleMetaData)
            type = ModuleType.Connector;
        else if (module instanceof JavaModuleMetaData)
            type = ModuleType.Client;
        else if (module instanceof WebModuleMetaData)
            type = ModuleType.Web;
        else if (module instanceof ServiceModuleMetaData)
            type = ModuleType.Service;
        return type;
    }

    /**
     * Gets the unique name for this module, as assigned by a deployer in accordance
     * with the EE 6 spec, Section EE.8.1.1. This value is not intended to be
     * set via any deployment descriptor. See also
     * https://jira.jboss.org/jira/browse/JBAS-7644
     *
     * @return the unique name of this module, distinct from the name of any
     *         other module associated with the same application, or
     *         <code>null</code> if the deployment processing logic has not
     *         assigned a unique name.
     */
    public String getUniqueName() {
        return uniqueName;
    }

    /**
     * Sets the unique name for this module, as assigned by a deployer in accordance
     * with the EE 6 spec, Section EE.8.1.1. This value is not intended to be
     * set via any deployment descriptor. See also
     * https://jira.jboss.org/jira/browse/JBAS-7644
     * <p/>
     * param uniqueName the unique name of this module, distinct from the name of any
     * other module associated with the same application
     */
    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }


}
