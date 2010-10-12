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
 * Represents a <replication-config> element of the jboss-web.xml deployment
 * descriptor
 *
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @version <tt>$Revision: 83549 $</tt>
 */
public class ReplicationConfig extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;
    protected ReplicationTrigger trigger;
    protected ReplicationGranularity granularity;
    protected Boolean fieldBatchMode;
    protected String cacheName;
    protected Boolean useJK;
    protected Integer maxUnreplicatedInterval;
    protected SnapshotMode snapshotMode;
    protected Integer snapshotInterval;
    protected String sessionNotificationPolicy = null;
    protected ReplicationMode mode;
    protected Integer backups;

    public Integer getBackups() {
        return this.backups;
    }

    public void setBackups(Integer backups) {
        this.backups = backups;
    }

    public ReplicationMode getReplicationMode() {
        return this.mode;
    }

    public void setReplicationMode(ReplicationMode mode) {
        this.mode = mode;
    }

    public ReplicationTrigger getReplicationTrigger() {
        return trigger;
    }

    public void setReplicationTrigger(ReplicationTrigger trigger) {
        this.trigger = trigger;
    }

    public ReplicationGranularity getReplicationGranularity() {
        return granularity;
    }

    public void setReplicationGranularity(ReplicationGranularity granularity) {
        this.granularity = granularity;
    }

    public Boolean getReplicationFieldBatchMode() {
        return fieldBatchMode;
    }

    public void setReplicationFieldBatchMode(Boolean fieldBatchMode) {
        this.fieldBatchMode = fieldBatchMode;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public Integer getSnapshotInterval() {
        return snapshotInterval;
    }

    public void setSnapshotInterval(Integer snapshotInterval) {
        this.snapshotInterval = snapshotInterval;
    }

    public SnapshotMode getSnapshotMode() {
        return snapshotMode;
    }

    public void setSnapshotMode(SnapshotMode snapshotMode) {
        this.snapshotMode = snapshotMode;
    }

    public Boolean getUseJK() {
        return useJK;
    }

    public void setUseJK(Boolean useJK) {
        this.useJK = useJK;
    }

    public Integer getMaxUnreplicatedInterval() {
        return maxUnreplicatedInterval;
    }

    public void setMaxUnreplicatedInterval(Integer maxUnreplicatedInterval) {
        this.maxUnreplicatedInterval = maxUnreplicatedInterval;
    }

    public String getSessionNotificationPolicy() {
        return sessionNotificationPolicy;
    }

    public void setSessionNotificationPolicy(String sessionNotificationPolicy) {
        this.sessionNotificationPolicy = sessionNotificationPolicy;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        sb.append("cacheName=").append(cacheName).append(";granularity=").append(granularity).append(";trigger=")
                .append(trigger).append(";fieldBatchMode=").append(fieldBatchMode).append(";useJK=").append(useJK)
                .append(";maxUnreplicatedInterval=").append(maxUnreplicatedInterval).append(";snapshotMode=")
                .append(snapshotMode).append(";snapshotInterval=").append(snapshotInterval)
                .append(";sessionNotificationPolicy=").append(sessionNotificationPolicy);
        return sb.toString();
    }
}
