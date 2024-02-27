/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.ContextServicesMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferencesMetaData;
import org.jboss.metadata.javaee.spec.Environment;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.ManagedExecutorsMetaData;
import org.jboss.metadata.javaee.spec.ManagedScheduledExecutorsMetaData;
import org.jboss.metadata.javaee.spec.ManagedThreadFactoriesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.spec.RemoteEnvironmentRefsGroupMetaData;

/**
 * EnvironmentRefsGroupMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */

public class EnvironmentRefsGroupMetaDataMerger extends RemoteEnvironmentRefsGroupMetaDataMerger {
    public static void merge(EnvironmentRefsGroupMetaData dest, Environment jbossEnv, Environment specEnv, String overridenFile, String overrideFile, boolean mustOverride) {
        RemoteEnvironmentRefsGroupMetaDataMerger.merge(dest, jbossEnv, specEnv, overrideFile, overridenFile, mustOverride);

        EJBLocalReferencesMetaData ejbLocalRefs = null;
        EJBLocalReferencesMetaData jbossEjbLocalRefs = null;
        ContextServicesMetaData specContextServices = null;
        ContextServicesMetaData jbossContextServices = null;
        ManagedExecutorsMetaData specManagedExecutors = null;
        ManagedExecutorsMetaData jbossManagedExecutors = null;
        ManagedScheduledExecutorsMetaData specManagedScheduledExecutors = null;
        ManagedScheduledExecutorsMetaData jbossManagedScheduledExecutors = null;
        ManagedThreadFactoriesMetaData specManagedThreadFactories = null;
        ManagedThreadFactoriesMetaData jbossManagedThreadFactories = null;

        if (specEnv != null) {
            ejbLocalRefs = specEnv.getEjbLocalReferences();
            specContextServices = specEnv.getContextServices();
            specManagedExecutors = specEnv.getManagedExecutors();
            specManagedScheduledExecutors = specEnv.getManagedScheduledExecutors();
            specManagedThreadFactories = specEnv.getManagedThreadFactories();
        }

        if (jbossEnv != null) {
            jbossEjbLocalRefs = jbossEnv.getEjbLocalReferences();
            jbossContextServices = jbossEnv.getContextServices();
            jbossManagedExecutors = jbossEnv.getManagedExecutors();
            jbossManagedScheduledExecutors = jbossEnv.getManagedScheduledExecutors();
            jbossManagedThreadFactories = jbossEnv.getManagedThreadFactories();
        } else {
            // Use the merge target for the static merge methods
            jbossEjbLocalRefs = dest.getEjbLocalReferences();
            jbossContextServices = dest.getContextServices();
            jbossManagedExecutors = dest.getManagedExecutors();
            jbossManagedScheduledExecutors = dest.getManagedScheduledExecutors();
            jbossManagedThreadFactories = dest.getManagedThreadFactories();
        }

        EJBLocalReferencesMetaData mergedEjbLocalRefs = EJBLocalReferencesMetaDataMerger.merge(jbossEjbLocalRefs, ejbLocalRefs,
                overridenFile, overrideFile);
        if (mergedEjbLocalRefs != null)
            dest.setEjbLocalReferences(mergedEjbLocalRefs);

        PersistenceContextReferencesMetaData persistenceContextRefs = PersistenceContextReferencesMetaDataMerger.merge(
                EnvironmentRefsGroupMetaData.getPersistenceContextRefs(jbossEnv), EnvironmentRefsGroupMetaData.getPersistenceContextRefs(specEnv), overridenFile, overrideFile);
        if (persistenceContextRefs != null)
            dest.setPersistenceContextRefs(persistenceContextRefs);

        final ContextServicesMetaData mergedContextServices = ContextServicesMetaDataMerger.merge(
                jbossContextServices, specContextServices, overridenFile, overrideFile);
        if (mergedContextServices != null)
            dest.setContextServices(mergedContextServices);

        final ManagedExecutorsMetaData mergedManagedExecutors = ManagedExecutorsMetaDataMerger.merge(
                jbossManagedExecutors, specManagedExecutors, overridenFile, overrideFile);
        if (mergedManagedExecutors != null)
            dest.setManagedExecutors(mergedManagedExecutors);

        final ManagedScheduledExecutorsMetaData mergedManagedScheduledExecutors = ManagedScheduledExecutorsMetaDataMerger.merge(
                jbossManagedScheduledExecutors, specManagedScheduledExecutors, overridenFile, overrideFile);
        if (mergedManagedScheduledExecutors != null)
            dest.setManagedScheduledExecutors(mergedManagedScheduledExecutors);

        final ManagedThreadFactoriesMetaData mergedManagedThreadFactories = ManagedThreadFactoriesMetaDataMerger.merge(
                jbossManagedThreadFactories, specManagedThreadFactories, overridenFile, overrideFile);
        if (mergedManagedThreadFactories != null)
            dest.setManagedThreadFactories(mergedManagedThreadFactories);
    }

