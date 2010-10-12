/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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

import java.io.Serializable;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * EnvironmentRefsGroupMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */

public class EnvironmentRefsGroupMetaData extends RemoteEnvironmentRefsGroupMetaData implements Serializable, Environment,
        MutableEnvironment {
    /** The serialVersionUID */
    private static final long serialVersionUID = 1337095770028220349L;

    /** The ejb local references */
    private EJBLocalReferencesMetaData ejbLocalReferences;

    /** The persistence context reference */
    private PersistenceContextReferencesMetaData persistenceContextRefs;

    /** The data sources */
    private DataSourcesMetaData dataSources;

    /**
     * Create a new EnvironmentRefsGroupMetaData.
     */
    public EnvironmentRefsGroupMetaData() {
        // For serialization
    }

    /**
     * Get the ejbLocalReferences.
     *
     * @return the ejbLocalReferences.
     */
    @Override
    public EJBLocalReferencesMetaData getEjbLocalReferences() {
        return ejbLocalReferences;
    }

    /**
     * Set the ejbLocalReferences.
     *
     * @param ejbLocalReferences the ejbLocalReferences.
     * @throws IllegalArgumentException for a null ejbLocalReferences
     */
    @Override
    public void setEjbLocalReferences(EJBLocalReferencesMetaData ejbLocalReferences) {
        if (ejbLocalReferences == null)
            throw new IllegalArgumentException("Null ejbLocalReferences");
        this.ejbLocalReferences = ejbLocalReferences;
    }

    /**
     * Get the persistenceContextRefs.
     *
     * @return the persistenceContextRefs.
     */
    @Override
    public PersistenceContextReferencesMetaData getPersistenceContextRefs() {
        return persistenceContextRefs;
    }

    private static PersistenceContextReferencesMetaData getPersistenceContextRefs(Environment env) {
        if (env == null)
            return null;
        return env.getPersistenceContextRefs();
    }

    /**
     * Set the persistenceContextRefs.
     *
     * @param persistenceContextRefs the persistenceContextRefs.
     * @throws IllegalArgumentException for a null persistenceContextRefs
     */
    @Override
    public void setPersistenceContextRefs(PersistenceContextReferencesMetaData persistenceContextRefs) {
        if (persistenceContextRefs == null)
            throw new IllegalArgumentException("Null persistenceContextRefs");
        this.persistenceContextRefs = persistenceContextRefs;
    }

    @Override
    public EJBLocalReferenceMetaData getEjbLocalReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, ejbLocalReferences);
    }

    @Override
    public PersistenceContextReferenceMetaData getPersistenceContextReferenceByName(String name) {
        return AbstractMappedMetaData.getByName(name, persistenceContextRefs);
    }

    @Override
    public DataSourcesMetaData getDataSources() {
        return dataSources;
    }

    @Override
    public void setDataSources(DataSourcesMetaData dataSources) {
        this.dataSources = dataSources;
    }

    @Override
    public DataSourceMetaData getDataSourceByName(String name) {
        return AbstractMappedMetaData.getByName(name, dataSources);
    }

    private static DataSourcesMetaData getDataSources(Environment env) {
        if (env == null)
            return null;
        return env.getDataSources();
    }

    public void merge(Environment jbossEnv, Environment specEnv, String overridenFile, String overrideFile, boolean mustOverride) {
        super.merge(jbossEnv, specEnv, overrideFile, overridenFile, mustOverride);

        EJBLocalReferencesMetaData ejbLocalRefs = null;
        EJBLocalReferencesMetaData jbossEjbLocalRefs = null;

        if (specEnv != null) {
            ejbLocalRefs = specEnv.getEjbLocalReferences();
        }

        if (jbossEnv != null) {
            jbossEjbLocalRefs = jbossEnv.getEjbLocalReferences();
        } else {
            // Use the merge target for the static merge methods
            jbossEjbLocalRefs = this.getEjbLocalReferences();
        }

        EJBLocalReferencesMetaData mergedEjbLocalRefs = EJBLocalReferencesMetaData.merge(jbossEjbLocalRefs, ejbLocalRefs,
                overridenFile, overrideFile);
        if (mergedEjbLocalRefs != null)
            this.setEjbLocalReferences(mergedEjbLocalRefs);

        PersistenceContextReferencesMetaData persistenceContextRefs = PersistenceContextReferencesMetaData.merge(
                getPersistenceContextRefs(jbossEnv), getPersistenceContextRefs(specEnv), overridenFile, overrideFile);
        if (persistenceContextRefs != null)
            setPersistenceContextRefs(persistenceContextRefs);

        DataSourcesMetaData dataSources = DataSourcesMetaData.merge(getDataSources(jbossEnv), getDataSources(specEnv),
                overridenFile, overrideFile);
        if (dataSources != null)
            setDataSources(dataSources);

    }

    @Override
    public void augment(RemoteEnvironmentRefsGroupMetaData augment, RemoteEnvironmentRefsGroupMetaData main,
            boolean resolveConflicts) {
        super.augment(augment, main, resolveConflicts);
        EnvironmentRefsGroupMetaData augmentE = (EnvironmentRefsGroupMetaData) augment;
        EnvironmentRefsGroupMetaData mainE = (EnvironmentRefsGroupMetaData) main;

        // Data sources
        if (getDataSources() == null) {
            setDataSources(augmentE.getDataSources());
        } else if (augmentE.getDataSources() != null) {
            getDataSources().augment(augmentE.getDataSources(), (mainE != null) ? mainE.getDataSources() : null,
                    resolveConflicts);
        }

        // EJB local references
        if (getEjbLocalReferences() == null) {
            if (augmentE.getEjbLocalReferences() != null)
                setEjbLocalReferences(augmentE.getEjbLocalReferences());
        } else if (augmentE.getEjbLocalReferences() != null) {
            getEjbLocalReferences().augment(augmentE.getEjbLocalReferences(),
                    (mainE != null) ? mainE.getEjbLocalReferences() : null, resolveConflicts);
        }

        // Persistence context refs
        if (getPersistenceContextRefs() == null) {
            if (augmentE.getPersistenceContextRefs() != null)
                setPersistenceContextRefs(augmentE.getPersistenceContextRefs());
        } else if (augmentE.getPersistenceContextRefs() != null) {
            getPersistenceContextRefs().augment(augmentE.getPersistenceContextRefs(),
                    (mainE != null) ? mainE.getPersistenceContextRefs() : null, resolveConflicts);
        }

    }
}
