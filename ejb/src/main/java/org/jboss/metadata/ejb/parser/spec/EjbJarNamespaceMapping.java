/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.parser.spec;

import java.util.HashMap;
import java.util.Map;

import org.jboss.metadata.ejb.spec.EjbJarVersion;

/**
 * Maps a dtd/xsd namespace with a specific version of EJB spec
 * <p/>
 * User: Jaikiran Pai
 */
public class EjbJarNamespaceMapping {
    private static final Map<String, EjbJarVersion> bindings = new HashMap<String, EjbJarVersion>();

    static {
        bindings.put("http://java.sun.com/j2ee/dtds/ejb-jar_1_1.dtd", EjbJarVersion.EJB_1_1);
        bindings.put("http://java.sun.com/dtd/ejb-jar_2_0.dtd", EjbJarVersion.EJB_2_0);
        bindings.put("http://java.sun.com/xml/ns/j2ee/ejb-jar_2_1.xsd", EjbJarVersion.EJB_2_1);
        bindings.put("http://java.sun.com/xml/ns/javaee/ejb-jar_3_0.xsd", EjbJarVersion.EJB_3_0);
        bindings.put("http://java.sun.com/xml/ns/javaee/ejb-jar_3_1.xsd", EjbJarVersion.EJB_3_1);
        bindings.put("http://xmlns.jcp.org/xml/ns/javaee/ejb-jar_3_2.xsd", EjbJarVersion.EJB_3_2);
        bindings.put("https://jakarta.ee/xml/ns/jakartaee/ejb-jar_4_0.xsd", EjbJarVersion.EJB_4_0);
    }

    public static EjbJarVersion getEjbJarVersion(String namespace) {
        return bindings.get(namespace);
    }
}
