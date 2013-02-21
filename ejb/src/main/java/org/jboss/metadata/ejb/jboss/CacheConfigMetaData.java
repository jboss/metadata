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
package org.jboss.metadata.ejb.jboss;

/**
 * Represents an <cache-config> element of the jboss.xml deployment descriptor
 *
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version <tt>$Revision: 80355 $</tt>
 */
public class CacheConfigMetaData {
    private String value = null;
    private Integer maxSize = null;
    private Integer idleTimeoutSeconds = null;
    private Integer removeTimeoutSeconds = null;
    private String name = null;
    private String persistenceManager = null;
    private String replicationIsPassivation = null;

    public String getPersistenceManager() {
        return persistenceManager;
    }

    public void setPersistenceManager(String persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Integer getIdleTimeoutSeconds() {
        return idleTimeoutSeconds;
    }

    public void setIdleTimeoutSeconds(Integer idleTimeoutSeconds) {
        this.idleTimeoutSeconds = idleTimeoutSeconds;
    }

    public Integer getRemoveTimeoutSeconds() {
        return removeTimeoutSeconds;
    }

    public void setRemoveTimeoutSeconds(Integer removeTimeoutSeconds) {
        this.removeTimeoutSeconds = removeTimeoutSeconds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReplicationIsPassivation() {
        return replicationIsPassivation;
    }

    public void setReplicationIsPassivation(String replicationIsPassivation) {
        this.replicationIsPassivation = replicationIsPassivation;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        sb.append("[");
        sb.append("cacheValue=").append(value);
        sb.append(", maxSize=").append(maxSize);
        sb.append(", idleTimeoutSeconds=").append(idleTimeoutSeconds);
        sb.append(", name=").append(name);
        sb.append(", replicationIsPassivation=").append(replicationIsPassivation);
        sb.append("]");
        return sb.toString();
    }

}
