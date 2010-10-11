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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.AugmentableMetaData;
import org.jboss.metadata.javaee.support.MergeableMappedMetaData;
import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptionGroup;

/**
 * MessageDestinationMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name = "message-destinationType", propOrder = { "descriptionGroup", "messageDestinationName", "jndiName",
        "mappedName", "lookupName" })
public class MessageDestinationMetaData extends NamedMetaDataWithDescriptionGroup implements
        MergeableMappedMetaData<MessageDestinationMetaData>, AugmentableMetaData<MessageDestinationMetaData> {
    /** The serialVersionUID */
    private static final long serialVersionUID = 2129990191983873784L;

    /** The mapped name */
    private String mappedName;
    /** The lookup name */
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
    @XmlElement(required = false)
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
    @XmlElement(required = false)
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

    @Override
    public MessageDestinationMetaData merge(MessageDestinationMetaData original) {
        MessageDestinationMetaData merged = new MessageDestinationMetaData();
        merged.merge(this, original);
        return merged;
    }

    /**
     * Merge the contents of override with original into this.
     *
     * @param override data which overrides original
     * @param original the original data
     */
    public void merge(MessageDestinationMetaData override, MessageDestinationMetaData original) {
        super.merge(override, original);
        if (override != null && override.mappedName != null)
            setMappedName(override.mappedName);
        else if (original.mappedName != null)
            setMappedName(original.mappedName);
        if (override != null && override.lookupName != null)
            setLookupName(override.lookupName);
        else if (original.lookupName != null)
            setLookupName(original.lookupName);
    }

    @Override
    public void augment(MessageDestinationMetaData augment, MessageDestinationMetaData main, boolean resolveConflicts) {
        // Mapped name
        if (getMappedName() == null) {
            if (augment.getMappedName() != null)
                setMappedName(augment.getMappedName());
        } else if (augment.getMappedName() != null) {
            if (!resolveConflicts && !getMappedName().equals(augment.getMappedName())
                    && (main == null || main.getMappedName() == null)) {
                throw new IllegalStateException("Unresolved conflict on mapped name: " + getMappedName());
            }
        }
        // Lookup name
        if (getLookupName() == null) {
            setLookupName(augment.getLookupName());
        } else if (augment.getLookupName() != null) {
            if (!resolveConflicts && !getLookupName().equals(augment.getLookupName())
                    && (main == null || main.getLookupName() == null)) {
                throw new IllegalStateException("Unresolved conflict on lookup name: " + getLookupName());
            }
        }
    }

}
