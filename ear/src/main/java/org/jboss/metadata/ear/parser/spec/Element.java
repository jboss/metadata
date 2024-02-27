/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ear.parser.spec;

import java.util.HashMap;
import java.util.Map;

/**
 * @author John Bailey
 */
public enum Element {
    // must be first
    UNKNOWN(null),
    ALT_DD("alt-dd"),
    APPLICATION_NAME("application-name"),
    CONNECTOR("connector"),
    CONTEXT_ROOT("context-root"),
    EJB("ejb"),
    INITIALIZATION_IN_ORDER("initialize-in-order"),
    JAVA("java"),
    LIBRARY_DIRECTORY("library-directory"),
    MESSAGE_DESTINATION("message-destination"),
    MODULE("module"),
    SECURITY_ROLE("security-role"),
    WEB("web"),
    WEB_URI("web-uri");

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
