/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ear.parser.spec;

import java.util.HashMap;
import java.util.Map;

/**
 * An enumeration of all the possible XML attributes in the EE schema, by name.
 *
 * @author Remy Maucherat
 */
public enum Attribute {
    // always first
    UNKNOWN(null),

    // javaee 6 attributes in alpha order
    ID("id"),

    LANG("lang"),

    VERSION("version");

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
