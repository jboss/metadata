/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.jboss;

import java.util.HashSet;

import org.jboss.metadata.javaee.jboss.IgnoreDependencyMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplWithDescriptionsMerger;

/**
 * IgnoreDependencyMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class IgnoreDependencyMetaDataMerger extends IdMetaDataImplWithDescriptionsMerger {
    public static void merge(IgnoreDependencyMetaData dest, IgnoreDependencyMetaData override, IgnoreDependencyMetaData original) {
        if (original != null && original.getInjectionTargets() != null) {
            if (dest.getInjectionTargets() == null)
                dest.setInjectionTargets(new HashSet<ResourceInjectionTargetMetaData>());
            dest.getInjectionTargets().addAll(original.getInjectionTargets());
        }

        if (override != null && override.getInjectionTargets() != null) {
            if (dest.getInjectionTargets() == null)
                dest.setInjectionTargets(new HashSet<ResourceInjectionTargetMetaData>());
            dest.getInjectionTargets().addAll(override.getInjectionTargets());
        }
    }
}
