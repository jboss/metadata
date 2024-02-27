/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
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
