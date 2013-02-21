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
