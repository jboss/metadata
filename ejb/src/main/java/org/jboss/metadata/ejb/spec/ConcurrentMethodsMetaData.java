/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

import java.util.ArrayList;

import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.merge.MergeUtil;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class ConcurrentMethodsMetaData extends ArrayList<ConcurrentMethodMetaData> implements IdMetaData {
    private static final long serialVersionUID = 1L;

    private String id;

    public ConcurrentMethodMetaData bestMatch(String methodName, String[] params) {
        ConcurrentMethodMetaData bestMatch = null;
        for (ConcurrentMethodMetaData method : this) {
            if (method.matches(methodName, params)) {
                // No previous best match
                if (bestMatch == null)
                    bestMatch = method;
                    // better match because the previous was a wildcard
                else if ("*".equals(bestMatch.getMethod().getMethodName()))
                    bestMatch = method;
                    // better because it specifies parameters
                else if (method.getMethod().getMethodParams() != null)
                    bestMatch = method;
            }
        }
        return bestMatch;
    }

    public ConcurrentMethodMetaData find(NamedMethodMetaData equivalent) {
        for (ConcurrentMethodMetaData method : this) {
            if (method.getMethod().equals(equivalent)) {
                return method;
            }
        }
        return null;
    }

    @Deprecated
    public ConcurrentMethodMetaData get(NamedMethodMetaData key) {
        return find(key);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public void merge(ConcurrentMethodsMetaData override, ConcurrentMethodsMetaData original) {
        MergeUtil.merge(this, override, original);
    }
}
