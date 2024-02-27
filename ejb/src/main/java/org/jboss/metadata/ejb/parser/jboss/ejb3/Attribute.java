/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.jboss.ejb3;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public enum Attribute {
    // always first
    UNKNOWN(null),
    IMPL_VERSION("impl-version"),;

    private String attributeName;

    private static final Map<String, Attribute> ATTRIBUTE_MAP;

    static {
        final Map<String, Attribute> map = new HashMap<String, Attribute>();
        for (Attribute element : values()) {
            final String name = element.getAttributeName();
            if (name != null) {
                map.put(name, element);
            }
        }
        ATTRIBUTE_MAP = map;
    }

    Attribute(String name) {
        this.attributeName = name;
    }

    public static Attribute forName(String localName) {
        final Attribute element = ATTRIBUTE_MAP.get(localName);
        return element == null ? UNKNOWN : element;
    }

    public String getAttributeName() {
        return this.attributeName;
    }
}
