/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.jboss.ejb3;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public enum Element {
    // must be first
    UNKNOWN(null),
    ASSEMBLY_DESCRIPTOR("assembly-descriptor"),
    DISTINCT_NAME("distinct-name"),
    EJB("ejb"),
    ENTERPRISE_BEANS("enterprise-beans"),
    ACTIVATION_CONFIG("activation-config");

    private final String name;

    Element(String name) {
        this.name = name;
    }

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

    public static Element forName(final String localName) {
        final Element element = MAP.get(localName);
        return element == null ? UNKNOWN : element;
    }
}
