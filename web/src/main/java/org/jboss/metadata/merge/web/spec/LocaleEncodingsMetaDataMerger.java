/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.web.spec;

import java.util.ArrayList;
import java.util.List;

import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;
import org.jboss.metadata.web.spec.LocaleEncodingMetaData;
import org.jboss.metadata.web.spec.LocaleEncodingsMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 75470 $
 */
public class LocaleEncodingsMetaDataMerger extends IdMetaDataImplMerger {
    public static void augment(LocaleEncodingsMetaData dest, LocaleEncodingsMetaData webFragmentMetaData, LocaleEncodingsMetaData webMetaData,
                               boolean resolveConflicts) {
        if (dest.getMappings() == null) {
            dest.setMappings(webFragmentMetaData.getMappings());
        } else if (webFragmentMetaData.getMappings() != null) {
            List<LocaleEncodingMetaData> mergedMappings = new ArrayList<LocaleEncodingMetaData>();
            for (LocaleEncodingMetaData mapping : dest.getMappings()) {
                mergedMappings.add(mapping);
            }
            for (LocaleEncodingMetaData mapping : webFragmentMetaData.getMappings()) {
                boolean found = false;
                for (LocaleEncodingMetaData check : dest.getMappings()) {
                    if (check.getLocale().equals(mapping.getLocale())) {
                        found = true;
                        // Check for a conflict
                        if (!resolveConflicts && !check.getEncoding().equals(mapping.getEncoding())) {
                            // If the parameter name does not exist in the main
                            // web, it's an error
                            boolean found2 = false;
                            if (webMetaData.getMappings() != null) {
                                for (LocaleEncodingMetaData check1 : webMetaData.getMappings()) {
                                    if (check1.getLocale().equals(check.getLocale())) {
                                        found2 = true;
                                        break;
                                    }
                                }
                            }
                            if (!found2)
                                throw new IllegalStateException("Unresolved conflict on locale: " + check.getLocale());
                        }
                    }
                }
                if (!found)
                    mergedMappings.add(mapping);
            }
            dest.setMappings(mergedMappings);
        }
    }

}
