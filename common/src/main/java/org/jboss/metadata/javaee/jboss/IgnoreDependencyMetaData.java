/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.jboss;

import java.util.Set;

import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * IgnoreDependencyMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class IgnoreDependencyMetaData extends IdMetaDataImplWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 68493962316154817L;

    /**
     * The injection targets
     */
    private Set<ResourceInjectionTargetMetaData> injectionTargets;

    /**
     * Get the injectionTargets.
     *
     * @return the injectionTargets.
     */
    public Set<ResourceInjectionTargetMetaData> getInjectionTargets() {
        return injectionTargets;
    }

    /**
     * Set the injectionTargets.
     *
     * @param injectionTargets the injectionTargets.
     * @throws IllegalArgumentException for a null injectionTargets
     */
    public void setInjectionTargets(Set<ResourceInjectionTargetMetaData> injectionTargets) {
        if (injectionTargets == null)
            throw new IllegalArgumentException("Null injectionTargets");
        this.injectionTargets = injectionTargets;
    }
}
