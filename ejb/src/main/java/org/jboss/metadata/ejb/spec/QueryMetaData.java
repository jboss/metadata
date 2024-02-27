/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * QueryMetaData.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
public class QueryMetaData extends IdMetaDataImplWithDescriptions {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -8080829946285127796L;

    /**
     * The query method
     */
    private NamedMethodMetaData queryMethod;

    /**
     * The result type mapping
     */
    private ResultTypeMapping resultTypeMapping = ResultTypeMapping.Local;

    /**
     * The ejb ql
     */
    private String ejbQL;

    /**
     * Create a new MethodPermissionMetaData.
     */
    public QueryMetaData() {
        // For serialization
    }

    /**
     * Get the ejbQL.
     *
     * @return the ejbQL.
     */
    public String getEjbQL() {
        return ejbQL;
    }

    /**
     * Set the ejbQL.
     *
     * @param ejbQL the ejbQL.
     * @throws IllegalArgumentException for a null ejbQL
     */
    public void setEjbQL(String ejbQL) {
        if (ejbQL == null)
            throw new IllegalArgumentException("Null ejbQL");
        this.ejbQL = ejbQL;
    }

    /**
     * Get the queryMethod.
     *
     * @return the queryMethod.
     */
    public NamedMethodMetaData getQueryMethod() {
        return queryMethod;
    }

    /**
     * Set the queryMethod.
     *
     * @param queryMethod the queryMethod.
     * @throws IllegalArgumentException for a null queryMethod
     */
    public void setQueryMethod(NamedMethodMetaData queryMethod) {
        if (queryMethod == null)
            throw new IllegalArgumentException("Null queryMethod");
        this.queryMethod = queryMethod;
    }

    /**
     * Get the resultTypeMapping.
     *
     * @return the resultTypeMapping.
     */
    public ResultTypeMapping getResultTypeMapping() {
        return resultTypeMapping;
    }

    /**
     * Set the resultTypeMapping.
     *
     * @param resultTypeMapping the resultTypeMapping.
     * @throws IllegalArgumentException for a null resultTypeMapping
     */
    public void setResultTypeMapping(ResultTypeMapping resultTypeMapping) {
        if (resultTypeMapping == null)
            throw new IllegalArgumentException("Null resultTypeMapping");
        this.resultTypeMapping = resultTypeMapping;
    }
}
