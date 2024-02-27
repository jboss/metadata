/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.web.spec;

import java.util.ArrayList;
import java.util.List;

import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;
import org.jboss.metadata.web.spec.ServletMappingMetaData;

/**
 * web-app/servlet-mapping metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class ServletMappingMetaDataMerger extends IdMetaDataImplMerger {
    public static void augment(ServletMappingMetaData dest, ServletMappingMetaData webFragmentMetaData, ServletMappingMetaData webMetaData, boolean resolveConflicts) {
        // Note: as this is purely additive, webMetaData is useless
        // Url pattern
        if (dest.getUrlPatterns() == null) {
            dest.setUrlPatterns(webFragmentMetaData.getUrlPatterns());
        } else if (webFragmentMetaData.getUrlPatterns() != null) {
            List<String> mergedUrlPatterns = new ArrayList<String>();
            for (String urlPattern : dest.getUrlPatterns()) {
                mergedUrlPatterns.add(urlPattern);
            }
            for (String urlPattern : webFragmentMetaData.getUrlPatterns()) {
                boolean found = false;
                for (String check : dest.getUrlPatterns()) {
                    if (check.equals(urlPattern)) {
                        found = true;
                    }
                }
                if (!found)
                    mergedUrlPatterns.add(urlPattern);
            }
            dest.setUrlPatterns(mergedUrlPatterns);
        }
    }
}
