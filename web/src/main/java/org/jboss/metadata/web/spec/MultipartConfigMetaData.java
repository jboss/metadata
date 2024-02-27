/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * web-app/servlet/multipart-config
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class MultipartConfigMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;

    private String location = null;
    private long maxFileSize = -1L;
    private boolean maxFileSizeSet = false;
    private long maxRequestSize = -1L;
    private boolean maxRequestSizeSet = false;
    private int fileSizeThreshold = 0;
    private boolean fileSizeThresholdSet = false;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
        maxFileSizeSet = true;
    }

    public long getMaxRequestSize() {
        return maxRequestSize;
    }

    public void setMaxRequestSize(long maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
        maxRequestSizeSet = true;
    }

    public int getFileSizeThreshold() {
        return fileSizeThreshold;
    }

    public void setFileSizeThreshold(int fileSizeThreshold) {
        this.fileSizeThreshold = fileSizeThreshold;
        fileSizeThresholdSet = true;
    }

    public boolean getMaxFileSizeSet() {
        return maxFileSizeSet;
    }

    public boolean getMaxRequestSizeSet() {
        return maxRequestSizeSet;
    }

    public boolean getFileSizeThresholdSet() {
        return fileSizeThresholdSet;
    }
}
