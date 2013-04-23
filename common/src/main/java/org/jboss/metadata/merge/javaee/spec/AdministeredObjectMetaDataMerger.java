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

import org.jboss.metadata.javaee.spec.AdministeredObjectMetaData;
import org.jboss.metadata.merge.javaee.support.NamedMetaDataMerger;

/**
 *
 * @author Eduardo Martins
 *
 */

public class AdministeredObjectMetaDataMerger {

    public static AdministeredObjectMetaData merge(AdministeredObjectMetaData dest, AdministeredObjectMetaData original) {
        AdministeredObjectMetaData merged = new AdministeredObjectMetaData();
        merge(merged, dest, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(AdministeredObjectMetaData dest, AdministeredObjectMetaData override, AdministeredObjectMetaData original) {

        NamedMetaDataMerger.merge(dest, override, original);

        if (override != null && override.getInterfaceName() != null)
            dest.setInterfaceName(override.getInterfaceName());
        else if (original != null && original.getInterfaceName() != null)
            dest.setInterfaceName(original.getInterfaceName());

        if (override != null && override.getClassName() != null)
            dest.setClassName(override.getClassName());
        else if (original != null && original.getClassName() != null)
            dest.setClassName(original.getClassName());

        if (override != null && override.getResourceAdapter() != null)
            dest.setResourceAdapter(override.getResourceAdapter());
        else if (original != null && original.getResourceAdapter() != null)
            dest.setResourceAdapter(original.getResourceAdapter());

        if (override != null && override.getProperties() != null)
            dest.setProperties(override.getProperties());
        else if (original != null && original.getProperties() != null)
            dest.setProperties(original.getProperties());

    }

}
