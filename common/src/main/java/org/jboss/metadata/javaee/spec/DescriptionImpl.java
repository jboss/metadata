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

import org.jboss.annotation.javaee.Description;
import org.jboss.metadata.javaee.support.LanguageMetaData;

/**
 * DescriptionImpl.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class DescriptionImpl extends LanguageMetaData implements Description {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 7060589158592181267L;

    /**
     * The description
     */
    private String description = "";

    /**
     * Create a new DescriptionImpl.
     */
    public DescriptionImpl() {
        super(Description.class);
    }

    @Override
    public String value() {
        return getDescription();
    }

    /**
     * Get the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description.
     *
     * @param description the description.
     * @throws IllegalArgumentException for a null description
     */
    public void setDescription(String description) {
        if (description == null)
            throw new IllegalArgumentException("Null description");
        this.description = description;
    }
}
