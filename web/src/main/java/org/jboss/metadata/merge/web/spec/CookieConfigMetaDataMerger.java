/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.web.spec;

import java.util.List;

import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;
import org.jboss.metadata.web.spec.AttributeValueMetaData;
import org.jboss.metadata.web.spec.CookieConfigMetaData;

/**
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision: 82920 $
 */
public class CookieConfigMetaDataMerger extends IdMetaDataImplMerger {
    public static void augment(CookieConfigMetaData dest, CookieConfigMetaData webFragmentMetaData, CookieConfigMetaData webMetaData, boolean resolveConflicts) {
        // Name
        if (dest.getName() == null) {
            dest.setName(webFragmentMetaData.getName());
        } else if (webFragmentMetaData.getName() != null) {
            if (!resolveConflicts && !dest.getName().equals(webFragmentMetaData.getName())
                    && (webMetaData == null || webMetaData.getName() == null)) {
                throw new IllegalStateException("Unresolved conflict on cookie name: " + dest.getName());
            }
        }
        // Domain
        if (dest.getDomain() == null) {
            dest.setDomain(webFragmentMetaData.getDomain());
        } else if (webFragmentMetaData.getDomain() != null) {
            if (!resolveConflicts && !dest.getDomain().equals(webFragmentMetaData.getDomain())
                    && (webMetaData == null || webMetaData.getDomain() == null)) {
                throw new IllegalStateException("Unresolved conflict on cookie domain: " + dest.getDomain());
            }
        }
        // Path
        if (dest.getPath() == null) {
            dest.setPath(webFragmentMetaData.getPath());
        } else if (webFragmentMetaData.getPath() != null) {
            if (!resolveConflicts && !dest.getPath().equals(webFragmentMetaData.getPath())
                    && (webMetaData == null || webMetaData.getPath() == null)) {
                throw new IllegalStateException("Unresolved conflict on cookie path: " + dest.getPath());
            }
        }
        // Comment
        if (dest.getComment() == null) {
            dest.setComment(webFragmentMetaData.getComment());
        } else if (webFragmentMetaData.getComment() != null) {
            if (!resolveConflicts && !dest.getComment().equals(webFragmentMetaData.getComment())
                    && (webMetaData == null || webMetaData.getComment() == null)) {
                throw new IllegalStateException("Unresolved conflict on cookie comment: " + dest.getComment());
            }
        }
        // HttpOnly
        if (!dest.getHttpOnlySet()) {
            if (webFragmentMetaData.getHttpOnlySet()) {
                dest.setHttpOnly(webFragmentMetaData.getHttpOnly());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.getHttpOnlySet() && (dest.getHttpOnly() != webFragmentMetaData.getHttpOnly())
                    && (webMetaData == null || !webMetaData.getHttpOnlySet())) {
                throw new IllegalStateException("Unresolved conflict on http only");
            }
        }
        // Secure
        if (!dest.getSecureSet()) {
            if (webFragmentMetaData.getSecureSet()) {
                dest.setSecure(webFragmentMetaData.getSecure());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.getSecureSet() && (dest.getSecure() != webFragmentMetaData.getSecure())
                    && (webMetaData == null || !webMetaData.getSecureSet())) {
                throw new IllegalStateException("Unresolved conflict on secure");
            }
        }
        // MaxAge
        if (!dest.getMaxAgeSet()) {
            if (webFragmentMetaData.getMaxAgeSet()) {
                dest.setMaxAge(webFragmentMetaData.getMaxAge());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.getMaxAgeSet() && (dest.getMaxAge() != webFragmentMetaData.getMaxAge())
                    && (webMetaData == null || !webMetaData.getMaxAgeSet())) {
                throw new IllegalStateException("Unresolved conflict on max age");
            }
        }
        List<AttributeValueMetaData> fragmentAttributes = webFragmentMetaData.getAttributes();
        if (fragmentAttributes != null) {
            List<AttributeValueMetaData> destinationAttributes = dest.getAttributes();
            if (destinationAttributes == null) {
                dest.setAttributes(fragmentAttributes);
            } else if (!resolveConflicts && !destinationAttributes.equals(fragmentAttributes) && (webMetaData == null || webMetaData.getAttributes() == null)) {
                throw new IllegalStateException("Unresolved conflict on cookie attributes: " + destinationAttributes);
            }
        }
    }
}
