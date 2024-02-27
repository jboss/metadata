/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.merge.javaee.support;

import org.jboss.metadata.javaee.jboss.NamedModule;
import org.jboss.metadata.javaee.support.NamedModuleImpl;

/**
 * Base class for metadata classes that include both a module name and a
 * description group.
 *
 * @author Brian Stansberry
 * @version $Revision$
 */
public abstract class NamedModuleImplMerger extends IdMetaDataImplWithDescriptionGroupMerger {

    public static void merge(NamedModuleImpl dest, NamedModuleImpl override, NamedModuleImpl original) {
        IdMetaDataImplWithDescriptionGroupMerger.merge(dest, override, original);
        mergeModuleName(dest, override, original);
    }

    public static void mergeModuleName(NamedModule dest, NamedModule override, NamedModule original) {
        if (override != null && override.getModuleName() != null)
            dest.setModuleName(override.getModuleName());
        else if (original != null && original.getModuleName() != null)
            dest.setModuleName(original.getModuleName());
    }

}
