/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3.messagelistenerinterface;

import jakarta.annotation.Resource;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnectionFactory;

/**
 * This bean does not define a message listener interface or implement one,
 * it must be augmented by xml.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 68485 $
 */
@MessageDriven(activationConfig =
        {@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class UnknownMessageListenerInterfaceMDB {
    @Resource(name = "qFactory")
    private QueueConnectionFactory qFactory;

    @Resource(name = "replyQueue")
    private Queue replyQueue;
}
