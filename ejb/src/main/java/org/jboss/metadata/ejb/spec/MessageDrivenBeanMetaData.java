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
