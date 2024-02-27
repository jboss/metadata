/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * MessageDestinationReferencesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class MessageDestinationReferencesMetaData extends AbstractMappedMetaData<MessageDestinationReferenceMetaData> {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -5571423286458126147L;

    /**
     * Create a new MessageDestinationReferencesMetaData.
     */
    public MessageDestinationReferencesMetaData() {
        super("message destination ref name");
    }
}
