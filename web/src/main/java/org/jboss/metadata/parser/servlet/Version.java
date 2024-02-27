/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import java.util.HashMap;

public enum Version {

    SERVLET_2_2("2.2"), SERVLET_2_3("2.3"), SERVLET_2_4("2.4"), SERVLET_2_5("2.5"), SERVLET_3_0("3.0"), SERVLET_3_1("3.1"), SERVLET_4_0("4.0"), SERVLET_5_0("5.0"), SERVLET_6_0("6.0");

    private static final HashMap<String, Version> systemIDmap = new HashMap<String, Version>();

    static {
        systemIDmap.put("http://java.sun.com/j2ee/dtds/web-app_2_2.dtd", Version.SERVLET_2_2);
        systemIDmap.put("http://java.sun.com/dtd/web-app_2_3.dtd", Version.SERVLET_2_3);
        systemIDmap.put("http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd", Version.SERVLET_2_4);
        systemIDmap.put("http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd", Version.SERVLET_2_5);
        systemIDmap.put("http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd", Version.SERVLET_3_0);
        systemIDmap.put("http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd", Version.SERVLET_3_1);
        systemIDmap.put("http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd", Version.SERVLET_4_0);
        systemIDmap.put("https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd", Version.SERVLET_5_0);
        systemIDmap.put("https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd", Version.SERVLET_6_0);
    }

    private static final HashMap<String, Version> publicIDmap = new HashMap<String, Version>();

    static {
        publicIDmap.put("-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN", Version.SERVLET_2_2);
        publicIDmap.put("-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN", Version.SERVLET_2_3);
    }

    Version(String version) {
        this.version = version;
    }

    public static Version fromSystemID(String systemID) {
        return systemIDmap.get(systemID);
    }

    public static Version fromPublicID(String publicID) {
        return publicIDmap.get(publicID);
    }

    private final String version;

    public String versionString() {
        return version;
    }
}
