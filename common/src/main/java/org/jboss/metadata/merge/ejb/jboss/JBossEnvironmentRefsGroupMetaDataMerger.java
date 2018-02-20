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
package org.jboss.metadata.merge.ejb.jboss;

import org.jboss.metadata.common.ejb.JBossEnvironmentRefsGroupMetaData;
import org.jboss.metadata.common.ejb.ResourceManagerMetaData;
import org.jboss.metadata.common.ejb.ResourceManagersMetaData;
import org.jboss.metadata.javaee.spec.AdministeredObjectsMetaData;
import org.jboss.metadata.javaee.spec.ConnectionFactoriesMetaData;
import org.jboss.metadata.javaee.spec.DataSourcesMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferencesMetaData;
import org.jboss.metadata.javaee.spec.Environment;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.JMSConnectionFactoriesMetaData;
import org.jboss.metadata.javaee.spec.JMSDestinationsMetaData;
import org.jboss.metadata.javaee.spec.MailSessionsMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.merge.javaee.spec.AdministeredObjectsMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.ConnectionFactoriesMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.EJBLocalReferencesMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.JMSConnectionFactoriesMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.JMSDestinationsMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.MailSessionsMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.PersistenceContextReferencesMetaDataMerger;
import org.jboss.metadata.merge.javaee.spec.RemoteEnvironmentRefsGroupMetaDataMerger;

