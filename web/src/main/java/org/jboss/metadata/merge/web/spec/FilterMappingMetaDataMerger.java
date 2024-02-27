/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.web.spec;

import java.util.ArrayList;
import java.util.List;

import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;
import org.jboss.metadata.web.spec.DispatcherType;
import org.jboss.metadata.web.spec.FilterMappingMetaData;

/**
 * web-app/filter-mapping metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class FilterMappingMetaDataMerger extends IdMetaDataImplMerger {
    public static void augment(FilterMappingMetaData dest, FilterMappingMetaData webFragmentMetaData, FilterMappingMetaData webMetaData, boolean resolveConflicts) {
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
        // Servlet names
        if (dest.getServletNames() == null) {
            dest.setServletNames(webFragmentMetaData.getServletNames());
        } else if (webFragmentMetaData.getServletNames() != null) {
            List<String> mergedServletNames = new ArrayList<String>();
            for (String servletName : dest.getServletNames()) {
                mergedServletNames.add(servletName);
            }
            for (String servletName : webFragmentMetaData.getServletNames()) {
                boolean found = false;
                for (String check : dest.getServletNames()) {
                    if (check.equals(servletName)) {
                        found = true;
                    }
                }
                if (!found)
                    mergedServletNames.add(servletName);
            }
            dest.setServletNames(mergedServletNames);
        }
        // Dispatchers
        if (dest.getDispatchers() == null) {
            dest.setDispatchers(webFragmentMetaData.getDispatchers());
        } else if (webFragmentMetaData.getDispatchers() != null) {
            List<DispatcherType> mergedDispatchers = new ArrayList<DispatcherType>();
            for (DispatcherType dispatcher : dest.getDispatchers()) {
                mergedDispatchers.add(dispatcher);
            }
            for (DispatcherType dispatcher : webFragmentMetaData.getDispatchers()) {
                boolean found = false;
                for (DispatcherType check : dest.getDispatchers()) {
                    if (check.equals(dispatcher)) {
                        found = true;
                    }
                }
                if (!found)
                    mergedDispatchers.add(dispatcher);
            }
            dest.setDispatchers(mergedDispatchers);
        }
    }

}
