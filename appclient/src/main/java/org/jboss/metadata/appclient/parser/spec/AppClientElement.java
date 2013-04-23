/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
