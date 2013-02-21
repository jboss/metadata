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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */
public class WebResourceCollectionMetaData extends NamedMetaDataWithDescriptions {
    private static final long serialVersionUID = 1;

    /**
     * The set of all http methods: DELETE, GET, HEAD, OPTIONS, POST, PUT, TRACE
     */
    public static final Set<String> ALL_HTTP_METHODS;
    public static final String[] ALL_HTTP_METHOD_NAMES;

    static {
        TreeSet<String> tmp = new TreeSet<String>();
        tmp.add("GET");
        tmp.add("POST");
        tmp.add("PUT");
        tmp.add("DELETE");
        tmp.add("HEAD");
        tmp.add("OPTIONS");
        tmp.add("TRACE");
        ALL_HTTP_METHODS = Collections.unmodifiableSortedSet(tmp);
        ALL_HTTP_METHOD_NAMES = new String[ALL_HTTP_METHODS.size()];
        ALL_HTTP_METHODS.toArray(ALL_HTTP_METHOD_NAMES);
    }

    private List<String> urlPatterns = new ArrayList<String>();
    private List<String> httpMethods = new ArrayList<String>();
    private List<String> httpMethodOmissions = new ArrayList<String>();

    /**
     * Get http methods in ALL_HTTP_METHODS not in the argument httpMethods.
     *
     * @param httpMethods a set of http method names
     * @return possibly empty http methods in ALL_HTTP_METHODS not in
     *         httpMethods.
     */
    public static String[] getMissingHttpMethods(Collection<String> httpMethods) {
        String[] methods = {};
        if (httpMethods.size() > 0 && httpMethods.containsAll(ALL_HTTP_METHODS) == false) {
            HashSet<String> missingMethods = new HashSet<String>(ALL_HTTP_METHODS);
            missingMethods.removeAll(httpMethods);
            methods = new String[missingMethods.size()];
            missingMethods.toArray(methods);
        }
        return methods;
    }

    public String getWebResourceName() {
        return getName();
    }

    public void setWebResourceName(String webResourceName) {
        super.setName(webResourceName);
    }

    public List<String> getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(List<String> urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    public List<String> getHttpMethods() {
        return httpMethods;
    }

    public void setHttpMethods(List<String> httpMethods) {
        this.httpMethods = httpMethods;
    }

    public List<String> getHttpMethodOmissions() {
        return httpMethodOmissions;
    }

    public void setHttpMethodOmissions(List<String> httpMethodOmissions) {
        this.httpMethodOmissions = httpMethodOmissions;
    }
}
