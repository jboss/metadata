/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import java.util.Set;

import org.jboss.metadata.javaee.support.NamedMetaData;

/**
 * ResourceInjectionMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class ResourceInjectionMetaData extends NamedMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 6333738851813890701L;

    /**
     * The mapped name
     */
    private String mappedName;
    private String resolvedJndiName;

    /**
     * The injection targets
     */
    private Set<ResourceInjectionTargetMetaData> injectionTargets;

    /**
     * The ignore dependency
     */
    private EmptyMetaData ignoreDependency;

    /**
     * Create a new ResourceInjectionMetaData.
     */
    public ResourceInjectionMetaData() {
        // For serialization
    }

    @Override
    public String getName() {
        return super.getName();
    }

    /**
     * Get the jndiName.
     *
     * @return the jndiName.
     */
    public String getJndiName() {
        return getMappedName();
    }

    /**
     * Set the jndiName.
     *
     * @param jndiName the jndiName.
     * @throws IllegalArgumentException for a null jndiName
     */
    public void setJndiName(String jndiName) {
        setMappedName(jndiName);
    }

    /**
     * The JNDI name to be looked up to resolve a resource reference.
     */
    public String getLookupName() {
        return getMappedName();
    }

    public void setLookupName(String lookupName) {
        setMappedName(lookupName);
    }

    /**
     * Get the mappedName.
     *
     * @return the mappedName.
     */
    public String getMappedName() {
        return mappedName;
    }

    /**
     * Set the mappedName.
     *
     * @param mappedName the mappedName.
     * @throws IllegalArgumentException for a null mappedName
     */
    public void setMappedName(String mappedName) {
        if (mappedName == null)
            throw new IllegalArgumentException("Null mappedName");
        this.mappedName = mappedName;
    }

    /**
     * An unmanaged runtime jndi name for the resource. Used by deployers to
     * propagate resolved resource location.
     *
     * @return
     */
    public String getResolvedJndiName() {
        return resolvedJndiName;
    }

    public void setResolvedJndiName(String resolvedJndiName) {
        this.resolvedJndiName = resolvedJndiName;
    }

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

    /**
     * Get the ignoreDependency.
     *
     * @return the ignoreDependency.
     */
    public EmptyMetaData getIgnoreDependency() {
        return ignoreDependency;
    }

    /**
     * Set the ignoreDependency.
     *
     * @param ignoreDependency the ignoreDependency.
     * @throws IllegalArgumentException for a null ignoreDependency
     */
    public void setIgnoreDependency(EmptyMetaData ignoreDependency) {
        if (ignoreDependency == null)
            throw new IllegalArgumentException("Null ignoreDependency");
        this.ignoreDependency = ignoreDependency;
    }

    /**
     * Get whether the dependency is ignored
     *
     * @return true when the dependency is ignored
     */
    public boolean isDependencyIgnored() {
        return ignoreDependency != null;
    }
}
