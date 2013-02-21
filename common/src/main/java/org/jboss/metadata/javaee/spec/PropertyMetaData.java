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

import org.jboss.metadata.javaee.support.NamedMetaData;

/**
 * PropertyMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class PropertyMetaData extends NamedMetaData {

    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -5215106879053864009L;
    /**
     * The value
     */
    private String value;

    /**
     * Create a new PropertyMetaData.
     */
    public PropertyMetaData() {
        // For serialization
    }

    /**
     * Get the value.
     *
     * @return the value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the value.
     *
     * @param value the value.
     * @throws IllegalArgumentException for a null value
     */
    public void setValue(String value) {
        if (value == null)
            throw new IllegalArgumentException("Null value");
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("{name=");
        tmp.append(super.getName());
        tmp.append(",value=");
        tmp.append(getValue());
        tmp.append('}');
        return tmp.toString();
    }
}
