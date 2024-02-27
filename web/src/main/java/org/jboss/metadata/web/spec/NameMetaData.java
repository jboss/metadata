/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class NameMetaData extends OrderingElementMetaData {
    private static final long serialVersionUID = 1;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOthers() {
        return false;
    }

}
