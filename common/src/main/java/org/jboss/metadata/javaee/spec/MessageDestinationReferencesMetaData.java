/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;
import org.jboss.metadata.javaee.support.AugmentableMetaData;
import org.jboss.metadata.javaee.support.JavaEEMetaDataUtil;

/**
 * MessageDestinationReferencesMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class MessageDestinationReferencesMetaData extends AbstractMappedMetaData<MessageDestinationReferenceMetaData> implements
        AugmentableMetaData<MessageDestinationReferencesMetaData> {
    /** The serialVersionUID */
    private static final long serialVersionUID = -5571423286458126147L;

    /**
     * Merge message destination references
     *
     * @param override the override references
     * @param overriden the overriden references
     * @param overridenFile the overriden file name
     * @param overrideFile the override file
     * @return the merged referencees
     */
    public static MessageDestinationReferencesMetaData merge(MessageDestinationReferencesMetaData override,
            MessageDestinationReferencesMetaData overriden, String overridenFile, String overrideFile, boolean mustOverride) {
        if (override == null && overriden == null)
            return null;

        if (override == null)
            return overriden;

        MessageDestinationReferencesMetaData merged = new MessageDestinationReferencesMetaData();
        return JavaEEMetaDataUtil.merge(merged, overriden, override, "message-destination-ref", overridenFile, overrideFile,
                mustOverride);
    }

    /**
     * Create a new MessageDestinationReferencesMetaData.
     */
    public MessageDestinationReferencesMetaData() {
        super("message destination ref name");
    }

    @Override
    public void augment(MessageDestinationReferencesMetaData augment, MessageDestinationReferencesMetaData main,
            boolean resolveConflicts) {
        for (MessageDestinationReferenceMetaData messageDestinationRef : augment) {
            if (containsKey(messageDestinationRef.getKey())) {
                if (!resolveConflicts && (main == null || !main.containsKey(messageDestinationRef.getKey()))) {
                    throw new IllegalStateException("Unresolved conflict on message destination reference named: "
                            + messageDestinationRef.getKey());
                } else {
                    get(messageDestinationRef.getKey()).augment(messageDestinationRef,
                            (main != null) ? main.get(messageDestinationRef.getKey()) : null, resolveConflicts);
                }
            } else {
                add(messageDestinationRef);
            }
        }
    }

}
