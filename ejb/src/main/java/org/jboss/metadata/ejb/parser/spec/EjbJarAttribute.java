/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.parser.spec;

import java.util.HashMap;
import java.util.Map;

/**
 * An enumeration of possible version independent XML attributes in the ejb-jar schema, by name.
 * <p/>
 * User: Jaikiran Pai
 */
public enum EjbJarAttribute {

    // always first
    UNKNOWN(null),

    ID("id"),

    METADATA_COMPLETE("metadata-complete"),

    VERSION("version"),;

    private String attributeName;

    private static final Map<String, EjbJarAttribute> ATTRIBUTE_MAP;

    static {
        final Map<String, EjbJarAttribute> map = new HashMap<String, EjbJarAttribute>();
        for (EjbJarAttribute element : values()) {
            final String name = element.getAttributeName();
            if (name != null) {
                map.put(name, element);
            }
        }
        ATTRIBUTE_MAP = map;
    }

    EjbJarAttribute(String name) {
        this.attributeName = name;
    }

    public static EjbJarAttribute forName(String localName) {
        final EjbJarAttribute element = ATTRIBUTE_MAP.get(localName);
        return element == null ? UNKNOWN : element;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

}
