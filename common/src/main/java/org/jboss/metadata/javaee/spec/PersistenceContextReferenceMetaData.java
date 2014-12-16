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
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.ResourceInjectionMetaDataWithDescriptions;

/**
 * PersistenceContextReferenceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class PersistenceContextReferenceMetaData extends ResourceInjectionMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 994249685587171979L;

    private static PersistenceContextSynchronizationType DEFAULT_PERSISTENCE_CONTEXT_SYNCHRONIZATION_TYPE = PersistenceContextSynchronizationType.Synchronized;
    /**
     * The persistence unit name
     */
    private String persistenceUnitName;

    /**
     * The persistence context type
     */
    private PersistenceContextTypeDescription persistenceContextType;

    /**
     * The persistence context type, default is synchronized
     */
    private PersistenceContextSynchronizationType persistenceContextSynchronization = DEFAULT_PERSISTENCE_CONTEXT_SYNCHRONIZATION_TYPE;

    /**
     * The properties
     */
    private PropertiesMetaData properties;

    /**
     * Create a new PersistenceUnitReferenceMetaData.
     */
    public PersistenceContextReferenceMetaData() {
        // For serialization
    }

    /**
     * Get the persistenceContextRefName.
     *
     * @return the persistenceContextRefName.
     */
    public String getPersistenceContextRefName() {
        return getName();
    }

    /**
     * Set the persistenceContextRefName.
     *
     * @param persistenceContextRefName the persistenceContextRefName.
     * @throws IllegalArgumentException for a null persistenceUnitRefName
     */
    public void setPersistenceContextRefName(String persistenceContextRefName) {
        setName(persistenceContextRefName);
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

    /**
     * Get the persistenceContextType.
     *
     * @return the persistenceContextType.
     */
    public PersistenceContextTypeDescription getPersistenceContextType() {
        return persistenceContextType;
    }

    /**
     * Set the persistenceContextType.
     *
     * @param persistenceContextType the persistenceContextType.
     * @throws IllegalArgumentException for a null persistenceContextType
     */
    public void setPersistenceContextType(PersistenceContextTypeDescription persistenceContextType) {
        if (persistenceContextType == null)
            throw new IllegalArgumentException("Null persistenceContextType");
        this.persistenceContextType = persistenceContextType;
    }

    /**
     *
     * @return
     */
    public PersistenceContextSynchronizationType getPersistenceContextSynchronization() {
        return persistenceContextSynchronization;
    }

    /**
     *
     * @param persistenceContextSynchronization
     */
    public void setPersistenceContextSynchronization(
            PersistenceContextSynchronizationType persistenceContextSynchronization) {
        this.persistenceContextSynchronization = persistenceContextSynchronization;
    }

    /**
     * Get the properties.
     *
     * @return the properties.
     */
    public PropertiesMetaData getProperties() {
        return properties;
    }

    /**
     * Set the properties.
     *
     * @param properties the properties.
     * @throws IllegalArgumentException for a null properties
     */
    public void setProperties(PropertiesMetaData properties) {
        if (properties == null)
            throw new IllegalArgumentException("Null properties");
        this.properties = properties;
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("PersistenceContextReferenceMetaData{");
        tmp.append("name=");
        tmp.append(getPersistenceContextRefName());
        tmp.append(",type=");
        tmp.append(getPersistenceContextType());
        tmp.append(",unit-name=");
        tmp.append(getPersistenceUnitName());
        tmp.append(",ignore-dependecy=");
        tmp.append(super.isDependencyIgnored());
        tmp.append(",jndi-name=");
        tmp.append(super.getJndiName());
        tmp.append(",resolvoed-jndi-name=");
        tmp.append(super.getResolvedJndiName());
        tmp.append(",properties=");
        tmp.append(getProperties());
        tmp.append('}');
        return tmp.toString();
    }
}
