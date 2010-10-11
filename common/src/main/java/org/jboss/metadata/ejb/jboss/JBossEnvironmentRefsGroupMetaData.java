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
package org.jboss.metadata.ejb.jboss;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import org.jboss.metadata.javaee.jboss.JBossServiceReferencesMetaData;
import org.jboss.metadata.javaee.spec.DataSourceMetaData;
import org.jboss.metadata.javaee.spec.DataSourcesMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferencesMetaData;
import org.jboss.metadata.javaee.spec.Environment;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.spec.RemoteEnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * JBossEnvironmentRefsGroupMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class JBossEnvironmentRefsGroupMetaData extends RemoteEnvironmentRefsGroupMetaData implements Serializable, Environment {
    /** The serialVersionUID */
    private static final long serialVersionUID = 4642263968653845579L;

    /** The ejb local references */
    private EJBLocalReferencesMetaData ejbLocalReferences;

    /** The service references */
    private JBossServiceReferencesMetaData serviceReferences;

    /** The persistence context reference */
    private PersistenceContextReferencesMetaData persistenceContextRefs;

    /** The data sources */
    private DataSourcesMetaData dataSources;

    /**
     * Merge an environment
     *
     * @param jbossEnvironmentRefsGroup the override environment
     * @param environmentRefsGroup the overriden environment
     * @param overridenFile the overriden file name
     * @param overrideFile the override file
     * @return the merged environment
     */
    public static JBossEnvironmentRefsGroupMetaData mergeNew(JBossEnvironmentRefsGroupMetaData jbossEnvironmentRefsGroup,
            EnvironmentRefsGroupMetaData environmentRefsGroup, ResourceManagersMetaData resourceMgrs, String overrideFile,
            String overridenFile, boolean mustOverride) {
        JBossEnvironmentRefsGroupMetaData merged = new JBossEnvironmentRefsGroupMetaData();

        merged.merge(jbossEnvironmentRefsGroup, environmentRefsGroup, resourceMgrs, overridenFile, overrideFile, mustOverride);

        return merged;
    }

    @Override
    public EJBLocalReferenceMetaData getEjbLocalReferenceByName(String name) {
        EJBLocalReferenceMetaData ref = null;
        if (this.ejbLocalReferences != null)
            ref = ejbLocalReferences.get(name);
        return ref;
    }

    @Override
    public PersistenceContextReferenceMetaData getPersistenceContextReferenceByName(String name) {
        PersistenceContextReferenceMetaData ref = null;
        if (this.persistenceContextRefs != null)
            ref = persistenceContextRefs.get(name);
        return ref;
    }

    @Override
    public ServiceReferencesMetaData getServiceReferences() {
        return serviceReferences;
    }

    @Override
    public void setServiceReferences(ServiceReferencesMetaData serviceReferences) {
        this.serviceReferences = (JBossServiceReferencesMetaData) serviceReferences;
    }

    @Override
    public EJBLocalReferencesMetaData getEjbLocalReferences() {
        return ejbLocalReferences;
    }

    @XmlElement(name = "ejb-local-ref")
    public void setEjbLocalReferences(EJBLocalReferencesMetaData ejbLocalReferences) {
        this.ejbLocalReferences = ejbLocalReferences;
    }

    @Override
    public PersistenceContextReferencesMetaData getPersistenceContextRefs() {
        return persistenceContextRefs;
    }

    @XmlElement(name = "persistence-context-ref")
    public void setPersistenceContextRefs(PersistenceContextReferencesMetaData persistenceContextRefs) {
        this.persistenceContextRefs = persistenceContextRefs;
    }

    @Override
    public DataSourcesMetaData getDataSources() {
        return dataSources;
    }

    @XmlElement(name = "data-source")
    public void setDataSources(DataSourcesMetaData dataSources) {
        this.dataSources = dataSources;
    }

    @Override
    public DataSourceMetaData getDataSourceByName(String name) {
        return AbstractMappedMetaData.getByName(name, dataSources);
    }

    /**
     * Merge an environment
     *
     * @param jbossEnvironmentRefsGroup the override environment
     * @param environmentRefsGroup the overriden environment
     * @param overridenFile the overriden file name
     * @param overrideFile the override file
     * @return the merged environment
     */
    public void merge(JBossEnvironmentRefsGroupMetaData jbossEnv, Environment specEnv, ResourceManagersMetaData resourceMgrs,
            String overrideFile, String overridenFile, boolean mustOverride) {
        if (jbossEnv == null && specEnv == null)
            return;

        super.merge(jbossEnv, specEnv, overridenFile, overrideFile, mustOverride);

        EJBLocalReferencesMetaData ejbLocalRefs = null;
        EJBLocalReferencesMetaData jbossEjbLocalRefs = null;
        PersistenceContextReferencesMetaData specPersistenceContextRefs = null;
        PersistenceContextReferencesMetaData jbossPersistenceContextRefs = null;

        if (specEnv != null) {
            ejbLocalRefs = specEnv.getEjbLocalReferences();
            specPersistenceContextRefs = specEnv.getPersistenceContextRefs();
        }

        if (jbossEnv != null) {
            jbossEjbLocalRefs = jbossEnv.getEjbLocalReferences();
            jbossPersistenceContextRefs = jbossEnv.getPersistenceContextRefs();
        } else {
            // Use the merge target for the static merge methods
            jbossEjbLocalRefs = this.getEjbLocalReferences();
            jbossPersistenceContextRefs = getPersistenceContextRefs();
        }

        EJBLocalReferencesMetaData mergedEjbLocalRefs = EJBLocalReferencesMetaData.merge(jbossEjbLocalRefs, ejbLocalRefs,
                overridenFile, overrideFile);
        if (mergedEjbLocalRefs != null)
            this.setEjbLocalReferences(mergedEjbLocalRefs);

        // Need to set the jndi name from resource mgr if referenced
        ResourceReferencesMetaData jbossResRefs = getResourceReferences();
        if (resourceMgrs != null && jbossResRefs != null) {
            for (ResourceReferenceMetaData ref : jbossResRefs) {
                ResourceManagerMetaData mgr = resourceMgrs.get(ref.getResourceName());
                if (mgr != null) {
                    if (mgr.getResJndiName() != null)
                        ref.setJndiName(mgr.getResJndiName());
                    else if (mgr.getResUrl() != null)
                        ref.setResUrl(mgr.getResUrl());
                }
            }
        }

        PersistenceContextReferencesMetaData mergedPcRefs = PersistenceContextReferencesMetaData.merge(
                jbossPersistenceContextRefs, specPersistenceContextRefs, overridenFile, overrideFile);
        if (mergedPcRefs != null)
            this.setPersistenceContextRefs(mergedPcRefs);

    }

    public void merge(JBossEnvironmentRefsGroupMetaData override, JBossEnvironmentRefsGroupMetaData original,
            ResourceManagersMetaData resourceManagers) {
        super.merge(override, original, "deployment descriptors", "annotations", false);

        EJBLocalReferencesMetaData originalLocalRefs = null;
        PersistenceContextReferencesMetaData originalPctxRefs = null;
        DataSourcesMetaData originalDataSources = null;
        if (original != null) {
            originalLocalRefs = original.ejbLocalReferences;
            originalPctxRefs = original.persistenceContextRefs;
            originalDataSources = original.dataSources;
        }

        EJBLocalReferencesMetaData overrideLocalRefs = null;
        PersistenceContextReferencesMetaData overridePctxRefs = null;
        DataSourcesMetaData overrideDataSources = null;
        if (override != null) {
            overrideLocalRefs = override.ejbLocalReferences;
            overridePctxRefs = override.persistenceContextRefs;
            overrideDataSources = override.dataSources;
        }

        EJBLocalReferencesMetaData mergedEjbLocalRefs = EJBLocalReferencesMetaData.merge(overrideLocalRefs, originalLocalRefs,
                null, "jboss.xml");
        if (mergedEjbLocalRefs != null)
            this.setEjbLocalReferences(mergedEjbLocalRefs);

        PersistenceContextReferencesMetaData mergedPctxRefs = PersistenceContextReferencesMetaData.merge(overridePctxRefs,
                originalPctxRefs, null, "jboss.xml");
        if (mergedPctxRefs != null)
            this.setPersistenceContextRefs(mergedPctxRefs);

        DataSourcesMetaData mergedDataSources = DataSourcesMetaData.merge(overrideDataSources, originalDataSources, null,
                "jboss.xml");
        if (mergedDataSources != null)
            this.setDataSources(mergedDataSources);

        // Need to set the jndi name from resource mgr if referenced
        ResourceReferencesMetaData jbossResRefs = getResourceReferences();
        if (resourceManagers != null && jbossResRefs != null) {
            for (ResourceReferenceMetaData ref : jbossResRefs) {
                ResourceManagerMetaData mgr = resourceManagers.get(ref.getResourceName());
                if (mgr != null) {
                    if (mgr.getResJndiName() != null)
                        ref.setJndiName(mgr.getResJndiName());
                    else if (mgr.getResUrl() != null)
                        ref.setResUrl(mgr.getResUrl());
                }
            }
        }
    }
}