    public static void augment(EnvironmentRefsGroupMetaData dest, RemoteEnvironmentRefsGroupMetaData augment, RemoteEnvironmentRefsGroupMetaData main,
                               boolean resolveConflicts) {
        RemoteEnvironmentRefsGroupMetaDataMerger.augment(dest, augment, main, resolveConflicts);
        EnvironmentRefsGroupMetaData augmentE = (EnvironmentRefsGroupMetaData) augment;
        EnvironmentRefsGroupMetaData mainE = (EnvironmentRefsGroupMetaData) main;

        // EJB local references
        if (dest.getEjbLocalReferences() == null) {
            if (augmentE.getEjbLocalReferences() != null)
                dest.setEjbLocalReferences(augmentE.getEjbLocalReferences());
        } else if (augmentE.getEjbLocalReferences() != null) {
            EJBLocalReferencesMetaDataMerger.augment(dest.getEjbLocalReferences(), augmentE.getEjbLocalReferences(),
                    (mainE != null) ? mainE.getEjbLocalReferences() : null, resolveConflicts);
        }

        // Persistence context refs
        if (dest.getPersistenceContextRefs() == null) {
            if (augmentE.getPersistenceContextRefs() != null)
                dest.setPersistenceContextRefs(augmentE.getPersistenceContextRefs());
        } else if (augmentE.getPersistenceContextRefs() != null) {
            PersistenceContextReferencesMetaDataMerger.augment(dest.getPersistenceContextRefs(), augmentE.getPersistenceContextRefs(),
                    (mainE != null) ? mainE.getPersistenceContextRefs() : null, resolveConflicts);
        }

        if (dest.getContextServices() == null) {
            if (augmentE.getContextServices() != null)
                dest.setContextServices(augmentE.getContextServices());
        } else if (augmentE.getContextServices() != null) {
            ContextServicesMetaDataMerger.augment(dest.getContextServices(), augmentE.getContextServices(), (mainE != null) ? mainE.getContextServices() : null,
                    resolveConflicts);
        }
        if (dest.getManagedExecutors() == null) {
            if (augmentE.getManagedExecutors() != null)
                dest.setManagedExecutors(augmentE.getManagedExecutors());
        } else if (augmentE.getManagedExecutors() != null) {
            ManagedExecutorsMetaDataMerger.augment(dest.getManagedExecutors(), augmentE.getManagedExecutors(), (mainE != null) ? mainE.getManagedExecutors() : null,
                    resolveConflicts);
        }
        if (dest.getManagedScheduledExecutors() == null) {
            if (augmentE.getManagedScheduledExecutors() != null)
                dest.setManagedScheduledExecutors(augmentE.getManagedScheduledExecutors());
        } else if (augmentE.getManagedScheduledExecutors() != null) {
            ManagedScheduledExecutorsMetaDataMerger.augment(dest.getManagedScheduledExecutors(), augmentE.getManagedScheduledExecutors(), (mainE != null) ? mainE.getManagedScheduledExecutors() : null,
                    resolveConflicts);
        }
        if (dest.getManagedThreadFactories() == null) {
            if (augmentE.getManagedThreadFactories() != null)
                dest.setManagedThreadFactories(augmentE.getManagedThreadFactories());
        } else if (augmentE.getManagedThreadFactories() != null) {
            ManagedThreadFactoriesMetaDataMerger.augment(dest.getManagedThreadFactories(), augmentE.getManagedThreadFactories(), (mainE != null) ? mainE.getManagedThreadFactories() : null,
                    resolveConflicts);
        }
    }
}
