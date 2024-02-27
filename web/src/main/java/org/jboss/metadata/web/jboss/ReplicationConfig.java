/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
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
@Deprecated
public class ReplicationConfig extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;
    protected ReplicationGranularity granularity;
    protected String cacheName;

    public ReplicationGranularity getReplicationGranularity() {
        return granularity;
    }

    public void setReplicationGranularity(ReplicationGranularity granularity) {
        this.granularity = granularity;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(100);
        sb.append("cacheName=").append(cacheName).append(";granularity=").append(granularity);
        return sb.toString();
    }
}
