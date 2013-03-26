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
    }

    public static EjbJarVersion getEjbJarVersion(String namespace) {
        return bindings.get(namespace);
    }
}
