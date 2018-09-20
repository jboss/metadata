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

package org.jboss.metadata.ear.parser.jboss;

import java.util.HashMap;
import java.util.Map;

/**
 * @author John Bailey
 */
public enum Version {
    UNKNOWN(null, null),
    APP_3_0("http://www.jboss.org/j2ee/dtd/jboss-app_3_0.dtd", "3.0"),
    APP_3_2("http://www.jboss.org/j2ee/dtd/jboss-app_3_2.dtd", "3.2"),
    APP_4_0("http://www.jboss.org/j2ee/dtd/jboss-app_4_0.dtd", "4.0"),
    APP_4_2("http://www.jboss.org/j2ee/dtd/jboss-app_4_2.dtd", "4.2"),
    APP_5_0("http://www.jboss.org/j2ee/dtd/jboss-app_5_0.dtd", "5.0"),
    APP_7_0("http://www.jboss.org/j2ee/schema/jboss-app_7_1.xsd", "7.0");

    private static final Map<String, Version> bindings = new HashMap<String, Version>();

    private final String location;
    private final String version;

    Version(final String location, final String version) {
        this.location = location;
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    static {
        for (Version version : values()) {
            bindings.put(version.location, version);
        }
    }

    public static Version forLocation(final String location) {
        final Version version = bindings.get(location);
        return version != null ? version : UNKNOWN;
    }
}
