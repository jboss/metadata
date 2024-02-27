/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import java.util.List;

/**
 * Models per servlet or per servlet type constraints
 *
 * @author Remy Maucherat
 * @version $Revision: 81768 $
 */
public class ServletSecurityMetaData extends HttpConstraintMetaData {
    private static final long serialVersionUID = 1;

    private List<HttpMethodConstraintMetaData> httpMethodConstraints;

    public List<HttpMethodConstraintMetaData> getHttpMethodConstraints() {
        return httpMethodConstraints;
    }

    public void setHttpMethodConstraints(List<HttpMethodConstraintMetaData> httpMethodConstraints) {
        this.httpMethodConstraints = httpMethodConstraints;
    }

}
