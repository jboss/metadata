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
