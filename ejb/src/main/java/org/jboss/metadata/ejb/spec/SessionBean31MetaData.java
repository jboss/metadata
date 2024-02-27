/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.util.List;
import java.util.Map;

import jakarta.ejb.ConcurrencyManagementType;
import jakarta.ejb.LockType;

import org.jboss.metadata.javaee.spec.EmptyMetaData;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public interface SessionBean31MetaData extends SessionBeanMetaData {//implements ITimeoutTarget, IScheduleTarget // FIXME: AbstractProcessor.processClass doesn't take super interfaces into account

    /**
     * Returns the init-on-startup value of the session bean metadata.
     * Returns null if none is defined.
     *
     * @return
     */
    Boolean isInitOnStartup();

    AsyncMethodsMetaData getAsyncMethods();

    /**
     * @return Returns {@link EmptyMetaData} if the bean represents a no-interface
     *         bean. Else returns null.
     *         Use the {@link #isNoInterfaceBean()} API which is more intuitive.
     * @see SessionBean31MetaData#isNoInterfaceBean()
     */
    EmptyMetaData getLocalBean();

    /**
     * @return Returns true if this bean exposes a no-interface view.
     *         Else returns false. This is similar to {@link #getLocalBean()}, but
     *         is more intuitive
     */
    boolean isNoInterfaceBean();

    /**
     * Returns true if this is a singleton session bean. Else returns false
     */
    boolean isSingleton();

    /**
     * Returns the concurrency management type of this bean
     *
     * @return
     */
    ConcurrencyManagementType getConcurrencyManagementType();

    /**
     * Returns a {@link Map} whose key represents a {@link NamedMethodMetaData} and whose value
     * represents {@link ConcurrentMethodMetaData} of this bean. Returns an empty {@link Map} if
     * there are no concurrent methods for this bean
     *
     * @return
     */
    ConcurrentMethodsMetaData getConcurrentMethods();

    /**
     * Returns the lock type applicable at the bean level
     *
     * @return
     */
    LockType getLockType();

    /**
     * Returns the access timeout metadata applicable at bean level
     *
     * @return
     */
    AccessTimeoutMetaData getAccessTimeout();

    /**
     * Returns the names of one or more Singleton beans in the same application
     * as the referring Singleton.
     *
     * @return
     */
    String[] getDependsOn();

    List<TimerMetaData> getTimers();

    NamedMethodMetaData getAfterBeginMethod();

    NamedMethodMetaData getBeforeCompletionMethod();

    NamedMethodMetaData getAfterCompletionMethod();

    StatefulTimeoutMetaData getStatefulTimeout();

    AroundTimeoutsMetaData getAroundTimeouts();
}
