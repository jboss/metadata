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

import javax.ejb.TransactionManagementType;

import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;

/**
 * SessionBeanMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public interface SessionBeanMetaData extends EnterpriseBeanMetaData {
    /**
     * Get the home.
     *
     * @return the home.
     */
    String getHome();

    /**
     * Get the remote.
     *
     * @return the remote.
     */
    String getRemote();

    /**
     * Get the localHome.
     *
     * @return the localHome.
     */
    String getLocalHome();

    /**
     * Get the local.
     *
     * @return the local.
     */
    String getLocal();

    /**
     * Get the businessLocals.
     *
     * @return the businessLocals.
     */
    BusinessLocalsMetaData getBusinessLocals();

    /**
     * Get the businessRemotes.
     *
     * @return the businessRemotes.
     */
    BusinessRemotesMetaData getBusinessRemotes();

    /**
     * Get the serviceEndpoint.
     *
     * @return the serviceEndpoint.
     */
    String getServiceEndpoint();

    /**
     * Get the sessionType.
     *
     * @return the sessionType.
     */
    SessionType getSessionType();

    /**
     * Is this stateful
     *
     * @return true for stateful
     */
    boolean isStateful();

    /**
     * Is this stateless
     *
     * @return true for stateless
     */
    boolean isStateless();

    /**
     * Get the timeoutMethod.
     *
     * @return the timeoutMethod.
     */
    NamedMethodMetaData getTimeoutMethod();

    /**
     * Get the initMethods.
     *
     * @return the initMethods.
     */
    InitMethodsMetaData getInitMethods();

    /**
     * Get the removeMethods.
     *
     * @return the removeMethods.
     */
    RemoveMethodsMetaData getRemoveMethods();

    @Override
    TransactionManagementType getTransactionType();

    /**
     * Get the aroundInvokes.
     *
     * @return the aroundInvokes.
     */
    AroundInvokesMetaData getAroundInvokes();

    /**
     * Get the postActivates.
     *
     * @return the postActivates.
     */
    LifecycleCallbacksMetaData getPostActivates();

    /**
     * Get the prePassivates.
     *
     * @return the prePassivates.
     */
    LifecycleCallbacksMetaData getPrePassivates();
}
