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
public enum TransactionTimeoutElement {
    UNKNOWN(null),

    TIMEOUT("timeout"),
    UNIT("unit"),;

    /**
     * Name of the element
     */
    private final String elementName;

    /**
     * Elements map
     */
    private static final Map<String, TransactionTimeoutElement> ELEMENT_MAP;

    static {
        final Map<String, TransactionTimeoutElement> map = new HashMap<String, TransactionTimeoutElement>();
        for (TransactionTimeoutElement element : values()) {
            final String name = element.getLocalName();
            if (name != null) {
                map.put(name, element);
            }
        }
        ELEMENT_MAP = map;
    }


    /**
     * @param name
     */
    TransactionTimeoutElement(final String name) {
        this.elementName = name;
    }

    /**
     * Get the local name of this element.
     *
     * @return the local name
     */
    public String getLocalName() {
        return this.elementName;
    }

    /**
     * Returns the {@link TransactionTimeoutElement} corresponding to the passed <code>elementName</code>
     * <p/>
     * If no such element exists then {@link TransactionTimeoutElement#UNKNOWN} is returned.
     *
     * @param elementName
     * @return
     */
    public static TransactionTimeoutElement forName(String elementName) {
        final TransactionTimeoutElement element = ELEMENT_MAP.get(elementName);
        return element == null ? UNKNOWN : element;
    }
}
