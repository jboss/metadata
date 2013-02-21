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
