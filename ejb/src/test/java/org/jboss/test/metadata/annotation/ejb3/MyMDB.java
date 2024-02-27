/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.annotation.ejb3;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;

import org.jboss.ejb3.annotation.ResourceAdapter;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 75391 $
 */
@MessageDriven(
        name = "MailMDB",
        messageListenerInterface = IMailListener.class,
        activationConfig = {
                @ActivationConfigProperty(propertyName = "prop1", propertyValue = "value1"),
                @ActivationConfigProperty(propertyName = "prop2", propertyValue = "value2")
        },
        mappedName = "java:/mdbs/MailMDB",
        description = "A custom IMailListener MDB"
)
@ResourceAdapter(value = "MDBResourceAdapter")
public class MyMDB implements IMailListener {
    public void onMessage(IMailMsg msg) {
    }
}
