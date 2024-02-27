/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * The ejb-jar 2.0 message-driven/message-driven-destination element
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 87568 $
 */
public class MessageDrivenDestinationMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;

    private String destinationType;

    private String subscriptionDurability;

    public String getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
    }

    public String getSubscriptionDurability() {
        return subscriptionDurability;
    }

    public void setSubscriptionDurability(String subscriptionDurability) {
        this.subscriptionDurability = subscriptionDurability;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        sb.append('[');
        sb.append("destinationType=").append(destinationType);
        sb.append(']');
        return sb.toString();
    }
}
