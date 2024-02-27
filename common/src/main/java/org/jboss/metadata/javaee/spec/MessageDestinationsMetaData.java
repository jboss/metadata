/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * MessageDestinationsMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 67878 $
 */
public class MessageDestinationsMetaData extends AbstractMappedMetaData<MessageDestinationMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -6198704374773701253L;

    /**
     * Create a new MessageDestinationsMetaData.
     */
    public MessageDestinationsMetaData() {
        super("message destination name");
    }
}
