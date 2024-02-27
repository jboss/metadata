/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import java.util.List;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * web-app/servlet-mapping metadata
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class ServletMappingMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;
    protected String servletName;
    protected List<String> urlPatterns;

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
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

    public boolean validateServletName() {
        if (this.servletName == null) {
            return false;
        }
        return true;
    }

    public boolean validateUrlPatterns() {
        if (this.urlPatterns == null) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder tmp = new StringBuilder("ServletMappingMetaData(id=");
        tmp.append(getId());
        tmp.append(",servletName=");
        tmp.append(servletName);
        tmp.append(",urlPatterns=");
        tmp.append(urlPatterns);
        tmp.append(')');
        return tmp.toString();
    }

}
