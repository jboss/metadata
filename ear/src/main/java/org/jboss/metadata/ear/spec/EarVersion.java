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

package org.jboss.metadata.ear.spec;

import java.util.HashMap;
import java.util.Map;

import org.jboss.metadata.merge.javaee.spec.JavaEEVersion;

/**
 * @author John Bailey
 */
public enum EarVersion {

    UNKNOWN(null, null, JavaEEVersion.UNKNOWN),
    APP_1_3("http://java.sun.com/dtd/application_1_3.dtd", "1.3", JavaEEVersion.UNKNOWN),
    APP_1_4("http://java.sun.com/xml/ns/j2ee/application_1_4.xsd", "1.4", JavaEEVersion.V1_4),
    APP_5_0("http://java.sun.com/xml/ns/javaee/application_5.xsd", "5.0", JavaEEVersion.V5),
    APP_6_0("http://java.sun.com/xml/ns/javaee/application_6.xsd", "6.0", JavaEEVersion.V6),
    APP_7_0("http://xmlns.jcp.org/xml/ns/javaee/application_7.xsd", "7.0", JavaEEVersion.V7);

    private static final Map<String, EarVersion> bindings = new HashMap<String, EarVersion>();

    private final String location;
    private final String version;
    private final JavaEEVersion javaEEVersion;

    EarVersion(final String location, final String version, final JavaEEVersion javaEEVersion) {
        this.location = location;
        this.version = version;
        this.javaEEVersion = javaEEVersion;
    }

    public String getVersion() {
        return version;
    }

    public JavaEEVersion getJavaEEVersion() {
        return javaEEVersion;
    }

    static {
        for (EarVersion version : values()) {
            bindings.put(version.location, version);
        }
    }

    public static EarVersion forLocation(final String location) {
        final EarVersion version = bindings.get(location);
        return version != null ? version : UNKNOWN;
    }

}
