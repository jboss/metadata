/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.web.spec;

import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;
import org.jboss.metadata.web.spec.SessionConfigMetaData;
import org.jboss.metadata.web.spec.SessionTrackingModeType;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 65928 $
 */
public class SessionConfigMetaDataMerger extends IdMetaDataImplMerger {
    public static void augment(SessionConfigMetaData dest, SessionConfigMetaData webFragmentMetaData, SessionConfigMetaData webMetaData, boolean resolveConflicts) {
        // Session timeout
        if (!dest.getSessionTimeoutSet()) {
            if (webFragmentMetaData.getSessionTimeoutSet()) {
                dest.setSessionTimeout(webFragmentMetaData.getSessionTimeout());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.getSessionTimeoutSet()
                    && (dest.getSessionTimeout() != webFragmentMetaData.getSessionTimeout())
                    && (webMetaData == null || !webMetaData.getSessionTimeoutSet())) {
                throw new IllegalStateException("Unresolved conflict on session timeout");
            }
        }
        // Cookie config
        if (dest.getCookieConfig() == null) {
            dest.setCookieConfig(webFragmentMetaData.getCookieConfig());
        } else if (webFragmentMetaData.getCookieConfig() != null) {
            CookieConfigMetaDataMerger.augment(dest.getCookieConfig(), webFragmentMetaData.getCookieConfig(),
                    (webMetaData != null) ? webMetaData.getCookieConfig() : null, resolveConflicts);
        }
        // Tracking modes (multiple, so additive, no conflict)
        if (dest.getSessionTrackingModes() == null) {
            dest.setSessionTrackingModes(webFragmentMetaData.getSessionTrackingModes());
        } else if (webFragmentMetaData.getSessionTrackingModes() != null) {
            for (SessionTrackingModeType sessionTrackingMode : webFragmentMetaData.getSessionTrackingModes()) {
                if (!dest.getSessionTrackingModes().contains(sessionTrackingMode)) {
                    dest.getSessionTrackingModes().add(sessionTrackingMode);
                }
            }
        }
    }

}
