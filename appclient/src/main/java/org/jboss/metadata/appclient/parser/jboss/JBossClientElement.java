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
