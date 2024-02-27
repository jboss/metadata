/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.spec;

/**
 * @author Jaikiran Pai
 */
public interface SessionBean32MetaData extends SessionBean31MetaData {

    /**
     * Returns the passivation-capable property of a stateful session bean
     *
     * @return
     */
    Boolean isPassivationCapable();
}
