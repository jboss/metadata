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
