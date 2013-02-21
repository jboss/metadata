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

import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.merge.javaee.support.ResourceInjectionMetaDataWithDescriptionsMerger;

/**
 * EnvironmentEntryMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class EnvironmentEntryMetaDataMerger extends ResourceInjectionMetaDataWithDescriptionsMerger {
    public static void merge(EnvironmentEntryMetaData dest, EnvironmentEntryMetaData override, EnvironmentEntryMetaData original) {
        ResourceInjectionMetaDataWithDescriptionsMerger.merge(dest, override, original);
        if (override != null && override.getType() != null)
            dest.setType(override.getType());
        else if (original != null && original.getType() != null)
            dest.setType(original.getType());
        if (override != null && override.getValue() != null)
            dest.setValue(override.getValue());
        else if (original != null && original.getValue() != null)
            dest.setValue(original.getValue());
    }

}
