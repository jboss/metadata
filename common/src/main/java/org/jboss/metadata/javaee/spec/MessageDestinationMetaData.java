/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptionGroup;

/**
 * MessageDestinationMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class MessageDestinationMetaData extends NamedMetaDataWithDescriptionGroup {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 2129990191983873784L;

    /**
     * The mapped name
     */
    private String mappedName;
    /**
     * The lookup name
     */
    private String lookupName;

    /**
     * Create a new MessageDestinationMetaData.
     */
    public MessageDestinationMetaData() {
        // For serialization
    }

    /**
     * Get the messageDestinationName.
     *
     * @return the messageDestinationName.
     */
    public String getMessageDestinationName() {
        return getName();
    }

    /**
     * Set the messageDestinationName.
     *
     * @param messageDestinationName the messageDestinationName.
     * @throws IllegalArgumentException for a null messageDestinationName
     */
    public void setMessageDestinationName(String messageDestinationName) {
        setName(messageDestinationName);
    }

    /**
     * Get the mappedName.
     *
     * @return the mappedName.
     */
    public String getMappedName() {
        return mappedName;
    }

    /**
     * Set the mappedName.
     *
     * @param mappedName the mappedName.
     * @throws IllegalArgumentException for a null mappedName
     */
    public void setMappedName(String mappedName) {
        if (mappedName == null)
            throw new IllegalArgumentException("Null mappedName");
        this.mappedName = mappedName;
    }

    /**
     * Get the jndiName.
     *
     * @return the jndiName.
     */
    public String getJndiName() {
        return getMappedName();
    }

    /**
     * Set the jndiName.
     *
     * @param jndiName the jndiName.
     * @throws IllegalArgumentException for a null jndiName
     */
    public void setJndiName(String jndiName) {
        setMappedName(jndiName);
    }

    /**
     * Get the lookupName.
     *
     * @return the lookupName.
     */
    public String getLookupName() {
        return lookupName;
    }

    /**
     * Set the lookupName.
     *
     * @param lookupName the lookupName.
     */
    public void setLookupName(String lookupName) {
        this.lookupName = lookupName;
    }
}
