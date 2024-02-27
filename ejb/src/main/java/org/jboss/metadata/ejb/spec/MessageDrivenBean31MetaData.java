/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.util.List;

/**
 * Metadata for EJB3.1 MDBs
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public interface MessageDrivenBean31MetaData extends MessageDrivenBeanMetaData {
    /**
     * Returns the {@link TimerMetaData} associated with this bean
     */
    List<TimerMetaData> getTimers();

    AroundTimeoutsMetaData getAroundTimeouts();
}
