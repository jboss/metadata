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

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;
import org.jboss.metadata.javaee.support.AugmentableMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 65928 $
 */
@XmlType(name = "session-configType", namespace = JavaEEMetaDataConstants.JAVAEE_NS, propOrder = { "sessionTimeout",
        "cookieConfig", "sessionTrackingModes" })
public class SessionConfigMetaData extends IdMetaDataImpl implements AugmentableMetaData<SessionConfigMetaData> {
    private static final long serialVersionUID = 1;
    private int sessionTimeout = 30;
    private boolean sessionTimeoutSet = false;
    private CookieConfigMetaData cookieConfig;
    private List<SessionTrackingModeType> sessionTrackingModes;

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    @XmlElement(name = "session-timeout")
    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
        sessionTimeoutSet = true;
    }

    public CookieConfigMetaData getCookieConfig() {
        return cookieConfig;
    }

    @XmlElement(name = "cookie-config")
    public void setCookieConfig(CookieConfigMetaData sessionCookie) {
        this.cookieConfig = sessionCookie;
    }

    public List<SessionTrackingModeType> getSessionTrackingModes() {
        return sessionTrackingModes;
    }

    @XmlElement(name = "tracking-mode", type = SessionTrackingModeType.class)
    public void setSessionTrackingModes(List<SessionTrackingModeType> sessionTrackingModes) {
        this.sessionTrackingModes = sessionTrackingModes;
    }

    public void augment(SessionConfigMetaData webFragmentMetaData, SessionConfigMetaData webMetaData, boolean resolveConflicts) {
        // Session timeout
        if (!sessionTimeoutSet) {
            if (webFragmentMetaData.sessionTimeoutSet) {
                setSessionTimeout(webFragmentMetaData.getSessionTimeout());
            }
        } else {
            if (!resolveConflicts && webFragmentMetaData.sessionTimeoutSet
                    && (getSessionTimeout() != webFragmentMetaData.getSessionTimeout())
                    && (webMetaData == null || !webMetaData.sessionTimeoutSet)) {
                throw new IllegalStateException("Unresolved conflict on session timeout");
            }
        }
        // Cookie config
        if (getCookieConfig() == null) {
            setCookieConfig(webFragmentMetaData.getCookieConfig());
        } else if (webFragmentMetaData.getCookieConfig() != null) {
            getCookieConfig().augment(webFragmentMetaData.getCookieConfig(),
                    (webMetaData != null) ? webMetaData.getCookieConfig() : null, resolveConflicts);
        }
        // Tracking modes (multiple, so additive, no conflict)
        if (getSessionTrackingModes() == null) {
            setSessionTrackingModes(webFragmentMetaData.getSessionTrackingModes());
        } else if (webFragmentMetaData.getSessionTrackingModes() != null) {
            for (SessionTrackingModeType sessionTrackingMode : webFragmentMetaData.getSessionTrackingModes()) {
                if (!getSessionTrackingModes().contains(sessionTrackingMode)) {
                    getSessionTrackingModes().add(sessionTrackingMode);
                }
            }
        }
    }

}
