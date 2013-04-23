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
