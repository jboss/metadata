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
