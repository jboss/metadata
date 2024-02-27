/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;


import jakarta.ejb.TransactionManagementType;

/**
 * MessageDrivenBeanMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public interface MessageDrivenBeanMetaData extends EnterpriseBeanMetaData {
    /**
     * Get the messagingType.
     *
     * @return the messagingType.
     */
    String getMessagingType();

    /**
     * Is this JMS
     *
     * @return true for jms
     */
    boolean isJMS();

    /**
     * Get the timeoutMethod.
     *
     * @return the timeoutMethod.
     */
    NamedMethodMetaData getTimeoutMethod();

    /**
     * Set the timeoutMethod.
     *
     * @param timeoutMethod the timeoutMethod.
     * @throws IllegalArgumentException for a null timeoutMethod
     */
    void setTimeoutMethod(NamedMethodMetaData timeoutMethod);

    @Override
    TransactionManagementType getTransactionType();

    /**
     * Get the messageDestinationType.
     *
     * @return the messageDestinationType.
     */
    String getMessageDestinationType();

    /**
     * Get the aroundInvokes.
     *
     * @return the aroundInvokes.
     */
    AroundInvokesMetaData getAroundInvokes();

    /**
     * Get the messageDestinationLink.
     *
     * @return the messageDestinationLink.
     */
    String getMessageDestinationLink();

    /**
     * Get the activationConfig.
     *
     * @return the activationConfig.
     */
    ActivationConfigMetaData getActivationConfig();

    /**
     * Get the messageSelector.
     *
     * @return the messageSelector.
     */
    String getMessageSelector();

    /**
     * Get the acknowledgeMode.
     *
     * @return the acknowledgeMode.
     */
    String getAcknowledgeMode();

    /**
     * Get the subscriptionDurability.
     *
     * @return the subscriptionDurability.
     */
    SubscriptionDurability getSubscriptionDurability();
}
