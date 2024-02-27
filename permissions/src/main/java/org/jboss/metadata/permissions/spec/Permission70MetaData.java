/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.permissions.spec;

import java.io.Serializable;

/**
 * Metadata for permission elements on Java EE 7 permissions.xml
 *
 * @author Eduardo Martins
 *
 */
public class Permission70MetaData implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5660157849775509141L;

    /**
     *
     */
    private String className;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String actions;

    /**
     *
     * @return
     */
    public String getClassName() {
        return className;
    }

    /**
     *
     * @param className
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getActions() {
        return actions;
    }

    /**
     *
     * @param actions
     */
    public void setActions(String actions) {
        this.actions = actions;
    }

}
