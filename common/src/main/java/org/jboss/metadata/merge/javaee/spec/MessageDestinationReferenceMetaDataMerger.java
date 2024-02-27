/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.javaee.spec;

import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.merge.javaee.support.ResourceInjectionMetaDataWithDescriptionsMerger;

/**
 * MessageDestinationReferenceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class MessageDestinationReferenceMetaDataMerger extends ResourceInjectionMetaDataWithDescriptionsMerger {
    public static MessageDestinationReferenceMetaData merge(MessageDestinationReferenceMetaData dest, MessageDestinationReferenceMetaData original) {
        MessageDestinationReferenceMetaData merged = new MessageDestinationReferenceMetaData();
        merge(merged, dest, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public static void merge(MessageDestinationReferenceMetaData dest, MessageDestinationReferenceMetaData override, MessageDestinationReferenceMetaData original) {
        ResourceInjectionMetaDataWithDescriptionsMerger.merge(dest, override, original);
        if (override != null && override.getType() != null)
            dest.setType(override.getType());
        else if (original.getType() != null)
            dest.setType(original.getType());
        if (override != null && override.getMessageDestinationUsage() != null)
            dest.setMessageDestinationUsage(override.getMessageDestinationUsage());
        else if (original.getMessageDestinationUsage() != null)
            dest.setMessageDestinationUsage(original.getMessageDestinationUsage());
        if (override != null && override.getLink() != null)
            dest.setLink(override.getLink());
        else if (original.getLink() != null)
            dest.setLink(original.getLink());
    }
}
