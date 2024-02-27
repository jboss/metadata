/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.PropertiesMetaData;
import org.jboss.metadata.merge.javaee.support.ResourceInjectionMetaDataWithDescriptionsMerger;

/**
 * PersistenceContextReferenceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class PersistenceContextReferenceMetaDataMerger extends ResourceInjectionMetaDataWithDescriptionsMerger {

    public static PersistenceContextReferenceMetaData merge(PersistenceContextReferenceMetaData dest, PersistenceContextReferenceMetaData original) {
        PersistenceContextReferenceMetaData merged = new PersistenceContextReferenceMetaData();
        merge(merged, dest, original);
        return merged;
    }

    public static void merge(PersistenceContextReferenceMetaData dest, PersistenceContextReferenceMetaData override, PersistenceContextReferenceMetaData original) {
        ResourceInjectionMetaDataWithDescriptionsMerger.merge(dest, override, original);
        if (override != null && override.getPersistenceUnitName() != null)
            dest.setPersistenceUnitName(override.getPersistenceUnitName());
        else if (original != null && original.getPersistenceUnitName() != null)
            dest.setPersistenceUnitName(original.getPersistenceUnitName());
        if (override != null && override.getPersistenceContextType() != null)
            dest.setPersistenceContextType(override.getPersistenceContextType());
        else if (original != null && original.getPersistenceContextType() != null)
            dest.setPersistenceContextType(original.getPersistenceContextType());
        if (override != null && override.getPersistenceContextSynchronization() != null)
            dest.setPersistenceContextSynchronization(override.getPersistenceContextSynchronization());
        else if (original != null && original.getPersistenceContextSynchronization() != null)
            dest.setPersistenceContextSynchronization(original.getPersistenceContextSynchronization());
        if (override != null && override.getProperties() != null) {
            if (dest.getProperties() == null)
                dest.setProperties(new PropertiesMetaData());
            dest.getProperties().addAll(override.getProperties());
        }
        if (original != null && original.getProperties() != null) {
            if (dest.getProperties() == null)
                dest.setProperties(new PropertiesMetaData());
            dest.getProperties().addAll(original.getProperties());
        }
    }
}
