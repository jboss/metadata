/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import java.util.List;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * web-app/filter-mapping metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class FilterMappingMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;
    private String filterName;
    private List<String> urlPatterns;
    private List<String> servletNames;
    private List<DispatcherType> dispatchers;

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public List<String> getServletNames() {
        return servletNames;
    }

    public void setServletNames(List<String> servletNames) {
        this.servletNames = servletNames;
    }

    public List<String> getUrlPatterns() {
        // Resolve special "" mapping as the empty String
        if (urlPatterns != null)
            for (int i = 0; i < urlPatterns.size(); i++) {
                if ("\"\"".equals(urlPatterns.get(i))) {
                    urlPatterns.set(i, "");
                }
            }
        return urlPatterns;
    }

    public void setUrlPatterns(List<String> urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    public List<DispatcherType> getDispatchers() {
        return dispatchers;
    }

    public void setDispatchers(List<DispatcherType> dispatchers) {
        this.dispatchers = dispatchers;
    }
}
