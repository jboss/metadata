/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.appclient.parser.jboss;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of all XML elements that are allowed in a jboss-client.xml file
 * <p/>
 * <p/>
 *
 * @author Stuart Douglas
 */
public enum JBossClientElement {
    UNKNOWN(null),

    DEPENDS("depends"),
    JNDI_NAME("jndi-name"),
    METADATA_COMPLETE("metadata-complete"),;

    /**
     * Name of the element
     */
    private final String elementName;

    /**
     * Elements map
     */
    private static final Map<String, JBossClientElement> ELEMENT_MAP;

    static {
        final Map<String, JBossClientElement> map = new HashMap<String, JBossClientElement>();
        for (JBossClientElement element : values()) {
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
    JBossClientElement(final String name) {
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
     * Returns the {@link JBossClientElement} corresponding to the passed <code>elementName</code>
     * <p/>
     * If no such element exists then {@link JBossClientElement#UNKNOWN} is returned.
     *
     * @param elementName
     * @return
     */
    public static JBossClientElement forName(String elementName) {
        final JBossClientElement element = ELEMENT_MAP.get(elementName);
        return element == null ? UNKNOWN : element;
    }
}
