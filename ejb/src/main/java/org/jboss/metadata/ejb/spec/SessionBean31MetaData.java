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
package org.jboss.metadata.ejb.spec;

import java.util.List;
import java.util.Map;

import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LockType;

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
