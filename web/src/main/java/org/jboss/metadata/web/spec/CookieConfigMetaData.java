/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;
import org.jboss.metadata.javaee.support.AugmentableMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision: 82920 $
 */
@XmlType(name = "cookie-configType", namespace = JavaEEMetaDataConstants.JAVAEE_NS, propOrder = { "name", "domain", "path",
        "comment", "httpOnly", "secure", "maxAge" })
public class CookieConfigMetaData extends IdMetaDataImpl implements AugmentableMetaData<CookieConfigMetaData> {
    private static final long serialVersionUID = 1;

    private String name = null;
    private String domain = null;
    private String path = null;
    private String comment = null;
    private boolean httpOnly = false;
    private boolean httpOnlySet = false;
    private boolean secure = false;
    private boolean secureSet = false;
    private int maxAge = -1;
    private boolean maxAgeSet = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean getHttpOnly() {
        return httpOnly;
    }

    @XmlElement(name = "http-only")
    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
        httpOnlySet = true;
    }

    public boolean getSecure() {
        return secure;
    }

    @XmlElement(name = "secure")
    public void setSecure(boolean secure) {
        this.secure = secure;
        secureSet = true;
    }

    public int getMaxAge() {
        return maxAge;
    }

    @XmlElement(name = "max-age")
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
        maxAgeSet = true;
    }

    public void augment(CookieConfigMetaData webFragmentMetaData, CookieConfigMetaData webMetaData, boolean resolveConflicts) {
        // Name
        if (getName() == null) {
            setName(webFragmentMetaData.getName());
        } else if (webFragmentMetaData.getName() != null) {
            if (!resolveConflicts && !getName().equals(webFragmentMetaData.getName())
                    && (webMetaData == null || webMetaData.getName() == null)) {
                throw new IllegalStateException("Unresolved conflict on cookie name: " + getName());
            }
        }
        // Domain
        if (getDomain() == null) {
            setDomain(webFragmentMetaData.getDomain());
        } else if (webFragmentMetaData.getDomain() != null) {
            if (!resolveConflicts && !getDomain().equals(webFragmentMetaData.getDomain())
                    && (webMetaData == null || webMetaData.getDomain() == null)) {
                throw new IllegalStateException("Unresolved conflict on cookie domain: " + getDomain());
            }
        }
        // Path
        if (getPath() == null) {
            setPath(webFragmentMetaData.getPath());
        } else if (webFragmentMetaData.getPath() != null) {
            if (!resolveConflicts && !getPath().equals(webFragmentMetaData.getPath())
                    && (webMetaData == null || webMetaData.getPath() == null)) {
                throw new IllegalStateException("Unresolved conflict on cookie path: " + getPath());
            }
        }
        // Comment
        if (getComment() == null) {
            setComment(webFragmentMetaData.getComment());
        } else if (webFragmentMetaData.getComment() != null) {
            if (!resolveConflicts && !getComment().equals(webFragmentMetaData.getComment())
                    && (webMetaData == null || webMetaData.getComment() == null)) {
                throw new IllegalStateException("Unresolved conflict on cookie comment: " + getComment());
            }
        }
        // HttpOnly
        if (!httpOnlySet) {
            if (webFragmentMetaData.httpOnlySet) {
                setHttpOnly(webFragmentMetaData.getHttpOnly());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.httpOnlySet && (getHttpOnly() != webFragmentMetaData.getHttpOnly())
                    && (webMetaData == null || !webMetaData.httpOnlySet)) {
                throw new IllegalStateException("Unresolved conflict on http only");
            }
        }
        // Secure
        if (!secureSet) {
            if (webFragmentMetaData.secureSet) {
                setSecure(webFragmentMetaData.getSecure());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.secureSet && (getSecure() != webFragmentMetaData.getSecure())
                    && (webMetaData == null || !webMetaData.secureSet)) {
                throw new IllegalStateException("Unresolved conflict on secure");
            }
        }
        // MaxAge
        if (!maxAgeSet) {
            if (webFragmentMetaData.maxAgeSet) {
                setMaxAge(webFragmentMetaData.getMaxAge());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.maxAgeSet && (getMaxAge() != webFragmentMetaData.getMaxAge())
                    && (webMetaData == null || !webMetaData.maxAgeSet)) {
                throw new IllegalStateException("Unresolved conflict on max age");
            }
        }
    }

}
