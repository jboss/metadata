/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import jakarta.ejb.TransactionManagementType;

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
