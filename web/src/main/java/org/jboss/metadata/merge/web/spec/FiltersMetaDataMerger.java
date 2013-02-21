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
package org.jboss.metadata.merge.web.spec;

import org.jboss.metadata.web.spec.FilterMetaData;
import org.jboss.metadata.web.spec.FiltersMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 65928 $
 */
public class FiltersMetaDataMerger {
    public static void augment(FiltersMetaData dest, FiltersMetaData webFragmentMetaData, FiltersMetaData webMetaData, boolean resolveConflicts) {
        for (FilterMetaData filterMetaData : webFragmentMetaData) {
            if (dest.containsKey(filterMetaData.getKey())) {
                FilterMetaDataMerger.augment(dest.get(filterMetaData.getKey()), filterMetaData,
                        (webMetaData != null) ? webMetaData.get(filterMetaData.getKey()) : null, resolveConflicts);
            } else {
                dest.add(filterMetaData);
            }
        }
    }

}
