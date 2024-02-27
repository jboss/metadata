/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.test.extension;

import java.util.concurrent.TimeUnit;

import org.jboss.metadata.ejb.parser.jboss.ejb3.AbstractMethodsBoundMetaData;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class TxTest extends AbstractMethodsBoundMetaData {
    private long timeout;
    private TimeUnit unit = TimeUnit.SECONDS;

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }
}
