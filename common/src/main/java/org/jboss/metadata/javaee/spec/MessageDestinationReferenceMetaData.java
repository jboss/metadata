/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.ResourceInjectionMetaDataWithDescriptions;

/**
 * MessageDestinationReferenceMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class MessageDestinationReferenceMetaData extends ResourceInjectionMetaDataWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 2129990191983873784L;

    /**
     * The type
     */
    private String type;

    /**
     * The usage
     */
    private MessageDestinationUsageType usage;

    /**
     * The link
     */
    private String link;

    /**
     * Create a new MessageDestinationReferenceMetaData.
     */
    public MessageDestinationReferenceMetaData() {
        // For serialization
    }

    /**
     * Get the messageDestinationRefName.
     *
     * @return the messageDestinationRefName.
     */
    public String getMessageDestinationRefName() {
        return getName();
    }

    /**
     * Set the messageDestinationRefName.
     *
     * @param messageDestinationRefName the messageDestinationRefName.
     * @throws IllegalArgumentException for a null messageDestinationRefName
     */
    public void setMessageDestinationRefName(String messageDestinationRefName) {
        setName(messageDestinationRefName);
    }

    /**
     * Get the type.
     *
     * @return the type.
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type.
     *
     * @param type the type.
     * @throws IllegalArgumentException for a null type
     */
    public void setType(String type) {
        if (type == null)
            throw new IllegalArgumentException("Null type");
        this.type = type;
    }

    /**
     * Get the usage.
     *
     * @return the usage.
     */
    public MessageDestinationUsageType getMessageDestinationUsage() {
        return usage;
    }

    /**
     * Set the usage.
     *
     * @param usage the usage.
     * @throws IllegalArgumentException for a null usage
     */
    public void setMessageDestinationUsage(MessageDestinationUsageType usage) {
        if (usage == null)
            throw new IllegalArgumentException("Null usage");
        this.usage = usage;
    }

    /**
     * Get the link.
     *
     * @return the link.
     */
    public String getLink() {
        return link;
    }

    /**
     * Set the link.
     *
     * @param link the link.
     * @throws IllegalArgumentException for a null link
     */
    public void setLink(String link) {
        if (link == null)
            throw new IllegalArgumentException("Null link");
        this.link = link;
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("MessageDestinationReferenceMetaData{");
        tmp.append("name=");
        tmp.append(getMessageDestinationRefName());
        tmp.append(",type=");
        tmp.append(getType());
        tmp.append(",link=");
        tmp.append(getLink());
        tmp.append(",ignore-dependecy=");
        tmp.append(super.isDependencyIgnored());
        tmp.append(",jndi-name=");
        tmp.append(super.getJndiName());
        tmp.append(",resolvoed-jndi-name=");
        tmp.append(super.getResolvedJndiName());
        tmp.append(",usage=");
        tmp.append(getMessageDestinationUsage());
        tmp.append('}');
        return tmp.toString();
    }
}
