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
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.support.JavaEEMetaDataUtil;

/**
 * PersistenceContextReferencesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class PersistenceContextReferencesMetaDataMerger  {
    public static PersistenceContextReferencesMetaData merge(PersistenceContextReferencesMetaData override,
            PersistenceContextReferencesMetaData overriden, String overridenFile, String overrideFile) {
        if (override == null && overriden == null)
            return null;

        if (override == null)
            return overriden;

        PersistenceContextReferencesMetaData merged = new PersistenceContextReferencesMetaData();
        return JavaEEMetaDataUtil.merge(merged, overriden, override, "persistence-context-ref", overridenFile, overrideFile,
                false);
    }

    public void augment(PersistenceContextReferencesMetaData dest, PersistenceContextReferencesMetaData augment, PersistenceContextReferencesMetaData main,
            boolean resolveConflicts) {
        for (PersistenceContextReferenceMetaData persistenceContextReference : augment) {
            if (dest.containsKey(persistenceContextReference.getKey())) {
                if (!resolveConflicts && (main == null || !main.containsKey(persistenceContextReference.getKey()))) {
                    throw new IllegalStateException("Unresolved conflict on persistence context reference named: "
                            + persistenceContextReference.getKey());
                } else {
                    dest.get(persistenceContextReference.getKey()).augment(persistenceContextReference,
                            (main != null) ? main.get(persistenceContextReference.getKey()) : null, resolveConflicts);
                }
            } else {
                dest.add(persistenceContextReference);
            }
        }
    }

}
