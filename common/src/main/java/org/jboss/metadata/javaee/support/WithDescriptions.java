/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.support;

import org.jboss.annotation.javaee.Descriptions;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public interface WithDescriptions {
    Descriptions getDescriptions();

    void setDescriptions(Descriptions descriptions);
}
