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
