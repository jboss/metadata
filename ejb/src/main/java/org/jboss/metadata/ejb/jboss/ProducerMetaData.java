/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import org.jboss.logging.Logger;
import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * Represents a producer element of the jboss.xml deployment descriptor for
 * the 1.4 schema
 *
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version <tt>$Revision: 80355 $</tt>
 */
public class ProducerMetaData extends IdMetaDataImpl {
    @SuppressWarnings("unused")
    private static final Logger log = Logger.getLogger(ProducerMetaData.class);

    // jboss.xml
    private String className;
    private String connectionFactory;
    private boolean local = false;

    public ProducerMetaData() {
    }

    public ProducerMetaData(boolean local) {
        this.local = local;
    }

    public boolean isLocal() {
        return local;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(String connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        sb.append('[');
        sb.append("className=").append(className);
        sb.append(", connectionFactory=").append(connectionFactory);
        sb.append(']');
        return sb.toString();
    }
}
