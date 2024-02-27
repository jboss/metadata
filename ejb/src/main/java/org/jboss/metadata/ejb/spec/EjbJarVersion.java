/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
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
    EJB_3_2("3.2"),

    /**
     * 4.0 version of EJB
     */
    EJB_4_0("4.0");

    private String version;

    EjbJarVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
