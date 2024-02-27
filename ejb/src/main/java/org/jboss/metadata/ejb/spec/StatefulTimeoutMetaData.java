/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class StatefulTimeoutMetaData extends AbstractTimeoutMetaData {
    private static final long serialVersionUID = -467025454498279681L;

    public StatefulTimeoutMetaData() {
    }

    public StatefulTimeoutMetaData(long timeout, TimeUnit unit) {
        super(timeout, unit);
    }
}
