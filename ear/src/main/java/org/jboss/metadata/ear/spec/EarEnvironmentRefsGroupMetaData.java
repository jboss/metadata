/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ear.spec;

import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;

/**
 * A EarEnvironmentRefsGroupMetaData.
 *
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class EarEnvironmentRefsGroupMetaData extends EnvironmentRefsGroupMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 8714123546582134095L;

    private MessageDestinationsMetaData messageDestinations;

    public MessageDestinationsMetaData getMessageDestinations() {
        return messageDestinations;
    }

    public void setMessageDestinations(MessageDestinationsMetaData messageDestinations) {
        this.messageDestinations = messageDestinations;
    }
}
