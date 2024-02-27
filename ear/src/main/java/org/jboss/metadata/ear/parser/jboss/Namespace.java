/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ear.parser.jboss;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public enum Namespace {
    // must be first
    UNKNOWN(null),
    JBOSS("http://www.jboss.com/xml/ns/javaee"),
    JBOSS_JAKARTA("urn:jboss:jakartaee:1.0"),
    SPEC("http://java.sun.com/xml/ns/javaee"),
    SPEC_7_0("http://xmlns.jcp.org/xml/ns/javaee"),
    JAKARTAEE("https://jakarta.ee/xml/ns/jakartaee");

    private final String uri;

    Namespace(String uri) {
        this.uri = uri;
    }

    public String getUriString() {
        return uri;
    }

    private static final Map<String, Namespace> MAP;

    static {
        final Map<String, Namespace> map = new HashMap<String, Namespace>();
        for (Namespace namespace : values()) {
            final String name = namespace.getUriString();
            if (name != null) map.put(name, namespace);
        }
        MAP = map;
    }

    public static Namespace forUri(String uri) {
        final Namespace element = MAP.get(uri);
        return element == null ? UNKNOWN : element;
    }
}
