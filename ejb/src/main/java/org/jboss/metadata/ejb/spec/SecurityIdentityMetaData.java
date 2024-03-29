/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.spec.EmptyMetaData;
import org.jboss.metadata.javaee.spec.RunAsMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;
import org.jboss.metadata.merge.javaee.support.IdMetaDataImplWithDescriptionsMerger;

/**
 * SecurityIdentityMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
public class SecurityIdentityMetaData extends IdMetaDataImplWithDescriptions implements MergeableMetaData<SecurityIdentityMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -6336033602938028216L;

    /**
     * The use caller identity
     */
    private EmptyMetaData useCallerIdentity;

    /**
     * The run as
     */
    private RunAsMetaData runAs;

    /**
     * The run as principal
     */
    private String runAsPrincipal;

    /**
     * The description.
     */
    private String description;

    /**
     * Create a new SecurityIdentityMetaData
     */
    public SecurityIdentityMetaData() {
        // For serialization
    }

    /**
     * Whether to use caller identity
     *
     * @return true for caller id
     */
    public boolean isUseCallerId() {
        return useCallerIdentity != null;
    }

    /**
     * Get the useCallerIdentity.
     *
     * @return the useCallerIdentity.
     */
    public EmptyMetaData getUseCallerIdentity() {
        return useCallerIdentity;
    }

    /**
     * Set the useCallerIdentity.
     *
     * @param useCallerIdentity the useCallerIdentity.
     */
    public void setUseCallerIdentity(EmptyMetaData useCallerIdentity) {
        this.useCallerIdentity = useCallerIdentity;
    }

    /**
     * Get the runAs.
     *
     * @return the runAs.
     */
    public RunAsMetaData getRunAs() {
        return runAs;
    }

    /**
     * Gets description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the runAs.
     *
     * @param runAs the runAs.
     * @throws IllegalArgumentException for a null runAs
     */
    public void setRunAs(RunAsMetaData runAs) {
        if (runAs == null)
            throw new IllegalArgumentException("Null runAs");
        this.runAs = runAs;
    }

    /**
     * Get the runAsPrincipal.
     *
     * @return the runAsPrincipal.
     */
    public String getRunAsPrincipal() {
        return runAsPrincipal;
    }

    /**
     * Set the runAsPrincipal.
     *
     * @param runAsPrincipal the runAsPrincipal.
     * @throws IllegalArgumentException for a null runAsPrincipal
     */
    public void setRunAsPrincipal(String runAsPrincipal) {
        if (runAsPrincipal == null)
            throw new IllegalArgumentException("Null runAsPrincipal");
        this.runAsPrincipal = runAsPrincipal;
    }

    public SecurityIdentityMetaData merge(SecurityIdentityMetaData original) {
        SecurityIdentityMetaData merged = new SecurityIdentityMetaData();
        merge(merged, original);
        return merged;
    }

    /**
     * Merge override + original into this
     *
     * @param override
     * @param original
     */
    @Override
    public void merge(SecurityIdentityMetaData override, SecurityIdentityMetaData original) {
        IdMetaDataImplWithDescriptionsMerger.merge(this, override, original);
        if (override != null && override.getUseCallerIdentity() != null)
            setUseCallerIdentity(override.getUseCallerIdentity());
        else if (original != null && original.getUseCallerIdentity() != null)
            setUseCallerIdentity(original.getUseCallerIdentity());
        if (override != null && override.getRunAs() != null)
            setRunAs(override.getRunAs());
        else if (original != null && original.getRunAs() != null)
            setRunAs(original.getRunAs());
        if (override != null && override.getRunAsPrincipal() != null)
            setRunAsPrincipal(override.getRunAsPrincipal());
        else if (original != null && original.getRunAsPrincipal() != null)
            setRunAsPrincipal(original.getRunAsPrincipal());
    }
}
