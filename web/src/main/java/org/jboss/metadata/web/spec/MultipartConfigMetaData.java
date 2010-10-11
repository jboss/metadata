/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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

import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.AugmentableMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * web-app/servlet/multipart-config
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
@XmlType(name = "multipart-configType", propOrder = { "location", "maxFileSize", "maxRequestSize", "fileSizeThreshold" })
public class MultipartConfigMetaData extends IdMetaDataImpl implements AugmentableMetaData<MultipartConfigMetaData> {
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

    public void augment(MultipartConfigMetaData webFragmentMetaData, MultipartConfigMetaData webMetaData,
            boolean resolveConflicts) {
        // Location
        if (getLocation() == null) {
            setLocation(webFragmentMetaData.getLocation());
        } else if (webFragmentMetaData.getLocation() != null) {
            if (!resolveConflicts && !getLocation().equals(webFragmentMetaData.getLocation())
                    && (webMetaData == null || webMetaData.getLocation() == null)) {
                throw new IllegalStateException("Unresolved conflict on location: " + getLocation());
            }
        }
        // Max file size
        if (!maxFileSizeSet) {
            if (webFragmentMetaData.maxFileSizeSet) {
                setMaxFileSize(webFragmentMetaData.getMaxFileSize());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.maxFileSizeSet
                    && (getMaxFileSize() != webFragmentMetaData.getMaxFileSize())
                    && (webMetaData == null || !webMetaData.maxFileSizeSet)) {
                throw new IllegalStateException("Unresolved conflict on max file size");
            }
        }
        // Max request size
        if (!maxRequestSizeSet) {
            if (webFragmentMetaData.maxRequestSizeSet) {
                setMaxRequestSize(webFragmentMetaData.getMaxRequestSize());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.maxRequestSizeSet
                    && (getMaxRequestSize() != webFragmentMetaData.getMaxRequestSize())
                    && (webMetaData == null || !webMetaData.maxRequestSizeSet)) {
                throw new IllegalStateException("Unresolved conflict on max request size");
            }
        }
        // File size threshold
        if (!fileSizeThresholdSet) {
            if (webFragmentMetaData.fileSizeThresholdSet) {
                setFileSizeThreshold(webFragmentMetaData.getFileSizeThreshold());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.fileSizeThresholdSet
                    && (getFileSizeThreshold() != webFragmentMetaData.getFileSizeThreshold())
                    && (webMetaData == null || !webMetaData.fileSizeThresholdSet)) {
                throw new IllegalStateException("Unresolved conflict on file size threshold");
            }
        }
    }

}
