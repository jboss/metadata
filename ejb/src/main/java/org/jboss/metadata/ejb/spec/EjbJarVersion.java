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

package org.jboss.metadata.ejb.spec;

/**
 * Various ejb-jar spec versions
 * <p/>
 * User: Jaikiran Pai
 */
public enum EjbJarVersion {
    /**
     * 1.1 version of EJB
     */
    EJB_1_1("1.1"),

    /**
     * 2.0 version of EJB
     */
    EJB_2_0("2.0"),

    /**
     * 2.1 version of EJB
     */
    EJB_2_1("2.1"),

    /**
     * 3.0 version of EJB
     */
    EJB_3_0("3.0"),

    /**
     * 3.1 version of EJB
     */
    EJB_3_1("3.1"),

    /**
     * 3.2 version of EJB
     */
    EJB_3_2("3.2");
    private String version;

    EjbJarVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
