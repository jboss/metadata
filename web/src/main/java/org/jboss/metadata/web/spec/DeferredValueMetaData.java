/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * The tag/attribute/deferred-method metadata
 *
 * @author Remy Maucherat
 * @version $Revision: 81768 $
 */
public class DeferredValueMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        StringBuilder tmp = new StringBuilder("DeferredValueMetaData(id=");
        tmp.append(getId());
        tmp.append(",type=");
        tmp.append(type);
        tmp.append(')');
        return tmp.toString();
    }

}
