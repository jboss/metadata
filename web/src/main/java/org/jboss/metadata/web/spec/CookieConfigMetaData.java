/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import java.util.List;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision: 82920 $
 */
public class CookieConfigMetaData extends IdMetaDataImpl {
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
    private List<AttributeValueMetaData> attributes;

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

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
        httpOnlySet = true;
    }

    public boolean getSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
        secureSet = true;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
        maxAgeSet = true;
    }

    public List<AttributeValueMetaData> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(List<AttributeValueMetaData> attributes) {
        this.attributes = attributes;
    }

    public boolean getHttpOnlySet() {
        return httpOnlySet;
    }

    public boolean getSecureSet() {
        return secureSet;
    }

    public boolean getMaxAgeSet() {
        return maxAgeSet;
    }
}
