/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import java.util.List;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 65928 $
 */
public class SessionConfigMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;
    private int sessionTimeout = 30;
    private boolean sessionTimeoutSet = false;
    private CookieConfigMetaData cookieConfig;
    private List<SessionTrackingModeType> sessionTrackingModes;

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
        sessionTimeoutSet = true;
    }

    public CookieConfigMetaData getCookieConfig() {
        return cookieConfig;
    }

    public void setCookieConfig(CookieConfigMetaData sessionCookie) {
        this.cookieConfig = sessionCookie;
    }

    public List<SessionTrackingModeType> getSessionTrackingModes() {
        return sessionTrackingModes;
    }

    public void setSessionTrackingModes(List<SessionTrackingModeType> sessionTrackingModes) {
        this.sessionTrackingModes = sessionTrackingModes;
    }

    public boolean getSessionTimeoutSet() {
        return sessionTimeoutSet;
    }
}
