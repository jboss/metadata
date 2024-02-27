/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.web.spec;

import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;
import org.jboss.metadata.web.spec.MultipartConfigMetaData;

/**
 * web-app/servlet/multipart-config
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class MultipartConfigMetaDataMerger extends IdMetaDataImplMerger {
    public static void augment(MultipartConfigMetaData dest, MultipartConfigMetaData webFragmentMetaData, MultipartConfigMetaData webMetaData,
                               boolean resolveConflicts) {
        // Location
        if (dest.getLocation() == null) {
            dest.setLocation(webFragmentMetaData.getLocation());
        } else if (webFragmentMetaData.getLocation() != null) {
            if (!resolveConflicts && !dest.getLocation().equals(webFragmentMetaData.getLocation())
                    && (webMetaData == null || webMetaData.getLocation() == null)) {
                throw new IllegalStateException("Unresolved conflict on location: " + dest.getLocation());
            }
        }
        // Max file size
        if (!dest.getMaxFileSizeSet()) {
            if (webFragmentMetaData.getMaxFileSizeSet()) {
                dest.setMaxFileSize(webFragmentMetaData.getMaxFileSize());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.getMaxFileSizeSet()
                    && (dest.getMaxFileSize() != webFragmentMetaData.getMaxFileSize())
                    && (webMetaData == null || !webMetaData.getMaxFileSizeSet())) {
                throw new IllegalStateException("Unresolved conflict on max file size");
            }
        }
        // Max request size
        if (!dest.getMaxRequestSizeSet()) {
            if (webFragmentMetaData.getMaxRequestSizeSet()) {
                dest.setMaxRequestSize(webFragmentMetaData.getMaxRequestSize());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.getMaxRequestSizeSet()
                    && (dest.getMaxRequestSize() != webFragmentMetaData.getMaxRequestSize())
                    && (webMetaData == null || !webMetaData.getMaxRequestSizeSet())) {
                throw new IllegalStateException("Unresolved conflict on max request size");
            }
        }
        // File size threshold
        if (!dest.getFileSizeThresholdSet()) {
            if (webFragmentMetaData.getFileSizeThresholdSet()) {
                dest.setFileSizeThreshold(webFragmentMetaData.getFileSizeThreshold());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.getFileSizeThresholdSet()
                    && (dest.getFileSizeThreshold() != webFragmentMetaData.getFileSizeThreshold())
                    && (webMetaData == null || !webMetaData.getFileSizeThresholdSet())) {
                throw new IllegalStateException("Unresolved conflict on file size threshold");
            }
        }
    }

}