/**
 * JBossEnvironmentRefsGroupMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class JBossEnvironmentRefsGroupMetaDataMerger extends RemoteEnvironmentRefsGroupMetaDataMerger {

    /**
     * Merge an environment
     *
     * @param jbossEnvironmentRefsGroup the override environment
     * @param environmentRefsGroup      the overriden environment
     * @param overridenFile             the overriden file name
     * @param overrideFile              the override file
     * @return the merged environment
     */
    public static JBossEnvironmentRefsGroupMetaData mergeNew(JBossEnvironmentRefsGroupMetaData jbossEnvironmentRefsGroup,
                                                             EnvironmentRefsGroupMetaData environmentRefsGroup, ResourceManagersMetaData resourceMgrs, String overrideFile,
                                                             String overridenFile, boolean mustOverride) {
        JBossEnvironmentRefsGroupMetaData merged = new JBossEnvironmentRefsGroupMetaData();

        JBossEnvironmentRefsGroupMetaDataMerger.merge(merged, jbossEnvironmentRefsGroup, environmentRefsGroup, resourceMgrs, overridenFile, overrideFile, mustOverride);

        return merged;
    }


    /**
     * Merge an environment
     *
     * @param dest          the destination
     * @param jbossEnv      the override environment
     * @param specEnv       the overriden environment
     * @param overridenFile the overriden file name
     * @param overrideFile  the override file
     * @return the merged environment
     */
    public static void merge(JBossEnvironmentRefsGroupMetaData dest, JBossEnvironmentRefsGroupMetaData jbossEnv, Environment specEnv, ResourceManagersMetaData resourceMgrs,
                             String overrideFile, String overridenFile, boolean mustOverride) {
        if (jbossEnv == null && specEnv == null)
            return;

        RemoteEnvironmentRefsGroupMetaDataMerger.merge(dest, jbossEnv, specEnv, overridenFile, overrideFile, mustOverride);

        EJBLocalReferencesMetaData ejbLocalRefs = null;
        EJBLocalReferencesMetaData jbossEjbLocalRefs = null;
        PersistenceContextReferencesMetaData specPersistenceContextRefs = null;
        PersistenceContextReferencesMetaData jbossPersistenceContextRefs = null;
        AdministeredObjectsMetaData specAdministeredObjects = null;
        AdministeredObjectsMetaData jbossAdministeredObjects = null;
        ConnectionFactoriesMetaData specConnectionFactories = null;
        ConnectionFactoriesMetaData jbossConnectionFactories = null;
        JMSConnectionFactoriesMetaData specJmsConnectionFactories = null;
        JMSConnectionFactoriesMetaData jbossJmsConnectionFactories = null;
        JMSDestinationsMetaData specJmsDestinations = null;
        JMSDestinationsMetaData jbossJmsDestinations = null;
        MailSessionsMetaData specMailSessions = null;
        MailSessionsMetaData jbossMailSessions = null;

        if (specEnv != null) {
            ejbLocalRefs = specEnv.getEjbLocalReferences();
            specPersistenceContextRefs = specEnv.getPersistenceContextRefs();
            specAdministeredObjects = specEnv.getAdministeredObjects();
            specConnectionFactories = specEnv.getConnectionFactories();
            specJmsConnectionFactories = specEnv.getJmsConnectionFactories();
            specJmsDestinations = specEnv.getJmsDestinations();
            specMailSessions = specEnv.getMailSessions();
        }

        if (jbossEnv != null) {
            jbossEjbLocalRefs = jbossEnv.getEjbLocalReferences();
            jbossPersistenceContextRefs = jbossEnv.getPersistenceContextRefs();
            jbossAdministeredObjects = jbossEnv.getAdministeredObjects();
            jbossConnectionFactories = jbossEnv.getConnectionFactories();
            jbossJmsConnectionFactories = jbossEnv.getJmsConnectionFactories();
            jbossJmsDestinations = jbossEnv.getJmsDestinations();
            jbossMailSessions = jbossEnv.getMailSessions();
        } else {
            // Use the merge target for the static merge methods
            jbossEjbLocalRefs = dest.getEjbLocalReferences();
            jbossPersistenceContextRefs = dest.getPersistenceContextRefs();
            jbossAdministeredObjects = dest.getAdministeredObjects();
            jbossConnectionFactories = dest.getConnectionFactories();
            jbossJmsConnectionFactories = dest.getJmsConnectionFactories();
            jbossJmsDestinations = dest.getJmsDestinations();
            jbossMailSessions = dest.getMailSessions();
        }

        EJBLocalReferencesMetaData mergedEjbLocalRefs = EJBLocalReferencesMetaDataMerger.merge(jbossEjbLocalRefs, ejbLocalRefs,
                overridenFile, overrideFile);
        if (mergedEjbLocalRefs != null)
            dest.setEjbLocalReferences(mergedEjbLocalRefs);

        // Need to set the jndi name from resource mgr if referenced
        ResourceReferencesMetaData jbossResRefs = dest.getResourceReferences();
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

        PersistenceContextReferencesMetaData mergedPcRefs = PersistenceContextReferencesMetaDataMerger.merge(
                jbossPersistenceContextRefs, specPersistenceContextRefs, overridenFile, overrideFile);
        if (mergedPcRefs != null)
            dest.setPersistenceContextRefs(mergedPcRefs);

        final AdministeredObjectsMetaData mergedAdministeredObjects = AdministeredObjectsMetaDataMerger.merge(
                jbossAdministeredObjects, specAdministeredObjects, overridenFile, overrideFile);
        if (mergedAdministeredObjects != null)
            dest.setAdministeredObjects(mergedAdministeredObjects);

        final ConnectionFactoriesMetaData mergedConnectionFactories = ConnectionFactoriesMetaDataMerger.merge(
                jbossConnectionFactories, specConnectionFactories, overridenFile, overrideFile);
        if (mergedConnectionFactories != null)
            dest.setConnectionFactories(mergedConnectionFactories);

        final JMSConnectionFactoriesMetaData mergedJmsConnectionFactories = JMSConnectionFactoriesMetaDataMerger.merge(
                jbossJmsConnectionFactories, specJmsConnectionFactories, overridenFile, overrideFile);
        if (mergedJmsConnectionFactories != null)
            dest.setJmsConnectionFactories(mergedJmsConnectionFactories);

        final JMSDestinationsMetaData mergedJmsDestinations = JMSDestinationsMetaDataMerger.merge(
                jbossJmsDestinations, specJmsDestinations, overridenFile, overrideFile);
        if (mergedJmsDestinations != null)
            dest.setJmsDestinations(mergedJmsDestinations);

        final MailSessionsMetaData mergedMailSessions = MailSessionsMetaDataMerger.merge(
                jbossMailSessions, specMailSessions, overridenFile, overrideFile);
        if (mergedMailSessions != null)
            dest.setMailSessions(mergedMailSessions);

    }

    public static void merge(JBossEnvironmentRefsGroupMetaData dest, JBossEnvironmentRefsGroupMetaData override, JBossEnvironmentRefsGroupMetaData original,
                             ResourceManagersMetaData resourceManagers) {
        merge(dest, override, original, "deployment descriptors", "annotations", false);

        EJBLocalReferencesMetaData originalLocalRefs = null;
        PersistenceContextReferencesMetaData originalPctxRefs = null;
        DataSourcesMetaData originalDataSources = null;
        if (original != null) {
            originalLocalRefs = original.getEjbLocalReferences();
            originalPctxRefs = original.getPersistenceContextRefs();
            originalDataSources = original.getDataSources();
        }

        EJBLocalReferencesMetaData overrideLocalRefs = null;
        PersistenceContextReferencesMetaData overridePctxRefs = null;
        DataSourcesMetaData overrideDataSources = null;
        if (override != null) {
            overrideLocalRefs = override.getEjbLocalReferences();
            overridePctxRefs = override.getPersistenceContextRefs();
            overrideDataSources = override.getDataSources();
        }

        EJBLocalReferencesMetaData mergedEjbLocalRefs = EJBLocalReferencesMetaDataMerger.merge(overrideLocalRefs, originalLocalRefs,
                null, "jboss.xml");
        if (mergedEjbLocalRefs != null)
            dest.setEjbLocalReferences(mergedEjbLocalRefs);

        PersistenceContextReferencesMetaData mergedPctxRefs = PersistenceContextReferencesMetaDataMerger.merge(overridePctxRefs,
                originalPctxRefs, null, "jboss.xml");
        if (mergedPctxRefs != null)
            dest.setPersistenceContextRefs(mergedPctxRefs);

        // Need to set the jndi name from resource mgr if referenced
        ResourceReferencesMetaData jbossResRefs = dest.getResourceReferences();
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
