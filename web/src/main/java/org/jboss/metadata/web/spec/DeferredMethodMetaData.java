/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * The tag/attribute/deferred-value metadata
 *
 * @author Remy Maucherat
 * @version $Revision: 81768 $
 */
public class DeferredMethodMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;

    private String methodSignature;

    public String getMethodSignature() {
        return methodSignature;
    }

    public void setMethodSignature(String methodSignature) {
        this.methodSignature = methodSignature;
    }

    public String toString() {
        StringBuilder tmp = new StringBuilder("DeferredMethodMetaData(id=");
        tmp.append(getId());
        tmp.append(",methodSignature=");
        tmp.append(methodSignature);
        tmp.append(')');
        return tmp.toString();
    }

}
