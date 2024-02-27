/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.common;

import java.util.List;

import org.jboss.metadata.ejb.spec.TimerMetaData;

/**
 * IScheduleTarget
 *
 * @author Jaikiran Pai
 * @version $Revision: $
 */
public interface IScheduleTarget extends ITimeoutTarget {

    List<TimerMetaData> getTimers();

    void setTimers(List<TimerMetaData> timers);

    void addTimer(TimerMetaData timer);
}
