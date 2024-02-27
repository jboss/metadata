/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Metadata for {@link jakarta.ejb.AccessTimeout}'s xml equivalent
 *
 * @author Jaikiran Pai
 */
public abstract class AbstractTimeoutMetaData implements Serializable {
    private static final long serialVersionUID = 1L;

    private long timeout;

    private TimeUnit unit;

    protected AbstractTimeoutMetaData() {
    }

    protected AbstractTimeoutMetaData(long timeout, TimeUnit unit) {
        this.timeout = timeout;
        this.unit = unit;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public long getTimeout() {
        return this.timeout;
    }

    public void setUnit(TimeUnit timeUnit) {
        this.unit = timeUnit;
    }

    public TimeUnit getUnit() {
        return this.unit;
    }
}
