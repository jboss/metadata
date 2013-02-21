/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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
