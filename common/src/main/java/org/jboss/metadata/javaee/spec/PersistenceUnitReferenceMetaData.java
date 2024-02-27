/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.ResourceInjectionMetaDataWithDescriptions;

/**
 * PersistenceUnitReferenceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class PersistenceUnitReferenceMetaData extends ResourceInjectionMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1900675456507941940L;

    /**
     * The persistence unit name
     */
    private String persistenceUnitName;

    /**
     * Create a new PersistenceUnitReferenceMetaData.
     */
    public PersistenceUnitReferenceMetaData() {
        // For serialization
    }

    /**
     * Get the persistenceUnitRefName.
     *
     * @return the persistenceUnitRefName.
     */
    public String getPersistenceUnitRefName() {
        return getName();
    }

    /**
     * Set the persistenceUnitRefName.
     *
     * @param persistenceUnitRefName the persistenceUnitRefName.
     * @throws IllegalArgumentException for a null peristenceUnitRefName
     */
    public void setPersistenceUnitRefName(String persistenceUnitRefName) {
        setName(persistenceUnitRefName);
    }

    /**
     * Get the persistenceUnitName.
     *
     * @return the persistenceUnitName.
     */
    public String getPersistenceUnitName() {
        return persistenceUnitName;
    }

    /**
     * Set the persistenceUnitName.
     *
     * @param persistenceUnitName the persistenceUnitName.
     * @throws IllegalArgumentException for a null persistenceUnitName
     */
    public void setPersistenceUnitName(String persistenceUnitName) {
        if (persistenceUnitName == null)
            throw new IllegalArgumentException("Null persistenceUnitName");
        this.persistenceUnitName = persistenceUnitName;
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("PersistenceUnitReferenceMetaData{");
        tmp.append("name=");
        tmp.append(super.getName());
        tmp.append(",unit-name=");
        tmp.append(getPersistenceUnitName());
        tmp.append(",ignore-dependecy=");
        tmp.append(super.isDependencyIgnored());
        tmp.append('}');
        return tmp.toString();
    }

}
