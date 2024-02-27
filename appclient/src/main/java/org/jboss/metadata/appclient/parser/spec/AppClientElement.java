/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.appclient.parser.spec;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of all XML elements that are allowed in a application-client.xml file
 * <p/>
 * <p/>
 *
 * @author Stuart Douglas
 */
public enum AppClientElement {
    UNKNOWN(null),

    CALLBACK_HANDLER("callback-handler"),
    MODULE_NAME("module-name"),
    MESSAGE_DESTINATION("message-destination");

    /**
     * Name of the element
     */
    private final String elementName;

    /**
     * Elements map
     */
    private static final Map<String, AppClientElement> ELEMENT_MAP;

    static {
        final Map<String, AppClientElement> map = new HashMap<String, AppClientElement>();
        for (AppClientElement element : values()) {
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
    AppClientElement(final String name) {
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
     * Returns the {@link org.jboss.metadata.appclient.parser.spec.AppClientElement} corresponding to the passed <code>elementName</code>
     * <p/>
     * If no such element exists then {@link org.jboss.metadata.appclient.parser.spec.AppClientElement#UNKNOWN} is returned.
     *
     * @param elementName
     * @return
     */
    public static AppClientElement forName(String elementName) {
        final AppClientElement element = ELEMENT_MAP.get(elementName);
        return element == null ? UNKNOWN : element;
    }
}
