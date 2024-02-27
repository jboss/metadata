/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class ListenerMetaData extends IdMetaDataImplWithDescriptionGroup {
    private static final long serialVersionUID = 1;

    private String listenerClass;

    public String getListenerClass() {
        return listenerClass;
    }

    public void setListenerClass(String listenerClass) {
        this.listenerClass = listenerClass;
    }

    public String toString() {
        StringBuilder tmp = new StringBuilder("ListenerMetaData(id=");
        tmp.append(getId());
        tmp.append(",listenerClass=");
        tmp.append(listenerClass);
        tmp.append(')');
        return tmp.toString();
    }
}
