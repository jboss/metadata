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

package org.jboss.metadata.parser.jbossweb;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Version {
    JBOSS_WEB_3_0("http://www.jboss.org/j2ee/dtd/jboss-web_3_0.dtd"),
    JBOSS_WEB_3_2("http://www.jboss.org/j2ee/dtd/jboss-web_3_2.dtd"),
    JBOSS_WEB_4_0("http://www.jboss.org/j2ee/dtd/jboss-web_4_0.dtd"),
    JBOSS_WEB_4_2("http://www.jboss.org/j2ee/dtd/jboss-web_4_2.dtd"),
    JBOSS_WEB_5_0("http://www.jboss.org/j2ee/dtd/jboss-web_5_0.dtd"),
    JBOSS_WEB_5_1(5, 1),
    JBOSS_WEB_6_0(6, 0),
    JBOSS_WEB_7_0(7, 0),
    JBOSS_WEB_7_1(7, 1),
    JBOSS_WEB_7_2(7, 2),
    JBOSS_WEB_8_0(8, 0),
    JBOSS_WEB_10_0(10, 0),
    JBOSS_WEB_11_0(11, 0),
    JBOSS_WEB_12_0(12, 0),
    JBOSS_WEB_12_1(12, 1),
    JBOSS_WEB_13_0(13, 0),
    JBOSS_WEB_14_0(14, 0),
    JBOSS_WEB_14_1(14, 1),
    ;
    private final String location;

    Version(int major, int minor) {
        this(String.format("http://www.jboss.org/j2ee/schema/jboss-web_%d_%d.xsd", major, minor));
    }

    Version(String location) {
        this.location = location;
    }

    private static final Map<String, Version> versions = new HashMap<>();
    static {
        for (Version version : EnumSet.allOf(Version.class)) {
            versions.put(version.location, version);
        }
    }

    public static Version findVersion(String location) {
        return versions.get(location);
    }
}
