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
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.EJBLocalReferencesMetaData;
import org.jboss.metadata.javaee.spec.Environment;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
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

        if (specEnv != null) {
            ejbLocalRefs = specEnv.getEjbLocalReferences();
        }

        if (jbossEnv != null) {
            jbossEjbLocalRefs = jbossEnv.getEjbLocalReferences();
        } else {
            // Use the merge target for the static merge methods
            jbossEjbLocalRefs = dest.getEjbLocalReferences();
        }

        EJBLocalReferencesMetaData mergedEjbLocalRefs = EJBLocalReferencesMetaDataMerger.merge(jbossEjbLocalRefs, ejbLocalRefs,
                overridenFile, overrideFile);
        if (mergedEjbLocalRefs != null)
            dest.setEjbLocalReferences(mergedEjbLocalRefs);

        PersistenceContextReferencesMetaData persistenceContextRefs = PersistenceContextReferencesMetaDataMerger.merge(
                EnvironmentRefsGroupMetaData.getPersistenceContextRefs(jbossEnv), EnvironmentRefsGroupMetaData.getPersistenceContextRefs(specEnv), overridenFile, overrideFile);
        if (persistenceContextRefs != null)
            dest.setPersistenceContextRefs(persistenceContextRefs);



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

    }
}
