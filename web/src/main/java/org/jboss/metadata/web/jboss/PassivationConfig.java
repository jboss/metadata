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
package org.jboss.metadata.web.jboss;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * Represents a <passivation-config> element of the jboss-web.xml deployment
 * descriptor
 *
 * @author Brian Stansberry
 * @version <tt>$Revision: 83549 $</tt>
 */
public class PassivationConfig extends IdMetaDataImpl {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1;

    private Boolean useSessionPassivation;

    private Integer passivationMinIdleTime;

    private Integer passivationMaxIdleTime;

    /**
     * Gets the max time (seconds) the session must be idle after which it's
     * eligible for passivation whether or not maxActiveSessions has been
     * reached.
     */
    public Integer getPassivationMaxIdleTime() {
        return passivationMaxIdleTime;
    }

    /**
     * Sets the max time (seconds) the session must be idle after which it's
     * eligible for passivation whether or not maxActiveSessions has been
     * reached.
     */
    public void setPassivationMaxIdleTime(Integer passivationMaxIdleTime) {
        this.passivationMaxIdleTime = passivationMaxIdleTime;
    }

    /**
     * Gets the min time (seconds) a session must be idle before it's eligible
     * for passivation. This overrides the maxActiveSessions config, to prevent
     * thrashing if the there are lots of active sessions. Setting to -1 means
     * it's ignored
     */
    public Integer getPassivationMinIdleTime() {
        return passivationMinIdleTime;
    }

    /**
     * Sets the min time (seconds) a session must be idle before it's eligible
     * for passivation. This overrides the maxActiveSessions config, to prevent
     * thrashing if the there are lots of active sessions. Setting to -1 means
     * it's ignored
     */
    public void setPassivationMinIdleTime(Integer passivationMinIdleTime) {
        this.passivationMinIdleTime = passivationMinIdleTime;
    }

    /**
     * Gets whether passivation is enabled for this webapp.
     */
    public Boolean getUseSessionPassivation() {
        return useSessionPassivation;
    }

    /**
     * Sets whether passivation is enabled for this webapp.
     */
    public void setUseSessionPassivation(Boolean useSessionPassivation) {
        this.useSessionPassivation = useSessionPassivation;
    }

}
