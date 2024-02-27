/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

/**
 * Models per servlet or per servlet type HTTP method constraint
 *
 * @author Remy Maucherat
 * @version $Revision: 81768 $
 */
public class HttpMethodConstraintMetaData extends HttpConstraintMetaData {
    private static final long serialVersionUID = 1;

    private String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
