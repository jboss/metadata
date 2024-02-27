/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.io.Serializable;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class AsyncMethodMetaData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String methodName;
    private MethodParametersMetaData methodParams;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public MethodParametersMetaData getMethodParams() {
        return methodParams;
    }

    public void setMethodParams(MethodParametersMetaData methodParams) {
        this.methodParams = methodParams;
    }
}
