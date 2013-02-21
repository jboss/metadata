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

package org.jboss.metadata.appclient.parser.spec;

import java.util.HashMap;
import java.util.Map;

/**
 * An enumeration of possible version independent XML attributes in the app client schema, by name.
 * <p/>
 */
public enum ApplicationClientAttribute {

    // always first
    UNKNOWN(null),

    ID("id"),

    METADATA_COMPLETE("metadata-complete"),

    VERSION("version"),;

    private String attributeName;

    private static final Map<String, ApplicationClientAttribute> ATTRIBUTE_MAP;

    static {
        final Map<String, ApplicationClientAttribute> map = new HashMap<String, ApplicationClientAttribute>();
        for (ApplicationClientAttribute element : values()) {
            final String name = element.getAttributeName();
            if (name != null) {
                map.put(name, element);
            }
        }
        ATTRIBUTE_MAP = map;
    }

    ApplicationClientAttribute(String name) {
        this.attributeName = name;
    }

    public static ApplicationClientAttribute forName(String localName) {
        final ApplicationClientAttribute element = ATTRIBUTE_MAP.get(localName);
        return element == null ? UNKNOWN : element;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

}
