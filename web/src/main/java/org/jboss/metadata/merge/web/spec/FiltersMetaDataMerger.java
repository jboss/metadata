/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
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
