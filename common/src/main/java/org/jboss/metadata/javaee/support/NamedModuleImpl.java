/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.javaee.support;

import org.jboss.metadata.javaee.jboss.NamedModule;

/**
 * Base class for metadata classes that include both a module name and a
 * description group.
 *
 * @author Brian Stansberry
 * @version $Revision$
 */
public abstract class NamedModuleImpl extends IdMetaDataImplWithDescriptionGroup implements NamedModule {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 6848308773842846138L;

    private String moduleName;

    @Override
    public String getModuleName() {
        return moduleName;
    }

    @Override
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
