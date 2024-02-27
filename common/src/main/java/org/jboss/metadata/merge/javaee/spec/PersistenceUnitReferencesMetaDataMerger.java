/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.PersistenceUnitReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferencesMetaData;
import org.jboss.metadata.merge.MergeUtil;

/**
 * PersistenceUnitReferencesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class PersistenceUnitReferencesMetaDataMerger {

    public static void merge(PersistenceUnitReferencesMetaData dest, PersistenceUnitReferencesMetaData override, PersistenceUnitReferencesMetaData original) {
        MergeUtil.merge(dest, override, original);
    }

    public static void augment(PersistenceUnitReferencesMetaData dest, PersistenceUnitReferencesMetaData augment, PersistenceUnitReferencesMetaData main,
                               boolean resolveConflicts) {
        for (PersistenceUnitReferenceMetaData persistenceUnitRef : augment) {
            if (dest.containsKey(persistenceUnitRef.getKey())) {
                if (!resolveConflicts && (main == null || !main.containsKey(persistenceUnitRef.getKey()))) {
                    throw new IllegalStateException("Unresolved conflict on persistence unit reference named: "
                            + persistenceUnitRef.getKey());
                } else {
                    ResourceInjectionMetaDataMerger.augment(dest.get(persistenceUnitRef.getKey()), persistenceUnitRef,
                            (main != null) ? main.get(persistenceUnitRef.getKey()) : null, resolveConflicts);
                }
            } else {
                dest.add(persistenceUnitRef);
            }
        }
    }

}
