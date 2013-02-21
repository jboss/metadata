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
