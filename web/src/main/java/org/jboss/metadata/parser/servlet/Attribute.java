/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import java.util.HashMap;
import java.util.Map;

/**
 * An enumeration of all the possible XML attributes in the web-app 3.0 schema, by name.
 *
 * @author Remy Maucherat
 */
public enum Attribute {
    // always first
    UNKNOWN(null),

    // web-app 3.0 attributes in alpha order
    ID("id"),

    METADATA_COMPLETE("metadata-complete"),

    VERSION("version"),;

    private final String name;

    Attribute(final String name) {
        this.name = name;
    }

    /**
     * Get the local name of this element.
     *
     * @return the local name
     */
    public String getLocalName() {
        return name;
    }

    private static final Map<String, Attribute> MAP;

    static {
        final Map<String, Attribute> map = new HashMap<String, Attribute>();
        for (Attribute element : values()) {
            final String name = element.getLocalName();
            if (name != null) map.put(name, element);
        }
        MAP = map;
    }

    public static Attribute forName(String localName) {
        final Attribute element = MAP.get(localName);
        return element == null ? UNKNOWN : element;
    }
}
