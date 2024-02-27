/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.permissions.parser.spec;

import java.util.HashMap;
import java.util.Map;

/**
 * An enumeration of all the possible XML elements in the EE schema, by name.
 *
 * @author Eduardo Martins
 */
public enum Element {

    // must be first
    UNKNOWN(null),

    // actual elements ordered by name
    ACTIONS("actions"),
    CLASS_NAME("class-name"),
    NAME("name"),
    PERMISSION("permission"),
    PERMISSIONS("permissions");

    private final String name;

    Element(final String name) {
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

    private static final Map<String, Element> MAP;

    static {
        final Map<String, Element> map = new HashMap<String, Element>();
        for (Element element : values()) {
            final String name = element.getLocalName();
            if (name != null) map.put(name, element);
        }
        MAP = map;
    }

    public static Element forName(String localName) {
        final Element element = MAP.get(localName);
        return element == null ? UNKNOWN : element;
    }
}
